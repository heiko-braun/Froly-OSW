package net.froly.osw.client.view;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.ui.HTMLPanel;
import net.froly.osw.client.widgets.AbstractPanel;
import net.froly.osw.client.widgets.XHtmlWidget;

public class SettingsView extends AbstractPanel {

    public SettingsView() {

        super("Settings");
    }

    @Override
    protected void htmlCallback(SafeHtmlBuilder sb) {
        sb.appendHtmlConstant("<h2>Account Details</h2>");

        sb.appendHtmlConstant("<form method='post'>");
        sb.appendHtmlConstant("<ul class='edit rounded'>");
        sb.appendHtmlConstant("<li><input type='text' placeholder='Username' name='uname' id='uname'/></li>");
        sb.appendHtmlConstant("<li><input type='password' placeholder='Password' name='pword' id='pword'/></li>");
        
        sb.appendHtmlConstant("<li>Auto Login <span class='toggle'><input type='checkbox' name='autologon'/></li>");
        sb.appendHtmlConstant("</ul>");
        sb.appendHtmlConstant("</form>");
        sb.appendHtmlConstant("<div style='padding:10px' id='submit-"+viewId+"'/>");
        
    }

    @Override
    protected void widgetCallback(HTMLPanel widget) {
        addCancelButton("Cancel", new RevealHandler("home", View.FADE));

        XHtmlWidget button = new XHtmlWidget("<a href='#' class='whitebutton' style='color:black'>Save</a>");
        button.addClickHandler(new ClickHandler()
        {
            @Override
            public void onClick(ClickEvent clickEvent) {
                System.out.println("Save settings");
            }
        }
        );
        html.add(button, "submit-"+viewId);
    }
}
