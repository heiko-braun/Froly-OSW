package net.froly.osw.client.view;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HTMLPanel;
import net.froly.osw.client.OswClient;
import net.froly.osw.client.widgets.ContentListView;

public class ContactsView extends ContentListView {

    public ContactsView() {
        super("Contacts");        
    }


    @Override
    protected void widgetCallback(HTMLPanel widget) {

        final ClickHandler noop = new ClickHandler() {
            public void onClick(ClickEvent event) {
                //
            }
        };

        addItem("Heiko Braun", noop);
        addItem("Peter Post", noop);
        addItem("Herr Kaiser", noop);
        addItem("Funny Van Dannen", noop);
        addItem("Mike Magic Trick", noop);
        addItem("Fred Feuerstein", noop);

        addButton("+", new RevealHandler("contact_search", View.FADE));
        addBackButton("Back", new RevealHandler("home", View.SLIDERIGHT));

        // add search

        OswClient.getViewManagement().addView("contact_search", new ContactSearchView());
    }
}
