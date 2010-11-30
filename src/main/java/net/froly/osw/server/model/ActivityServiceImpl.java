package net.froly.osw.server.model;

import net.froly.osw.client.model.ActivityService;
import net.froly.osw.client.model.Message;
import org.onesocialweb.client.Inbox;
import org.onesocialweb.client.OswService;
import org.onesocialweb.model.acl.*;
import org.onesocialweb.model.activity.*;
import org.onesocialweb.model.atom.AtomFactory;
import org.onesocialweb.model.atom.AtomReplyTo;
import org.onesocialweb.model.atom.DefaultAtomFactory;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import java.text.DateFormat;
import java.util.*;
import java.util.logging.Logger;

public class ActivityServiceImpl extends OswServiceServlet implements ActivityService {

    private static Logger log = Logger.getLogger(ActivityServiceImpl.class.getName());

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

        final OswService osw = getOrCreateService();
                
        Inbox inbox = osw.getInbox();
        inbox.refresh();

        List<Message> messages = new ArrayList<Message>();
        for(ActivityEntry activity : inbox.getEntries())
        {
            Message message = activityToMessage(activity);
            messages.add(message);
        }

        return messages;

    }

    private static Message activityToMessage(ActivityEntry activity) {
        String author = (activity.hasActor()) ? activity.getActor().getUri() : null;
        String status = (activity.hasTitle()) ? activity.getTitle() : null;
        String published = (activity.hasPublished()) ?
                DateFormat.getDateTimeInstance(DateFormat.SHORT,
                        DateFormat.SHORT).format(activity.getPublished()) : null;


        Message message = new Message(activity.getId(), author, status);
        message.setDateFrom(published);
        message.setNumReplies(activity.getRepliesLink()!=null? activity.getRepliesLink().getCount() : 0);

        // inline Urls
        /*List<String> urls = Util.extractUrls(status);
        if(!urls.isEmpty())
        {
            // replace inline urls with footnotes
            StringBuffer sb = new StringBuffer();
            int i=0;
            int cursor=0;
            for(String url : urls)
            {
                i++;
                int index = status.indexOf(url);
                sb.append(status.substring(cursor, index));
                sb.append(" [").append(i).append("] "); // replacement
                cursor = index+url.length(); // advance cursor
            }

            message.setMessage(sb.toString());
            message.getInlineUrls().addAll(urls);
        }  */


        // recipients
        Iterator<AtomReplyTo> recipients = activity.getRecipients().iterator();

        while (recipients.hasNext()) {
            final AtomReplyTo recipient = recipients.next();
            final String recipientJID = extractRecipientJID(recipient.getHref());
            message.getRecipients().add(recipientJID);
        }
        return message;
    }

    @Override
    public List<Message> getReplies(String id) {

        final OswService osw = getOrCreateService();

        Inbox inbox = osw.getInbox();
        ActivityEntry activityEntry = inbox.getEntry(id);

        List<Message> replies = new ArrayList<Message>();

        try {
            List<ActivityEntry> entries = osw.getReplies(activityEntry);
            for(ActivityEntry entry : entries)
                replies.add(activityToMessage(entry));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to fetch replies", e);
        }

        // Create-Date ASC
        Collections.reverse(replies);

        return replies;
    }

    @Override
    public void sendMessage(Message msg) {

        final String message = msg.getMessage();

        if(null==message)
            throw new RuntimeException("Message payload cannot be null");
        
        ActivityObject object = activityFactory.object();
        object.setType(ActivityObject.STATUS_UPDATE);
        object.addContent(atomFactory.content(message, "text/plain", null));

        ActivityEntry entry = activityFactory.entry();
        entry.setPublished(Calendar.getInstance().getTime());
        entry.addVerb(activityFactory.verb(ActivityVerb.POST));
        entry.addObject(object);
        entry.setAclRules(defaultRules);
        entry.setTitle(message);
        
        try {
            OswService osw = getOrCreateService();
            osw.postActivity(entry);
        } catch (Exception e) {
            throw new RuntimeException("Failed to send Message", e);
        }
    }

    @Override
    public void commentMessage(String parentId, Message msg) {

        final String message = msg.getMessage();
        if(null==message)
            throw new RuntimeException("Message payload cannot be null");
        
        final OswService osw = getOrCreateService();

        Inbox inbox = osw.getInbox();
        ActivityEntry parentActivity = inbox.getEntry(parentId);

        ActivityObject object = activityFactory.object();
		object.setType(ActivityObject.COMMENT);
		object.addContent(atomFactory.content(message, "text/plain", null));

		ActivityEntry commentEntry = activityFactory.entry();
		commentEntry.setPublished(Calendar.getInstance().getTime());
		commentEntry.addVerb(activityFactory.verb(ActivityVerb.POST));
		commentEntry.addObject(object);
		commentEntry.setAclRules(defaultRules);
		commentEntry.setTitle(message);
		commentEntry.setParentId(parentActivity.getId());
		commentEntry.setParentJID(parentActivity.getActor().getUri());

        try {
            osw.postComment(commentEntry);
        } catch (Exception e) {
            throw new RuntimeException("Failed to comment Message", e);
        }
    }

    @Override
    public void deleteMessage(String id) {
        OswService osw = getOrCreateService();
        try {
            osw.deleteActivity(id);
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete message", e);
        }
    }

    private static String extractRecipientJID(String recipientHref) {
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