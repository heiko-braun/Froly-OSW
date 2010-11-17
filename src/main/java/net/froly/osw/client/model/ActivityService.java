package net.froly.osw.client.model;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.google.gwt.core.client.GWT;

import java.util.List;

@RemoteServiceRelativePath("ActivityService")
public interface ActivityService extends RemoteService {


    List<Message> getMessages();

    List<Message> getReplies(String id);

    void sendMessage(Message msg);

    void deleteMessage(String id);


    /**
     * Utility/Convenience class.
     * Use ActivityService.App.getInstance() to access static instance of ActivityServiceAsync
     */
    public static class App {
        private static final ActivityServiceAsync ourInstance = (ActivityServiceAsync) GWT.create(ActivityService.class);

        public static ActivityServiceAsync getInstance() {
            return ourInstance;
        }
    }
}
