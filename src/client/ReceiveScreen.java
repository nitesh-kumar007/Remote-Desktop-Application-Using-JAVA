package client;

import java.awt.Image;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

//Class to handle receiving screen from the server
class ReceiveScreen extends Thread {
    private InputStream inputStream = null;
    private JPanel panel = null;
    private boolean continueLoop = true;
    private Image image = null;

    //Constructor to initialize the screen receiver
    public ReceiveScreen(InputStream inputStream, JPanel panel) {
        this.inputStream = inputStream;
        this.panel = panel;
        start();
    }

    public void run() {
        try {
            while (continueLoop) {
                ByteArrayOutputStream buffer = new ByteArrayOutputStream();
                byte[] bytes = new byte[2560 * 1600];
                int count;

                // Read the image bytes
                while ((count = inputStream.read(bytes)) != -1) {
                    buffer.write(bytes, 0, count);
                    // Check for end of JPEG image marker
                    if (count > 2 && bytes[count - 2] == (byte) -1 && bytes[count - 1] == (byte) -39) {
                        break;
                    }
                }

                // Convert the byte array to an image
                byte[] imageData = buffer.toByteArray();
                if (imageData.length > 0) {
                    image = ImageIO.read(new ByteArrayInputStream(imageData));
                    panel.getGraphics().drawImage(image, 0, 0, panel.getWidth(), panel.getHeight(), panel);
                }
                buffer.reset();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Method to stop screen receiving
    public void close() {
        continueLoop = false;
        try {
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
