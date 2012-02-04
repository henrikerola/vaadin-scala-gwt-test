package com.github.henrikerola.vaadin.scalagwttest

import com.vaadin.Application
import vaadin.scala.Label
import vaadin.scala.Window

class ScalaApplication extends Application {

  def init(): Unit = {
		val mainWindow = new Window("Vaadin_scala_gwt_test Application");
		setMainWindow(mainWindow);
		
		mainWindow.add(new MyComponent());
  }
}