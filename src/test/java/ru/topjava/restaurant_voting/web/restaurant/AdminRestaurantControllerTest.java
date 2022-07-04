package ru.topjava.restaurant_voting.web.restaurant;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.topjava.restaurant_voting.repository.RestaurantRepository;
import ru.topjava.restaurant_voting.web.AbstractControllerTest;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.topjava.restaurant_voting.web.restaurant.AdminRestaurantController.REST_URL;
import static ru.topjava.restaurant_voting.web.restaurant.RestaurantTestData.RESTAURANT_MATCHER;

class AdminRestaurantControllerTest extends AbstractControllerTest {

    @Autowired
    RestaurantRepository restaurantRepository;

    @Test
    @WithUserDetails(value = "admin")
    void getAllWithMenuForToday() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/today"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(RESTAURANT_MATCHER.contentJson(restaurantRepository.getAllWithMenuForToday()));
    }

    @Test
    @WithUserDetails(value = "user")
    void getAllWithMenuForTodayForbidden() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/today"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }
}