package controller.implementation;

import model.abstractmodel.AbstractPatientDataStruct;
import model.abstractmodel.AbstractPatientDatabaseModel;

import java.util.Date;

public class SearchPatientPanelController {

    protected final AbstractPatientDatabaseModel model;

    public SearchPatientPanelController(AbstractPatientDatabaseModel model) {
        this.model = model;
    }

    public AbstractPatientDatabaseModel findPatients(AbstractPatientDataStruct patient) {
        AbstractPatientDatabaseModel result = model;
        if (!patient.getPatientName().toString().isEmpty()) {
            result = result.searchPatientName(patient.getPatientName().toString());
        }
        if (!patient.getPatientAddressOfRegistration().toString().isEmpty()) {
            result = result.searchAddressOfRegistration(patient.getPatientAddressOfRegistration().toString());
        }
        if (((Date) patient.getPatientBirthDate()).getTime() != 0) {
            result = result.searchBirthDate((Date) patient.getPatientBirthDate());
        }
        if (((Date) patient.getPatientAcceptanceDate()).getTime() != 0) {
            result = result.searchAcceptanceDate((Date) patient.getPatientBirthDate());
        }
        if (!patient.getDoctorName().toString().isEmpty()) {
            result = result.searchDoctorName(patient.getDoctorName().toString());
        }
        if (!patient.getConclusion().toString().isEmpty()) {
            result = result.searchConclusion(patient.getConclusion().toString());
        }
        return result;
    }
}
