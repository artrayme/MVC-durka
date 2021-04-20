package servercontroller;

import gui.MainWindow;
import model.abstractmodel.AbstractPatientDataStruct;
import model.abstractmodel.AbstractPatientDatabaseModel;
import model.implementation.DataStruct;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;
import saver.XmlLoader;
import saver.XmlSaver;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.*;
import java.net.Socket;
import java.util.*;

public class ClientThread extends Thread {

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

    private static final Logger logger = LogManager.getLogger(MainWindow.class);
    private final Socket incoming;
    private AbstractPatientDatabaseModel model;
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

    private void addPatient() {
        model.add(new DataStruct(in.nextLine(), in.nextLine(), in.nextLine(), in.nextLine(), in.nextLine(), in.nextLine(), in.nextLine(), in.nextLine(), in.nextLine(), in.nextLine()));
    }

    private void searchPatient() {
        AbstractPatientDatabaseModel resultModel = searching();

        String[][] result = new String[resultModel.getDatabaseSize()][10];
        var modelPart = resultModel.getDatabasePart(0, resultModel.getDatabaseSize());
        arrayCreating(result, modelPart);
        out.println(result.length);
        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < 10; j++) {
                out.println(result[i][j]);
            }
        }
    }


    private void searchedSize() {
        AbstractPatientDatabaseModel result = searching();
        out.println(result.getDatabaseSize());
    }

    private AbstractPatientDatabaseModel searching() {
        String patientName = in.nextLine();
        String patientSecondName = in.nextLine();
        String patientFatherName = in.nextLine();
        String addressOfRegistration = in.nextLine();
        Calendar birthDate = null;
        try {
            long time = Long.parseLong(in.nextLine());
            birthDate = new GregorianCalendar();
            birthDate.setTimeInMillis(time);
        }catch (NumberFormatException e){

        }
        Calendar acceptanceDate = null;
        try {
            long time = Long.parseLong(in.nextLine());
            acceptanceDate = new GregorianCalendar();
            acceptanceDate.setTimeInMillis(time);
        }catch (NumberFormatException e){

        }
        String doctorName = in.nextLine();
        String doctorSecondName = in.nextLine();
        String doctorFatherName = in.nextLine();

        String conclusion = in.nextLine();

        AbstractPatientDatabaseModel resultModel = model;

        if (!patientName.isEmpty()) {
            resultModel = resultModel.searchPatientName(patientName);
        }
        if (!patientSecondName.isEmpty()) {
            resultModel = resultModel.searchPatientSecondName(patientSecondName);
        }
        if (!patientFatherName.isEmpty()) {
            resultModel = resultModel.searchPatientFatherName(patientFatherName);
        }
        if (!addressOfRegistration.isEmpty()) {
            resultModel = resultModel.searchAddressOfRegistration(addressOfRegistration);
        }
        if (birthDate != null) {
            resultModel = resultModel.searchBirthDate(birthDate);
        }
        if (acceptanceDate != null) {
            resultModel = resultModel.searchAcceptanceDate(acceptanceDate);
        }
        if (!doctorName.isEmpty()) {
            resultModel = resultModel.searchDoctorName(doctorName);
        }
        if (!doctorSecondName.isEmpty()) {
            resultModel = resultModel.searchDoctorSecondName(doctorSecondName);
        }
        if (!doctorFatherName.isEmpty()) {
            resultModel = resultModel.searchDoctorFatherName(doctorFatherName);
        }
        if (!conclusion.isEmpty()) {
            resultModel = resultModel.searchConclusion(conclusion);
        }
        return resultModel;
    }

    private void arrayCreating(String[][] result, AbstractPatientDataStruct[] modelPart) {
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
    }

    private void removePatient() {
        AbstractPatientDatabaseModel found = searching();
        for (AbstractPatientDataStruct patient : found)
            model.remove(patient);

        out.println(found.getDatabaseSize());

    }

    private void commandChooser(String inputCommand) {
        String[] commandWithArguments = inputCommand.split(" ");

        ServerCommands command = ServerCommands.NOT_A_COMMAND;
        try {
            command = ServerCommands.valueOf(commandWithArguments[0]);
        } catch (IllegalArgumentException e) {
            logger.warn("[Client] Not supported command = " + command);
        }
        switch (command) {
            case GET_DATABASE_SIZE -> out.println(model == null ? -1 : model.getDatabaseSize());
            case SET_DATABASE_NAME -> {
                if (model != null)
                    model.setName(commandWithArguments[1]);
            }
            case GET_DATABASE_NAME -> out.println(model == null ? "DATABASE_NOT_CONNECTED" : model.getName());
            case GET_DATABASE_PART -> printDatabasePartToTheStream(commandWithArguments);
            case ADD -> addPatient();
            case SEARCH -> searchPatient();
            case SEARCHED_SIZE -> searchedSize();
            case REMOVE -> removePatient();
            case SAVE_TO_FILE -> saveDatabaseToTheFile(commandWithArguments[1]);
            case LOAD_FROM_FILE -> loadDatabaseFromTheFile(commandWithArguments[1]);
            case NOT_A_COMMAND -> out.println("NOT_SUPPORTED_COMMAND");
            default -> throw new IllegalStateException("Unexpected value: " + ServerCommands.valueOf(commandWithArguments[0]));
        }

        out.flush();

    }

    private void loadDatabaseFromTheFile(String filename) {
        try {
            model = XmlLoader.load(new File(filename));
            out.println("true");
        } catch (ParserConfigurationException | SAXException | IOException e) {
            out.println("false");
            e.printStackTrace();
        }
    }

    private void saveDatabaseToTheFile(String filename) {
        try {
            XmlSaver.save(new File(filename),model);
            System.out.println("SERVER " + filename);
            out.println("true");
        } catch (ParserConfigurationException | TransformerException e) {
            out.println("false");
            e.printStackTrace();
        }
    }


    private void printDatabasePartToTheStream(String[] command) {
        int startIndex = Integer.parseInt(command[1]);
        int count = Integer.parseInt(command[2]);
        String[][] result = new String[count][10];
        var modelPart = model.getDatabasePart(startIndex, count);
        arrayCreating(result, modelPart);
        for (int i = 0; i < count; i++) {
            for (int j = 0; j < 10; j++) {
                out.println(result[i][j]);
            }
        }

    }


}
