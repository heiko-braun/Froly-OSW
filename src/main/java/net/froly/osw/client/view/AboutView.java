package net.froly.osw.client.view;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.ui.HTMLPanel;
import net.froly.osw.client.widgets.AbstractView;


public class AboutView extends AbstractView  {

    public AboutView() {
        super("About");
    }

    @Override
    protected void htmlCallback(SafeHtmlBuilder sb) {
        super.htmlCallback(sb);
        sb.appendHtmlConstant("<center style='padding-top:50px; padding-bottom:35px;color:#ffffff; font-weight:BOLD; text-shadow: rgba(0, 0, 0, 0.199219) 0px 1px 1px;'>" +
                "iPhone client, Version 1.0 Beta<br> " +
                "by <tt>urban-reality.com</tt><br><br>" +
                " OneSocialWeb backend: <br>The OSW development team.<br>" +
                "(http://onesocialweb.org)</center>");

        sb.appendHtmlConstant("<div style='padding:15px;'><a href='#' class='grayButton goback' style='padding:15px;'>Close</a></div>");
    }

}
