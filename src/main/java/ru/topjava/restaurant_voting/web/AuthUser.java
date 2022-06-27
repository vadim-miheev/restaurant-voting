package ru.topjava.restaurant_voting.web;

import lombok.Getter;
import lombok.ToString;
import org.springframework.lang.NonNull;
import ru.topjava.restaurant_voting.model.User;

@Getter
@ToString(of = "user")
public class AuthUser extends org.springframework.security.core.userdetails.User {

    private final User user;

    public AuthUser(@NonNull User user) {
        super(user.getLogin(), user.getPassword(), user.getRoles());
        this.user = user;
    }

    public int id() {
        return user.id();
    }
}