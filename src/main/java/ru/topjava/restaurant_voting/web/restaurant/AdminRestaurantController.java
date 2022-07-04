package ru.topjava.restaurant_voting.web.restaurant;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.topjava.restaurant_voting.dto.RestaurantTo;

import java.util.List;

@RestController
@RequestMapping(value = AdminRestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class AdminRestaurantController extends AbstractRestaurantController {
    protected static final String REST_URL = "/api/admin/restaurants";

    @Override
    @GetMapping("/today")
    List<RestaurantTo> getAllWithMenuForToday() {
        return super.getAllWithMenuForToday();
    }
}
