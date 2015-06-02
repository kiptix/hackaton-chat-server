package com.btc.hackaton.chat;

import java.time.LocalDate;

public class OutMessage {
    public static final String TYPE = "message";
    
    private Long id;

    private final User sender;

    private final String recipient;

    private final LocalDate date;

    private final String message;

    public OutMessage(User sender, String recipient, LocalDate date, String message) {
        this.sender = sender;
        this.recipient = recipient;
        this.date = date;
        this.message = message;
    }

    public User getSender() {
        return sender;
    }

    public String getRecipient() {
        return recipient;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getMessage() {
        return message;
    }
}
