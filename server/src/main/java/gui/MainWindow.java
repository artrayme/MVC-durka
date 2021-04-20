package gui;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import servercontroller.Server;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class MainWindow extends JFrame {

    private static final Logger logger = LogManager.getLogger(MainWindow.class);

    private final Server server;

    private static final JTextArea SERVER_LOG_AREA = new JTextArea();

    private int serverPort = 0;
    private boolean isServerOn = false;
    private final JTextField addressField = new JTextField();
    private final JButton serverUpDownButton = new JButton();
    private final Box controlComponents = new Box(BoxLayout.X_AXIS);
    private final JLabel informationLabel = new JLabel();

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
        this.add(new JScrollPane(SERVER_LOG_AREA), BorderLayout.CENTER);
        this.add(informationLabel, BorderLayout.NORTH);
        this.add(controlComponents, BorderLayout.SOUTH);

        addressFieldInit();
        serverUpDownButtonInit();
        informationLabelInit();
        serverLogAreaInit();
    }

    public static void addLog(String logText) {
        SERVER_LOG_AREA.append(logText);
    }

    private void serverLogAreaInit() {
        SERVER_LOG_AREA.setEditable(false);
    }


    private void informationLabelInit() {
        informationLabel.setFont(new Font(informationLabel.getName(), Font.PLAIN, 20));
        updateServerStatusLabel();
    }

    private void serverUpDownButtonInit() {
        updateServerUpDownButtonText();
        serverUpDownButton.addActionListener(e -> {
            logger.debug("[Window] Server button clicked");
            logger.debug("[Window] isServerOn = " + isServerOn);
            isServerOn = !isServerOn;
            if (isServerOn)
                upServer();
            else
                downServer();
            updateServerUpDownButtonText();
            updateServerStatusLabel();
        });
    }

    private void downServer() {
        logger.debug("[Window] DownServer method");
        try {
            server.closeServer();
            logger.info("[Server] Server is down");
        } catch (IOException e) {
            logger.error("[Server] Error at server stop ", e);
        }
    }

    private void upServer() {
        logger.debug("[Window] UpServer method");
        try {
            server.upServer(serverPort);
            logger.info("[Server] Server is up");
        } catch (IOException e) {
            logger.error("[Server] Error at server starting ", e);
        }
    }

    private void updateServerStatusLabel() {
        logger.debug("[Window] Updating a status label");
        if (isServerOn) {
            informationLabel.setForeground(new Color(3, 144, 21));
            informationLabel.setText("The server runs on port " + server.getPort());
        } else {
            informationLabel.setForeground(Color.RED);
            informationLabel.setText("Server down");
        }
    }

    private void updateServerUpDownButtonText() {
        logger.debug("[Window] Updating a button text");
        if (isServerOn)
            serverUpDownButton.setText("Close server");
        else
            serverUpDownButton.setText("Start server");
    }

    private void addressFieldInit() {
        addressField.setText(String.valueOf(serverPort));
        addressField.addActionListener(e -> {
            setPort();
        });

    }

    private void setPort() {
        try {
            int temp = Integer.parseInt(addressField.getText());
            if (temp >= 0 && temp < 65535) {
                addressField.setBackground(Color.white);
                serverPort = temp;
            } else {
                addressField.setBackground(Color.red);
            }
        } catch (NumberFormatException e) {
            addressField.setBackground(Color.red);
        }
    }

}
