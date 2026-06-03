package org.example;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.MouseEvent;

public class AutoclickThread implements Runnable {
  public void run() {
    try {
      Robot robot = new Robot();
      while (true) {
        robot.mousePress(MouseEvent.BUTTON1_DOWN_MASK);
        robot.mouseRelease(MouseEvent.BUTTON1_DOWN_MASK);
      }
    } catch (AWTException e) {
      e.printStackTrace();
    }
  }
}
