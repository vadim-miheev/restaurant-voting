package ru.topjava.restaurant_voting.web.restaurant;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import ru.topjava.restaurant_voting.dto.RestaurantTo;
import ru.topjava.restaurant_voting.repository.RestaurantRepository;

import java.util.List;

import static ru.topjava.restaurant_voting.util.RestaurantUtil.getTosWithAnyMenu;

@Slf4j
public abstract class AbstractRestaurantController {

    @Autowired
    RestaurantRepository restaurantRepository;

    /** @return All Restaurants that have a menu for today */
    List<RestaurantTo> getAllWithMenuForToday() {
        log.info("getAllWithMenuForToday");
        return getTosWithAnyMenu(restaurantRepository.getAllWithMenuForToday());
    }
}
