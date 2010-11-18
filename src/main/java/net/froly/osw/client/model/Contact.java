package net.froly.osw.client.model;

import java.io.Serializable;

public class Contact implements Serializable {
    String fullName;
    String userId;
    boolean beingFollowed;

    public Contact() {
    }

    public Contact(String fullName, String userId) {
        this.fullName = fullName;
        this.userId = userId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public boolean isBeingFollowed() {
        return beingFollowed;
    }

    public void setBeingFollowed(boolean beingFollowed) {
        this.beingFollowed = beingFollowed;
    }
}
