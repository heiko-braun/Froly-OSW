package net.froly.osw.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTMLPanel;
import net.froly.osw.client.OswClient;
import net.froly.osw.client.Tokens;
import net.froly.osw.client.ViewManagement;
import net.froly.osw.client.model.Contact;
import net.froly.osw.client.model.ContactService;
import net.froly.osw.client.widgets.ScrollContentListView;

import java.util.List;

public class ContactsView extends ScrollContentListView {

    public ContactsView() {
        super("Contacts");
    }

    @Override
    protected void widgetCallback(HTMLPanel widget) {
        
        addBottom("Refresh", new ClickHandler()
        {
            @Override
            public void onClick(ClickEvent clickEvent) {

                clearContent();
                
                ContactService.App.getInstance().getSubscriptions(
                        OswClient.getCurrentUserJID(), new AsyncCallback<List<Contact>>()
                        {
                            @Override
                            public void onFailure(Throwable throwable) {
                                GWT.log("Failed to get contacts");
                            }

                            @Override
                            public void onSuccess(List<Contact> contacts) {

                                for(final Contact contact : contacts)
                                {
                                    String name = contact.getFullName() !=null ?
                                            contact.getFullName() : contact.getUserId();

                                    String style = contact.isBeingFollowed() ?
                                            "color:#fff" : "color:#999";

                                    SafeHtmlBuilder sb = new SafeHtmlBuilder();                                    
                                    sb.appendHtmlConstant("<div style='"+style+"'>");
                                    sb.appendEscaped(name);
                                    sb.appendHtmlConstant("</div>");

                                    addContent(sb.toSafeHtml(), new ClickHandler()
                                    {
                                        @Override
                                        public void onClick(ClickEvent clickEvent) {
                                            ViewManagement viewManagement = OswClient.getViewManagement();
                                            ProfileView profileView = (ProfileView)viewManagement.getView(Tokens.PROFILE);
                                            profileView.display(contact);
                                            viewManagement.showView(Tokens.PROFILE);
                                        }
                                    });
                                }
                            }
                        }

                );
            }
        });

        addBottom("Search", new ClickHandler()
        {
            @Override
            public void onClick(ClickEvent clickEvent) {
                
            }
        });

        addBackButton("Home", new RevealHandler("home", View.SLIDERIGHT));

        // add search

        OswClient.getViewManagement().addView("contact_search", new ContactSearchView());
    }
}
