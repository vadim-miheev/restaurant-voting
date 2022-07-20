package ru.topjava.restaurant_voting.web.vote;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.topjava.restaurant_voting.repository.UserRepository;
import ru.topjava.restaurant_voting.repository.VoteRepository;
import ru.topjava.restaurant_voting.service.ClockProviderService;
import ru.topjava.restaurant_voting.web.AbstractControllerTest;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.topjava.restaurant_voting.service.VoteService.VOTING_DEADLINE;
import static ru.topjava.restaurant_voting.web.user.UserTestData.USER_ID;
import static ru.topjava.restaurant_voting.web.vote.VoteController.REST_URL;
import static ru.topjava.restaurant_voting.web.vote.VoteTestData.*;

class VoteControllerTest extends AbstractControllerTest {
    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ClockProviderService clockProvider;

    @AfterAll
    private static void afterAll(@Autowired ClockProviderService clockProvider) {
        clockProvider.resetToDefault();
    }

    @Test
    @WithUserDetails(value = "user")
    void getUserVoteForToday() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/current"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(VOTE_MATCHER.contentJson(voteRepository.getUserVoteForToday(
                        userRepository.getReferenceById(USER_ID)).orElse(null)));
    }

    @Test
    void getUserVoteForTodayUnauthorized() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/current"))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails(value = "user")
    void voteBeforeDeadline() throws Exception {
        clockProvider.fixTimeTo(ZonedDateTime.of(LocalDate.now(), VOTING_DEADLINE, ZoneId.systemDefault()));
        perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("restaurantId", String.valueOf(EXISTING_RESTAURANT_ID)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails(value = "user")
    void voteAfterDeadline() throws Exception {
        clockProvider.fixTimeTo(ZonedDateTime.of(LocalDate.now(), VOTING_DEADLINE.plusNanos(1), ZoneId.systemDefault()));
        perform(MockMvcRequestBuilders.post(REST_URL).contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("restaurantId", String.valueOf(EXISTING_RESTAURANT_ID)))
                .andDo(print())
                .andExpect(status().isConflict());
    }

    @Test
    @WithUserDetails(value = "user")
    void voteForNonExistent() throws Exception {
        perform(MockMvcRequestBuilders.post(REST_URL).contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("restaurantId", String.valueOf(NON_EXISTENT_RESTAURANT_ID)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}