package net.froly.osw.server.model;

import net.froly.osw.client.model.Contact;
import net.froly.osw.client.model.ContactProfile;
import net.froly.osw.client.model.ContactService;
import net.froly.osw.server.AccountInfo;
import net.froly.osw.server.OswServiceExtension;
import net.froly.osw.server.ServiceHolder;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.onesocialweb.client.OswService;
import org.onesocialweb.client.OswServiceFactory;
import org.onesocialweb.model.vcard4.Profile;
import org.onesocialweb.smack.OswServiceFactoryImp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ContactServiceImpl extends OswServiceServlet implements ContactService {

    private Logger log = LoggerFactory.getLogger(getClass());
    private final static int PORT = 5222;
    
    @Override
    public boolean login(String user, String pass, String host) {

        HttpSession session = getThreadLocalRequest().getSession();
        AccountInfo account = new AccountInfo(user, pass, host);
        
        if(null == session.getAttribute("osw"))
        {
            log.info("Creating OSW service ...");
            OswServiceFactory serviceFactory = new OswServiceFactoryImp()
            {
                @Override
                public OswService createService() {
                    return new OswServiceExtension();
                }
            };

            OswService service = serviceFactory.createService();

            if(!service.isConnected()) {
                log.info("Attempt to login: {}", account);

                try {
                    service.connect(account.getHost(), PORT, null);
                    service.login(account.getUser(), account.getPass(), "console");
                } catch (Exception e) {
                    log.error("Failed to connect and login", e);
                    return false;
                }

            }

            session.setAttribute("osw", new ServiceHolder(service));
        }


        OswService osw = ((ServiceHolder) session.getAttribute("osw")).getService();
        return osw!=null;
}

    @Override
    public List<Contact> getSubscriptions(String jid) {

        OswServiceExtension osw = (OswServiceExtension) getService();

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

    @Override
    public ContactProfile getProfile(String jid) {

        OswServiceExtension osw = (OswServiceExtension) getService();

        try {
            Profile profile = osw.getProfile(jid);
            ContactProfile result = new ContactProfile();
            result.setJid(jid);
            result.setFullName(profile.getFullName());
            result.setPhotoUri(profile.getPhotoUri());
            result.setEmail(profile.getEmail());
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to get profile", e);
        }

    }
}