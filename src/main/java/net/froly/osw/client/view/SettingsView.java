package net.froly.osw.client.view;

import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.ui.HTMLPanel;
import net.froly.osw.client.widgets.AbstractPanel;

public class SettingsView extends AbstractPanel {

    public SettingsView() {

        super("Settings");
    }

    @Override
    protected void htmlCallback(SafeHtmlBuilder sb) {
        sb.appendHtmlConstant("<h2>Account Details</h2>");

        sb.appendHtmlConstant("<form method='post'>");
        sb.appendHtmlConstant("<ul>");
        sb.appendHtmlConstant("<li><input type='text' placeholder='Username' name='uname' id='uname'/></li>");
        sb.appendHtmlConstant("<li><input type='password' placeholder='Password' name='pword' id='pword'/></li>");
        //sb.appendHtmlConstant("<li><input type='submit' class='submit' name='action' value='Save Entry' /></li>");
        sb.appendHtmlConstant("<li>Auto Login <span class='toggle'><input type='checkbox' name='autologon'/></li>");
        sb.appendHtmlConstant("</ul>");
        sb.appendHtmlConstant("</form>");
        sb.appendHtmlConstant("<a href='#' style='margin:0 10px;color:rgba(0,0,0,.9)' class='submit whiteButton'>Save</a>");

        // <a style="margin:0 10px;color:rgba(0,0,0,.9)" href="#" class="submit whiteButton">Submit</a>
    }

    @Override
    protected void widgetCallback(HTMLPanel widget) {
        addCancelButton("Cancel", new RevealHandler("home", View.FADE));
    }
}
