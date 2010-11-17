package net.froly.osw.client.view;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import net.froly.osw.client.OswClient;
import net.froly.osw.client.Tokens;
import net.froly.osw.client.ViewManagement;
import net.froly.osw.client.model.Message;
import net.froly.osw.client.widgets.ScrollContentListPanel;


public class MessageDetailView extends ScrollContentListPanel {

    public MessageDetailView() {
        super("Message Detail");    
    }

    @Override
    protected void widgetCallback(HTMLPanel widget) {
        addBackButton("Back", new RevealHandler(Tokens.MESSSAGES, View.SLIDERIGHT));

        //HTML replyBtn = new HTML("<li><a href='#'>Reply</a></li>");
        //html.add(replyBtn, "message-buttons-"+viewId);

        addBottom("Reply", new ClickHandler()
        {
            @Override
            public void onClick(ClickEvent clickEvent) {

                ViewManagement viewManagement = OswClient.getViewManagement();
                ComposeMessageView composer = (ComposeMessageView) viewManagement.getView(Tokens.MESSSAGE_COMPOSE);
                composer.setIsReply(true);
                viewManagement.showView(Tokens.MESSSAGE_COMPOSE, View.SLIDEUP);
            }
        });
    }

    public void display(Message message)
    {
        // clear
        final String targetId = "scroll-" + viewId;
        html.getElementById(targetId).setInnerHTML("<div/>");

        SafeHtmlBuilder sb = new SafeHtmlBuilder();
        sb.appendHtmlConstant("<div class='message' style='padding:12px; color:#fff;display: -webkit-box;'>");
        sb.appendHtmlConstant("<b style='font-size:small;color:#808080;'>From: "+message.getFrom()+"</b><br/>");
        sb.appendHtmlConstant("<b style='font-size:small;color:#808080;'>To: "+message.getRecipients()+"</b><br/>");
        sb.appendEscaped(message.getMessage().replaceAll("\n", ""));
        sb.appendHtmlConstant("</div>");
        
        //if(message.getNumReplies()>0)
        //    sb.appendHtmlConstant("<small class='counter'>"+message.getNumReplies()+"</small>");

        html.add(new HTML(sb.toSafeHtml()), targetId);

    }
}

