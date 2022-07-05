package ru.topjava.restaurant_voting.web.restaurant.menu;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.topjava.restaurant_voting.error.AppException;
import ru.topjava.restaurant_voting.model.Menu;
import ru.topjava.restaurant_voting.repository.MenuRepository;
import ru.topjava.restaurant_voting.repository.RestaurantRepository;
import ru.topjava.restaurant_voting.web.restaurant.AdminRestaurantController;

import javax.persistence.EntityNotFoundException;
import javax.validation.Validator;
import java.net.URI;
import java.util.List;

import static org.springframework.boot.web.error.ErrorAttributeOptions.Include.MESSAGE;

@RestController
@RequestMapping(value = AdminMenuController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
@Slf4j
public class AdminMenuController {
    protected static final String REST_URL = AdminRestaurantController.REST_URL + "/{restaurantId}/menus";

    MenuRepository menuRepository;

    RestaurantRepository restaurantRepository;

    Validator validator;

    @GetMapping("/{id}")
    ResponseEntity<Menu> get(@PathVariable int restaurantId, @PathVariable int id) {
        //TODO logs
        Menu menu = menuRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Menu not found"));
        if (menu.getRestaurant().getId() == null || menu.getRestaurant().getId() != restaurantId) {
            throw new AppException(HttpStatus.CONFLICT, "The requested menu does not apply to the specified restaurant",
                    ErrorAttributeOptions.of(MESSAGE));
        }
        return ResponseEntity.ok(menu);
    }

    @GetMapping
    List<Menu> getAll(@PathVariable int restaurantId) {
        //TODO logs
        return menuRepository.getMenusByRestaurant(restaurantRepository.getReferenceById(restaurantId));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Menu> create(@PathVariable int restaurantId, @RequestBody Menu menu) {
        menu.setRestaurant(restaurantRepository.getReferenceById(restaurantId));
        validator.validate(menu).forEach(menuConstraintViolation -> {
            throw new AppException(HttpStatus.UNPROCESSABLE_ENTITY, menuConstraintViolation.getPropertyPath() + " "
                    + menuConstraintViolation.getMessage(), ErrorAttributeOptions.of(MESSAGE));
        });
        Menu created = menuRepository.save(menu);
        log.info("created {}", menu);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(restaurantId, created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }
}
