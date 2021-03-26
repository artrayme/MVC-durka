package controller.implementation;

import model.abstractmodel.AbstractPatientDataStruct;
import model.abstractmodel.AbstractPatientDatabaseModel;
import model.implementation.DataStruct;

import java.util.Calendar;

public class AddPatientPanelController {

    protected AbstractPatientDatabaseModel model;

    public AddPatientPanelController(AbstractPatientDatabaseModel model) {
        this.model = model;
    }

    public boolean addNewPatient(String patientName,
                                 String patientSecondName,
                                 String patientFatherName,
                                 String addressOfRegistration,
                                 Calendar birthDate,
                                 Calendar acceptanceDate,
                                 String doctorName,
                                 String doctorSecondName,
                                 String doctorFatherName,
                                 String conclusion) {
        AbstractPatientDataStruct patient = new DataStruct(patientName, patientSecondName, patientFatherName,
                addressOfRegistration, birthDate, acceptanceDate,
                doctorName, doctorSecondName, doctorFatherName, conclusion);
        model.add(patient);
        return true;
    }

}
