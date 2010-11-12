package net.froly.osw.client;

import com.google.gwt.core.client.EntryPoint;
import net.froly.osw.client.view.*;

/**
 * Application entry point.
 */
public class OswClient implements EntryPoint {

    private static ViewManagement viewManagement = new ViewManagement();

    public void onModuleLoad() {

        initJQTouch();
                        
        // ---------------------------------
        // Home

        viewManagement.addView(Tokens.HOME, new HomeView());

        // ---------------------------------
        // Messages
 
        viewManagement.addView(Tokens.MESSSAGES, new MessageView());


        // ---------------------------------
        // Settings

        viewManagement.addView(Tokens.SETTINGS, new SettingsView());


        // ---------------------------------
        // Contacts
        
        viewManagement.addView(Tokens.CONTACTS, new ContactsView());
        
        // ---------------------------------
        
        // default view
        viewManagement.showView(Tokens.HOME, View.FADE);
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
