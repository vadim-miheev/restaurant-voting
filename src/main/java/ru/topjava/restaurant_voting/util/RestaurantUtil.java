package ru.topjava.restaurant_voting.util;

import lombok.experimental.UtilityClass;
import ru.topjava.restaurant_voting.model.Restaurant;
import ru.topjava.restaurant_voting.dto.RestaurantTo;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class RestaurantUtil {
    public static List<RestaurantTo> getTos(Collection<Restaurant> restaurants) {
        return restaurants.stream()
                .filter(r -> r.getMenus().size() == 1)
                .map(r -> new RestaurantTo(r.id(), r.getName(), r.getMenus().get(0)))
                .collect(Collectors.toList());
    }
}
