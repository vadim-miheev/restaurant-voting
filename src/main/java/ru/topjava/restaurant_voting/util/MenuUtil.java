package ru.topjava.restaurant_voting.util;

import jakarta.persistence.EntityNotFoundException;
import lombok.experimental.UtilityClass;
import org.springframework.security.web.firewall.RequestRejectedException;
import ru.topjava.restaurant_voting.model.Menu;
import ru.topjava.restaurant_voting.repository.MenuRepository;

import java.util.Objects;

@UtilityClass
public class MenuUtil {
    public static void checkRestaurantId (Menu menu, int restaurantId) {
        if (menu.getRestaurant().getId() == null || menu.getRestaurant().getId() != restaurantId) {
            throw new RequestRejectedException("This menu does not apply to the specified restaurant");
        }
    }

    public static void checkMenuIdBeforeUpdate(Menu menu, int menuId) {
        if (menu.isNew()) {
            menu.setId(menuId);
        } else if (Objects.requireNonNull(menu.getId()) != menuId) {
            throw new RequestRejectedException("Menu id cannot be changed");
        }
    }

    public static void checkMenuExist(MenuRepository menuRepository, int menuId) {
        if (!menuRepository.existsById(menuId)) {
            throw new EntityNotFoundException("Menu with specified ID does not exist");
        }
    }
}
