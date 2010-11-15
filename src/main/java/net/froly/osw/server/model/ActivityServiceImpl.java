package net.froly.osw.server.model;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import net.froly.osw.client.model.ActivityService;
import net.froly.osw.client.model.Message;
import org.onesocialweb.client.Inbox;
import org.onesocialweb.client.OswService;
import org.onesocialweb.client.OswServiceFactory;
import org.onesocialweb.model.activity.ActivityEntry;
import org.onesocialweb.smack.OswServiceFactoryImp;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

public class ActivityServiceImpl extends RemoteServiceServlet implements ActivityService {

    private OswService osw = null;
    public static final int PORT = 5222;
    private static final String USERNAME = "heiko";
    private static final String PASSWORD = "12uy.Er4";

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        ServletContext context = config.getServletContext();
        if(null==context.getAttribute("osw"))
        {
            OswServiceFactory serviceFactory = new OswServiceFactoryImp();
            context.setAttribute("osw", serviceFactory.createService());
        }

        this.osw = (OswService)context.getAttribute("osw");
    }

    public List<Message> getMessages() {

        if(!osw.isConnected())
        {
            try {
                osw.connect("social.openliven.com", PORT, null);
                osw.login(USERNAME, PASSWORD, "console");
            } catch (Exception e) {
                throw new RuntimeException("Failed to connect", e);
            }
        }

        Inbox inbox = osw.getInbox();
        inbox.refresh();

        List<Message> messages = new ArrayList<Message>();
        for(ActivityEntry activity : inbox.getEntries())
        {
            String author = (activity.hasActor()) ? activity.getActor().getUri() : null;
            String status = (activity.hasTitle()) ? activity.getTitle() : null;
            String published = (activity.hasPublished()) ?
                    DateFormat.getDateTimeInstance(DateFormat.SHORT,
                            DateFormat.SHORT).format(activity.getPublished()) : null;


            Message message = new Message(activity.getId(), author, status);
            message.setDateFrom(published);
            message.setNumReplies(activity.getRepliesLink()!=null? activity.getRepliesLink().getCount() : 0);


            messages.add(message);

        }

        return messages;

    }


}