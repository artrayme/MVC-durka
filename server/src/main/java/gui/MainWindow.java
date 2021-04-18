package gui;

import servercontroller.Server;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class MainWindow extends JFrame {

    private final Server server;

    private int serverPort = 8080;
    private boolean isServerOn = false;
    private final JTextField addressField = new JTextField();
    private final JButton serverUpDownButton = new JButton();
    private final Box controlComponents = new Box(BoxLayout.X_AXIS);
    private final JLabel informationLabel = new JLabel();
    private final LogTextArea serverLog = new LogTextArea();

    public MainWindow(Server server) {
        this.server = server;
        server.start();
        init();
    }

    private void init() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        controlComponents.add(serverUpDownButton);
        controlComponents.add(addressField);
        this.setLayout(new BorderLayout());
        this.add(new JScrollPane(serverLog), BorderLayout.CENTER);
        this.add(informationLabel, BorderLayout.NORTH);
        this.add(controlComponents, BorderLayout.SOUTH);

        addressFieldInit();
        serverUpDownButtonInit();
        informationLabelInit();
        serverLogAreaInit();
    }

    private void serverLogAreaInit() {
        //ToDO
//        serverLog.setEditable(false);
//        serverLog.setText("Test info");
    }

    private void informationLabelInit() {
        informationLabel.setFont(new Font(informationLabel.getName(), Font.PLAIN, 20));
        updateServerStatusLabel();
    }

    private void serverUpDownButtonInit() {
        updateServerUpDownButtonText();
        serverUpDownButton.addActionListener(e -> {
            isServerOn = !isServerOn;
            updateServerUpDownButtonText();
            updateServerStatusLabel();
            if (isServerOn) upServer();
            else downServer();

        });
    }

    private void downServer() {
        System.out.println("Down server");
        try {
            server.closeServer();
//            server.interrupt();
//            serverThread.interrupt();
        } catch (IOException e) {
            e.printStackTrace();
        }

//            serverThread = null;
    }

    private void upServer() {
        System.out.println("Up Server");
        try {
            server.upServer(serverPort);
//            server.join();
//            server.start();
        } catch (IOException ioException) {
            //ToDo
            ioException.printStackTrace();
        }
    }

    private void updateServerStatusLabel() {
        if (isServerOn) {
            informationLabel.setForeground(new Color(3, 144, 21));
            informationLabel.setText("The server runs on port " + serverPort);
        } else {
            informationLabel.setForeground(Color.RED);
            informationLabel.setText("Server down");
        }
    }

    private void updateServerUpDownButtonText() {
        if (isServerOn)
            serverUpDownButton.setText("Close server");
        else
            serverUpDownButton.setText("Start server");
    }


    private void addressFieldInit() {
        addressField.setText(String.valueOf(serverPort));
        try {
            serverPort = Integer.parseInt(addressField.getText());
            addressField.setBackground(Color.white);
        } catch (NumberFormatException e) {
            addressField.setBackground(Color.red);
        }
    }

}
