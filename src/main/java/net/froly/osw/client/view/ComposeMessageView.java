package net.froly.osw.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTMLPanel;
import net.froly.osw.client.OswClient;
import net.froly.osw.client.Tokens;
import net.froly.osw.client.model.ActivityService;
import net.froly.osw.client.model.Message;
import net.froly.osw.client.widgets.AbstractPanel;
import net.froly.osw.client.widgets.XHtmlWidget;

public class ComposeMessageView extends AbstractPanel {

    private boolean isReply = false;

    public ComposeMessageView() {
        super("Compose");    
    }

    @Override
    protected void htmlCallback(SafeHtmlBuilder sb) {
        sb.appendHtmlConstant("<h2>New Message</h2>");
        sb.appendHtmlConstant("<form id='form-"+viewId+"' method='post'>");
        sb.appendHtmlConstant("<ul class='edit rounded'>");
        sb.appendHtmlConstant("<li><textarea id='msg-"+viewId+"' name='msg' placeholder='Your message contents ...'></textarea></li>");
        sb.appendHtmlConstant("</ul>");
        sb.appendHtmlConstant("</form>");
        sb.appendHtmlConstant("<div style='padding:10px' id='submit-"+viewId+"'/>");        
    }

    @Override
    protected void widgetCallback(HTMLPanel widget) {

        addCancelButton("Cancel", new ClickHandler()
        {
            @Override
            public void onClick(ClickEvent clickEvent) {
                doneOrCancel();
            }
        });

        XHtmlWidget button = new XHtmlWidget("<a href='#' class='whitebutton' style='color:black'>Send</a>");
        button.addClickHandler(new ClickHandler()
        {
            @Override
            public void onClick(ClickEvent clickEvent) {
                String payload = getMsgText(viewId);

                if(null==payload || payload.equals(""))
                    return;

                Message message  = new Message();
                message.setMessage(payload);

                ActivityService.App.getInstance().sendMessage(
                        message, new AsyncCallback<Void>()
                        {
                            @Override
                            public void onFailure(Throwable throwable) {
                                GWT.log("Failed to send message", throwable);
                            }

                            @Override
                            public void onSuccess(Void aVoid) {
                                doneOrCancel();    
                            }
                        }
                );

            }
        }
        );
        html.add(button, "submit-"+viewId);
    }

    private void doneOrCancel() {
        OswClient.getViewManagement().showView(getCancelTarget(), View.FADE);
        // clear state
        setIsReply(false);
    }

    public void setIsReply(boolean isReply) {
        this.isReply = isReply;
    }

    private String getCancelTarget()
    {
        String target = isReply ? Tokens.MESSSAGE_COMPOSE : Tokens.MESSSAGES;
        return target;
    }

    public static native String getMsgText(String viewId) /*-{        
        return $wnd.document.forms["form-"+viewId].elements[0].value;
    }-*/;
}
