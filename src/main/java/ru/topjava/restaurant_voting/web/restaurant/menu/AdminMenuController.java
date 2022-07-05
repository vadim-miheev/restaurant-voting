package ru.topjava.restaurant_voting.web.restaurant.menu;

import lombok.AllArgsConstructor;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.topjava.restaurant_voting.error.AppException;
import ru.topjava.restaurant_voting.model.Menu;
import ru.topjava.restaurant_voting.repository.MenuRepository;
import ru.topjava.restaurant_voting.repository.RestaurantRepository;
import ru.topjava.restaurant_voting.web.restaurant.AdminRestaurantController;

import javax.persistence.EntityNotFoundException;

import static org.springframework.boot.web.error.ErrorAttributeOptions.Include.MESSAGE;

@RestController
@RequestMapping(value = AdminMenuController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class AdminMenuController {
    protected static final String REST_URL = AdminRestaurantController.REST_URL + "/{restaurantId}/menus";

    MenuRepository menuRepository;

    RestaurantRepository restaurantRepository;

    @GetMapping("/{id}")
    ResponseEntity<Menu> get(@PathVariable int restaurantId, @PathVariable int id) {
        Menu menu = menuRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Menu not found"));
        if (menu.getRestaurant().getId() == null || menu.getRestaurant().getId() != restaurantId) {
            throw new AppException(HttpStatus.NOT_FOUND, "The requested menu does not apply to the specified restaurant",
                    ErrorAttributeOptions.of(MESSAGE));
        }
        return ResponseEntity.ok(menu);
    }
}
