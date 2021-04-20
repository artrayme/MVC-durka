package model.implementation;

import model.abstractmodel.AbstractPatientDatabaseModel;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Calendar;
import java.util.Scanner;

public class ServerDatabase implements AbstractPatientDatabaseModel {
    enum ServerCommands {
        GET_DATABASE_SIZE,
        SET_DATABASE_NAME,
        GET_DATABASE_NAME,
        GET_DATABASE_PART,
        SEARCH,
        REMOVE,
        NOT_A_COMMAND
    }
    private final Socket socket = new Socket();
    Scanner input;
    PrintWriter output;

    public ServerDatabase(String localhost, int port) throws IOException {
        socket.connect(new InetSocketAddress(localhost, port), 1000);
        input = new Scanner(socket.getInputStream());
        output = new PrintWriter(socket.getOutputStream(), true);
        output.println(ServerCommands.NOT_A_COMMAND);
        input.nextLine();
    }

    @Override
    public int getDatabaseSize() {
        output.println(ServerCommands.GET_DATABASE_SIZE);
        int result = input.nextInt();
        if (input.hasNextLine()){ input.nextLine(); }
        return result;
    }

    @Override
    public void add(String[] element) {

    }

    @Override
    public int remove(String[] element) {
        return 0;
    }

    @Override
    public void remove(int index) {
    }

    @Override
    public String[] get(int index) {
//        return socket.getInputStream();
        return null;
    }

    @Override
    public void setName(String name) {
        output.println(ServerCommands.SET_DATABASE_NAME+" SomeDatabaseName");
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public AbstractPatientDatabaseModel search(String[] params) throws IOException {
        return null;
    }

    @Override
    public String[][] getDatabasePart(int start, int count) {
        output.println(ServerCommands.GET_DATABASE_PART + " " + start + " " + count);
        String[][] result = new String[count][10];

        for (int i = 0; i < count; i++) {
            for (int j = 0; j < 10; j++) {
                result[i][j] = input.nextLine();
            }
        }

        return result;
    }

    public boolean saveDatabaseToTheFile(String filename) {
        //ToDo;
        throw new UnsupportedOperationException();
//        return false;
    }

    public boolean loadDatabaseFromTheFile(String filename) {
        throw new UnsupportedOperationException();
//        return false;
    }

    public void setServerAddress(String ip, int port) throws IOException {
        socket.close();
        socket.connect(new InetSocketAddress(ip, port), 1000);
    }
}
