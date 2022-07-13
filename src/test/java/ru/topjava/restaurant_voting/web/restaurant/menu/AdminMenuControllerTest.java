package ru.topjava.restaurant_voting.web.restaurant.menu;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.topjava.restaurant_voting.model.Menu;
import ru.topjava.restaurant_voting.repository.MenuRepository;
import ru.topjava.restaurant_voting.repository.RestaurantRepository;
import ru.topjava.restaurant_voting.util.JsonUtil;
import ru.topjava.restaurant_voting.web.AbstractControllerTest;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.topjava.restaurant_voting.web.restaurant.AdminRestaurantController.REST_URL;
import static ru.topjava.restaurant_voting.web.restaurant.RestaurantTestData.*;
import static ru.topjava.restaurant_voting.web.restaurant.menu.AdminMenuController.SUBRESOURCE_URL;
import static ru.topjava.restaurant_voting.web.restaurant.menu.DishTestData.*;
import static ru.topjava.restaurant_voting.web.restaurant.menu.MenuTestData.*;


class AdminMenuControllerTest extends AbstractControllerTest {
    @Autowired
    MenuRepository menuRepository;

    @Autowired
    RestaurantRepository restaurantRepository;

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
    @WithUserDetails()
    void getAllForbidden() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/" + RESTAURANT_ID_1 + SUBRESOURCE_URL))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails(value = "admin")
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/" + RESTAURANT_ID_2 + SUBRESOURCE_URL + "/" + MENU_ID_4))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENU_MATCHER.contentJson(MENU_4));
    }

    @Test
    @WithUserDetails(value = "admin")
    void getWithWrongRestaurant() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/" + RESTAURANT_ID_3 + SUBRESOURCE_URL + "/" + MENU_ID_5))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithUserDetails(value = "admin")
    void create() throws Exception {
        Menu newMenu = new Menu(null, null, LocalDate.now(), List.of(DISH_1, DISH_2, DISH_5, DISH_6));

        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL + "/" + RESTAURANT_ID_4 + SUBRESOURCE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newMenu)))
                .andDo(print())
                .andExpect(status().isCreated());

        Menu created = MENU_MATCHER.readFromJson(action);
        int newId = created.id();

        newMenu.setId(newId);
        MENU_MATCHER.assertMatch(created, newMenu);

        newMenu.setRestaurant(restaurantRepository.getReferenceById(RESTAURANT_ID_4));
        MENU_MATCHER.assertMatch(menuRepository.findById(newId).orElse(null), newMenu);
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