package net.froly.osw.client.model;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;

public class MessageModel implements HasHandlers {

    private HandlerManager handlerManager;

    public MessageModel() {
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

}
