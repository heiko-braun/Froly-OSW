package net.froly.osw.server;

import org.jivesoftware.smack.XMPPConnection;
import org.onesocialweb.smack.OswServiceImp;

public class OswServiceExtension extends OswServiceImp
    {
        public XMPPConnection getConnection()
        {
            return connection;
        }
    }