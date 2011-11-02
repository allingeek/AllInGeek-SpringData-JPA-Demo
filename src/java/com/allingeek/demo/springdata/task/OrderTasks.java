package com.allingeek.demo.springdata.task;

import java.util.List;

import com.allingeek.demo.springdata.domain.Drink;
import com.allingeek.demo.springdata.domain.Order;
import com.allingeek.demo.springdata.repository.DrinkRepository;
import com.allingeek.demo.springdata.repository.OrderRepository;

public class OrderTasks {
    private static final String ORDER_FORMAT = 
            "===============\nOrder for customer: %s includes:\n%s";
    
    protected OrderTasks() {}
    
    public static void placeOrder(OrderRepository orderRepository, String customerName, List<Drink> drinks) {
        Order order = new Order();
        order.setCustomerName(customerName);
        order.setItems(drinks);
        orderRepository.save(order);
    }
    
    public static void orderOneOfEverything(OrderRepository orderRepository, DrinkRepository drinkRepository, String customerName) {
        List<Drink> drinks = drinkRepository.findAll();
        placeOrder(orderRepository, customerName, drinks);
    }
    
    public static String listOrders(OrderRepository repository) {
        List<Order> orders = repository.findAll();
        StringBuilder output = new StringBuilder();
        for(Order order : orders) {
            output.append(String.format(
                    ORDER_FORMAT, 
                    order.getCustomerName(), 
                    DrinkTasks.listDrinks(order.getItems())
                    ));
        }
        return output.toString();
    }
}
