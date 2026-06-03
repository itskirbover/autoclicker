package org.example;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AbstractDocument;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class App {
  private static boolean isAutoclicking;
  private static Thread autoclickThread;

  public static void main(String[] args) throws AWTException {
    System.out.println(new App().getGreeting());
    try {
      UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
    } catch (Exception e) {
      try {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
      } catch (Exception e1) {
        e1.printStackTrace();
      }
    }
    JFrame frame = new JFrame("Autoclicker");
    JButton toggleACButton = new JButton("Toggle");
    JTextField minutesField = new JTextField();
    JTextField secondsField = new JTextField();
    JTextField millisecondsField = new JTextField();

    Robot robot = new Robot();
    isAutoclicking = false;
    int[] nums = { 0, 0, 100 };
    autoclickThread = null;

    toggleACButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if (isAutoclicking) {
          isAutoclicking = false;
          autoclickThread = null;
        } else {
          isAutoclicking = true;
          autoclickThread = new Thread(() -> {
            while (true) {
              int delay = ((nums[0] * 6000) + (nums[1] * 1000) + (nums[2]));
              System.out.println("Delay: " + delay + "ms");
              robot.mousePress(MouseEvent.BUTTON1_DOWN_MASK);
              robot.delay(delay);
              robot.mouseRelease(MouseEvent.BUTTON1_DOWN_MASK);
              if (!isAutoclicking) {
                break;
              }
            }
          });
          autoclickThread.start();
        }
      }
    });
    JTextField[] fields = { minutesField, secondsField, millisecondsField };

    for (int i = 0; i < fields.length; i++) {
      final int index = i;
      fields[index].setPreferredSize(new Dimension(60, 40));
      AbstractDocument doc = (AbstractDocument) fields[index].getDocument();
      doc.setDocumentFilter(new DocumentFilter() {
        @Override
        public void insertString(FilterBypass fb, int offset, String string, javax.swing.text.AttributeSet attr)
            throws javax.swing.text.BadLocationException {
          if (string.matches("\\d+")) {
            super.insertString(fb, offset, string, attr);
            nums[index] = Integer.parseInt(fields[index].getText());
          }
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, javax.swing.text.AttributeSet attrs)
            throws javax.swing.text.BadLocationException {
          int maxCharacters = 3;
          int currentLength = fb.getDocument().getLength();
          int futureLength = currentLength + text.length() - length;

          if (text.matches("\\d+") && futureLength <= maxCharacters) {
            super.replace(fb, offset, length, text, attrs);
            // i dont fucking know what this does but it works so im not gonna question it
            nums[index] = Integer.parseInt(fields[index].getText());
          }
        }

        @Override
        public void remove(FilterBypass fb, int offset, int length) throws javax.swing.text.BadLocationException {
          super.remove(fb, offset, length);
          if (fields[index].getText().isEmpty()) {
            nums[index] = 0;
          } else {
            nums[index] = Integer.parseInt(fields[index].getText());
          }
        }
      });
    }
    toggleACButton.setPreferredSize(new Dimension(120, 40));

    frame.add(minutesField);
    frame.add(secondsField);
    frame.add(millisecondsField);
    frame.add(toggleACButton);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setLayout(new FlowLayout());
    frame.setSize(400, 300);
    frame.setVisible(true);

  }

  public String getGreeting() {
    return "hi";
  }
}
