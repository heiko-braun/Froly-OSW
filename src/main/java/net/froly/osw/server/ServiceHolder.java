package net.froly.osw.server;

import org.onesocialweb.client.OswService;
import org.onesocialweb.client.exception.ConnectionRequired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

/**
 * Ensure cleanup when session is being destroyed
 */
public class ServiceHolder implements HttpSessionBindingListener
{
    private Logger log = LoggerFactory.getLogger(getClass());

    private OswService service;

    public ServiceHolder(OswService service) {
        this.service = service;
    }

    @Override
    public void valueBound(HttpSessionBindingEvent httpSessionBindingEvent) {
        log.info("Bound OSW service to session: "+httpSessionBindingEvent.getSession().getId());
    }

    @Override
    public void valueUnbound(HttpSessionBindingEvent httpSessionBindingEvent) {

        log.info("Destroy OSW service with session: " + httpSessionBindingEvent.getSession().getId());

        try {
            service.disconnect();
        } catch (ConnectionRequired connectionRequired) {
            log.error("Error on disconnect service", connectionRequired);
        }
    }

    public OswService getService() {
        return service;
    }
}
    
