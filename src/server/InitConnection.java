package server;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import javax.imageio.ImageIO;

public class InitConnection implements Runnable {
    private ServerSocket sc;
    private int port;
    private String password;

    public InitConnection(int port, String password) {
        this.port = port;
        this.password = password;
    }

    @Override
    public void run() {
        try {
            sc = new ServerSocket(port);
            System.out.println("Waiting for client to connect for screen sharing on port: " + port);
            Socket clientSocket = sc.accept();
            System.out.println("Client Connected for Screen Sharing");

            DataInputStream dis = new DataInputStream(clientSocket.getInputStream());
            DataOutputStream dos = new DataOutputStream(clientSocket.getOutputStream());

            String receivedPassword = dis.readUTF();
            System.out.println("Received Password: " + receivedPassword);

            if (receivedPassword.equals(password)) {
                dos.writeUTF("valid");
                GraphicsEnvironment gEnv = GraphicsEnvironment.getLocalGraphicsEnvironment();
                GraphicsDevice gDev = gEnv.getDefaultScreenDevice();
                Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                String width = String.valueOf(screenSize.width);
                String height = String.valueOf(screenSize.height);
                dos.writeUTF(width);
                dos.writeUTF(height);
                System.out.println("Password is valid. Screen sharing started with resolution: " + width + "x" + height);

                Rectangle screenBounds = new Rectangle(screenSize);
                Robot robot = new Robot(gDev);
                new SendScreen(clientSocket, robot, screenBounds);
                new ReceiveEvents(clientSocket, robot);
            } else {
                dos.writeUTF("invalid");
                System.out.println("Password is invalid. Connection closed.");
                clientSocket.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
