package net.froly.osw.client.view;

import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import net.froly.osw.client.Tokens;
import net.froly.osw.client.model.Message;
import net.froly.osw.client.widgets.AbstractPanel;


public class MessageDetailView extends AbstractPanel {

    public MessageDetailView() {
        super("Message Detail");    
    }

    @Override
    protected void htmlCallback(SafeHtmlBuilder sb) {
        sb.appendHtmlConstant("<div id='message-detail-"+viewId+"'/>");
        sb.appendHtmlConstant("<ul class='individual' id='message-buttons-"+viewId+"'/>");        
    }

    @Override
    protected void widgetCallback(HTMLPanel widget) {
        addBackButton("Back", new RevealHandler(Tokens.MESSSAGES, View.SLIDERIGHT));

        //HTML replyBtn = new HTML("<li><a href='#'>Reply</a></li>");
        //html.add(replyBtn, "message-buttons-"+viewId);
    }

    public void display(Message message)
    {
        // clear
        final String targetId = "message-detail-" + viewId;
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

