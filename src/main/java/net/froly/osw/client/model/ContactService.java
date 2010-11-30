package net.froly.osw.client.model;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.google.gwt.core.client.GWT;

import java.util.List;

@RemoteServiceRelativePath("ContactService")
public interface ContactService extends RemoteService {


    List<Contact> getSubscriptions(String jid);

    ContactProfile getProfile(String jid);

    boolean login(String user, String password, String host);

    /**
     * Utility/Convenience class.
     * Use ContactService.App.getInstance() to access static instance of ContactServiceAsync
     */
    public static class App {
        private static final ContactServiceAsync ourInstance = (ContactServiceAsync) GWT.create(ContactService.class);

        public static ContactServiceAsync getInstance() {
            return ourInstance;
        }
    }
}
