package net.froly.osw.client.model;

import com.google.gwt.event.shared.EventHandler;

public interface InboxUpdatedEventHandler extends EventHandler {
    void onMessagesRefresh(InboxUpdatedEvent event);    
}
