package net.froly.osw.client.model;

import java.io.Serializable;

public class ContactProfile implements Serializable {

    String jid;
    String fullName;
    String photoUri;
    String email;

    public ContactProfile(String jid) {
        this.jid = jid;
    }

    public ContactProfile() {
    }

    public String getJid() {
        return jid;
    }

    public void setJid(String jid) {
        this.jid = jid;
    }

    public String getFullName() {
        if(fullName==null)
            return "";
        else
            return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhotoUri() {
        return photoUri;
    }

    public void setPhotoUri(String photoUri) {
        this.photoUri = photoUri;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
