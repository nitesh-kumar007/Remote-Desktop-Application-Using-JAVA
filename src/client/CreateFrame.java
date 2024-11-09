package client;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.Socket;
//Class which create the frame of screen receiving from server
public class CreateFrame extends JFrame {
    private Socket cSocket = null;
    private JPanel cPanel = null;
    private ClientFileSender fileSender;
    private String serverIp;
    private int serverPort;

    public CreateFrame(Socket s, String width, String height, String serverIp, int serverPort) {
        this.cSocket = s;
        this.serverIp = serverIp;
        this.serverPort = serverPort;
        this.fileSender = new ClientFileSender(serverIp, serverPort);
        double w = Double.parseDouble(width.trim());
        double h = Double.parseDouble(height.trim());

        // Main panel for screen sharing
        cPanel = new JPanel();
        cPanel.setPreferredSize(new Dimension((int) w, (int) h));
        cPanel.setLayout(new BorderLayout());

        // Container for screen sharing
        JPanel container = new JPanel();
        container.setLayout(new BorderLayout());
        container.add(cPanel, BorderLayout.CENTER);

        try {
            new ReceiveScreen(cSocket.getInputStream(), cPanel);
            new SendEvents(cSocket, cPanel, width, height);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        setTitle("Remote Control");
        setSize((int) w, (int) h + 50); // Adjust size to include space for the button
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        add(container, BorderLayout.CENTER); // Add container to the frame
    }
}
