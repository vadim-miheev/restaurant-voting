package ru.topjava.restaurant_voting.web.restaurant;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.topjava.restaurant_voting.dto.restaurant.RestaurantWithCurrentMenu;
import ru.topjava.restaurant_voting.model.Restaurant;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static ru.topjava.restaurant_voting.util.RestaurantUtil.updateExisting;

@RestController
@RequestMapping(value = AdminRestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@Tag(name = "Restaurant Controller (for Admin)")
public class AdminRestaurantController extends AbstractRestaurantController {
    public static final String REST_URL = "/api/admin/restaurants";

    @Override
    @GetMapping("/today")
    List<RestaurantWithCurrentMenu> getAllWithMenuForToday() {
        return super.getAllWithMenuForToday();
    }

    @GetMapping("/{id}")
    Restaurant get(@PathVariable int id) {
        log.info("get Restaurant:{}", id);
        return restaurantRepository.getWithMenus(id).orElseThrow(
                () -> new EntityNotFoundException("There is no restaurant with such id"));
    }

    @GetMapping
    List<Restaurant> getAll() {
        log.info("getAll");
        return restaurantRepository.findAll();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable int id) {
        restaurantRepository.deleteExisted(id);
        log.info("delete Restaurant:{}", id);
    }

    @PostMapping
    ResponseEntity<Restaurant> create(@Valid @RequestBody Restaurant restaurant) {
        if(!restaurant.isNew()) restaurant.setId(null);
        Restaurant created = restaurantRepository.save(restaurant);
        log.info("created {}", restaurant);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping("/{id}")
    @Transactional
    ResponseEntity<Restaurant> update(@PathVariable int id, @Valid @RequestBody Restaurant forUpdate) {
        Restaurant existing = restaurantRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Restaurant with specified ID does not exist"));
        updateExisting(existing, forUpdate);
        log.info("updated {}", existing);
        return ResponseEntity.ok(restaurantRepository.save(existing));
    }
}
