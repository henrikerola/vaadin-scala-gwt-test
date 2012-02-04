package com.github.henrikerola.vaadin.scalagwttest.widgetset.client.ui;

import com.google.gwt.dom.client.Document
import com.google.gwt.event.dom.client.ClickEvent
import com.google.gwt.event.dom.client.ClickHandler
import com.google.gwt.user.client.ui.Widget
import com.google.gwt.user.client.Event
import com.vaadin.terminal.gwt.client.ApplicationConnection
import com.vaadin.terminal.gwt.client.Paintable
import com.vaadin.terminal.gwt.client.UIDL
import com.google.gwt.user.client.ui.Composite
import com.google.gwt.user.client.ui.SimplePanel
import com.google.gwt.user.client.ui.FocusPanel

object VMyComponent {
  val className = "v-mycomponent";

  val varClick = "click";

  val attrClicks = "clicks";
  val attrMessage = "message";
}

class VMyComponent extends Composite with Paintable {

  var client: ApplicationConnection = _
  var paintableId: String = _

  val panel = new FocusPanel()
  initWidget(panel)
  panel.addClickHandler((_: ClickEvent) => {
    // Send a variable change to the server side component so it knows the widget has been clicked
    // The last parameter (immediate) tells that the update should be sent to the server
    // right away
    client.updateVariable(paintableId, VMyComponent.varClick, "left click", true);
  })

  override def updateFromUIDL(uidl: UIDL, client: ApplicationConnection): Unit = {
    // This call should be made first. 
    // It handles sizes, captions, tooltips, etc. automatically.
    if (client.updateComponent(this, uidl, false)) {
      // If client.updateComponent returns true there has been no changes and we
      // do not need to update anything.
      return
    }

    // Save reference to server connection object to be able to send
    // user interaction later
    this.client = client;

    // Save the client side identifier (paintable id) for the widget
    paintableId = uidl.getId();

    // Process attributes/variables from the server
    // The attribute names are the same as we used in 
    // paintContent on the server-side
    val clicks = uidl.getIntAttribute(VMyComponent.attrClicks);
    val message = uidl.getStringAttribute(VMyComponent.attrMessage);

    getElement().setInnerHTML("After <b>" + clicks + "</b> mouse clicks:\n" + message);
  }

  implicit def clickHandler(f: ClickEvent => Unit): ClickHandler = new ClickHandler {
    def onClick(event: ClickEvent) = f(event)
  }
}
