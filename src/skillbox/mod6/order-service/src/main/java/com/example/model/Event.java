package com.example.model;

import lombok.Data;

import java.time.Instant;

@Data
public class Event {
    private String status;
    private Instant date;
}

