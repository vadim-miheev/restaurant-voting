package ru.topjava.restaurant_voting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.topjava.restaurant_voting.model.Menu;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Integer> {
}
