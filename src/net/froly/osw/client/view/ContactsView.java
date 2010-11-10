package net.froly.osw.client.view;

import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.ui.HTMLPanel;
import net.froly.osw.client.OswClient;
import net.froly.osw.client.widgets.AbstractPanel;

public class ContactsView extends AbstractPanel {

    public ContactsView() {
        super("Contacts");
    }

    @Override
    protected void htmlCallback(SafeHtmlBuilder sb) {
        
        sb.appendHtmlConstant("<ul class='edgetoedge'>");
        sb.appendHtmlConstant("<li class='arrow'><a href=''>Peter Post</a>");
        sb.appendHtmlConstant("<li class='arrow'><a href=''>Magic Mike</a>");
        sb.appendHtmlConstant("<li class='arrow'><a href=''>Pia Haerle</a>");
        sb.appendHtmlConstant("<li class='arrow'><a href=''>Mr. Minit</a>");
        sb.appendHtmlConstant("<li class='arrow'><a href=''>Heiko Herrlich</a>");
        sb.appendHtmlConstant("<li class='arrow'><a href=''>Herr Kaiser</a>");
        sb.appendHtmlConstant("</ul>");
    }

    @Override
    protected void widgetCallback(HTMLPanel widget) {
        addButton("+", new RevealHandler("contact_search", View.FADE));
        addBackButton("Back", new RevealHandler("home", View.SLIDERIGHT));

        // add search

        OswClient.getViewManagement().addView("contact_search", new ContactSearchView());
    }
}
