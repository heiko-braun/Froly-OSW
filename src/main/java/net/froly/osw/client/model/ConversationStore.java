package net.froly.osw.client.model;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.List;

/**
 * Shared 'inbox' handle.
 */
public class ConversationStore implements HasHandlers {

    private HandlerManager handlerManager;
    private Message parent = null;

    public ConversationStore() {
        handlerManager = new HandlerManager(this);
    }

    public void loadConversation(final Message parent) {
        this.parent = parent;

        DeferredCommand.add(new Command()
        {
            @Override
            public void execute() {
                ActivityService.App.getInstance().getReplies(
                        parent.getId(),new AsyncCallback<List<Message>>()
                        {
                            @Override
                            public void onFailure(Throwable e) {
                                String msg = "Failed to load replies";
                                Window.alert(msg);
                                GWT.log(msg, e);
                            }

                            @Override
                            public void onSuccess(final List<Message> result) {
                                fireEvent(new ConversationUpdatedEvent(getParent(), result));
                            }
                        }
                );
            }
        });
    }

    private Message getParent() {
        return parent;
    }

    @Override
    public void fireEvent(GwtEvent<?> event) {
        handlerManager.fireEvent(event);
    }

    public HandlerRegistration addConversationUpdatedHandler(
            ConversationUpdatedHandler handler) {
        return handlerManager.addHandler(ConversationUpdatedEvent.TYPE, handler);
    }
}
