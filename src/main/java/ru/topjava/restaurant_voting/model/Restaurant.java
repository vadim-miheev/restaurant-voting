package ru.topjava.restaurant_voting.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
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
    @JsonManagedReference
    @OnDelete(action = OnDeleteAction.CASCADE)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @NotNull
    //@JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<Menu> menus = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    @Schema(hidden = true)
    @NotNull
    private List<Vote> votes = new ArrayList<>();

    public Restaurant(Integer id, String name) {
        super(id);
        this.name = name;
    }

    public Restaurant addMenusAndGetInstance(List<Menu> menus) {
        this.menus = menus;
        return this;
    }
}
