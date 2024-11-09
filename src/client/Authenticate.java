package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
//Class to handle client authentication and establish the connection
class Authenticate extends JFrame implements ActionListener {
    private Socket cSocket = null;
    DataOutputStream psswrchk = null;
    DataInputStream verification = null;
    String verify = "";
    JButton SUBMIT;
    JPanel panel;
    JLabel label, label1;
    String width = "", height = "";
    final JTextField text1;
    private String serverIp;
    private int serverPort;
    private int fileTransferPort;
    private boolean screenSharingStarted = false;

    //Constructor to initialize the authenticate process
    Authenticate(Socket cSocket, String serverIp, int serverPort, int fileTransferPort) {
        this.cSocket = cSocket;
        this.serverIp = serverIp;
        this.serverPort = serverPort;
        this.fileTransferPort = fileTransferPort;
        label1 = new JLabel("Password");
        text1 = new JTextField(15);
        label = new JLabel("");
        SUBMIT = new JButton("SUBMIT");
        SUBMIT.addActionListener(this);
        panel = new JPanel(new GridLayout(2, 1));
        panel.add(label1);
        panel.add(text1);
        panel.add(label);
        panel.add(SUBMIT);
        add(panel, BorderLayout.CENTER);
        setTitle("LOGIN FORM");
        pack();
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        String value1 = text1.getText();
        try {
            psswrchk = new DataOutputStream(cSocket.getOutputStream());
            verification = new DataInputStream(cSocket.getInputStream());
            psswrchk.writeUTF(value1);
            System.out.println("Password sent: " + value1);
            verify = verification.readUTF();
            System.out.println("Verification result: " + verify);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (verify.equals("valid")) {
            try {
                width = verification.readUTF();
                height = verification.readUTF();
                System.out.println("Received screen dimensions: " + width + "x" + height);
            } catch (IOException e) {
                e.printStackTrace();
            }
            CreateFrame frame = new CreateFrame(cSocket, width, height, serverIp, fileTransferPort);
            screenSharingStarted = true;
            dispose();
        } else {
            System.out.println("Enter the valid password");
            JOptionPane.showMessageDialog(this, "Incorrect password", "Error", JOptionPane.ERROR_MESSAGE);
            dispose();
        }
    }

    //Method to check if screen sharing is started
    public boolean isScreenSharingStarted() {
        return screenSharingStarted;
    }
}
