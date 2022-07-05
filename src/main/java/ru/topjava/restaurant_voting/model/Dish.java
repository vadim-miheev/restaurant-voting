package ru.topjava.restaurant_voting.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;

@Getter
@AllArgsConstructor
public class Dish {
    @Pattern(regexp = "^[^\\t]*$", message = "The name cannot contain a tab character")
    @Length(min = 2, max = 128)
    private String name;

    @PositiveOrZero
    private Long priceInCents;
}
