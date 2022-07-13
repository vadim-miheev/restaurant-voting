package ru.topjava.restaurant_voting.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.topjava.restaurant_voting.model.User;
import ru.topjava.restaurant_voting.model.Vote;

import java.util.Optional;

@Repository
public interface VoteRepository extends BaseRepository<Vote> {
    @Query("SELECT v FROM Vote v WHERE v.user = :user AND v.date = CURRENT_DATE()")
    Optional<Vote> getUserVoteForToday(User user);
}
