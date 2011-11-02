package com.allingeek.demo.springdata.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;

@Entity
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String customerName;
    @OneToMany
    @JoinTable(name = "order_drink", joinColumns = @JoinColumn(name = "order_id"), inverseJoinColumns = @JoinColumn(name = "drink_id"))
    private List<Drink> items;
    
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getCustomerName() {
        return customerName;
    }
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    public List<Drink> getItems() {
        return items;
    }
    public void setItems(List<Drink> items) {
        this.items = items;
    }
}
