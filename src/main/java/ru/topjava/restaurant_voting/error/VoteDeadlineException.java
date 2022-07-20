package ru.topjava.restaurant_voting.error;

import java.time.format.DateTimeFormatter;

import static ru.topjava.restaurant_voting.service.VoteService.VOTING_DEADLINE;

public class VoteDeadlineException extends RuntimeException {
    public VoteDeadlineException() {
        super("You cannot change your vote after " + VOTING_DEADLINE.format(DateTimeFormatter.ofPattern("hh:mm a")));
    }
}
