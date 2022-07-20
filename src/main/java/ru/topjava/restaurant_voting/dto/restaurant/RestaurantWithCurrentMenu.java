package ru.topjava.restaurant_voting.dto.restaurant;

import io.swagger.v3.oas.annotations.media.Schema;
import ru.topjava.restaurant_voting.model.Menu;

@Schema(name = "Restaurant (with today's menu)")
public record RestaurantWithCurrentMenu(int id, String name, Menu currentMenu) {
}
