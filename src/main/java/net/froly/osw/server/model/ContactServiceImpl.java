package net.froly.osw.server.model;

import net.froly.osw.client.model.Contact;
import net.froly.osw.client.model.ContactService;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

public class ContactServiceImpl extends OswServiceServlet implements ContactService {

    private static Logger log = Logger.getLogger(ContactServiceImpl.class.getName());

    @Override
    public List<Contact> getSubscriptions(String jid) {

        OswServiceExtension osw = (OswServiceExtension)getOrCreateService();

        List<Contact> results = new ArrayList<Contact>();

        Roster roster = osw.getConnection().getRoster();
        Collection<RosterEntry> rosterEntryCollection = roster.getEntries();
        for(RosterEntry rosterEntry : rosterEntryCollection)
        {
            Contact c = new Contact(rosterEntry.getName(), rosterEntry.getUser());
            results.add(c);
        }

        // match with subscriptions
        try {
            List<String> subscriptions = osw.getSubscriptions(jid);
            for(String subscriptionJid : subscriptions)
            {
                for(Contact c  : results)
                {
                    if(c.getUserId().equals(subscriptionJid))
                    {
                        c.setBeingFollowed(true);
                        break;
                    }
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("Failed to get subscriptions", e);
        }

        return results;
    }
}