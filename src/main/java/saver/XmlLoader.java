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

public class XmlLoader {

    public static AbstractPatientDatabaseModel load(File file) throws ParserConfigurationException, SAXException, IOException {

        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser;

        parser = factory.newSAXParser();

        SAXReaderHandler saxReaderHandler = new SAXReaderHandler();
        parser.parse(file, saxReaderHandler);

        return saxReaderHandler.getResult();
    }

    private static class SAXReaderHandler extends DefaultHandler {
        private AbstractPatientDatabaseModel database;

        private String lastElementName;

        private String patientName;
        private String addressOfRegistration;
        private long birthDate;
        private long acceptanceDate;
        private String doctorName;
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
                database.add(new DataStruct(patientName, addressOfRegistration, birthDate, acceptanceDate, doctorName, conclusion));
            }
        }

        @Override
        public void characters(char[] ch, int start, int length) {
            String nodeText = new String(ch, start, length);
            nodeText = nodeText.replace("\n", "").trim();

            if (!nodeText.isEmpty()) {
                if (lastElementName.equals(XmlConstants.PATIENT_NAME)) {
                    patientName = nodeText;
                } else if (lastElementName.equals(XmlConstants.ADDRESS_OF_REGISTRATION)) {
                    addressOfRegistration = nodeText;
                } else if (lastElementName.equals(XmlConstants.BIRTH_DATE)) {
                    birthDate = Long.parseLong(nodeText);
                } else if (lastElementName.equals(XmlConstants.ACCEPTANCE_DATE)) {
                    acceptanceDate = Long.parseLong(nodeText);
                } else if (lastElementName.equals(XmlConstants.DOCTOR_NAME)) {
                    doctorName = nodeText;
                } else if (lastElementName.equals(XmlConstants.CONCLUSION)) {
                    conclusion = nodeText;
                }
            }
        }

        public AbstractPatientDatabaseModel getResult() {
            return database;
        }
    }

}
