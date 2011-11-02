package com.allingeek.demo.springdata.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.allingeek.demo.springdata.domain.Category;
import com.allingeek.demo.springdata.domain.Drink;

public interface DrinkRepository extends JpaRepository<Drink, Long> {
    List<Drink> findAllByCategory(Category category);
    Drink findByName(String name);
}
