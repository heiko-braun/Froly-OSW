package net.froly.osw.client.model;

import com.google.gwt.event.shared.GwtEvent;

import java.util.List;

public class ConversationUpdatedEvent extends GwtEvent<ConversationUpdatedHandler> {

    public static Type<ConversationUpdatedHandler> TYPE = new Type<ConversationUpdatedHandler>();

    private final Message parent;
    private final List<Message> replies;

    public ConversationUpdatedEvent(Message parent, List<Message> replies) {
        this.parent = parent;
        this.replies= replies;
    }

    @Override
    public Type<ConversationUpdatedHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(ConversationUpdatedHandler handler) {
        handler.onConversationUpdated(this);
    }

    public Message getParent() {
        return parent;
    }

    public List<Message> getReplies() {
        return replies;
    }
}