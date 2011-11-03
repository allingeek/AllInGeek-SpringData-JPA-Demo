package com.allingeek.demo.springdata.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.allingeek.demo.springdata.domain.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
