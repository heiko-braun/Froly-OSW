package net.froly.osw.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTMLPanel;
import net.froly.osw.client.OswClient;
import net.froly.osw.client.Tokens;
import net.froly.osw.client.ViewManagement;
import net.froly.osw.client.model.*;
import net.froly.osw.client.widgets.ScrollContentListView;

import java.util.List;

/**
 * Top most message list view.
 * Unfolds into conversations and message detail.
 */
public class MessageListView extends ScrollContentListView {

    private ActivityServiceAsync service = ActivityService.App.getInstance();

    private List<Message> messages = null;

    public MessageListView() {
        super("Messages");

        OswClient.getMessageModel().addMessageReadHandler(
                new MessageReadEventHandler()
                {
                    @Override
                    public void onMessageRead(MessageReadEvent event) {
                        
                        List<Message> messageList = getMessages();
                        if(messageList !=null)
                        {
                            render(messageList);
                        }
                    }
                }
        );
    }

    public List<Message> getMessages() {
        return messages;
    }

    @Override
    protected void widgetCallback(HTMLPanel widget) {

        super.widgetCallback(widget);

        addBackButton("Home", OswClient.NOOP_HANDLER);

        addBottom("Refresh", new ClickHandler()
        {
            public void onClick(ClickEvent event) {

                assert service!=null : "ActivityService is null";

                OswClient.loading(true);
                clearContent();

                service.getMessages(new AsyncCallback<List<Message>>()
                {
                    public void onFailure(Throwable e) {
                        OswClient.loading(false);
                        GWT.log("Failed to retrieve messages", e);
                    }

                    public void onSuccess(List<Message> result) {

                        update(result);
                    }

                });
            }
        });


        // bottom toolbar
        super.addBottom("Compose", new RevealHandler(Tokens.MESSSAGE_COMPOSE, View.SLIDEUP));

    }

    private void update(List<Message> result) {

        this.messages = result;
        
        OswClient.loading(false);

        render(result);
    }

    private void render(List<Message> result) {
        
        clearContent();
        
        for(final Message message : result)
        {
            SafeHtmlBuilder sb = new SafeHtmlBuilder();

            boolean isRead = ReadFlags.isRead(message);

            sb.appendHtmlConstant("<div class='message-content' style='padding-right:50px;'>");
            sb.appendHtmlConstant("<b style='color:#808080;'>"+message.getFrom()+"</b><br/>");
            if(isRead) sb.appendHtmlConstant("<div style='color:#808080;'>");
            sb.appendEscaped(message.getMessage().replaceAll("\n", ""));
            if(isRead) sb.appendHtmlConstant("</div>");
            sb.appendHtmlConstant("</div>");

            if(message.getNumReplies()>0)
                sb.appendHtmlConstant("<small class='counter'>"+message.getNumReplies()+"</small>");

            addContent(sb.toSafeHtml(), new ClickHandler()
            {
                @Override
                public void onClick(ClickEvent clickEvent) {

                    ViewManagement viewManagement = OswClient.getViewManagement();

                    if(message.getNumReplies()==0)
                    {
                        // revel it
                        viewManagement.showView(Tokens.MESSSAGE_DETAIL);

                        // no replies, direct view
                        MessageDetailView detail = (MessageDetailView)viewManagement.getView(Tokens.MESSSAGE_DETAIL);
                        detail.display(message);

                    }
                    else
                    {
                        // has replies, threaded conversation view
                        ConversationView conversation = (ConversationView)
                                viewManagement.getView(Tokens.MESSAGE_CONVERSATION);

                        // reveal it
                        viewManagement.showView(Tokens.MESSAGE_CONVERSATION);

                        // show message
                        conversation.display(message);
                    }

                }
            });

        }
    }
}
