package net.froly.osw.client.view;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import net.froly.osw.client.OswClient;
import net.froly.osw.client.widgets.ListPanel;

public class HomeView extends ListPanel {

    public HomeView() {
        super("Froly OSW");
    }

    @Override
    protected void widgetCallback(HTMLPanel widget) {
        HTML welcome = new HTML("<center>One true, open, decentralized social network.</center>");
        welcome.setStyleName("content");

        addPre(welcome);

        addItem("Messages", new ClickHandler()
        {
            public void onClick(ClickEvent event) {
                OswClient.getViewManagement().showView("messages");
            }
        });

        addItem("Contacts", new RevealHandler("contacts"));
        addItem("Settings", new RevealHandler("settings"));

        //addPost(new HTML("<div class='content' style='color:#808080'>We dream of a world where all social networks are connected and work together in a way similar to email. Our project aims to define a language to bridge these networks and make it easy for social networks to join a bigger social web. You're invited to help make this a reality.</div>"));
        
    }
}

