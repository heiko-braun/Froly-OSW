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
        sb.appendHtmlConstant("<div id='listPanelPre-"+viewId+"'></div>");
        sb.appendHtmlConstant("<ul id='listPanelContainer-"+viewId+"' class='edgetoedge'></ul>");
        sb.appendHtmlConstant("<div id='listPanelPost-"+viewId+"'></div>");
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
