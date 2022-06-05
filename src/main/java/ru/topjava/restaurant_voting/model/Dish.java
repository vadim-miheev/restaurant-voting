package ru.topjava.restaurant_voting.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Getter
@Setter
@Entity
@Table(name = "dishes")
public class Dish extends BaseEntity {
    @Length(max = 128)
    @NotBlank
    @Column(nullable = false, length = 128)
    private String name;

    @Column(nullable = false)
    @PositiveOrZero
    @NotNull
    private Long priceInCents;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false)
    private Menu menu;
}
