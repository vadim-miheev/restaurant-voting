package ru.topjava.restaurant_voting.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.topjava.restaurant_voting.model.Restaurant;

import java.util.List;
import java.util.Optional;

@Repository
public interface RestaurantRepository extends BaseRepository<Restaurant> {
    @Query("SELECT r FROM Restaurant r LEFT JOIN FETCH r.menus m")
    List<Restaurant> getAllWithMenus();

    @Query("SELECT r FROM Restaurant r LEFT JOIN FETCH r.menus m WHERE m.date = CURRENT_DATE()")
    List<Restaurant> getAllWithMenuForToday();

    @Query("SELECT r FROM Restaurant r LEFT JOIN FETCH r.menus m WHERE r.id = :restaurantId")
    Optional<Restaurant> getWithMenus(int restaurantId);
}
