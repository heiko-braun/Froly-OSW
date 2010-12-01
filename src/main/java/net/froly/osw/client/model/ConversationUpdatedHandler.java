package net.froly.osw.client.model;

import com.google.gwt.event.shared.EventHandler;

public interface ConversationUpdatedHandler extends EventHandler {
    void onConversationUpdated(ConversationUpdatedEvent event);
}
