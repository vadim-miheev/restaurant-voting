package ru.topjava.restaurant_voting.web.restaurant;

import ru.topjava.restaurant_voting.model.Menu;
import ru.topjava.restaurant_voting.model.Restaurant;
import ru.topjava.restaurant_voting.web.MatcherFactory;

import java.util.ArrayList;
import java.util.List;

import static ru.topjava.restaurant_voting.web.restaurant.menu.MenuTestData.*;

public class RestaurantTestData {
    public static MatcherFactory.Matcher<Restaurant> RESTAURANT_MATCHER = MatcherFactory.usingEqualsComparator(Restaurant.class);

    public static final int RESTAURANT_ID_1 = 1;
    public static final int RESTAURANT_ID_2 = 2;
    public static final int RESTAURANT_ID_3 = 3;
    public static final int RESTAURANT_ID_4 = 4;

    public static final List<Menu> RESTAURANT_1_MENUS = List.of(MENU_1, MENU_2, MENU_3);
    public static final List<Menu> RESTAURANT_2_MENUS = List.of(MENU_4, MENU_5);
    public static final List<Menu> RESTAURANT_3_MENUS = List.of(MENU_6, MENU_7);

    public static final Restaurant RESTAURANT_1 = new Restaurant(RESTAURANT_ID_1, "restaurant1", RESTAURANT_1_MENUS);
    public static final Restaurant RESTAURANT_2 = new Restaurant(RESTAURANT_ID_2, "restaurant2", RESTAURANT_2_MENUS);
    public static final Restaurant RESTAURANT_3 = new Restaurant(RESTAURANT_ID_3, "restaurant3", RESTAURANT_3_MENUS);
    public static final Restaurant RESTAURANT_4 = new Restaurant(RESTAURANT_ID_4, "restaurant4", new ArrayList<>());
}
