package servercontroller;

import gui.MainWindow;
import model.abstractmodel.AbstractPatientDatabaseModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.Socket;
import java.util.GregorianCalendar;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ClientThread extends Thread {

    enum ServerCommands {
        GET_DATABASE_SIZE,
        SET_DATABASE_NAME,
        GET_DATABASE_NAME,
        GET_DATABASE_PART,
        SEARCH,
        REMOVE,
        NOT_A_COMMAND
    }


    private static final Logger logger = LogManager.getLogger(MainWindow.class);
    private final Socket incoming;
    private final AbstractPatientDatabaseModel model;
    private OutputStream outputStream;
    private InputStream inputStream;
    Scanner in;
    PrintWriter out;

    public ClientThread(Socket incoming, AbstractPatientDatabaseModel model) {
        this.incoming = incoming;
        this.model = model;
    }

    public void closeStreams() throws IOException {
        out.close();
        in.close();
        outputStream.close();
        inputStream.close();
        incoming.close();
        logger.info("[Client] Client " + this.getId() + " was disconnected");
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
                String line = "";
                try {
                    line = in.nextLine();
                } catch (NoSuchElementException | IllegalStateException e) {
                    logger.warn("[Client] Something bad at Scanner");
                    isActive = false;
                }
                String command = line.trim();
                logger.info("[Client] Client " + this.getId() + " send command - " + command);
                commandChooser(command);
            }
            closeStreams();
        } catch (IOException e) {
            logger.warn("[Client] Something wrong at", e);
        }
    }

    private void commandChooser(String inputCommand) {
        String[] commandWithArguments = inputCommand.split(" ");

        ServerCommands command = ServerCommands.NOT_A_COMMAND;
        try {
            command = ServerCommands.valueOf(commandWithArguments[0]);
        } catch (IllegalArgumentException e){
            logger.warn("[Client] Not supported command = " + command);
        }
        switch (command) {
            case GET_DATABASE_SIZE -> out.println(model==null?-1:model.getDatabaseSize());
            case SET_DATABASE_NAME -> {
                if (model != null) model.setName(commandWithArguments[1]);
            }
            case GET_DATABASE_NAME -> out.println(model==null?"DATABASE_NOT_CONNECTED":model.getName());
            case GET_DATABASE_PART -> printDatabasePartToTheStream(commandWithArguments);
            case SEARCH -> {
            }
            case REMOVE -> {
            }
            case NOT_A_COMMAND -> out.println("NOT_SUPPORTED_COMMAND");

            default -> throw new IllegalStateException("Unexpected value: " + ServerCommands.valueOf(commandWithArguments[0]));
        }

        out.flush();

    }

    private void printDatabasePartToTheStream(String[] command) {
        int startIndex = Integer.parseInt(command[1]);
        int count = Integer.parseInt(command[2]);
        String[][] result = new String[count][10];
        var modelPart = model.getDatabasePart(startIndex, count);
        for (int i = 0; i < modelPart.length && modelPart[i] != null; i++) {
            result[i][0] = (String) modelPart[i].getPatientName();
            result[i][1] = (String) modelPart[i].getPatientSecondName();
            result[i][2] = (String) modelPart[i].getPatientFatherName();
            result[i][3] = (String) modelPart[i].getPatientAddressOfRegistration();
            result[i][4] = ((GregorianCalendar) modelPart[i].getPatientBirthDate()).getTime().toString();
            result[i][5] = ((GregorianCalendar) modelPart[i].getPatientAcceptanceDate()).getTime().toString();
            result[i][6] = (String) modelPart[i].getDoctorName();
            result[i][7] = (String) modelPart[i].getDoctorSecondName();
            result[i][8] = (String) modelPart[i].getDoctorFatherName();
            result[i][9] = (String) modelPart[i].getConclusion();
        }
        for (int i = 0; i < count; i++) {
            for (int j = 0; j < 10; j++) {
                out.println(result[i][j]);
            }
        }

    }

}
