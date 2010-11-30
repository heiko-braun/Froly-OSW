package net.froly.osw.client.widgets;

import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

/**
 * Scrollable content list panel with a bottom toolbar
 */
public class ScrollContentListView extends ContentListView {
    
    public ScrollContentListView(String title) {
        super(title);
    }

    @Override
    protected void htmlCallback(SafeHtmlBuilder sb) {

        sb.appendHtmlConstant("<div class='vertical-scroll use-bottom-toolbar'>"); // scroll wrapper
        sb.appendHtmlConstant("<div id='scroll-"+viewId+"'>"); // will be scrolled
        sb.appendHtmlConstant("<!-- this div is the one being scrolled -->");

        sb.appendHtmlConstant("<div id='listPanelPre-"+viewId+"'></div>");
        sb.appendHtmlConstant("<ul id='listPanelContainer-"+viewId+"' class='edgetoedge'></ul>");
        sb.appendHtmlConstant("<div id='listPanelPost-"+viewId+"'></div>");

        sb.appendHtmlConstant("</div>");
        sb.appendHtmlConstant("</div>"); // end scroll wrapper

        sb.appendHtmlConstant("<div class='tabbar'>");
        sb.appendHtmlConstant("<ul id='tabbar-"+viewId+"'/>");
        sb.appendHtmlConstant("</div>");

    }

    public void addBottom(String name, ClickHandler handler)
    {
        String s = "<li><a href='#'>" + name + "</a></li>";
        XHtmlWidget btn = new XHtmlWidget(s);
        btn.addClickHandler(handler);
        html.add(btn, "tabbar-"+viewId);
    }
   
}
