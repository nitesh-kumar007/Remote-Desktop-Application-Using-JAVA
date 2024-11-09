package server;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import javax.imageio.ImageIO;

class SendScreen extends Thread {
    private Socket socket = null;
    private Robot robot = null;
    private Rectangle rectangle = null;
    private boolean continueLoop = true;
    private OutputStream oos = null;

    public SendScreen(Socket socket, Robot robot, Rectangle rect) {
        this.socket = socket;
        this.robot = robot;
        this.rectangle = rect;
        start();
    }

    public void run() {
        try {
            oos = socket.getOutputStream();
        } catch (IOException ex) {
            ex.printStackTrace();
            return;
        }

        while (continueLoop) {
            try {
                BufferedImage image = robot.createScreenCapture(rectangle);
                ImageIO.write(image, "jpeg", oos);
                oos.flush();  // Ensure the stream is flushed

                Thread.sleep(100);  // Adjusted sleep for better performance
            } catch (IOException ex) {
                ex.printStackTrace();
                continueLoop = false;  // Stop loop on IO error
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();  // Reset interrupt status
            }
        }

        try {
            oos.close();  // Close output stream when done
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
