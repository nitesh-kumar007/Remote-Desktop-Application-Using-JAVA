package client;

import javax.swing.*;
import java.awt.*;

// Class representing the initial welcome screen for the client
public class WelcomeScreen extends JFrame {
    // Constructor to set up the welcome screen
    public WelcomeScreen() {
        setTitle("Welcome");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Label displaying the welcome message
        JLabel monogramLabel = new JLabel("Welcome to the Client Side!", JLabel.CENTER);
        monogramLabel.setFont(new Font("Arial", Font.BOLD, 20));
        add(monogramLabel, BorderLayout.CENTER);

        // Button to proceed to the client initialization
        JButton proceedButton = new JButton("Start Client");
        proceedButton.addActionListener(e -> onProceed());
        add(proceedButton, BorderLayout.SOUTH);
    }

    // Method to handle the action when the proceed button is clicked
    private void onProceed() {
        dispose(); // Close the welcome screen
        Start app = new Start(); // Start the client initialization process
        app.initialize();
    }

    // Main method to display the welcome screen
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            WelcomeScreen welcomeScreen = new WelcomeScreen();
            welcomeScreen.setVisible(true);
        });
    }
}
