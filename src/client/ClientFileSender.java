package client;

import javax.swing.*;
import java.io.*;
import java.net.Socket;

//Class to manage the file sending functions
public class ClientFileSender {
    private final String serverAddress;
    private final int serverPort;

    //Constructor to initialize the file sender with server details
    public ClientFileSender(String serverAddress, int serverPort) {
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
    }
   
    //method to connect and send the file using GUI
    public void connectAndSendFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select a file to send");

        int userSelection = fileChooser.showOpenDialog(null);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSend = fileChooser.getSelectedFile();
            try (Socket socket = new Socket(serverAddress, serverPort);
                 DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
                 FileInputStream fis = new FileInputStream(fileToSend)) {

                dos.writeUTF("FILE_TRANSFER");
                dos.writeUTF(fileToSend.getName());
                dos.writeLong(fileToSend.length());

                byte[] buffer = new byte[4096];
                int read;
                while ((read = fis.read(buffer)) > 0) {
                    dos.write(buffer, 0, read);
                }
                dos.flush();
                JOptionPane.showMessageDialog(null, "File sent: " + fileToSend.getName(), "Success", JOptionPane.INFORMATION_MESSAGE);

            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Failed to send file: " + ex.getMessage(), "File Transfer Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }
    }
}