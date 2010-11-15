package net.froly.osw.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.PopupPanel;
import net.froly.osw.client.view.*;
import net.froly.osw.client.widgets.XHtmlWidget;

/**
 * Application entry point.
 */
public class OswClient implements EntryPoint {

    private static ViewManagement viewManagement = new ViewManagement();
    private static XHtmlWidget progress;
    private static PopupPanel loading;

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


        // progress

        loading = new PopupPanel(true);
        loading.setWidget(new HTML("<div class='loading'>Loading...</div>"));        
    }

    public static void loading(boolean visible)
    {
        if(visible)
            loading.show();
        else
            loading.hide();
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