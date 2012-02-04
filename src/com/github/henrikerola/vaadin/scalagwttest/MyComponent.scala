package com.github.henrikerola.vaadin.scalagwttest;

import java.util.Map
import com.vaadin.terminal.PaintTarget
import com.vaadin.ui.ClientWidget
import com.vaadin.ui.AbstractComponent
import com.github.henrikerola.vaadin.scalagwttest.widgetset.client.ui.VMyComponent

@ClientWidget(value = classOf[VMyComponent])
class MyComponent extends AbstractComponent {

  var message = "Click here.";
  var clicks = 0;

  override def paintContent(target: PaintTarget): Unit = {
    super.paintContent(target);

    // Paint any component specific content by setting attributes
    // These attributes can be read in updateFromUIDL in the widget.
    target.addAttribute(VMyComponent.attrClicks, clicks);
    target.addAttribute(VMyComponent.attrMessage, message);

    // We could also set variables in which values can be returned
    // but declaring variables here is not required
  }

  override def changeVariables(source: AnyRef, variables: Map[String, Object]) {
    super.changeVariables(source, variables);
    
    // Variables set by the widget are returned in the "variables" map.
    if (variables.containsKey(VMyComponent.varClick)) {

      // When the user has clicked the component we increase the 
      // click count, update the message and request a repaint so 
      // the changes are sent back to the client.
      clicks = clicks + 1;
      message += "<br/>" + variables.get(VMyComponent.varClick);

      requestRepaint();
    }
  }
}
