package net.froly.osw.client.view;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.Element;
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
    
    public MessageListView() {
        super("Messages");

        MessageStore messageModel = OswClient.getMessageStore();

        messageModel.addMessageReadHandler(
                new MessageReadEventHandler()
                {
                    @Override
                    public void onMessageRead(MessageReadEvent event)
                    {
                        Message updated = event.getMessage();
                        Element item = html.getElementById("msg-" + updated.getId());
                        if(item!=null)
                        {
                            System.out.println("mark read (inbox): "+item.getId());
                            item.addClassName("isRead");
                        }
                    }
                }
        );

        messageModel.addInboxUpdatedHandler(
                new InboxUpdatedEventHandler()
                {
                    @Override
                    public void onMessagesRefresh(InboxUpdatedEvent event)
                    {
                        render(event.getInbox());
                    }
                }
        );
    }    

    @Override
    protected void widgetCallback(HTMLPanel widget) {

        super.widgetCallback(widget);

        addBackButton("Home", OswClient.NOOP_HANDLER);

        addBottom("Refresh", new ClickHandler()
        {
            public void onClick(ClickEvent event) {

                clearContent();

                OswClient.getMessageStore().refresh();
            }
        });


        // bottom toolbar
        super.addBottom("Compose", new RevealHandler(Tokens.MESSSAGE_COMPOSE, View.SLIDEUP));

    }

    private void render(List<Message> result) {
        
        clearContent();
        
        for(final Message message : result)
        {
            SafeHtml messageItem = renderMessage(message);

            addContent(messageItem, new ClickHandler()
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

                        // reveal it
                        viewManagement.showView(Tokens.MESSAGE_CONVERSATION);

                        // show message
                        OswClient.getConversationStore().loadConversation(message);
                    }

                }
            });

        }
    }

    private SafeHtml renderMessage(Message message) {
        SafeHtmlBuilder sb = new SafeHtmlBuilder();
        String readCss = ReadFlags.isRead(message) ? " isRead" : "";

        sb.appendHtmlConstant("<div id='msg-"+message.getId()+"' class='message-content"+readCss+"' style='padding-right:50px;'>");
        sb.appendHtmlConstant("<b style='color:#808080;'>"+message.getFrom()+"</b><br/>");
        sb.appendEscaped(message.getMessage().replaceAll("\n", ""));
        sb.appendHtmlConstant("</div>");

        if(message.getNumReplies()>0)
            sb.appendHtmlConstant("<small class='counter'>"+message.getNumReplies()+"</small>");

        return sb.toSafeHtml();
    }
}
