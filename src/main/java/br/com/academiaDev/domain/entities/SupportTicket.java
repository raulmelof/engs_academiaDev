package br.com.academiaDev.domain.entities;

import java.time.LocalDateTime;
import java.util.UUID;

public class SupportTicket {
    private final UUID id;
    private final String title;
    private final String message;
    private final User user;
    private final LocalDateTime date;

    public SupportTicket(String title, String message, User user) {
        this.id = UUID.randomUUID();
        this.title = title;
        this.message = message;
        this.user = user;
        this.date = LocalDateTime.now();
    }

    public UUID getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    public User getUser() {
        return user;
    }

    public LocalDateTime getDate() {
        return date;
    }
}