//package server;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//
//public class SetPassword extends JFrame implements ActionListener {
//    private static final long serialVersionUID = 1L;
//    static String port = "4907";
//    JButton SUBMIT;
//    JPanel panel;
//    JLabel label1, label;
//    JTextField text1;
//    String value1;
//
//    SetPassword() {
//        label1 = new JLabel("Set Password");
//        text1 = new JTextField(15);
//        label = new JLabel("");
//        this.setLayout(new BorderLayout());
//        SUBMIT = new JButton("SUBMIT");
//        panel = new JPanel(new GridLayout(2, 1));
//        panel.add(label1);
//        panel.add(text1);
//        panel.add(label);
//        panel.add(SUBMIT);
//        add(panel, BorderLayout.CENTER);
//        SUBMIT.addActionListener(this);
//        setTitle("Set Password to connect to the Client");
//    }
//
//    public void actionPerformed(ActionEvent ae) {
//        value1 = text1.getText();
//        dispose();
//        new Thread(new InitConnection(Integer.parseInt(port), value1)).start();
//        new Thread(new ServerFileReceiver(5000)).start();
//    }
//
//    public String getValue1() {
//        return value1;
//    }
//
//    public static void main(String[] args) {
//        SetPassword frame1 = new SetPassword();
//        frame1.setSize(300, 80);
//        frame1.setLocation(500, 300);
//        frame1.setVisible(true);
//    }
//}
//



package server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SetPassword extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;
    static String port = "4907";
    JButton SUBMIT;
    JPanel panel;
    JLabel label1, label;
    JTextField text1;
    String value1;

    SetPassword() {
        label1 = new JLabel("Set Password");
        text1 = new JTextField(15);
        label = new JLabel("");
        this.setLayout(new BorderLayout());
        SUBMIT = new JButton("SUBMIT");
        panel = new JPanel(new GridLayout(2, 1));
        panel.add(label1);
        panel.add(text1);
        panel.add(label);
        panel.add(SUBMIT);
        add(panel, BorderLayout.CENTER);
        SUBMIT.addActionListener(this);
        setTitle("Set Password to connect to the Client");
    }

    public void actionPerformed(ActionEvent ae) {
        value1 = text1.getText();
        dispose();
        new Thread(new InitConnection(Integer.parseInt(port), value1)).start();
        new Thread(new ServerFileReceiver(5000)).start();
        EventQueue.invokeLater(() -> {
            ShutDownServer mainScreen = new ShutDownServer();
            mainScreen.setVisible(true);
        });
    }

    public String getValue1() {
        return value1;
    }

    public static void main(String[] args) {
        SetPassword frame1 = new SetPassword();
        frame1.setSize(300, 80);
        frame1.setLocation(500, 300);
        frame1.setVisible(true);
    }
}
