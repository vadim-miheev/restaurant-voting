package ru.topjava.restaurant_voting.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
@NoArgsConstructor
public class Restaurant extends BaseEntity {
    @Length(min = 2, max = 128)
    @NotBlank
    @Column(nullable = false, unique = true, length = 128)
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    @Column(nullable = false)
    @NotNull
    @JsonManagedReference
    private List<Menu> menus;

    public Restaurant(Integer id, String name, List<Menu> menus) {
        super(id);
        this.name = name;
        this.menus = menus;
    }
}
