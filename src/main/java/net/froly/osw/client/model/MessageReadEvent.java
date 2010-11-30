package net.froly.osw.client.model;

import com.google.gwt.event.shared.GwtEvent;

public class MessageReadEvent extends GwtEvent<MessageReadEventHandler> {

    public static Type<MessageReadEventHandler> TYPE = new Type<MessageReadEventHandler>();

    private final Message message;

    public MessageReadEvent(Message message) {
        this.message = message;
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

}

