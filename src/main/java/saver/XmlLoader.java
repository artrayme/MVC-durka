package saver;

import model.abstractmodel.AbstractPatientDatabaseModel;
import model.implementation.DataStruct;
import model.implementation.Database;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;

public class XmlLoader {

    public static AbstractPatientDatabaseModel load(File file) throws ParserConfigurationException, SAXException, IOException {

        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();

        SAXReaderHandler saxReaderHandler = new SAXReaderHandler();
        parser.parse(file, saxReaderHandler);

        return saxReaderHandler.getResult();
    }

    private static class SAXReaderHandler extends DefaultHandler {
        private final Logger log = Logger.getLogger(XmlLoader.class.getName());

        private AbstractPatientDatabaseModel database;

        private String lastElementName;

        private String patientName;
        private String patientSecondName;
        private String patientFatherName;
        private String addressOfRegistration;
        private long patientBirthDate;
        private long patientAcceptanceDate;
        private String doctorName;
        private String doctorSecondName;
        private String doctorFatherName;
        private String conclusion;

        @Override
        public void startElement(String uri, String localName, String element, Attributes attributes) {
            lastElementName = element;
            if (attributes.getLength() != 0) {
                if (element.equals(XmlConstants.TABLE)) {
                    database = new Database(Integer.parseInt(attributes.getValue(XmlConstants.TABLE_SIZE)));
                    database.setName(attributes.getValue(XmlConstants.TABLE_NAME));
                }
            }
        }

        @Override
        public void endElement(String uri, String localName, String element) {
            if (element.equals(XmlConstants.PATIENT)) {
                Calendar birthDate = new GregorianCalendar();
                birthDate.setTimeInMillis(patientBirthDate);
                Calendar acceptanceDate = new GregorianCalendar();
                acceptanceDate.setTimeInMillis(patientAcceptanceDate);
                database.add(new DataStruct(patientName, patientSecondName, patientFatherName, addressOfRegistration,
                        birthDate, acceptanceDate, doctorName, doctorSecondName, doctorFatherName, conclusion));
            }
        }

        @Override
        public void characters(char[] ch, int start, int length) {
            String nodeText = new String(ch, start, length);
            nodeText = nodeText.replace("\n", "").trim();

            if (!nodeText.isEmpty()) {
                if (lastElementName.equals(XmlConstants.PATIENT_NAME)) {
                    patientName = nodeText;
                } else if (lastElementName.equals(XmlConstants.PATIENT_SECOND_NAME)) {
                    patientSecondName = nodeText;
                } else if (lastElementName.equals(XmlConstants.PATIENT_FATHER_NAME)) {
                    patientFatherName = nodeText;
                } else if (lastElementName.equals(XmlConstants.ADDRESS_OF_REGISTRATION)) {
                    addressOfRegistration = nodeText;
                } else if (lastElementName.equals(XmlConstants.BIRTH_DATE)) {
                    try {
                        patientBirthDate = Long.parseLong(nodeText);
                    } catch (NumberFormatException exception) {
                        log.log(Level.INFO, "Something wrong with patient's birth date: " + nodeText);
                    }
                } else if (lastElementName.equals(XmlConstants.ACCEPTANCE_DATE)) {
                    try {
                        patientAcceptanceDate = Long.parseLong(nodeText);
                    } catch (NumberFormatException exception) {
                        log.log(Level.INFO, "Something wrong with patient's acceptance date: " + nodeText);
                    }
                } else if (lastElementName.equals(XmlConstants.DOCTOR_NAME)) {
                    doctorName = nodeText;
                } else if (lastElementName.equals(XmlConstants.DOCTOR_SECOND_NAME)) {
                    doctorSecondName = nodeText;
                } else if (lastElementName.equals(XmlConstants.DOCTOR_FATHER_NAME)) {
                    doctorFatherName = nodeText;
                } else if (lastElementName.equals(XmlConstants.CONCLUSION)) {
                    conclusion = nodeText;
                } else {
                    log.log(Level.INFO, "Node ignored: " + nodeText);
                }
            }
        }

        public AbstractPatientDatabaseModel getResult() {
            return database;
        }
    }

}
