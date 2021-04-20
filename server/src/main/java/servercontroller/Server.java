package servercontroller;

import gui.MainWindow;
import model.abstractmodel.AbstractPatientDatabaseModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

public class Server extends Thread {
    private static final Logger logger = LogManager.getLogger(MainWindow.class);

    private boolean isThreadRunning;
    private final List<WeakReference<ClientThread>> clients = new ArrayList<>();
    private ServerSocket serverSocket;
    private AbstractPatientDatabaseModel model;

    public Server(AbstractPatientDatabaseModel model) throws IOException {
        this.model = model;
    }

    public int getPort() {
        return serverSocket.getLocalPort();
    }

    public void upServer(int serverPort) throws IOException {
        if (serverSocket != null)
            closeServer();
        serverSocket = new ServerSocket(serverPort);
        logger.info("[Server] Server start on port " + serverSocket.getLocalPort());
    }

    public void closeServer() throws IOException {
        logger.debug("[Server] Closing server");
        for (WeakReference<ClientThread> client : clients) {
            client.get().closeStreams();
            logger.info("[Server] Closing client " + client.get().getId());
        }
        serverSocket.close();
        clients.clear();
        logger.info("[Server] Server socket closed");
    }

    public void interruptThread() {
        isThreadRunning = false;
    }

    @Override
    public void run() {
        isThreadRunning = true;
        while (isThreadRunning) {
            logger.debug("[Server] Start main cycle");
            if (serverSocket != null && !serverSocket.isClosed()) {
                try {
                    logger.info("[Server] Try to connect client...");
                    ClientThread clientThread = new ClientThread(serverSocket.accept(), model);
                    clients.add(new WeakReference<>(clientThread));
                    clientThread.start();
                    logger.info("[Server] Client " + clientThread.getId() + " connected");
                } catch (IOException e) {
                    logger.info("[Server] Client socked closed");
                }
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                logger.error("[Server] Something wrong with thread", e);
            }
        }
    }

}
