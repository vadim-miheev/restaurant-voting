package ru.topjava.restaurant_voting.web.restaurant.menu;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.topjava.restaurant_voting.web.AbstractControllerTest;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.topjava.restaurant_voting.web.restaurant.AdminRestaurantController.REST_URL;
import static ru.topjava.restaurant_voting.web.restaurant.RestaurantTestData.RESTAURANT_1_MENUS;
import static ru.topjava.restaurant_voting.web.restaurant.RestaurantTestData.RESTAURANT_ID_1;
import static ru.topjava.restaurant_voting.web.restaurant.menu.AdminMenuController.SUBRESOURCE_URL;
import static ru.topjava.restaurant_voting.web.restaurant.menu.MenuTestData.MENU_MATCHER;


class AdminMenuControllerTest extends AbstractControllerTest {


    @Test
    @WithUserDetails()
    void getAllForbidden() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/" + RESTAURANT_ID_1 + SUBRESOURCE_URL))
            .andDo(print())
            .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails(value = "admin")
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/" + RESTAURANT_ID_1 + SUBRESOURCE_URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENU_MATCHER.contentJson(RESTAURANT_1_MENUS));
    }

    @Test
    @WithUserDetails(value = "admin")
    void get() {
    }

    @Test
    @WithUserDetails(value = "admin")
    void create() {
    }

    @Test
    @WithUserDetails(value = "admin")
    void update() {
    }

    @Test
    @WithUserDetails(value = "admin")
    void delete() {
    }
}