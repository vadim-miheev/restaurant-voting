package ru.topjava.restaurant_voting.web.restaurant;

import ru.topjava.restaurant_voting.model.Restaurant;
import ru.topjava.restaurant_voting.web.MatcherFactory;

public class RestaurantTestData {
    public static MatcherFactory.Matcher<Restaurant> RESTAURANT_MATCHER = MatcherFactory.usingEqualsComparator(Restaurant.class);
}
