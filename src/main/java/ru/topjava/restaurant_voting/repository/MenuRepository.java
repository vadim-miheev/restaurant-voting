package ru.topjava.restaurant_voting.repository;

import org.springframework.stereotype.Repository;
import ru.topjava.restaurant_voting.model.Menu;
import ru.topjava.restaurant_voting.model.Restaurant;

import java.util.List;

@Repository
public interface MenuRepository extends BaseRepository<Menu> {
    List<Menu> getMenusByRestaurant(Restaurant restaurantId);
}
