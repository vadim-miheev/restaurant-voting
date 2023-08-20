package ru.topjava.restaurant_voting.service;


import jakarta.validation.ClockProvider;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.ZonedDateTime;

@Service
@Getter
public class ClockProviderService implements ClockProvider {
    private Clock clock = Clock.systemDefaultZone();

    public void fixTimeTo(ZonedDateTime zonedDateTime) {
        this.clock = Clock.fixed(zonedDateTime.toInstant(), zonedDateTime.getZone());
    }

    public void resetToDefault() {
        this.clock = Clock.systemDefaultZone();
    }
}
