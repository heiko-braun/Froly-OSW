package net.froly.osw.client.widgets;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import net.froly.osw.client.view.View;

/**
 * Base view abstraction.
 * Provides core view functionality (builder callbacks) and
 * maintains the view id. 
 */
public abstract class AbstractView extends View {

    protected final HTMLPanel html;
    protected final String viewId;

    public AbstractView(String title) {
        
        viewId = HTMLPanel.createUniqueId();

        SafeHtmlBuilder sb = new SafeHtmlBuilder();

        sb.appendHtmlConstant("<div class='toolbar' id='abstractPanelCaption-"+viewId+"'>");

        sb.appendHtmlConstant("<h1>");
        sb.appendEscaped(title);
        sb.appendHtmlConstant("</h1>");
        sb.appendHtmlConstant("</div>");

        // html customization hook
        htmlCallback(sb);

        html = new HTMLPanel(sb.toSafeHtml());
        html.getElement().setAttribute("id", viewId);

        widgetCallback(html);
    }

    /**
     * Called while running the html builder.
     * Used to customize the actual HTML structure.
     * @param sb
     */
    protected void htmlCallback(SafeHtmlBuilder sb)
    {

    }

    /**
     * Called after the Panel was created.
     * Used to bind GWT widgets to the HTML structure.
     */
    protected void widgetCallback(HTMLPanel widget)
    {
                
    }

    public void addButton(String name, final ClickHandler handler)
    {
        String s = "<a class=\"button\" href='#'>"+name+"</a>";
        XHtmlWidget btn = new XHtmlWidget(s);
        btn.addClickHandler(handler);

        html.add(btn, "abstractPanelCaption-"+viewId);
    }

    public void addBackButton(String name, final ClickHandler handler)
    {
        String s = "<a class=\"button back\">"+name+"</a>";
        XHtmlWidget btn = new XHtmlWidget(s);
        btn.addClickHandler(handler);

        html.add(btn, "abstractPanelCaption-"+viewId);
    }

    public void addCancelButton(String name, final ClickHandler handler)
    {
        String s = "<a class=\"button cancel\">"+name+"</a>";
        XHtmlWidget btn = new XHtmlWidget(s);
        btn.addClickHandler(handler);

        html.add(btn, "abstractPanelCaption-"+viewId);
    }

    @Override
    public Widget asWidget() {
        return html; 
    }
}
