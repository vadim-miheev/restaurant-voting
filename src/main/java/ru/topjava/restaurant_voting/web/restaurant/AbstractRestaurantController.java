package ru.topjava.restaurant_voting.web.restaurant;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import ru.topjava.restaurant_voting.repository.RestaurantRepository;
import ru.topjava.restaurant_voting.to.RestaurantTo;

import java.util.List;

import static ru.topjava.restaurant_voting.util.RestaurantUtil.getTos;

@Slf4j
public abstract class AbstractRestaurantController {

    @Autowired
    RestaurantRepository restaurantRepository;

    List<RestaurantTo> getAllWithMenuForToday() {
        log.info("getAllWithMenuForToday");
        return getTos(restaurantRepository.getAllWithMenuForToday());
    }
}
