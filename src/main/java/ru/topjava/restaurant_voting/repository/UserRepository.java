package ru.topjava.restaurant_voting.repository;

import org.springframework.stereotype.Repository;
import ru.topjava.restaurant_voting.model.User;

import java.util.Optional;

@Repository
public interface UserRepository extends BaseRepository<User> {
    Optional<User> getUserByLogin(String login);
}
