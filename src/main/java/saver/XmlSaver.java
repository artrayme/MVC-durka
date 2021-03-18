package saver;

import model.abstractmodel.AbstractPatientDataStruct;
import model.abstractmodel.AbstractPatientDatabaseModel;
import model.implementation.DataStruct;
import model.implementation.Database;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class XmlSaver {
    public static void save(File file, AbstractPatientDatabaseModel database) throws ParserConfigurationException {
        DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
        Document document = documentBuilder.newDocument();

        Element root = document.createElement(XmlConstants.TABLE);
        root.setAttribute(XmlConstants.TABLE_NAME, database.getName());
        root.setAttribute(XmlConstants.TABLE_SIZE, String.valueOf(database.getDatabaseSize()));
        document.appendChild(root);
        elementsCreating(database, document, root);

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = null;
        try {
            transformer = transformerFactory.newTransformer();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        }
        DOMSource domSource = new DOMSource(document);
        StreamResult streamResult = new StreamResult(file);
        try {
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(domSource, streamResult);
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

    private static void elementsCreating(AbstractPatientDatabaseModel database, Document document, Element root) {
        for (AbstractPatientDataStruct i: database){
            Element element = document.createElement(XmlConstants.PATIENT);

            Element patientName = document.createElement(XmlConstants.PATIENT_NAME);
            patientName.appendChild(document.createTextNode(i.getPatientName().toString()));
            element.appendChild(patientName);

            Element addressOfRegistration = document.createElement(XmlConstants.ADDRESS_OF_REGISTRATION);
            addressOfRegistration.appendChild(document.createTextNode(i.getPatientAddressOfRegistration().toString()));
            element.appendChild(addressOfRegistration);

            Element birthDate = document.createElement(XmlConstants.BIRTH_DATE);
            birthDate.appendChild(document.createTextNode(i.getPatientBirthDate().toString()));
            element.appendChild(birthDate);

            Element acceptanceDate = document.createElement(XmlConstants.ACCEPTANCE_DATE);
            acceptanceDate.appendChild(document.createTextNode(i.getPatientAcceptanceDate().toString()));
            element.appendChild(acceptanceDate);

            Element doctorName = document.createElement(XmlConstants.DOCTOR_NAME);
            doctorName.appendChild(document.createTextNode(i.getDoctorName().toString()));
            element.appendChild(doctorName);

            Element conclusion = document.createElement(XmlConstants.CONCLUSION);
            conclusion.appendChild(document.createTextNode(i.getConclusion().toString()));
            element.appendChild(conclusion);
            root.appendChild(element);
        }
    }

}
