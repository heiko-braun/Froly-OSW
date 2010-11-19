package net.froly.osw.client.view;

import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.ui.HTMLPanel;
import net.froly.osw.client.OswClient;
import net.froly.osw.client.widgets.AbstractView;


public class ContactSearchView extends AbstractView {
    public ContactSearchView() {
        super("New Contact");
    }

    @Override
    protected void htmlCallback(SafeHtmlBuilder sb) {
        sb.appendHtmlConstant("<form><ul class='edit rounded'><li><input type='text' name='search' placeholder='Search' id='some_id'></li></ul></form>");
    }

    @Override
    protected void widgetCallback(HTMLPanel widget) {
        addCancelButton("Cancel", OswClient.NOOP_HANDLER);
    }
}
