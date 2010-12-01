package net.froly.osw.client.model;

import com.google.gwt.event.shared.GwtEvent;

import java.util.List;

public class MessageReadEvent extends GwtEvent<MessageReadEventHandler> {

    public static Type<MessageReadEventHandler> TYPE = new Type<MessageReadEventHandler>();

    private final Message message;
    private final List<Message> inbox;

    public MessageReadEvent(Message message, List<Message> inbox) {
        this.message = message;
        this.inbox = inbox;
    }

    @Override
    public Type<MessageReadEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(MessageReadEventHandler handler) {
        handler.onMessageRead(this);
    }

    public Message getMessage() {
        return message;
    }

    public List<Message> getInbox() {
        return inbox;
    }
}

