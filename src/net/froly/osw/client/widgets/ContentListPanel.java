package net.froly.osw.client.widgets;

import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

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
}
