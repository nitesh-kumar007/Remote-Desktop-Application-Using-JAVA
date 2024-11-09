package client;

import java.net.Socket;
import javax.swing.*;

//Class to Start the Client-side Application
public class Start extends JFrame {
    private static final long serialVersionUID = 1L;
    private static String port = "4907";
    private String serverIp;
    private ClientFileSender fileSender;

    //Main Method to display the Welcome Screen
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            WelcomeScreen welcomeScreen = new WelcomeScreen();
            welcomeScreen.setVisible(true);
        });
    }

    //Method to initialize the client-side Application
    public void initialize() {
        serverIp = JOptionPane.showInputDialog("Please enter Server's IP Address");
        int fileTransferPort = 5000; // Port for file transfer

        try {
            Socket sc = new Socket(serverIp, Integer.parseInt(port));
            System.out.println("Connecting to the Server for Screen Sharing");
            Authenticate frame1 = new Authenticate(sc, serverIp, Integer.parseInt(port), fileTransferPort);
            frame1.setSize(300, 80);
            frame1.setLocation(500, 300);
            frame1.setVisible(true);

            // Wait until screen sharing is started before initiating file transfer
            new Thread(() -> {
                // Ensure screen sharing is established before proceeding
                while (!frame1.isScreenSharingStarted()) {
                    try {
                        Thread.sleep(100); // Check every 100 ms
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }

                // Start file transfer functionality after screen sharing is established
                fileSender = new ClientFileSender(serverIp, fileTransferPort);
                fileSender.connectAndSendFile();
            }).start();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}