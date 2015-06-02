package com.btc.hackaton.chat;

import java.util.Objects;

public class User {

    private final String userName;
    
    private String displayName;
    
    private String color;

    public User(String userName) {
        this.userName = userName;
        this.displayName = userName;
        this.color = "#000000";
    }

    public String getUserName() {
        return userName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getColor() {
        return color;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setColor(String color) {
        this.color = color;
    }
    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + Objects.hashCode(this.userName);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final User other = (User) obj;
        if (!Objects.equals(this.userName, other.userName)) {
            return false;
        }
        return true;
    }
    
    
}
