package net.froly.osw.client;

import com.google.gwt.user.client.ui.RootPanel;
import net.froly.osw.client.view.View;

import java.util.HashMap;
import java.util.Map;

/**
 * Maintains references to the managed panels. 
 */
public class ViewManagement {
    private static Map<String, View> aliases = new HashMap<String, View>();
    
    public void addView(String alias, View view)
    {
        RootPanel.get().add(view.asWidget());
        aliases.put(alias, view);
    }

    public View getView(String alias)
    {
        return aliases.get(alias);   
    }

    public void showView(String alias)
    {
        View view = aliases.get(alias);
        if(view!=null)
            view.reveal(View.SLIDELEFT);
    }

    public void showView(String alias, String anim)
    {
        View view = aliases.get(alias);
        if(view!=null)
            view.reveal(anim);
    }
}
