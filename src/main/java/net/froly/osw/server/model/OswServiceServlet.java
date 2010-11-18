package net.froly.osw.server.model;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import org.jivesoftware.smack.XMPPConnection;
import org.onesocialweb.client.OswService;
import org.onesocialweb.client.OswServiceFactory;
import org.onesocialweb.client.exception.ConnectionRequired;
import org.onesocialweb.smack.OswServiceFactoryImp;
import org.onesocialweb.smack.OswServiceImp;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;
import java.util.logging.Logger;

public class OswServiceServlet extends RemoteServiceServlet {

    private static Logger log = Logger.getLogger(OswServiceServlet.class.getName());

    public static final int PORT = 5222;
    private static final String USERNAME = "heiko";
    private static final String PASSWORD = "12uy.Er4";
    
    protected OswService getOrCreateService()
    {
        HttpSession session = getThreadLocalRequest().getSession();
        if(null== session.getAttribute("osw"))
        {
            log.info("Create OSW service");
            OswServiceFactory serviceFactory = new OswServiceFactoryImp()
            {
                @Override
                public OswService createService() {
                    return new OswServiceExtension(); 
                }
            };
            session.setAttribute("osw", new ServiceHolder(serviceFactory.createService()));
        }

        OswService osw = ((ServiceHolder) session.getAttribute("osw")).getService();
        
        if(!osw.isConnected()) {
            log.info("Not connected. Attempt login ...");
            connectAndLogin(osw);
        }

        return osw;
    }

    protected void connectAndLogin(OswService osw)
    {
        try {
            osw.connect("social.openliven.com", PORT, null);
            osw.login(USERNAME, PASSWORD, "console");
        } catch (Exception e) {
            throw new RuntimeException("Failed to connect", e);
        }

    }
    
    /**
     * Ensure cleanup when session is being destroyed
     */
    private class ServiceHolder implements HttpSessionBindingListener
    {
        private OswService service;

        private ServiceHolder(OswService service) {
            this.service = service;
        }

        @Override
        public void valueBound(HttpSessionBindingEvent httpSessionBindingEvent) {
            //
        }

        @Override
        public void valueUnbound(HttpSessionBindingEvent httpSessionBindingEvent) {

            log.info("Destroy OSW service");

            try {
                service.disconnect();
            } catch (ConnectionRequired connectionRequired) {
                connectionRequired.printStackTrace();
            }
        }

        public OswService getService() {
            return service;
        }
    }

    class OswServiceExtension extends OswServiceImp
    {
        public XMPPConnection getConnection()
        {
            return connection;
        }
    }
}
