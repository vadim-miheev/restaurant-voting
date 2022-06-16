package ru.topjava.restaurant_voting.util;

import ru.topjava.restaurant_voting.model.Restaurant;
import ru.topjava.restaurant_voting.to.RestaurantTo;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class RestaurantUtil {
    public static List<RestaurantTo> getTos(Collection<Restaurant> restaurants) {
        return restaurants.stream()
                .filter(r -> r.getMenus().size() == 1)
                .map(r -> new RestaurantTo(r.id(), r.getName(), r.getMenus().get(0)))
                .collect(Collectors.toList());
    }
}
