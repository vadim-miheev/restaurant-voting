package ru.topjava.restaurant_voting.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "votes", uniqueConstraints = {
        @UniqueConstraint(name = "votes_unique_user_date_idx", columnNames = {"user_id", "date"})
})
@NoArgsConstructor
public class Vote extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    @NotNull
    @JsonIgnore
    private User user;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @NotNull
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Restaurant restaurant;

    @Column(nullable = false, updatable = false, columnDefinition = "date default CURRENT_DATE()")
    private Date date = Date.valueOf(LocalDate.now());

    public Vote(User user, Restaurant restaurant) {
        this.user = user;
        this.restaurant = restaurant;
    }

    @JsonGetter
    private Restaurant getRestaurant() {
        return this.restaurant;
    }
}
