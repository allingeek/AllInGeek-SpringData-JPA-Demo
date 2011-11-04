package com.allingeek.demo.springdata;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManagerFactory;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.allingeek.demo.springdata.domain.Category;
import com.allingeek.demo.springdata.domain.Drink;
import com.allingeek.demo.springdata.repository.CategoryRepository;
import com.allingeek.demo.springdata.repository.DrinkRepository;
import com.allingeek.demo.springdata.repository.OrderRepository;
import com.allingeek.demo.springdata.repository.specification.DrinkSpecifications;
import com.allingeek.demo.springdata.task.DrinkTasks;

public class TheBar {
    private static Logger logger = LoggerFactory.getLogger(TheBar.class);
    
    @Autowired
    private EntityManagerFactory emf;
    @Autowired
    private DrinkRepository drinkRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    
    public void coolStuff() throws IllegalAccessException, InvocationTargetException, IOException {
        // Lets add a new drink that we define
        Drink someNewDrink = new Drink();
        this.metamodelExploration(Drink.class, someNewDrink);
        drinkRepository.save(someNewDrink);
        
        // Lets list all of the drinks
        List<Drink> drinks = drinkRepository.findAll();
        String ourDrinks = DrinkTasks.listDrinks(drinks);
        System.out.println(String.format("=================\nAll our drinks: \n%s", ourDrinks));
        
        // Lets list all of the drinks that take a while to prep
        drinks = drinkRepository.findAll(DrinkSpecifications.takesLongToPrepare());
        String drinksThatTakeTime = DrinkTasks.listDrinks(drinks);
        System.out.println(String.format("=================\nAll our drinks that take forever: \n%s", drinksThatTakeTime));

        List<Category> categories = categoryRepository.findAll();
        if (!categories.isEmpty()) {
            Category category = categories.get(0);
            drinks = drinkRepository.findAllByCategory(category);
            String drinksInASpecificCategory = DrinkTasks.listDrinks(drinks);
            System.out.println(String.format("=================\nAll our drinks in the %s category: \n%s", category.getName(), drinksInASpecificCategory));
        } else {
            logger.error("There are no categories");
        }
    }
    
    public void populateDrinks() {
        Category carribian = new Category();
        carribian.setName("Carribian");
        categoryRepository.save(carribian);
        
        Category beer = new Category();
        beer.setName("Beer");
        categoryRepository.save(beer);
        
        DrinkTasks.createDrink(drinkRepository, "DarkAndStormy", 3000, "$8.50", carribian);
        DrinkTasks.createDrink(drinkRepository, "Mojito", 20000, "$10.50", carribian);
        DrinkTasks.createDrink(drinkRepository, "IPA", 2000, "$5.50", beer);
        DrinkTasks.createDrink(drinkRepository, "Porter", 2000, "$4.50", beer);
        DrinkTasks.createDrink(drinkRepository, "Pale Ale", 2000, "$4.50", beer);
    }
    
    private <T> T metamodelExploration(Class<T> clazz, T entity) throws IllegalAccessException, InvocationTargetException, IOException {
        Metamodel m = emf.getMetamodel();
        EntityType<T> dMM = m.entity(clazz);
        Set<Attribute<T,?>> attrs = dMM.getDeclaredAttributes();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Class type;
        for(Attribute<T,?> attribute : attrs) {
            logger.debug(String.format("Attribute: %s (%s)", attribute.getName(), attribute.getJavaType()));
            System.out.print("Value: ");
            type = attribute.getJavaType();
            if(type.equals(String.class)) {
                BeanUtils.setProperty(entity, attribute.getName(), reader.readLine());
            } else if(type.equals(Integer.class)) {
                BeanUtils.setProperty(entity, attribute.getName(), Integer.parseInt(reader.readLine()));
            }
        }
        return entity;
    }
    
    public static void main(String[] args) throws IllegalAccessException, InvocationTargetException, IOException {
        logger.info("Creating context.");
        ApplicationContext ctx = new ClassPathXmlApplicationContext(new String[] {"context.xml"});
        logger.info("Context created.");
        TheBar bar = ctx.getBean(TheBar.class);
        
        bar.populateDrinks();
        bar.coolStuff();
    }

}
