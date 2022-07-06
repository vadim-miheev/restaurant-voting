package ru.topjava.restaurant_voting.web.restaurant.menu;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.topjava.restaurant_voting.model.Menu;
import ru.topjava.restaurant_voting.repository.MenuRepository;
import ru.topjava.restaurant_voting.repository.RestaurantRepository;
import ru.topjava.restaurant_voting.service.MenuService;
import ru.topjava.restaurant_voting.web.restaurant.AdminRestaurantController;

import java.net.URI;
import java.util.List;

import static ru.topjava.restaurant_voting.service.MenuService.checkRestaurantId;

@RestController
@RequestMapping(value = AdminMenuController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
@Slf4j
public class AdminMenuController {
    protected static final String REST_URL = AdminRestaurantController.REST_URL + "/{restaurantId}/menus";
    MenuRepository menuRepository;
    RestaurantRepository restaurantRepository;
    MenuService menuService;

    @GetMapping("/{menuId}")
    ResponseEntity<Menu> get(@PathVariable int restaurantId, @PathVariable int menuId) {
        Menu menu = menuRepository.findById(menuId).orElseThrow();
        checkRestaurantId(menu, restaurantId);
        log.info("get menu:{} from restaurant:{}", menuId, restaurantId);
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
        Menu created = menuService.validateAndCreate(menu);
        log.info("created {} for restaurant:{}", menu, restaurantId);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{menuId}")
                .buildAndExpand(restaurantId, created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }
}
