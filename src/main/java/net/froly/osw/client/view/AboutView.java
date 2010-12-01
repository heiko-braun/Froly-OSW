package net.froly.osw.client.view;

import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import net.froly.osw.client.widgets.AbstractView;
import net.froly.osw.client.Build;

public class AboutView extends AbstractView  {

    public AboutView() {
        super("About");
    }

    @Override
    protected void htmlCallback(SafeHtmlBuilder sb) {
        super.htmlCallback(sb);

        String buildNumber = Build.VERSION + "_"+Build.BUILD+"#"+Build.USER;
        sb.appendHtmlConstant("<center style='padding-top:50px; padding-bottom:35px;color:#ffffff; font-weight:BOLD; text-shadow: rgba(0, 0, 0, 0.199219) 0px 1px 1px;'>" +
                "OSW iPhone Client:<br> " +
                "<tt>urban-reality.com</tt><br>" +
                "<span style='font-size:small'>(v. "+buildNumber+")</span><br><br>"+
                " OneSocialWeb: <br>The OSW development team.<br>" +
                "(http://onesocialweb.org)</center>");

        sb.appendHtmlConstant("<div style='padding:15px;'><a href='#' class='grayButton goback' style='padding:15px;'>Close</a></div>");
    }

}
