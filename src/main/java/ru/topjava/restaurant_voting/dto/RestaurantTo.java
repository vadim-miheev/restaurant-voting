package ru.topjava.restaurant_voting.dto;

import ru.topjava.restaurant_voting.model.Menu;

public record RestaurantTo(int id, String name, Menu currentMenu) {
}
