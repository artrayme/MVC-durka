package controller;

import model.abstractmodel.AbstractPatientDatabaseModel;
import model.implementation.BufferDatabase;

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
                    addressOfRegistration,  birthDate!=null?String.valueOf(birthDate.getTimeInMillis()):"null",
                    acceptanceDate!=null?String.valueOf(acceptanceDate.getTimeInMillis()):"null",
                    doctorName, doctorSecondName, doctorFatherName,
                    conclusion};
            AbstractPatientDatabaseModel result = new BufferDatabase(model, params);
            return result;
        } else
            return null;
    }

}
