package net.froly.osw.client.model;

import java.io.Serializable;
import java.util.*;

public class Message implements Serializable {

    private String id;

    private String from;
    private String dateFrom;
    private String message;

    private int numReplies;
    private Set<String> recipients = new HashSet<String>();
    private List<String> inlineUrls = new ArrayList<String>();

    public Message(String id, String from, String message) {
        this.id = id;
        this.from = from;
        this.message = message;
    }

    public Message() {

    }

    public Set<String> getRecipients() {
        return recipients;
    }

    public void setRecipients(Set<String> recipients) {
        this.recipients = recipients;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getNumReplies() {
        return numReplies;
    }

    public void setNumReplies(int numReplies) {
        this.numReplies = numReplies;
    }

    public List<String> getInlineUrls() {
        return inlineUrls;
    }    
}

