package net.froly.osw.client.widgets;

import com.google.gwt.user.client.ui.Widget;
import net.froly.osw.client.OswClient;

public abstract class View {

    public final static String SLIDELEFT = "slideleft";
    public final static String SLIDERIGHT = "slideright";
    public final static String FADE = "fade";

    public abstract Widget asWidget();

    public void reveal(String anim)
    {
        OswClient.goTo(getViewId(), anim);
    }

    public void reveal()
    {
        OswClient.goTo(getViewId(), FADE);
    }

    public String getViewId()
    {
      return asWidget().getElement().getAttribute("id"); 
    }   
}
