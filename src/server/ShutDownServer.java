package server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ShutDownServer extends JFrame {
    private static final long serialVersionUID = 1L;

    public ShutDownServer() {
        setTitle("Server Main Screen");
        setSize(400, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton shutdownButton = new JButton("Shutdown Server");
        shutdownButton.setFont(new Font("Serif", Font.BOLD, 18));
        shutdownButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                shutdownServer();
            }
        });

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(shutdownButton, BorderLayout.CENTER);
        getContentPane().add(panel, BorderLayout.CENTER);
    }

    private void shutdownServer() {
        // Implement server shutdown logic here
        System.out.println("Server is shutting down...");
        System.exit(0);
    }

    public static void main(String[] args) {
        ShutDownServer mainScreen = new ShutDownServer();
        mainScreen.setVisible(true);
    }
}
