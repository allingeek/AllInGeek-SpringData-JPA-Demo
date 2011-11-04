package com.allingeek.demo.springdata.task;

import java.util.List;

import com.allingeek.demo.springdata.domain.Category;
import com.allingeek.demo.springdata.domain.Drink;
import com.allingeek.demo.springdata.repository.DrinkRepository;

public class DrinkTasks {
    private static final String DRINK_FORMAT = 
            "Drink(name: %s, prepTime: %s, cost: %s, category name: %s)\n";
    
    protected DrinkTasks() {}

    public static void createDrink(
            DrinkRepository repository, 
            String name, 
            Integer preparationTimeInMillis, 
            String cost, 
            Category category) {
        Drink drink = new Drink();
        drink.setName(name);
        drink.setPreparationTimeInMillis(preparationTimeInMillis);
        drink.setCost(cost);
        drink.setCategory(category);
        repository.save(drink);
    }
    
    public static String listDrinks(DrinkRepository repository) {
        return listDrinks(repository.findAll());
    }
    
    public static String listDrinks(List<Drink> drinks) {
        StringBuilder output = new StringBuilder();
        for(Drink drink : drinks) {
            output.append(String.format(
                    DRINK_FORMAT, 
                    drink.getName(), 
                    drink.getPreparationTimeInMillis(), 
                    drink.getCost(), 
                    (drink.getCategory() == null)? "Uncategorized" : drink.getCategory().getName()));
        }
        return output.toString();
    }
}
