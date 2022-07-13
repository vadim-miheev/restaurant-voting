package ru.topjava.restaurant_voting.util;

import lombok.experimental.UtilityClass;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.http.HttpStatus;
import ru.topjava.restaurant_voting.error.AppException;
import ru.topjava.restaurant_voting.model.Menu;
import ru.topjava.restaurant_voting.repository.MenuRepository;

import java.util.Objects;

import static org.springframework.boot.web.error.ErrorAttributeOptions.Include.MESSAGE;

@UtilityClass
public class MenuUtil {
    public static void checkRestaurantId (Menu menu, int restaurantId) {
        if (menu.getRestaurant().getId() == null || menu.getRestaurant().getId() != restaurantId) {
            throw new AppException(HttpStatus.BAD_REQUEST, "This menu does not apply to the specified restaurant",
                    ErrorAttributeOptions.of(MESSAGE));
        }
    }

    public static void checkMenuIdBeforeUpdate(Menu menu, int menuId) {
        if (menu.isNew()) {
            menu.setId(menuId);
        } else if (Objects.requireNonNull(menu.getId()) != menuId) {
            throw new AppException(HttpStatus.UNPROCESSABLE_ENTITY, "Menu id cannot be changed",
                    ErrorAttributeOptions.of(MESSAGE));
        }
    }

    public static void checkMenuExist(MenuRepository menuRepository, int menuId) {
        if (!menuRepository.existsById(menuId)) {
            throw new AppException(HttpStatus.BAD_REQUEST, "Menu with specified ID does not exist",
                    ErrorAttributeOptions.of(MESSAGE));
        }
    }
}
