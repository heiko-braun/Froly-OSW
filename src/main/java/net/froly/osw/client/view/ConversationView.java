package net.froly.osw.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTMLPanel;
import net.froly.osw.client.OswClient;
import net.froly.osw.client.Tokens;
import net.froly.osw.client.ViewManagement;
import net.froly.osw.client.model.ActivityService;
import net.froly.osw.client.model.Message;
import net.froly.osw.client.widgets.ScrollContentListPanel;

import java.util.List;

public class ConversationView extends ScrollContentListPanel {

    private Message message = null;

    public ConversationView() {
        super("Conversation");
    }

    @Override
    protected void widgetCallback(HTMLPanel widget) {

        super.widgetCallback(widget);

        addBackButton("Messages", new RevealHandler(Tokens.MESSSAGES, View.SLIDERIGHT));

        // bottom toolbar
        super.addBottom("Reply", new RevealHandler(Tokens.MESSSAGE_COMPOSE, View.SLIDEUP));

    }

    public void update()
    {
        clearContent();

        addContent(renderMessage(getParent()), new MessageClickHandler(message));

        ActivityService.App.getInstance().getReplies(
            getParent().getId(),new AsyncCallback<List<Message>>()
                {
                    @Override
                    public void onFailure(Throwable e) {
                        GWT.log("Failed to get replies", e);
                    }

                    @Override
                    public void onSuccess(List<Message> result) {
                        for(final Message message : result)
                        {
                            addContent(renderMessage(message), new MessageClickHandler(message));
                        }
                    }
                }
        );
    }

    private static SafeHtml renderMessage(Message message)
    {
        SafeHtmlBuilder sb = new SafeHtmlBuilder();

        sb.appendHtmlConstant("<div class='message-content' style='padding-right:50px;'>");
        sb.appendHtmlConstant("<b style='color:#808080;'>"+message.getFrom()+"</b><br/>");
        sb.appendEscaped(message.getMessage().replaceAll("\n", ""));
        sb.appendHtmlConstant("</div>");

        return sb.toSafeHtml();
    }
        
    public Message getParent() {
        return message;
    }

    public void setParent(Message message) {
        this.message = message;
    }

    class MessageClickHandler implements ClickHandler
    {
        private Message msg;

        MessageClickHandler(Message msg) {
            this.msg = msg;
        }

        @Override
        public void onClick(ClickEvent clickEvent) {
            // update view
            ViewManagement viewManagement = OswClient.getViewManagement();
            MessageDetailView detail = (MessageDetailView)viewManagement.getView(Tokens.MESSSAGE_DETAIL);
            detail.setConversation(true);
            detail.display(this.msg);

            // reveal it
            viewManagement.showView(Tokens.MESSSAGE_DETAIL);
        }
    }
}
