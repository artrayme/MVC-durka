package model.implementation;

import model.abstractmodel.AbstractPatientDatabaseModel;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class ServerDatabase implements AbstractPatientDatabaseModel {


    enum ServerCommands {
        GET_DATABASE_SIZE,
        SET_DATABASE_NAME,
        GET_DATABASE_NAME,
        GET_DATABASE_PART,
        ADD,
        SEARCH,
        SEARCHED_SIZE,
        REMOVE,
        SAVE_TO_FILE,
        LOAD_FROM_FILE,
        NOT_A_COMMAND,
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
        if (input.hasNextLine()) {
            input.nextLine();
        }
        return result;
    }

    @Override
    public void add(String[] element) {
        output.println(ServerCommands.ADD);
        for (int i = 0; i < element.length; i++) {
            output.println(element[i]);
        }
    }

    @Override
    public int remove(String[] params) {
        output.println(ServerCommands.REMOVE);
        for (int i = 0; i < params.length; i++) {
            output.println(params[i]);
        }
        int result = input.nextInt();
        if (input.hasNextLine()) {
            input.nextLine();
        }
        return result;
    }

    @Override
    public void remove(int index) {
        throw new UnsupportedOperationException();
    }

    public int sizeOfSearched(String[] paterns){
        output.println(ServerCommands.SEARCHED_SIZE);
        for (int i = 0; i < paterns.length; i++) {
            output.println(paterns[i]);
        }

        int result = input.nextInt();
        if (input.hasNextLine()) {
            input.nextLine();
        }
        return result;
    }

    @Override
    public void setName(String name) {
        output.println(ServerCommands.SET_DATABASE_NAME + " SomeDatabaseName");
    }

    @Override
    public String getName() {
        output.println(ServerCommands.GET_DATABASE_NAME);
        return input.nextLine();
    }

    @Override
    public String[][] search(String[] params) throws IOException {
        output.println(ServerCommands.SEARCH);
        for (int i = 0; i < params.length; i++) {
            output.println(params[i]);
        }
        int size = Integer.parseInt(input.nextLine().trim());
        String[][] result = new String[size][10];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < 10; j++) {
                result[i][j] = input.nextLine();
            }
        }
        return result;
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
        output.println(ServerCommands.SAVE_TO_FILE + " " + filename);
        System.out.println("result = X" );
        String result = input.nextLine();
        System.out.println("result = " + result);
        return result.equals("true");
    }

    public boolean loadDatabaseFromTheFile(String filename) {
        output.println(ServerCommands.LOAD_FROM_FILE + " " + filename);
        String result = input.nextLine();
        return result.equals("true");
    }

    public void setServerAddress(String ip, int port) throws IOException {
        socket.close();
        socket.connect(new InetSocketAddress(ip, port), 1000);
    }
}
