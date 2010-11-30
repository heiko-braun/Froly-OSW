package net.froly.osw.client.view;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import net.froly.osw.client.OswClient;
import net.froly.osw.client.Tokens;
import net.froly.osw.client.widgets.ListView;
import net.froly.osw.client.widgets.XHtmlWidget;

public class HomeView extends ListView {

    private XHtmlWidget status;

    public HomeView() {
        super("OneSocialWeb");
    }

    @Override
    protected void htmlCallback(SafeHtmlBuilder sb) {
        super.htmlCallback(sb);
        
        sb.appendHtmlConstant("<div id='status-"+viewId+"'/>");
        sb.appendHtmlConstant("<div class='content'>Add this page to your home screen to view it in full screen mode.</div>");
    }

    @Override
    protected void widgetCallback(HTMLPanel widget) {
        HTML welcome = new HTML("<center>A truely open, decentralized Social Network.</center>");
        welcome.setStyleName("content");

        addPre(welcome);

        addItem("Messages", new ClickHandler()
        {
            public void onClick(ClickEvent event) {
                OswClient.getViewManagement().showView(Tokens.MESSSAGES);
            }
        });

        addItem("Contacts", new RevealHandler(Tokens.CONTACTS));
        addItem("Settings", new RevealHandler(Tokens.SETTINGS));

        addButton("About", new RevealHandler(Tokens.ABOUT, View.SLIDEUP));

        // ---------------

        status = new XHtmlWidget("<div class='info'/>");
        html.add(status, "status-"+viewId);

    }

    public void updateStatus(String msg) {
        status.getElement().setInnerHTML(msg);
    }   
}

