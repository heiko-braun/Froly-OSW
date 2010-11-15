package net.froly.osw.client.model;

import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.List;

public interface ActivityServiceAsync {

    void getMessages(AsyncCallback<List<Message>> callback);
}
