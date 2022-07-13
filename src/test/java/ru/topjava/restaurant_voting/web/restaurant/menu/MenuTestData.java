package ru.topjava.restaurant_voting.web.restaurant.menu;

import ru.topjava.restaurant_voting.model.Dish;
import ru.topjava.restaurant_voting.model.Menu;
import ru.topjava.restaurant_voting.web.MatcherFactory;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.topjava.restaurant_voting.web.restaurant.menu.DishTestData.*;

public class MenuTestData {
    public static final MatcherFactory.Matcher<Menu> MENU_MATCHER = MatcherFactory.usingAssertions(Menu.class,
            (a, e) -> assertThat(a).usingRecursiveComparison().isEqualTo(e),
            (a, e) -> assertThat(a).usingRecursiveComparison().isEqualTo(e));

    public static final int MENU_ID_1 = 1;
    public static final int MENU_ID_2 = 2;
    public static final int MENU_ID_3 = 3;
    public static final int MENU_ID_4 = 4;
    public static final int MENU_ID_5 = 5;
    public static final int MENU_ID_6 = 6;
    public static final int MENU_ID_7 = 7;
    public static final int MENU_ID_NONEXISTENT = Integer.MAX_VALUE;

    public static final List<Dish> MENU_1_DISHES = List.of(DISH_1, DISH_2, DISH_3, DISH_4);
    public static final List<Dish> MENU_2_DISHES = List.of(DISH_5, DISH_6, DISH_7, DISH_8);
    public static final List<Dish> MENU_3_DISHES = List.of(DISH_9, DISH_10, DISH_11);
    public static final List<Dish> MENU_4_DISHES = List.of(DISH_1, DISH_2, DISH_3, DISH_4);
    public static final List<Dish> MENU_5_DISHES = List.of(DISH_5, DISH_6, DISH_7);
    public static final List<Dish> MENU_6_DISHES = List.of(DISH_1, DISH_2, DISH_3);
    public static final List<Dish> MENU_7_DISHES = List.of(DISH_4, DISH_5, DISH_6, DISH_7);

    public static final Menu MENU_1 = new Menu(MENU_ID_1, null, LocalDate.of(2022, 6, 16), MENU_1_DISHES);
    public static final Menu MENU_2 = new Menu(MENU_ID_2, null, LocalDate.of(2022, 6, 15), MENU_2_DISHES);
    public static final Menu MENU_3 = new Menu(MENU_ID_3, null, LocalDate.now(), MENU_3_DISHES);
    public static final Menu MENU_4 = new Menu(MENU_ID_4, null, LocalDate.of(2022, 6, 15), MENU_4_DISHES);
    public static final Menu MENU_5 = new Menu(MENU_ID_5, null, LocalDate.now(), MENU_5_DISHES);
    public static final Menu MENU_6 = new Menu(MENU_ID_6, null, LocalDate.of(2022, 6, 15), MENU_6_DISHES);
    public static final Menu MENU_7 = new Menu(MENU_ID_7, null, LocalDate.now(), MENU_7_DISHES);
}
