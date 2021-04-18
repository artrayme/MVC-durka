package servercontroller;

import model.abstractmodel.AbstractPatientDatabaseModel;
import model.implementation.DataStruct;
import model.implementation.Database;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.GregorianCalendar;
import java.util.Scanner;

public class ClientThread extends Thread{
    private final Socket incoming;
    private AbstractPatientDatabaseModel model;
    private OutputStream outputStream;
    private InputStream inputStream;
    Scanner in;
    PrintWriter out;

    public ClientThread(Socket incoming, AbstractPatientDatabaseModel model) {
        System.out.println("new Client added");
        this.incoming = incoming;
        this.model = model;
    }

    public void closeStreams() throws IOException {
        out.close();
        in.close();
        outputStream.close();
        inputStream.close();
        incoming.close();
    }

    @Override
    public void run() {
        try {
            outputStream = incoming.getOutputStream();
            inputStream = incoming.getInputStream();
            in = new Scanner(inputStream);
            out = new PrintWriter(outputStream, true);
            boolean isActive = true;
            while (isActive) {
                out.println("Write something");
                String line = in.nextLine();
                String command = line.trim();
                switch (command) {
                    case "BYE": {
                        isActive = false;
                        break;
                    }
                    case "print": {
                        if (model == null)
                            out.println("model = null");
                        else
                            out.println("model size = " + model.getDatabaseSize());
                        break;
                    }
                    case "add": {
                        if (model == null)
                            out.println("error: model is null");
                        else {
                            model.add(new DataStruct("a", "b", "c", "d", new GregorianCalendar(), new GregorianCalendar(), "e", "j", "k", "qwe"));
                        }
                    }
                    case "new": {
                        model = new Database();
                    }
                }
            }
            incoming.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
