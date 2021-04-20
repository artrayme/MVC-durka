package controller;

import model.abstractmodel.AbstractPatientDatabaseModel;

import java.io.IOException;
import java.util.Calendar;

public class SearchPatientPanelController {

    protected final AbstractPatientDatabaseModel model;
    protected String[] params;

    public SearchPatientPanelController(AbstractPatientDatabaseModel model) {
        this.model = model;
    }

    public AbstractPatientDatabaseModel findPatients(String patientName,
                                                     String patientSecondName,
                                                     String patientFatherName,
                                                     String addressOfRegistration,
                                                     Calendar birthDate,
                                                     Calendar acceptanceDate,
                                                     String doctorName,
                                                     String doctorSecondName,
                                                     String doctorFatherName,
                                                     String conclusion) throws IOException {

        if (model != null) {
            params = new String[]{patientName, patientSecondName, patientFatherName,
                    addressOfRegistration,  String.valueOf(birthDate.getTimeInMillis()), String.valueOf(acceptanceDate.getTimeInMillis()),
                    doctorName, doctorSecondName, doctorFatherName,
                    conclusion};
            AbstractPatientDatabaseModel result = model;
            result = result.search(params);
            return result;
        } else
            return null;
    }

}
