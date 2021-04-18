package saver;

import model.abstractmodel.AbstractPatientDataStruct;
import model.abstractmodel.AbstractPatientDatabaseModel;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.GregorianCalendar;

public class XmlSaver {
    public static void save(File file, AbstractPatientDatabaseModel database) throws ParserConfigurationException, TransformerException {
        DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
        Document document = documentBuilder.newDocument();

        Element root = document.createElement(XmlConstants.TABLE);
        root.setAttribute(XmlConstants.TABLE_NAME, database.getName());
        root.setAttribute(XmlConstants.TABLE_SIZE, String.valueOf(database.getDatabaseSize()));
        document.appendChild(root);
        elementsCreating(database, document, root);

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource domSource = new DOMSource(document);
        StreamResult streamResult = new StreamResult(file);
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.transform(domSource, streamResult);

    }

    private static void elementsCreating(AbstractPatientDatabaseModel database, Document document, Element root) {
        for (AbstractPatientDataStruct i : database) {
            Element element = document.createElement(XmlConstants.PATIENT);

            Element patientName = document.createElement(XmlConstants.PATIENT_NAME);
            patientName.appendChild(document.createTextNode(i.getPatientName().toString()));
            element.appendChild(patientName);

            Element patientSecondName = document.createElement(XmlConstants.PATIENT_SECOND_NAME);
            patientSecondName.appendChild(document.createTextNode(i.getPatientSecondName().toString()));
            element.appendChild(patientSecondName);

            Element patientFatherName = document.createElement(XmlConstants.PATIENT_FATHER_NAME);
            patientFatherName.appendChild(document.createTextNode(i.getPatientFatherName().toString()));
            element.appendChild(patientFatherName);

            Element addressOfRegistration = document.createElement(XmlConstants.ADDRESS_OF_REGISTRATION);
            addressOfRegistration.appendChild(document.createTextNode(i.getPatientAddressOfRegistration().toString()));
            element.appendChild(addressOfRegistration);

            Element birthDate = document.createElement(XmlConstants.BIRTH_DATE);
            birthDate.appendChild(document.createTextNode(String.valueOf(((GregorianCalendar) i.getPatientBirthDate()).getTimeInMillis())));
            element.appendChild(birthDate);

            Element acceptanceDate = document.createElement(XmlConstants.ACCEPTANCE_DATE);
            acceptanceDate.appendChild(document.createTextNode(String.valueOf(((GregorianCalendar) i.getPatientAcceptanceDate()).getTimeInMillis())));
            element.appendChild(acceptanceDate);

            Element doctorName = document.createElement(XmlConstants.DOCTOR_NAME);
            doctorName.appendChild(document.createTextNode(i.getDoctorName().toString()));
            element.appendChild(doctorName);

            Element doctorSecondName = document.createElement(XmlConstants.DOCTOR_SECOND_NAME);
            doctorSecondName.appendChild(document.createTextNode(i.getDoctorSecondName().toString()));
            element.appendChild(doctorSecondName);

            Element doctorFatherName = document.createElement(XmlConstants.DOCTOR_FATHER_NAME);
            doctorFatherName.appendChild(document.createTextNode(i.getDoctorFatherName().toString()));
            element.appendChild(doctorFatherName);

            Element conclusion = document.createElement(XmlConstants.CONCLUSION);
            conclusion.appendChild(document.createTextNode(i.getConclusion().toString()));
            element.appendChild(conclusion);

            root.appendChild(element);
        }
    }

}
