package net.froly.osw.client.model;

import com.google.code.gwt.storage.client.Storage;
import net.froly.osw.client.OswClient;


public class ReadFlags {

    private final static String READ_FLAG = "osw.read.";

    public static void markRead(Message message)
    {
        Storage storage = OswClient.getStorage();
        storage.setItem(READ_FLAG+message.getId(), Boolean.TRUE.toString()+";"+"num="+message.getNumReplies());
    }

    public static boolean isRead(Message message)
    {
        boolean result = false;
        Storage storage = OswClient.getStorage();

        String key = READ_FLAG + message.getId();
        String item = storage.getItem(key);
        if(item !=null)
        {
            String[] values = item.split(";");
            String numCommentsValue = values[1].split("=")[1];
            result = (message.getNumReplies()==Integer.valueOf(numCommentsValue));
        }

        return result;
    }
}
