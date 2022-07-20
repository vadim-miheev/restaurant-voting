package ru.topjava.restaurant_voting.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.topjava.restaurant_voting.error.VoteDeadlineException;
import ru.topjava.restaurant_voting.model.Restaurant;
import ru.topjava.restaurant_voting.model.User;
import ru.topjava.restaurant_voting.model.Vote;
import ru.topjava.restaurant_voting.repository.RestaurantRepository;
import ru.topjava.restaurant_voting.repository.VoteRepository;

import javax.persistence.EntityNotFoundException;
import java.time.LocalTime;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class VoteService {
    public static final LocalTime VOTING_DEADLINE = LocalTime.of(11, 0);

    VoteRepository voteRepository;

    RestaurantRepository restaurantRepository;

    ClockProviderService clockProvider;

    public Vote voteForRestaurant(int restaurantId, User user) {
        Restaurant restaurantToVote = restaurantRepository.findById(restaurantId).orElseThrow(
                () -> new EntityNotFoundException("Restaurant with such id does not exist"));

        Optional<Vote> previousUserVote = voteRepository.getUserVoteForToday(user);
        if (previousUserVote.isPresent() && LocalTime.now(clockProvider.getClock()).isAfter(VOTING_DEADLINE)) {
            log.info("Vote after deadline - user:{}", user.id());
            throw new VoteDeadlineException();
        }

        Vote voteToSave = previousUserVote.orElse(new Vote(user, restaurantToVote));
        if(!voteToSave.isNew()) voteToSave.setRestaurant(restaurantToVote);

        return voteRepository.save(voteToSave);
    }
}
