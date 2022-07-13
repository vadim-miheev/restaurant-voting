package ru.topjava.restaurant_voting.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.topjava.restaurant_voting.model.converter.DishesListConverter;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "menus", uniqueConstraints = {
        @UniqueConstraint(name = "menus_unique_restaurant_date_idx", columnNames = {"restaurant_id", "date"})
})
@NoArgsConstructor
public class Menu extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "restaurant_id", nullable = false, updatable = false)
    @NotNull
    @JsonBackReference
    private Restaurant restaurant;

    @Column(nullable = false)
    @NotNull
    private LocalDate date;

    @Size(min = 1, max = 20)
    @NotNull
    @Convert(converter = DishesListConverter.class)
    @Valid
    private List<Dish> dishes = new ArrayList<>();

    public Menu(Integer id, Restaurant restaurant, LocalDate date, List<Dish> dishes) {
        super(id);
        this.restaurant = restaurant;
        this.date = date;
        this.dishes = dishes;
    }
}
