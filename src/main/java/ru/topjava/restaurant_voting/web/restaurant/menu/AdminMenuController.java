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
import ru.topjava.restaurant_voting.error.UnprocessableEntityException;
import ru.topjava.restaurant_voting.model.Menu;
import ru.topjava.restaurant_voting.repository.MenuRepository;
import ru.topjava.restaurant_voting.repository.RestaurantRepository;
import ru.topjava.restaurant_voting.web.restaurant.AdminRestaurantController;

import javax.persistence.EntityNotFoundException;
import javax.validation.Validator;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

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

    @GetMapping("/{menuId}")
    ResponseEntity<Menu> get(@PathVariable int restaurantId, @PathVariable int menuId) {
        Menu menu = menuRepository.findById(menuId).orElseThrow(() -> new EntityNotFoundException("Menu not found"));
        if (menu.getRestaurant().getId() == null || menu.getRestaurant().getId() != restaurantId) {
            throw new AppException(HttpStatus.CONFLICT, "The requested menu does not apply to the specified restaurant",
                    ErrorAttributeOptions.of(MESSAGE));
        }
        log.info("get restaurant:{} menu:{}", restaurantId, menuId);
        return ResponseEntity.ok(menu);
    }

    @GetMapping
    List<Menu> getAll(@PathVariable int restaurantId) {
        log.info("getAll restaurant:{}", restaurantId);
        return menuRepository.getMenusByRestaurant(restaurantRepository.getReferenceById(restaurantId));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Menu> create(@PathVariable int restaurantId, @RequestBody Menu menu) {
        menu.setRestaurant(restaurantRepository.getReferenceById(restaurantId));

        var violations = validator.validate(menu);
        if (!violations.isEmpty()) {
            throw new UnprocessableEntityException(violations.stream()
                    .map(violation -> violation.getPropertyPath() + " " + violation.getMessage())
                    .collect(Collectors.joining("; ")));
        }

        if(!menu.isNew()) menu.setId(null);
        Menu created = menuRepository.save(menu);
        log.info("created {}", menu);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{menuId}")
                .buildAndExpand(restaurantId, created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }
}
