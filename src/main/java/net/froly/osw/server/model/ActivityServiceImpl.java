package net.froly.osw.server.model;

import net.froly.osw.client.model.ActivityService;
import net.froly.osw.client.model.Message;
import org.onesocialweb.client.Inbox;
import org.onesocialweb.client.OswService;
import org.onesocialweb.model.acl.*;
import org.onesocialweb.model.activity.*;
import org.onesocialweb.model.atom.AtomFactory;
import org.onesocialweb.model.atom.AtomGenerator;
import org.onesocialweb.model.atom.AtomReplyTo;
import org.onesocialweb.model.atom.DefaultAtomFactory;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

public class ActivityServiceImpl extends OswServiceServlet implements ActivityService {
    
    private ActivityFactory activityFactory = new DefaultActivityFactory();
    private AtomFactory atomFactory = new DefaultAtomFactory();
    private AclFactory aclFactory = new DefaultAclFactory();
    /*private RelationFactory relationFactory = new DefaultRelationFactory();
    private VCard4Factory profileFactory = new DefaultVCard4Factory();*/

    /** Default acl setting for activities */
    private List<AclRule> defaultRules;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        AclRule rule = aclFactory.aclRule();
        rule.addSubject(aclFactory.aclSubject(null, AclSubject.EVERYONE));
        rule.addAction(aclFactory.aclAction(AclAction.ACTION_VIEW, AclAction.PERMISSION_GRANT));
        defaultRules = new ArrayList<AclRule>();
        defaultRules.add(rule);
                
    }

    public List<Message> getMessages() {

        OswService osw = getOrCreateService();

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

            // recipients
            Iterator<AtomReplyTo> recipients = activity.getRecipients().iterator();
			while (recipients.hasNext()) {
				final AtomReplyTo recipient = recipients.next();
				final String recipientJID = extractRecipientJID(recipient.getHref());
                message.getRecipients().add(recipientJID);
            }

            messages.add(message);

        }

        return messages;

    }

    @Override
    public void sendMessage(Message msg) {

        final String message = msg.getMessage();

        ActivityObject object = activityFactory.object();
        object.setType(ActivityObject.STATUS_UPDATE);
        object.addContent(atomFactory.content(message, "text/plain", null));

        ActivityEntry entry = activityFactory.entry();
        entry.setPublished(Calendar.getInstance().getTime());
        entry.addVerb(activityFactory.verb(ActivityVerb.POST));
        entry.addObject(object);
        entry.setAclRules(defaultRules);
        entry.setTitle(message);

        AtomGenerator generator = atomFactory.generator();
        generator.setUri("http:/froly.net/osw-client");
        generator.setVersion("0.1");
        generator.setText("froly-osw");
        entry.setGenerator(generator);

        try {
            OswService osw = getOrCreateService();
            osw.postActivity(entry);
        } catch (Exception e) {
            throw new RuntimeException("Failed to send Message", e);
        }
    }

    @Override
    public void deleteMessage(String id) {
        
    }

    private String extractRecipientJID(String recipientHref) {
		if(recipientHref.startsWith("xmpp:")) {
			int i = recipientHref.indexOf("?");
			if(i == -1) {
				return "";
			}
			else {
				return recipientHref.substring(5, i);
			}
		}
		else {
			return recipientHref;
		}
	}

}