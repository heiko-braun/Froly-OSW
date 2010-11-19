package net.froly.osw.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTMLPanel;
import net.froly.osw.client.OswClient;
import net.froly.osw.client.Tokens;
import net.froly.osw.client.bundle.Resources;
import net.froly.osw.client.model.Contact;
import net.froly.osw.client.model.ContactProfile;
import net.froly.osw.client.model.ContactService;
import net.froly.osw.client.widgets.AbstractView;
import net.froly.osw.client.widgets.XHtmlWidget;

/**
 * displays a user's profile.
 */
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

         ContactService.App.getInstance().getProfile(
                 contact.getUserId(), new AsyncCallback<ContactProfile>()
                 {
                     @Override
                     public void onFailure(Throwable throwable) {
                         GWT.log("Failed to retrieve profile", throwable);
                         OswClient.alert("Failed to retrieve profile.");
                     }

                     @Override
                     public void onSuccess(ContactProfile contactProfile) {

                         Resources res = GWT.create(Resources.class);
                         String imgUri = contactProfile.getPhotoUri() != null ?
                                 contactProfile.getPhotoUri() : res.contact().getURL();

                         String jid = contactProfile.getJid();
                         String displayName = contactProfile.getFullName() != null ?
                                 contactProfile.getFullName() : jid.substring(0, jid.indexOf("@"));

                         SafeHtmlBuilder sb = new SafeHtmlBuilder();
                         sb.appendHtmlConstant("<div class='profile' style='padding:12px; color:#fff;display: -webkit-box;-webkit-box-orient:vertical;'>");                         
                         sb.appendHtmlConstant("<div style='padding-left:20px;'><img src='"+imgUri+"' align='center' style='width:32px; height32px; padding:10px;'/>");
                         sb.appendHtmlConstant("<b>"+ displayName +"</b></br>");                         
                         sb.appendHtmlConstant("</div>");

                         sb.appendHtmlConstant("<ul class='rounded'>");
                         sb.appendHtmlConstant("<li class='plain'><div class='list-prefix'>JID:</div> "+contactProfile.getJid()+"</li>");
                         sb.appendHtmlConstant("<li class='plain'><div class='list-prefix'>Email:</div> "+(contactProfile.getEmail()!=null?contactProfile.getEmail() : "")+"</li>");
                         sb.appendHtmlConstant("<li class='plain'><div class='list-prefix'>Timezone:</div></li>");
                         sb.appendHtmlConstant("</ul>");


                         sb.appendHtmlConstant("</div>");  // outer div

                         html.add(new XHtmlWidget(sb.toSafeHtml().asString()), "profile-"+viewId);
                     }
                 }
         );
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
