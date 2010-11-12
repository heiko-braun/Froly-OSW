package net.froly.osw.client.view;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HTMLPanel;
import net.froly.osw.client.widgets.ContentListPanel;

/**
 * message panel
 */
public class MessageView extends ContentListPanel {

    final static ClickHandler noop = new ClickHandler()
    {
        public void onClick(ClickEvent event) {

        }
    };
    
    public MessageView() {
        super("Messages");
    }

    @Override
    protected void widgetCallback(HTMLPanel widget) {
        addItem("One", noop);
        addItem("Two", noop);

        addItem("Three", noop);
        addBackButton("Back", new RevealHandler("home", View.SLIDERIGHT));

    }
}
