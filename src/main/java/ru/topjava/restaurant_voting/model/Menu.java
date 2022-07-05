package ru.topjava.restaurant_voting.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;
import ru.topjava.restaurant_voting.model.converter.DishesListConverter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "menus", uniqueConstraints = {
        @UniqueConstraint(name = "menus_unique_restaurant_date_idx", columnNames = {"restaurant_id", "date"})
})
public class Menu extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "restaurant_id", nullable = false, updatable = false)
    @NotNull
    @JsonBackReference
    private Restaurant restaurant;

    @Column(nullable = false, updatable = false)
    @NotNull
    private Date date;

    @Size(min = 2, max = 20)
    @NotNull
    @Convert(converter = DishesListConverter.class)
    private List<Dish> dishes = new ArrayList<>();
}
