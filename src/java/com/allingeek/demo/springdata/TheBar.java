package com.allingeek.demo.springdata;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.allingeek.demo.springdata.domain.Category;
import com.allingeek.demo.springdata.repository.CategoryRepository;
import com.allingeek.demo.springdata.repository.DrinkRepository;
import com.allingeek.demo.springdata.repository.OrderRepository;
import com.allingeek.demo.springdata.task.DrinkTasks;

public class TheBar {
    private static Logger logger = LoggerFactory.getLogger(TheBar.class);
    
    private ApplicationContext ctx;
    private DrinkRepository drinkRepository;
    private OrderRepository orderRepository;
    private CategoryRepository categoryRepository;
    
    public TheBar() {
        logger.info("Creating context.");
        this.ctx = new ClassPathXmlApplicationContext(new String[] {"context.xml"});
        this.drinkRepository = this.ctx.getBean(DrinkRepository.class);
        this.orderRepository = this.ctx.getBean(OrderRepository.class);
        this.categoryRepository = this.ctx.getBean(CategoryRepository.class);
        logger.info("Context created.");
    }
    
    public void populateDrinks() {
        Category carribian = new Category();
        carribian.setName("Carribian");
        this.categoryRepository.save(carribian);
        DrinkTasks.createDrink(drinkRepository, "DarkAndStormy", 10000, "$8.50", carribian);
        DrinkTasks.createDrink(drinkRepository, "Mojito", 20000, "$10.50", carribian);
        logger.info("\n"+DrinkTasks.listDrinks(drinkRepository));
    }
    
//    private <T> void metamodelExploration(T object) {
//        EntityManager em = ctx.getBean(EntityManager.class);
//        Metamodel m = em.getMetamodel();
//        EntityType<T> dMM = m.entity(T.class);
//        Set<Attribute<Drink,?>> attrs = dMM.getDeclaredAttributes();
//        for(Attribute<Drink,?> attribute : attrs) {
//            
//        }
//    }
    
    public static void main(String[] args) {
        TheBar bar = new TheBar();
        bar.populateDrinks();
        
    }

}
