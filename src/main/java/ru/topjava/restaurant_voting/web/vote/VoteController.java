package ru.topjava.restaurant_voting.web.vote;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.topjava.restaurant_voting.error.VoteDeadlineException;
import ru.topjava.restaurant_voting.model.Restaurant;
import ru.topjava.restaurant_voting.model.Vote;
import ru.topjava.restaurant_voting.repository.RestaurantRepository;
import ru.topjava.restaurant_voting.repository.VoteRepository;
import ru.topjava.restaurant_voting.web.AuthUser;
import ru.topjava.restaurant_voting.web.restaurant.AbstractRestaurantController;

import javax.persistence.EntityNotFoundException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@RestController
@RequestMapping(value = VoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
public class VoteController extends AbstractRestaurantController {
    static final String REST_URL = "/api/votes";

    static final LocalTime VOTING_DEADLINE = LocalTime.of(11, 0);

    VoteRepository voteRepository;

    RestaurantRepository restaurantRepository;

    @GetMapping("/current")
    ResponseEntity<Vote> getUserVoteForToday(@AuthenticationPrincipal AuthUser authUser) {
        log.info("getUserVoteForToday - user:{}", authUser.id());
        return ResponseEntity.of(voteRepository.getUserVoteForToday(authUser.getUser()));
    }

    @PostMapping
    ResponseEntity<Vote> vote(@RequestParam int restaurantId, @AuthenticationPrincipal AuthUser authUser) {
        Restaurant restaurantToVote = restaurantRepository.findById(restaurantId).orElseThrow(
                () -> new EntityNotFoundException("Restaurant with such id does not exist"));

        Optional<Vote> previousUserVote = voteRepository.getUserVoteForToday(authUser.getUser());
        if (previousUserVote.isPresent() && LocalTime.now().isAfter(VOTING_DEADLINE)) {
            log.info("Vote after deadline - user:{}", authUser.id());
            throw new VoteDeadlineException("You cannot change your vote after "
                    + VOTING_DEADLINE.format(DateTimeFormatter.ofPattern("hh:mm a")));
        }

        Vote voteToSave = previousUserVote.orElse(new Vote(authUser.getUser(), restaurantToVote));
        if(!voteToSave.isNew()) voteToSave.setRestaurant(restaurantToVote);

        log.info("Vote - user:{} restaurant:{}", authUser.id(), restaurantId);
        return ResponseEntity.ok(voteRepository.save(voteToSave));
    }
}
