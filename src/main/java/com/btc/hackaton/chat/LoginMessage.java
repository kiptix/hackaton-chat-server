package com.btc.hackaton.chat;

public class LoginMessage implements Message {
    public static final String TYPE = "login";
    
    private final String userName;
    
    private final String displayName;

    public LoginMessage(String userName, String displayName) {
        this.userName = userName;
        this.displayName = displayName;
    }

    public String getUserName() {
        return userName;
    }

    public String getDisplayName() {
        return displayName;
    }
    
}
