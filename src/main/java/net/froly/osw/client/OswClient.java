package net.froly.osw.client;

import com.google.code.gwt.storage.client.Storage;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.PopupPanel;
import net.froly.osw.client.bundle.Resources;
import net.froly.osw.client.model.ContactService;
import net.froly.osw.client.model.ContactServiceAsync;
import net.froly.osw.client.view.*;
import net.froly.osw.client.widgets.XHtmlWidget;


/**
 * Application entry point.
 */
public class OswClient implements EntryPoint {

    private static ViewManagement viewManagement = new ViewManagement();
    private static XHtmlWidget progress;
    private static PopupPanel loading;

    public static Resources IMAGES = GWT.create(Resources.class);

    private HomeView homeView;

    public static final ClickHandler NOOP_HANDLER = new ClickHandler() {
        @Override
        public void onClick(ClickEvent clickEvent) {
            //NOOP
        }
    };

    public void onModuleLoad() {

        /*XHtmlWidget blank = new XHtmlWidget("<div style='margin:0px; background-color:black; width:100%; height:100%; z-index:999; padding:0px; top:0px; left:0px;'/>");
        RootPanel.get().add(blank);*/

        // ---------------------------------
        // Home


        homeView = new HomeView();
        viewManagement.addView(Tokens.HOME, homeView);

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

        //blank.setVisible(false);

        initJQTouch();
        
        // default view
        viewManagement.showView(Tokens.HOME, View.FADE);

        if(null == OswClient.getCurrentUserJID())
        {
            homeView.updateStatus("Not logged in!");
        }
        else
        {
            homeView.updateStatus("Trying to login ...");
            login();
        }

    }

    public static void login() {                

        final HomeView home = (HomeView)OswClient.getViewManagement().getView(Tokens.HOME);
        home.updateStatus("Trying to login ...");

        final Storage storage = Storage.getLocalStorage();
        final String user = storage.getItem(SettingsView.OSW_USER);
        final String pass = storage.getItem(SettingsView.OSW_PASS);
        final String host = storage.getItem(SettingsView.OSW_HOST);

        ContactServiceAsync svc = ContactService.App.getInstance();
        svc.login(user, pass, host, new AsyncCallback<Boolean>()
        {
            @Override
            public void onFailure(Throwable throwable) {
                home.updateStatus("Login failed.");
                Window.alert("Login failed.");
            }

            @Override
            public void onSuccess(Boolean loginSuccess)
            {
                if(!loginSuccess)
                {
                    home.updateStatus("Login failed.");
                    Window.alert("Login failed");
                }
                else
                {
                    home.updateStatus("Logged in as " + getCurrentUserJID());                    
                }
            }
        });
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

    public static Storage getStorage()
    {

        if (!Storage.isSupported()) {
            throw new RuntimeException("Storage not supported");            
        }

        Storage local = Storage.getLocalStorage();
        return local;
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

    public static native void goBack() /*-{
        $wnd.jQT.goBack();
    }-*/;

    public static native void alert(String msg) /*-{
        alert(msg);
    }-*/;

    public static native void confirm(String msg) /*-{
        confirm(msg);
    }-*/;
    
    public static String getCurrentUserJID() {
        final Storage storage = Storage.getLocalStorage();

        if(null==storage.getItem(SettingsView.OSW_USER))
        {
            return null;
        }
        else
        {
            final String user = storage.getItem(SettingsView.OSW_USER);
            final String host = storage.getItem(SettingsView.OSW_HOST);
            return user+"@"+host;
        }
    }
    
}
