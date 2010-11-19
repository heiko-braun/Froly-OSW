package net.froly.osw.client.view;

import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.ui.HTMLPanel;
import net.froly.osw.client.OswClient;
import net.froly.osw.client.Tokens;
import net.froly.osw.client.model.Contact;
import net.froly.osw.client.widgets.AbstractView;
import net.froly.osw.client.widgets.XHtmlWidget;

public class ProfileView extends AbstractView {

    private Contact contact = null;

    public ProfileView() {
        super("Profile");        
    }

    @Override
    protected void htmlCallback(SafeHtmlBuilder sb) {
        sb.appendHtmlConstant("<div id='profile-"+viewId+"'/>");
    }

    public void display(Contact contact) {
        this.contact = contact;

        clearProfile();

        SafeHtmlBuilder sb = new SafeHtmlBuilder();
        sb.appendHtmlConstant("<div class='profile' style='padding:12px; color:#fff;display: -webkit-box;'>");
        sb.appendHtmlConstant("<b style='font-size:small;color:#808080;'>JID: "+getContact().getUserId()+"</b><br/>");
                
        sb.appendHtmlConstant("</div>");

        html.add(new XHtmlWidget(sb.toSafeHtml().asString()), "profile-"+viewId);
    }

    @Override
    protected void widgetCallback(HTMLPanel widget) {
        addBackButton("Contacts", OswClient.NOOP_HANDLER);
    }

    private void clearProfile()
    {
        html.getElementById("profile-"+viewId).setInnerHTML("<div/>");
    }

    public Contact getContact() {
        return contact;
    }
}
