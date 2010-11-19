package net.froly.osw.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootPanel;
import net.froly.osw.client.view.*;
import net.froly.osw.client.widgets.XHtmlWidget;

/**
 * Application entry point.
 */
public class OswClient implements EntryPoint {

    private static ViewManagement viewManagement = new ViewManagement();
    private static XHtmlWidget progress;
    private static PopupPanel loading;

    public static final ClickHandler NOOP_HANDLER = new ClickHandler() {
        @Override
        public void onClick(ClickEvent clickEvent) {
            //NOOP
        }
    };

    public void onModuleLoad() {

        XHtmlWidget blank = new XHtmlWidget("<div style='margin:0px; background-color:black; width:100%; height:100%; z-index:999; padding:0px; top:0px; left:0px;'/>");
        RootPanel.get().add(blank);

        // ---------------------------------
        // Home

        viewManagement.addView(Tokens.HOME, new HomeView());

        // ---------------------------------
        // Messages
 
        viewManagement.addView(Tokens.MESSSAGES, new MessageListView());
        viewManagement.addView(Tokens.MESSSAGE_DETAIL, new MessageDetailView());
        viewManagement.addView(Tokens.MESSSAGE_COMPOSE, new ComposeMessageView());
        viewManagement.addView(Tokens.MESSAGE_CONVERSATION, new ConversationView());


        // ---------------------------------
        // Settings

        viewManagement.addView(Tokens.SETTINGS, new SettingsView());


        // ---------------------------------
        // Contacts
        
        viewManagement.addView(Tokens.CONTACTS, new ContactsView());
        viewManagement.addView(Tokens.PROFILE, new ProfileView());

        // ---------------------------------

        // progress

        //loading = new PopupPanel(true);
        //loading.setWidget(new HTML("<div class='loading'>Loading...</div>"));

        initJQTouch();

        blank.setVisible(false);
        
        // default view
        viewManagement.showView(Tokens.HOME, View.FADE);

    }

    public static void loading(boolean visible)
    {
        /*if(visible)
            loading.show();
        else
            loading.hide();*/
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
            statusBar: 'black',
            useFastTouch: true            
        });        
    }-*/;

    public static native void goTo(String page, String anim) /*-{        
        $wnd.jQT.goTo("#"+page, anim);
    }-*/;

    public static String getCurrentUserJID() {
        return "heiko@social.openliven.com";
    }
}
