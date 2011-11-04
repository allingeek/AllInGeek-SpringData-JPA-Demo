package com.allingeek.demo.springdata.repository.specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.allingeek.demo.springdata.domain.Drink;

public class DrinkSpecifications {
    public static Specification<Drink> drinkWithEvenId() {
        return new Specification<Drink>() {
            public Predicate toPredicate(Root<Drink> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
              return cb.equal(cb.mod(root.get("id").as(Integer.class), cb.literal(2)), 0);
            }
        };
    }
    
    public static Specification<Drink> takesLongToPrepare() {
        return new Specification<Drink>() {
            public Predicate toPredicate(Root<Drink> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
              return cb.greaterThan(root.get("preparationTimeInMillis").as(Integer.class), cb.literal(5000));
            }
        };
    }
}
