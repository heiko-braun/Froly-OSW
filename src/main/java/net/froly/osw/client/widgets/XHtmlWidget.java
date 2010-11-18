package net.froly.osw.client.widgets;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Widget;

/**
 * A custom XHTML widget that supports {@link com.google.gwt.event.dom.client.ClickHandler}'s
 * 
 * @see net.froly.osw.client.widgets.ListPanel
 */
public class XHtmlWidget extends Widget implements HasClickHandlers {

    private static Element xBLANK_DIV = Document.get().createDivElement(); 
    private static Element xBLANK_X= xDiv();

    protected XHtmlWidget(){
        this("<div/>");
    }

    public XHtmlWidget(String xHtml){

        setElement(
                xMakeHtml(
                        (xHtml.startsWith("<") ? xHtml :
                                "<div style='display:inherit;'> "+xHtml+" </div>").replaceAll("\r|\n", "<br/>")
                )
        );

        sinkEvents(Event.ONMOUSEDOWN);
        sinkEvents(Event.ONMOUSEUP);
    }

    public XHtmlWidget(Element x){
        setElement(x);
    }

    public static Element xDiv(){
        return xBLANK_DIV.cloneNode(false).cast();
    }
    public static Element xMakeHtml(String x){
        xBLANK_X.setInnerHTML(x);
        /*if(xUserAgent.xStandards())//Non IE browsers don't have to forget their reference to the new child */
        return xBLANK_X.getFirstChildElement();
        
        /*Element xRet = xBLANK_X.getFirstChildElement();//Get the element created inside our div
        xBLANK_X=xDiv();//Reset the div so IE won't lose it's reference the next time the xBLANK_X is overwritten
        return xRet;*/
    }

    public HandlerRegistration addClickHandler(ClickHandler handler) {
        return addDomHandler(handler, ClickEvent.getType());
    }

    @Override
    public void onBrowserEvent(Event event) {
        int type = event.getTypeInt();
        if(Event.ONMOUSEDOWN==type)
        {
            Element child = getElement().getFirstChildElement();
            if(child!=null)  // jqTouch 'ul li a.active' selector
                child.addClassName("active");            
        }
        else if(Event.ONMOUSEUP==type)
        {
            Element child = getElement().getFirstChildElement();
            if(child!=null)
                child.removeClassName("active");
        }

        // delegate
        super.onBrowserEvent(event);
    }
}

