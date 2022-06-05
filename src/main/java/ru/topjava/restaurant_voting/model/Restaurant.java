package ru.topjava.restaurant_voting.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "restaurants")
public class Restaurant extends BaseEntity {
    @Length(min = 2, max = 128)
    @NotBlank
    @Column(nullable = false, unique = true, length = 128)
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    @Column(nullable = false)
    @NotNull
    private List<Menu> menus;
}
