package client;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.io.*;
import java.net.Socket;

//Class to create GUI for sending files to the server
public class FileSenderGUI extends JFrame {

    private final File[] fileToSend = new File[1];
    private final String serverIp;
    private final int serverPort;

    //constructor to initialize the file sender GUI
    public FileSenderGUI(String serverIp, int serverPort) {
        this.serverIp = serverIp;
        this.serverPort = serverPort;
        initialize();
    }

    //Method to set up the GUI components
    private void initialize() {
        JFrame jFrame = new JFrame("File Sender");
        jFrame.setSize(450, 450);
        jFrame.setLayout(new BoxLayout(jFrame.getContentPane(), BoxLayout.Y_AXIS));
        jFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel jlTitle = new JLabel("File Sender");
        jlTitle.setFont(new Font("Arial", Font.BOLD, 25));
        jlTitle.setBorder(new EmptyBorder(20, 0, 10, 0));
        jlTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel jlFileName = new JLabel("Choose a file to send.");
        jlFileName.setFont(new Font("Arial", Font.BOLD, 20));
        jlFileName.setBorder(new EmptyBorder(50, 0, 0, 0));
        jlFileName.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel jpButton = new JPanel();
        jpButton.setBorder(new EmptyBorder(75, 0, 10, 0));

        JButton jbSendFile = new JButton("Send File");
        jbSendFile.setPreferredSize(new Dimension(150, 75));
        jbSendFile.setFont(new Font("Arial", Font.BOLD, 20));

        JButton jbChooseFile = new JButton("Choose File");
        jbChooseFile.setPreferredSize(new Dimension(150, 75));
        jbChooseFile.setFont(new Font("Arial", Font.BOLD, 20));

        jpButton.add(jbSendFile);
        jpButton.add(jbChooseFile);

        //Open file chooser dialogue to select the files
        jbChooseFile.addActionListener(e -> {
            JFileChooser jFileChooser = new JFileChooser();
            jFileChooser.setDialogTitle("Choose a file to send.");
            if (jFileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                fileToSend[0] = jFileChooser.getSelectedFile();
                jlFileName.setText("The file you want to send is: " + fileToSend[0].getName());
            }
        });
        //Sends the selected files
        jbSendFile.addActionListener(e -> {
            if (fileToSend[0] == null) {
                jlFileName.setText("Please choose a file to send first!");
            } else {
                sendFileToServer(fileToSend[0]);
            }
        });

        jFrame.add(jlTitle);
        jFrame.add(jlFileName);
        jFrame.add(jpButton);
        jFrame.setVisible(true);
    }
    
    //Method to send the files to the server
    public void sendFileToServer(File file) {
        try (Socket socket = new Socket(serverIp, serverPort);
             DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
             FileInputStream fis = new FileInputStream(file)) {

            dos.writeUTF("FILE_TRANSFER");
            dos.writeUTF(file.getName());
            dos.writeLong(file.length());

            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                dos.write(buffer, 0, bytesRead);
            }
            dos.flush();
            JOptionPane.showMessageDialog(this, "File sent: " + file.getName(), "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Failed to send file: " + ex.getMessage(), "File Transfer Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new FileSenderGUI("localhost", 5000); // Ensure the port number matches the server
    }
}

