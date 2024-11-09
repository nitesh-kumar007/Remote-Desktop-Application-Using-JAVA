package server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WelcomeScreen extends JFrame {
    private static final long serialVersionUID = 1L;

    public WelcomeScreen() {
        setTitle("Welcome to the Server Application");
        setSize(400, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JLabel welcomeLabel = new JLabel("Welcome to the Server Application", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Serif", Font.BOLD, 20));
        JButton startButton = new JButton("Start Server");
        startButton.setFont(new Font("Serif", Font.BOLD, 18));
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startServer();
            }
        });
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(welcomeLabel, BorderLayout.CENTER);
        panel.add(startButton, BorderLayout.SOUTH);
        getContentPane().add(panel);
    }

    private void startServer() {
        // Close the welcome screen
        dispose();
        // Start the main application
        SetPassword frame1 = new SetPassword();
        frame1.setSize(300, 80);
        frame1.setLocation(500, 300);
        frame1.setVisible(true);
    }
    
    public static void main(String[] args) {
        WelcomeScreen welcomeScreen = new WelcomeScreen();
        welcomeScreen.setVisible(true);
    }
}
