package ru.topjava.restaurant_voting.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "menus", uniqueConstraints = {
        @UniqueConstraint(name = "menus_unique_restaurant_date_idx", columnNames = {"restaurant_id", "date"})
})
public class Menu extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @NotNull
    private Restaurant restaurant;

    @Column(nullable = false)
    @NotNull
    private LocalDate date;

    @Size(min = 2, max = 20)
    @NotNull
    @OneToMany(mappedBy = "menu")
    private List<Dish> dishes = new java.util.ArrayList<>();
}
