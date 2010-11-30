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
import net.froly.osw.client.widgets.ScrollContentListView;

import java.util.List;

public class ConversationView extends ScrollContentListView {

    private Message parent = null;

    public ConversationView() {
        super("Conversation");
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

    public void display(Message parent)
    {
        this.parent = parent;

        clearContent();

        addContent(renderMessage(getParent()), new MessageClickHandler(parent));

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
