package ru.topjava.restaurant_voting.util;

import lombok.experimental.UtilityClass;
import ru.topjava.restaurant_voting.dto.restaurant.RestaurantWithCurrentMenu;
import ru.topjava.restaurant_voting.model.Menu;
import ru.topjava.restaurant_voting.model.Restaurant;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@UtilityClass
public class RestaurantUtil {
    public static List<RestaurantWithCurrentMenu> getTosWithAnyMenu(Collection<Restaurant> restaurants) {
        return getTosWithMenuFilter(restaurants, menu -> true);
    }

    public static List<RestaurantWithCurrentMenu> getTosWithMenuFilter(Collection<Restaurant> restaurants, Predicate<Menu> predicate) {
        return restaurants.stream()
                .filter(r -> r.getMenus().size() > 0)
                .map(r -> new RestaurantWithCurrentMenu(r.id(), r.getName(),
                        r.getMenus().stream()
                                .filter(predicate)
                                .findAny().orElseThrow()))
                .collect(Collectors.toList());
    }

    public static void updateExisting(Restaurant existing, Restaurant forUpdate) {
        if (forUpdate.isNew()) {
            forUpdate.setId(existing.id());
        } else if (Objects.requireNonNull(forUpdate.getId()) != existing.id()) {
            throw new IllegalArgumentException("Restaurant id cannot be changed");
        }
        existing.setName(forUpdate.getName());
    }
}
