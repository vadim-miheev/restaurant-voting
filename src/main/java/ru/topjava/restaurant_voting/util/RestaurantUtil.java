package ru.topjava.restaurant_voting.util;

import lombok.experimental.UtilityClass;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.http.HttpStatus;
import ru.topjava.restaurant_voting.dto.RestaurantTo;
import ru.topjava.restaurant_voting.error.AppException;
import ru.topjava.restaurant_voting.model.Restaurant;
import ru.topjava.restaurant_voting.repository.RestaurantRepository;

import javax.persistence.EntityNotFoundException;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.springframework.boot.web.error.ErrorAttributeOptions.Include.MESSAGE;

@UtilityClass
public class RestaurantUtil {
    public static List<RestaurantTo> getTos(Collection<Restaurant> restaurants) {
        return restaurants.stream()
                .filter(r -> r.getMenus().size() == 1)
                .map(r -> new RestaurantTo(r.id(), r.getName(), r.getMenus().get(0)))
                .collect(Collectors.toList());
    }

    public static void checkRestaurantIdBeforeUpdate(Restaurant restaurant, int id) {
        if (restaurant.isNew()) {
            restaurant.setId(id);
        } else if (Objects.requireNonNull(restaurant.getId()) != id) {
            throw new AppException(HttpStatus.UNPROCESSABLE_ENTITY, "Restaurant id cannot be changed",
                    ErrorAttributeOptions.of(MESSAGE));
        }
    }

    public static void checkRestaurantExist(RestaurantRepository restaurantRepository, int id) {
        if (!restaurantRepository.existsById(id)) {
            throw new EntityNotFoundException("Restaurant with specified ID does not exist");
        }
    }
}
