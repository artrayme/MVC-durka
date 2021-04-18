package servercontroller;

import model.abstractmodel.AbstractPatientDatabaseModel;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

public class Server extends Thread {
    private boolean isThreadRunning;
    private final List<WeakReference<ClientThread>> clients = new ArrayList<>();
    private ServerSocket serverSocket;
    private AbstractPatientDatabaseModel model;

    public Server(AbstractPatientDatabaseModel model) throws IOException {
        System.out.println("server constructor");
        this.model = model;
    }

    public void upServer(int serverPort) throws IOException {
        if (serverSocket != null)
            closeServer();
        serverSocket = new ServerSocket(serverPort);
    }

    public void closeServer() throws IOException {
        System.out.println("Closing Clients: " + clients.size());
        for (WeakReference<ClientThread> client : clients) {
            System.out.println("Close client " + client.get());
            client.get().closeStreams();
        }
        serverSocket.close();
    }

    public void interruptThread(){
        isThreadRunning = false;
    }

    @Override
    public void run() {
        isThreadRunning = true;
        while (isThreadRunning) {
//            System.out.println("run proishodit, server isClosed = " + serverSocket.isClosed() );
            if (serverSocket != null && !serverSocket.isClosed()) {
                try {
                    System.out.println("try to connect client");
                    ClientThread clientThread = new ClientThread(serverSocket.accept(), model);
                    clients.add(new WeakReference<ClientThread>(clientThread));
                    clientThread.start();
                } catch (IOException e) {
                    System.out.println("tot eror");
                    //ToDo
//                e.printStackTrace();
                }
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
