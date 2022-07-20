package ru.topjava.restaurant_voting.dto.restaurant;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Restaurant (without menus)")
public record RestaurantWithoutMenus(int id, String name) {
}
