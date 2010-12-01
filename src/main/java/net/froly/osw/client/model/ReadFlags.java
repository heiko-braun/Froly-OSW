package net.froly.osw.client.model;

import com.google.code.gwt.storage.client.Storage;
import net.froly.osw.client.OswClient;

import java.util.ArrayList;
import java.util.List;


public class ReadFlags {

    private final static String READ_FLAG = "osw.read.";

    public static void markRead(Message message)
    {
        Storage storage = OswClient.getStorage();

        StringBuffer sb = new StringBuffer();
        sb.append(Boolean.TRUE.toString()).append(";");
        sb.append("num=").append(message.getNumReplies()).append(";");
                
        storage.setItem( READ_FLAG+message.getId(), sb.toString());
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

    public static List<String> getFlaggedMessages(List<Message> messages)
    {
        List<String> result = new ArrayList<String>();

        // render replies
        for(Message message : messages)
        {
            if(isRead(message))
                result.add(message.getId());
        }

        return result;
    }
}
