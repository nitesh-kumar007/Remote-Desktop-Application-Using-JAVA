package server;

import java.awt.Robot;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

class ReceiveEvents extends Thread {
    Socket socket = null;
    Robot robot = null;
    boolean continueLoop = true;

    public ReceiveEvents(Socket socket, Robot robot) {
        this.socket = socket;
        this.robot = robot;
        start(); // Start the thread and hence calling run method
    }

    public void run() {
        Scanner scanner = null;
        try {
            scanner = new Scanner(socket.getInputStream());
            while (continueLoop) {
                // Receive commands and respond accordingly
                int command = scanner.nextInt();
                switch (command) {
                    case -1:
                        robot.mousePress(scanner.nextInt());
                        break;
                    case -2:
                        robot.mouseRelease(scanner.nextInt());
                        break;
                    case -3:
                        robot.keyPress(scanner.nextInt());
                        break;
                    case -4:
                        robot.keyRelease(scanner.nextInt());
                        break;
                    case -5:
                        robot.mouseMove(scanner.nextInt(), scanner.nextInt());
                        break;
                    case -6:
                        int x1 = scanner.nextInt();
                        int y1 = scanner.nextInt();
                        int x2 = scanner.nextInt();
                        int y2 = scanner.nextInt();
                        int mouseButton = scanner.nextInt();
                        robot.mouseMove(x1, y1);
                        robot.mousePress(mouseButton);
                        robot.mouseMove(x2, y2);
                        robot.mouseRelease(mouseButton);
                        break;
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}