package org.example;

import ch.bailu.gtk.gtk.Application;
import ch.bailu.gtk.gio.ApplicationFlags;
import ch.bailu.gtk.gtk.ApplicationWindow;
import ch.bailu.gtk.gtk.Button;
import ch.bailu.gtk.type.Strs;

public class App {
  public static void main(String[] args) {
    System.out.println(new App().getGreeting());
    var app = new Application("com.itskirbover.autoclicker", ApplicationFlags.FLAGS_NONE);
    app.onActivate(() -> {
      var window = new ApplicationWindow(app);
      var button = new Button();
      button.setLabel("Hello!");
      window.setChild(button);
      window.show();
    });
    var result = app.run(args.length, new Strs(args));
    System.exit(0);
  }

  public String getGreeting() {
    return "Hello World!";
  }
}
