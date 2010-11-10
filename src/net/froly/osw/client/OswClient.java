package net.froly.osw.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HTML;
import net.froly.osw.client.view.RevealHandler;
import net.froly.osw.client.view.SettingsView;
import net.froly.osw.client.widgets.ListPanel;
import net.froly.osw.client.widgets.View;

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

        final ClickHandler revealSettings = new RevealHandler("settings");
        final ClickHandler revealHome = new RevealHandler("home", View.SLIDERIGHT);
                        
        // ---------------------------------
        // Home

        ListPanel home = new ListPanel("Froly OSW")
        {{
                HTML welcome = new HTML("One true, open, decentralized social network.");
                welcome.setStyleName("content");
                
                addPre(welcome);

                addItem("Messages", new ClickHandler()
                {
                    public void onClick(ClickEvent event) {
                        viewManagement.showView("messages");
                    }
                });

                addItem("Contacts", noop);

                addItem("Settings", revealSettings);
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
