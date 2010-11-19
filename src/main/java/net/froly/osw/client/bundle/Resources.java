package net.froly.osw.client.bundle;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

public interface Resources extends ClientBundle {
    
    @Source("contact.png")
    ImageResource contact();

    @Source("contacts.png")
    ImageResource contacts();

    @Source("refresh.png")
    ImageResource refresh();

    @Source("search.png")
    ImageResource search();

    @Source("chat.png")
    ImageResource chat();

    @Source("chats.png")
    ImageResource chats();

    @Source("settings.png")
    ImageResource settings();
}