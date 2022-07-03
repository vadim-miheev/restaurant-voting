package ru.topjava.restaurant_voting.web.vote;

import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.topjava.restaurant_voting.model.Vote;
import ru.topjava.restaurant_voting.repository.VoteRepository;
import ru.topjava.restaurant_voting.service.VoteService;
import ru.topjava.restaurant_voting.web.AuthUser;
import ru.topjava.restaurant_voting.web.restaurant.AbstractRestaurantController;

@RestController
@RequestMapping(value = VoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
public class VoteController extends AbstractRestaurantController {
    static final String REST_URL = "/api/votes";

    VoteRepository voteRepository;
    
    VoteService voteService;

    @GetMapping("/current")
    ResponseEntity<Vote> getUserVoteForToday(@AuthenticationPrincipal AuthUser authUser) {
        log.info("getUserVoteForToday - user:{}", authUser.id());
        return ResponseEntity.of(voteRepository.getUserVoteForToday(authUser.getUser()));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Vote> vote(@RequestBody ObjectNode objectNode, @AuthenticationPrincipal AuthUser authUser) {
        int restaurantId = objectNode.get("restaurantId").asInt();
        Vote countedVote = voteService.voteForRestaurant(restaurantId, authUser.getUser());
        log.info("Vote - user:{} restaurant:{}", authUser.id(), restaurantId);
        return ResponseEntity.ok(countedVote);
    }
}