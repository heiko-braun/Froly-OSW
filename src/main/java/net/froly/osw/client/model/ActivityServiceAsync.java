package net.froly.osw.client.model;

import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.List;

public interface ActivityServiceAsync {

    void getMessages(AsyncCallback<List<Message>> callback);
    
    void sendMessage(Message msg, AsyncCallback<Void> async);

    void deleteMessage(String id, AsyncCallback<Void> async);

    void getReplies(String id, AsyncCallback<List<Message>> async);

    void commentMessage(String parentId, Message msg, AsyncCallback<Void> async);
}
