package net.froly.osw.server.model;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import net.froly.osw.server.ServiceHolder;
import org.onesocialweb.client.OswService;

import javax.servlet.http.HttpSession;

public class OswServiceServlet extends RemoteServiceServlet {
   
    protected OswService getService()
    {
        HttpSession session = getThreadLocalRequest().getSession();

        ServiceHolder serviceHolder = (ServiceHolder) session.getAttribute("osw");
        if(serviceHolder!=null)
        {
            return serviceHolder.getService();
        }
        else
        {
            throw new RuntimeException("Login required");           
        }

    }      
}
