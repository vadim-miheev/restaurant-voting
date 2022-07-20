package ru.topjava.restaurant_voting.web.restaurant;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.topjava.restaurant_voting.dto.restaurant.RestaurantWithCurrentMenu;

import java.util.List;

@RestController
@RequestMapping(value = RestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@Tag(name = "Restaurant Controller")
public class RestaurantController extends AbstractRestaurantController {
    protected static final String REST_URL = "/api/restaurants";

    @Override
    @GetMapping("/today")
    List<RestaurantWithCurrentMenu> getAllWithMenuForToday() {
        return super.getAllWithMenuForToday();
    }
}
