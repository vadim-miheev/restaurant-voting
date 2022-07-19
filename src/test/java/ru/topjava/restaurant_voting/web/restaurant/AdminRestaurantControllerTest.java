package ru.topjava.restaurant_voting.web.restaurant;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.topjava.restaurant_voting.model.Restaurant;
import ru.topjava.restaurant_voting.repository.RestaurantRepository;
import ru.topjava.restaurant_voting.util.JsonUtil;
import ru.topjava.restaurant_voting.util.RestaurantUtil;
import ru.topjava.restaurant_voting.web.AbstractControllerTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.topjava.restaurant_voting.web.restaurant.AdminRestaurantController.REST_URL;
import static ru.topjava.restaurant_voting.web.restaurant.RestaurantTestData.*;

class AdminRestaurantControllerTest extends AbstractControllerTest {

    @Autowired
    RestaurantRepository restaurantRepository;

    @Test
    @WithUserDetails(value = "admin")
    void getAllWithMenuForToday() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/today"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(RESTAURANT_TO_MATCHER.contentJson(RestaurantUtil.getTosWithMenuFilter(RESTAURANTS,
                        menu -> menu.getDate().isEqual(LocalDate.now()))));
    }

    @Test
    @WithUserDetails(value = "user")
    void getAllWithMenuForTodayForbidden() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/today"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails(value = "admin")
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/" + RESTAURANT_ID_2))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(RESTAURANT_WITHOUT_MENU_MATCHER.contentJson(RESTAURANT_2));
    }

    @Test
    @WithUserDetails(value = "admin")
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(RESTAURANT_WITHOUT_MENU_MATCHER.contentJson(RESTAURANTS));
    }

    @Test
    @WithUserDetails(value = "admin")
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + "/" + RESTAURANT_ID_3))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertTrue(restaurantRepository.findById(RESTAURANT_ID_3).isEmpty());
    }

    @Test
    @WithUserDetails(value = "admin")
    void create() throws Exception {
        Restaurant newRestaurant = new Restaurant(null, "New Restaurant");
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newRestaurant)))
                .andDo(print())
                .andExpect(status().isCreated());
        Restaurant created = RESTAURANT_MATCHER.readFromJson(action);
        int newId = created.id();

        newRestaurant.setId(newId);
        RESTAURANT_MATCHER.assertMatch(created, newRestaurant);
        RESTAURANT_MATCHER.assertMatch(created, restaurantRepository.findById(newId).orElse(null));
    }

    @Test
    @WithUserDetails(value = "admin")
    void update() throws Exception {
        Restaurant restaurantForUpdate = new Restaurant(RESTAURANT_ID_1, "Updated Name");
        ResultActions action = perform(MockMvcRequestBuilders.put(REST_URL + "/" + RESTAURANT_ID_1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(restaurantForUpdate)))
                .andDo(print())
                .andExpect(status().isOk());
        Restaurant updated = RESTAURANT_MATCHER.readFromJson(action);

        int updatedId = updated.id();
        assertEquals(updatedId, RESTAURANT_ID_1);

        restaurantForUpdate.setId(updatedId);
        RESTAURANT_MATCHER.assertMatch(updated, restaurantForUpdate.addMenusAndGetInstance(RESTAURANT_1_MENUS));
        RESTAURANT_MATCHER.assertMatch(updated, restaurantRepository.findById(updatedId).orElse(null));
    }
}