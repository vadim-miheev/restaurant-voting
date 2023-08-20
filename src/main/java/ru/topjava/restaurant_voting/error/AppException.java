package ru.topjava.restaurant_voting.error;

import lombok.Getter;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Getter
public class AppException extends ResponseStatusException {
    private final ErrorAttributeOptions options;
    private final HttpStatus status;

    public AppException(HttpStatus status, String message, ErrorAttributeOptions options) {
        super(status, message);
        this.options = options;
        this.status = status;
    }

    @Override
    public String getMessage() {
        return getReason();
    }

    public HttpStatus getStatus() { return this.status; }
}
