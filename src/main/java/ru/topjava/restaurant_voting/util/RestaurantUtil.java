package ru.topjava.restaurant_voting.util;

import lombok.experimental.UtilityClass;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.http.HttpStatus;
import ru.topjava.restaurant_voting.dto.RestaurantTo;
import ru.topjava.restaurant_voting.error.AppException;
import ru.topjava.restaurant_voting.model.Menu;
import ru.topjava.restaurant_voting.model.Restaurant;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static org.springframework.boot.web.error.ErrorAttributeOptions.Include.MESSAGE;

@UtilityClass
public class RestaurantUtil {
    public static List<RestaurantTo> getTosWithAnyMenu(Collection<Restaurant> restaurants) {
        return getTosWithMenuFilter(restaurants, menu -> true);
    }

    public static List<RestaurantTo> getTosWithMenuFilter(Collection<Restaurant> restaurants, Predicate<Menu> predicate) {
        return restaurants.stream()
                .filter(r -> r.getMenus().size() > 0)
                .map(r -> new RestaurantTo(r.id(), r.getName(),
                        r.getMenus().stream()
                                .filter(predicate)
                                .findAny().orElseThrow()))
                .collect(Collectors.toList());
    }

    public static void updateExisting(Restaurant existing, Restaurant forUpdate) {
        if (forUpdate.isNew()) {
            forUpdate.setId(existing.id());
        } else if (Objects.requireNonNull(forUpdate.getId()) != existing.id()) {
            throw new AppException(HttpStatus.UNPROCESSABLE_ENTITY, "Restaurant id cannot be changed",
                    ErrorAttributeOptions.of(MESSAGE));
        }
        existing.setName(forUpdate.getName());
    }
}
