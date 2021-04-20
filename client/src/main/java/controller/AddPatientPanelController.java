package controller;

import model.abstractmodel.AbstractPatientDatabaseModel;

import java.util.Calendar;

public class AddPatientPanelController {
    protected String[] patientData;
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
        patientData = new String[]{patientName, patientSecondName, patientFatherName,
                addressOfRegistration, String.valueOf(birthDate.getTimeInMillis()), String.valueOf(acceptanceDate.getTimeInMillis()),
                doctorName, doctorSecondName, doctorFatherName, conclusion};
        if (model != null) {
            model.add(patientData);
            return true;
        }
        return false;
    }

}
