package net.froly.osw.client.model;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.List;

/**
 * Shared 'inbox' handle.
 */
public class MessageStore implements HasHandlers {

    private HandlerManager handlerManager;
    private ActivityServiceAsync service = ActivityService.App.getInstance();
    private List<Message> inbox = null;

    public MessageStore() {
        handlerManager = new HandlerManager(this);
    }

    @Override
    public void fireEvent(GwtEvent<?> event) {
        handlerManager.fireEvent(event);
    }

    public HandlerRegistration addMessageReadHandler(
            MessageReadEventHandler handler) {
        return handlerManager.addHandler(MessageReadEvent.TYPE, handler);
    }

    public HandlerRegistration addInboxUpdatedHandler(
            InboxUpdatedEventHandler handler) {
        return handlerManager.addHandler(InboxUpdatedEvent.TYPE, handler);
    }


    public void refresh() {
        DeferredCommand.add(new Command()
        {
            @Override
            public void execute() {
                service.getMessages(new AsyncCallback<List<Message>>()
                {
                    public void onFailure(Throwable e) {
                        GWT.log("Failed to retrieve messages", e);
                    }

                    public void onSuccess(List<Message> result) {

                        inbox = result;
                        fireEvent(new InboxUpdatedEvent(inbox));
                    }

                });
            }
        });
    }

    public void markRead(Message message) {
        ReadFlags.markRead(message);
        fireEvent(new MessageReadEvent(message, inbox));
    }
}
