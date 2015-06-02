package com.btc.hackaton.chat;

public class InMessage implements Message {
    public static final String TYPE = "message";
    
    private final String recipient;
    
    private final String message;

    public InMessage(String receipient, String message) {
        this.recipient = receipient;
        this.message = message;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getMessage() {
        return message;
    }
}
