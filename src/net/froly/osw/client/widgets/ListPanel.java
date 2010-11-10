package net.froly.osw.client.widgets;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.Widget;

/**
 * Simple list panel implementation
 */
public class ListPanel extends AbstractPanel {
      
    public ListPanel(String title) {
        super(title);
    }

    @Override
    protected void htmlCallback(SafeHtmlBuilder sb) {
        sb.appendHtmlConstant("<div id='listPanelPre-"+viewId+"'></div>");
        sb.appendHtmlConstant("<ul id='listPanelContainer-"+viewId+"' class='rounded'></ul>");        
        sb.appendHtmlConstant("<div id='listPanelPost-"+viewId+"'></div>");
    }

    public void addItem(String name, final ClickHandler handler)
    {
        String s = "<li class='arrow' style='color:#fff'>" + name + "</li>";
        InlineHTML listItem = new InlineHTML(s);
        listItem.addClickHandler(handler);

        html.add(listItem, "listPanelContainer-"+viewId);
    }

    public void addPre(HTML content)
    {
        html.add(content, "listPanelPre-"+viewId);
    }

    public void addPost(HTML content)
    {
        html.add(content, "listPanelPost-"+viewId);    
    }

    public Widget asWidget() {
        return html;
    }   
}
