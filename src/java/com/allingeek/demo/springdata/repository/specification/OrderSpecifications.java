package com.allingeek.demo.springdata.repository.specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.allingeek.demo.springdata.domain.Order;

public class OrderSpecifications {

    public static Specification<Order> orderWithEvenId() {
        return new Specification<Order>() {
            public Predicate toPredicate(Root<Order> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
              return cb.equal(cb.mod(root.get("id").as(Integer.class), cb.literal(2)), 0);
            }
        };
    }
    
    public static Specification<Order> orderWithIdEvenlyDivisibleByTen() {
        return new Specification<Order>() {
            public Predicate toPredicate(Root<Order> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
              return cb.equal(cb.mod(root.get("id").as(Integer.class), cb.literal(10)), 0);
            }
        };
    }
}
