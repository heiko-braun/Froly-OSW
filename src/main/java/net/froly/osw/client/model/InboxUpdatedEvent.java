package net.froly.osw.client.model;

import com.google.gwt.event.shared.GwtEvent;

import java.util.List;

public class InboxUpdatedEvent extends GwtEvent<InboxUpdatedEventHandler> {

    public static Type<InboxUpdatedEventHandler> TYPE = new Type<InboxUpdatedEventHandler>();

    private final List<Message> inbox;

    public InboxUpdatedEvent(List<Message> messages) {
       this.inbox = messages;
    }

    @Override
    public Type<InboxUpdatedEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(InboxUpdatedEventHandler handler) {
        handler.onMessagesRefresh(this);
    }

    public List<Message> getInbox() {
        return inbox;
    }

}