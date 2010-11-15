package net.froly.osw.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import net.froly.osw.client.OswClient;
import net.froly.osw.client.model.ActivityService;
import net.froly.osw.client.model.ActivityServiceAsync;
import net.froly.osw.client.model.Message;
import net.froly.osw.client.widgets.ContentListPanel;

import java.util.List;

/**
 * message panel
 */
public class MessageView extends ContentListPanel {


    private ActivityServiceAsync service = ActivityService.App.getInstance();

    final static ClickHandler noop = new ClickHandler()
    {
        public void onClick(ClickEvent event) {

        }
    };

    public MessageView() {
        super("Messages");
    }

    @Override
    protected void widgetCallback(HTMLPanel widget) {

        addBackButton("Back", new RevealHandler("home", View.SLIDERIGHT));        
        addButton("Refresh", new ClickHandler()
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

                        OswClient.loading(false);
                                                                        
                        addPre(new HTML("<div class='info'>"+result.size()+" Messages</div>"));

                        for(Message message : result)
                        {
                            SafeHtmlBuilder sb = new SafeHtmlBuilder();
                            sb.appendHtmlConstant("<div class='message-content' style='padding-right:50px;'>");
                            sb.appendHtmlConstant("<b style='color:#808080;'>"+message.getFrom()+"</b><br/>");
                            sb.appendEscaped(message.getMessage().replaceAll("\n", ""));
                            sb.appendHtmlConstant("</div>");

                            if(message.getNumReplies()>0)
                                sb.appendHtmlConstant("<small class='counter'>"+message.getNumReplies()+"</small>");

                            addContent(sb.toSafeHtml(), noop);

                        }
                    }
                });
            }
        });
    }
}
