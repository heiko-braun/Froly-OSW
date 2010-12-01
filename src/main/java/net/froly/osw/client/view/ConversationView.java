package net.froly.osw.client.view;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTMLPanel;
import net.froly.osw.client.OswClient;
import net.froly.osw.client.Tokens;
import net.froly.osw.client.ViewManagement;
import net.froly.osw.client.model.*;
import net.froly.osw.client.widgets.ScrollContentListView;

import java.util.List;

public class ConversationView extends ScrollContentListView {

    private Message parent = null;
    private List<Message> replies = null;

    public ConversationView() {
        super("Conversation");

        OswClient.getMessageModel().addMessageReadHandler(
                new MessageReadEventHandler()
                {
                    @Override
                    public void onMessageRead(MessageReadEvent event)
                    {
                        if(getParent()!=null && getReplies()!=null)
                        {
                            render(getParent(), getReplies());
                        }                        
                    }
                }
        );
    }

    public List<Message> getReplies() {
        return replies;
    }

    @Override
    protected void widgetCallback(HTMLPanel widget) {

        super.widgetCallback(widget);

        addBackButton("Messages", OswClient.NOOP_HANDLER);

        // bottom toolbar
        super.addBottom("Reply", new ClickHandler()
        {
            @Override
            public void onClick(ClickEvent clickEvent) {

                ViewManagement viewManagement = OswClient.getViewManagement();
                ComposeMessageView composer = (ComposeMessageView)viewManagement.getView(Tokens.MESSSAGE_COMPOSE);
                composer.setParent(getParent());
                viewManagement.showView(Tokens.MESSSAGE_COMPOSE, View.SLIDEUP);
            }
        });

    }

    public void display(final Message parent)
    {
        this.parent = parent;

        ActivityService.App.getInstance().getReplies(
                getParent().getId(),new AsyncCallback<List<Message>>()
                {
                    @Override
                    public void onFailure(Throwable e) {
                        Window.alert("Failed to get replies");
                    }

                    @Override
                    public void onSuccess(List<Message> result) {
                        replies = result;
                        render(parent, result);
                    }
                }
        );
    }

    private void render(Message parent, List<Message> result) {

        // clear screen
        clearContent();

        // render parent
        addContent(renderMessage(getParent()), new MessageClickHandler(parent));

        // render replies
        for(final Message message : result)
        {
            addContent(renderMessage(message), new MessageClickHandler(message));
        }
    }

    private static SafeHtml renderMessage(Message message)
    {
        SafeHtmlBuilder sb = new SafeHtmlBuilder();

        boolean flagged = ReadFlags.isRead(message);

        sb.appendHtmlConstant("<div class='message-content' style='padding-right:50px;'>");
        sb.appendHtmlConstant("<b style='color:#808080;'>"+message.getFrom()+"</b><br/>");
        if(flagged) sb.appendHtmlConstant("<div style='color:#808080;'>");
        sb.appendEscaped(message.getMessage().replaceAll("\n", ""));
        if(flagged) sb.appendHtmlConstant("</div>");
        sb.appendHtmlConstant("</div>");

        return sb.toSafeHtml();
    }

    private Message getParent() {
        return parent;
    }


    class MessageClickHandler implements ClickHandler
    {
        private final Message msg;

        MessageClickHandler(Message msg) {
            this.msg = msg;
        }

        @Override
        public void onClick(ClickEvent clickEvent) {
            // update view
            ViewManagement viewManagement = OswClient.getViewManagement();

            // reveal it
            viewManagement.showView(Tokens.MESSSAGE_DETAIL);

            MessageDetailView detail = (MessageDetailView)viewManagement.getView(Tokens.MESSSAGE_DETAIL);
            detail.setParent(getParent());
            detail.display(this.msg);
        }
    }
}
