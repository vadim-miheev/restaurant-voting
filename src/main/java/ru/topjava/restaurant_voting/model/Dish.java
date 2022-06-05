package ru.topjava.restaurant_voting.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "dishes")
public class Dish extends BaseEntity {
    private String name;
    private Long priceInCents;

    @ManyToOne(fetch = FetchType.LAZY)
    private Menu menu;
}
