package server;

import javax.swing.*;
import java.io.*;
import java.net.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ServerFileReceiver implements Runnable {
    private int port;

    public ServerFileReceiver(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("File Receiver Server started, waiting for connections...");
            while (true) {
                try (Socket socket = serverSocket.accept();
                     DataInputStream dis = new DataInputStream(socket.getInputStream())) {

                    System.out.println("Client connected for file transfer");
                    String saveDirectory = chooseDirectory();

                    if (saveDirectory != null) {
                        File file = new File(saveDirectory, "received_file_" + System.currentTimeMillis());
                        try (FileOutputStream fos = new FileOutputStream(file)) {
                            byte[] buffer = new byte[4096];
                            int read;
                            while ((read = dis.read(buffer)) != -1) {
                                fos.write(buffer, 0, read);
                            }
                            System.out.println("File received successfully and saved to " + file.getAbsolutePath());
                        }
                    } else {
                        System.out.println("File save location was not selected. Transfer canceled.");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String chooseDirectory() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int returnValue = fileChooser.showSaveDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile().getAbsolutePath();
        }
        return null;
    }

    public static void main(String[] args) {
        Thread serverThread = new Thread(new ServerFileReceiver(5000));
        serverThread.start();
    }
}
