package ru.topjava.restaurant_voting.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User extends BaseEntity {
    @Column(nullable = false)
    @NotBlank
    private String name;

    @Column(unique = true, nullable = false)
    @NotBlank
    private String login;

    @Column(nullable = false)
    @NotBlank
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    @CollectionTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "role"}, name = "uk_user_roles"))
    @ElementCollection(fetch = FetchType.EAGER)
    @JoinColumn(name = "id") //https://stackoverflow.com/a/62848296/548473
    private Set<Role> roles;
}
