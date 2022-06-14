package ru.topjava.restaurant_voting.web.restaurant;

import lombok.extern.slf4j.Slf4j;
import ru.topjava.restaurant_voting.to.RestaurantTo;

import java.util.List;
@Slf4j
public abstract class AbstractRestaurantController {
    List<RestaurantTo> getAllWithMenuForToday() {
        log.info("getAllWithMenuForToday");
        return null; //TODO
    }

    void vote(int id) {
        log.info("vote {}", id);
        //TODO
    }
}
