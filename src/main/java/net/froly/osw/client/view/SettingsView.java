package net.froly.osw.client.view;

import com.google.code.gwt.storage.client.Storage;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HTMLPanel;
import net.froly.osw.client.OswClient;
import net.froly.osw.client.widgets.AbstractView;
import net.froly.osw.client.widgets.XHtmlWidget;

public class SettingsView extends AbstractView {
    public static final String OSW_USER = "osw.user";
    public static final String OSW_PASS = "osw.pass";
    public static final String OSW_HOST = "osw.host";

    public SettingsView() {

        super("Settings");
    }

    @Override
    protected void htmlCallback(SafeHtmlBuilder sb) {

        String uname = null;
        String pword = null;
        String host = null;


        final Storage storage = OswClient.getStorage(); 
                
        if(storage!=null && storage.getItem(OSW_USER)!=null)
        {
            uname = storage.getItem(OSW_USER);
            pword = storage.getItem(OSW_PASS);
            host = storage.getItem(OSW_HOST);
        }

        sb.appendHtmlConstant("<h2>Account Details</h2>");

        sb.appendHtmlConstant("<form method='post' id='form-"+viewId+"'>");
        sb.appendHtmlConstant("<ul class='edit rounded'>");

        sb.appendHtmlConstant("<li><input type='text' placeholder='Username' name='uname' id='uname' "+getValue(uname)+"/></li>");
        sb.appendHtmlConstant("<li><input type='password' placeholder='Password' name='pword' id='pword' "+getValue(pword)+"/></li>");
        sb.appendHtmlConstant("<li><input type='text' name='host' placeholder='Host' id='host' "+getValue(host)+"/></li>");

        //sb.appendHtmlConstant("<li>Auto Login <span class='toggle'><input type='checkbox' name='autologon'/></li>");
        sb.appendHtmlConstant("</ul>");
        sb.appendHtmlConstant("</form>");
        sb.appendHtmlConstant("<div style='padding:10px' id='submit-"+viewId+"'/>");

    }

    private String getValue(String item) {
        if(item==null || item.equals(""))
            return "";
        else
            return "value='"+item+"'";
    }

    @Override
    protected void widgetCallback(HTMLPanel widget) {

        // cancel
        addCancelButton("Cancel", OswClient.NOOP_HANDLER);

        addButton("Reset", new ClickHandler()
        {
            @Override
            public void onClick(ClickEvent clickEvent) {
                if(Window.confirm("Reset client?"))
                {
                    Storage storage = OswClient.getStorage();
                    storage.clear();
                    reload();
                }
            }
        });

        XHtmlWidget button = new XHtmlWidget("<a href='#' class='whitebutton' style='color:black'>Save</a>");
        button.addClickHandler(new ClickHandler()
        {
            @Override
            public void onClick(ClickEvent clickEvent) {
                final Storage storage = OswClient.getStorage();
                
                storage.setItem(OSW_USER, getUName(viewId));
                storage.setItem(OSW_PASS, getPWord(viewId));
                storage.setItem(OSW_HOST, getHost(viewId));

                OswClient.login();

                DeferredCommand.addCommand(new Command()
                {
                    @Override
                    public void execute() {
                        OswClient.goBack();     
                    }
                });
            }
        }
        );

        html.add(button, "submit-"+viewId);        
    }

    public static native String getUName(String viewId) /*-{
        return $wnd.document.forms["form-"+viewId].elements[0].value;
    }-*/;

    public static native String reload() /*-{
        return $wnd.location.reload();
    }-*/;

     public static native String getPWord(String viewId) /*-{
        return $wnd.document.forms["form-"+viewId].elements[1].value;
    }-*/;

     public static native String getHost(String viewId) /*-{
        return $wnd.document.forms["form-"+viewId].elements[2].value;
    }-*/;
}
