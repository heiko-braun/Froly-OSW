package net.froly.osw.client.view;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import net.froly.osw.client.OswClient;

public class RevealHandler implements ClickHandler {

    private final String target;
    private final String anim;

    public RevealHandler(String target) {
        this(target, View.SLIDELEFT);
    }

    public RevealHandler(String target, String anim) {
        this.target = target;
        this.anim = anim;
    }

    public void onClick(ClickEvent event) {
        OswClient.getViewManagement().showView(target, anim);        
    }
}
