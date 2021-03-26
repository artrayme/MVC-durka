package controller.implementation;

import model.abstractmodel.AbstractPatientDatabaseModel;

import java.awt.geom.GeneralPath;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class SearchPatientPanelController {

    protected final AbstractPatientDatabaseModel model;

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
                                                     String conclusion) {
        AbstractPatientDatabaseModel result = model;
        if (!patientName.isEmpty()) {
            result = result.searchPatientName(patientName);
        }
        if (!patientSecondName.isEmpty()) {
            result = result.searchPatientSecondName(patientSecondName);
        }
        if (!patientFatherName.isEmpty()) {
            result = result.searchPatientFatherName(patientFatherName);
        }
        if (!addressOfRegistration.isEmpty()) {
            result = result.searchAddressOfRegistration(addressOfRegistration);
        }
        if (birthDate!=null) {
            result = result.searchBirthDate(birthDate);
        }
        if (acceptanceDate!=null) {
            result = result.searchAcceptanceDate(acceptanceDate);
        }
        if (!doctorName.isEmpty()) {
            result = result.searchDoctorName(doctorName);
        }
        if (!doctorSecondName.isEmpty()) {
            result = result.searchDoctorSecondName(doctorSecondName);
        }
        if (!doctorFatherName.isEmpty()) {
            result = result.searchDoctorFatherName(doctorFatherName);
        }
        if (!conclusion.isEmpty()) {
            result = result.searchConclusion(conclusion);
        }
        return result;
    }

}
