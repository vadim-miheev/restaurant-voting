package ru.topjava.restaurant_voting.error;

public class VoteDeadlineException extends RuntimeException {
    public VoteDeadlineException(String msg) {
        super(msg);
    }
}
