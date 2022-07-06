package ru.topjava.restaurant_voting.service;

import lombok.AllArgsConstructor;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ru.topjava.restaurant_voting.error.AppException;
import ru.topjava.restaurant_voting.model.Menu;
import ru.topjava.restaurant_voting.repository.MenuRepository;

import javax.validation.Valid;

import static org.springframework.boot.web.error.ErrorAttributeOptions.Include.MESSAGE;

@Service
@AllArgsConstructor
public class MenuService {
    MenuRepository menuRepository;

    public Menu validateAndCreate(@Valid Menu menu) {
        if(!menu.isNew()) menu.setId(null);
        return menuRepository.save(menu);
    }

    public static void checkRestaurantId (Menu menu, int restaurantId) {
        if (menu.getRestaurant().getId() == null || menu.getRestaurant().getId() != restaurantId) {
            throw new AppException(HttpStatus.BAD_REQUEST, "This menu does not apply to the specified restaurant",
                    ErrorAttributeOptions.of(MESSAGE));
        }
    }
}
