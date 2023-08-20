package ru.topjava.restaurant_voting.model;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Getter
@AllArgsConstructor
public class Dish {
    @Pattern(regexp = "^[^\\t]*$", message = "The name cannot contain a tab character")
    @Length(min = 2, max = 128)
    private String name;

    @PositiveOrZero
    private Long priceInCents;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Dish dish = (Dish) o;

        if (!name.equals(dish.name)) return false;
        return priceInCents.equals(dish.priceInCents);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + priceInCents.hashCode();
        return result;
    }
}
