package ru.topjava.restaurant_voting.to;

import lombok.AllArgsConstructor;
import lombok.Value;
import ru.topjava.restaurant_voting.model.Menu;

import java.util.List;
@Value
@AllArgsConstructor
public class RestaurantTo {
    int id;
    String name;
    Menu currentMenu;
}
