package net.froly.osw.client.widgets;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.ui.HTML;

/**
 * Use 'edgetoedge' styles.
 */
public class ContentListPanel extends ListPanel {

    public ContentListPanel(String title) {
        super(title);
    }

    @Override
    protected void htmlCallback(SafeHtmlBuilder sb) {

        sb.appendHtmlConstant("<div class='vertical-scroll use-bottom-toolbar'>"); // scroll wrapper
        sb.appendHtmlConstant("<div>"); // will be scrolled (style='-webkit-transform: translate3d(0px, 0px, 0px);')
        sb.appendHtmlConstant("<!-- this div is the one being scrolled -->");
        
        sb.appendHtmlConstant("<div id='listPanelPre-"+viewId+"'></div>");
        sb.appendHtmlConstant("<ul id='listPanelContainer-"+viewId+"' class='edgetoedge'></ul>");
        sb.appendHtmlConstant("<div id='listPanelPost-"+viewId+"'></div>");

        sb.appendHtmlConstant("</div>");
        sb.appendHtmlConstant("</div>"); // end scroll wrapper
    }

    public void addContent(SafeHtml content, final ClickHandler handler)
    {
        // '#' links will be ignored by jqTouch, but match the default css selector
        String s = "<li class='arrow'><a href='#' style='white-space:normal;padding-bottom:20px;'>" + content.asString() + "</a></li>";
        XHtmlWidget listItem = new XHtmlWidget(s);
        listItem.addClickHandler(handler);
        html.add(listItem, "listPanelContainer-"+viewId);
    }

    public void clearContent()
    {
        html.getElementById("listPanelPre-"+viewId).setInnerHTML("<div/>");
        html.getElementById("listPanelContainer-"+viewId).setInnerHTML("<div/>");
    }
}