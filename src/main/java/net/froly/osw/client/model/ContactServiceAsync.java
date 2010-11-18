package net.froly.osw.client.model;

import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.List;


public interface ContactServiceAsync {
    
    void getSubscriptions(String jid, AsyncCallback<List<Contact>> async);
}
