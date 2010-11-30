package net.froly.osw.client.model;

import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.List;


public interface ContactServiceAsync {
    
    void getSubscriptions(String jid, AsyncCallback<List<Contact>> async);

    void getProfile(String jid, AsyncCallback<ContactProfile> async);

    void login(String user, String password, String host, AsyncCallback<Boolean> async);
}
