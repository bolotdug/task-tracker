package com.akvelon.tasktracker.util;

import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;

@Component
public class DateSupplier {

    public Instant getInstant() {
        return Instant.now();
    }

    public Date getDate(Instant instant) {
        return Date.from(instant);
    }
}
