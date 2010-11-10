package net.froly.osw.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HTML;
import net.froly.osw.client.view.ContactsView;
import net.froly.osw.client.view.RevealHandler;
import net.froly.osw.client.view.SettingsView;
import net.froly.osw.client.view.View;
import net.froly.osw.client.widgets.ListPanel;

/**
 * Application entry point.
 */
public class OswClient implements EntryPoint {

    private static ViewManagement viewManagement = new ViewManagement();

    public void onModuleLoad() {

        initJQTouch();

        final ClickHandler noop = new ClickHandler()
        {
            public void onClick(ClickEvent event) {

            }
        };

        final ClickHandler revealContacts = new RevealHandler("contacts");
        final ClickHandler revealSettings = new RevealHandler("settings");
        final ClickHandler revealHome = new RevealHandler("home", View.SLIDERIGHT);
                        
        // ---------------------------------
        // Home

        ListPanel home = new ListPanel("Froly OSW")
        {{
                HTML welcome = new HTML("<center>One true, open, decentralized social network.</center>");
                welcome.setStyleName("content");
                
                addPre(welcome);

                addItem("Messages", new ClickHandler()
                {
                    public void onClick(ClickEvent event) {
                        viewManagement.showView("messages");
                    }
                });

                addItem("Contacts", revealContacts);
                addItem("Settings", revealSettings);

                addPost(new HTML("<div class='content' style='color:#808080'>We dream of a world where all social networks are connected and work together in a way similar to email. Our project aims to define a language to bridge these networks and make it easy for social networks to join a bigger social web. You're invited to help make this a reality.</div>"));
            }};


        viewManagement.addView("home", home);

        // ---------------------------------
        // Messages

        final ListPanel messageView = new ListPanel("Messages")
        {{
                addItem("One", noop);
                addItem("Two", noop);

                addItem("Three", revealHome);
                addBackButton("Back", revealHome);
                addButton("+", noop);

            }};

        
        viewManagement.addView("messages", messageView);


        // ---------------------------------
        // Settings

        viewManagement.addView("settings", new SettingsView());


        // ---------------------------------
        // Contacts

        viewManagement.addView("contacts", new ContactsView());
        
        // ---------------------------------
        
        // default view
        viewManagement.showView("home", View.FADE);
    }


    public static ViewManagement getViewManagement()
    {
        return viewManagement;   
    }

    /*
        -----------------------
        JSNI below
        -----------------------
    */

    
    public static native void initJQTouch() /*-{
        $wnd.jQT = $wnd.$.jQTouch({            
            statusBar: 'black'
        });        
    }-*/;

    public static native void goTo(String page, String anim) /*-{        
        $wnd.jQT.goTo("#"+page, anim);
    }-*/;
}
