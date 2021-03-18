package model.implementation;

import model.abstractmodel.AbstractPatientDataStruct;

import java.util.Date;

public class DataStruct extends AbstractPatientDataStruct {
    private final PatientName patientName;
    private final AddressOfRegistration addressOfRegistration;
    private final BirthDate birthDate;
    private final AcceptanceDate acceptanceDate;
    private final DoctorName doctorName;
    private final Conclusion conclusion;

    public DataStruct(String patientName,
                      String addressOfRegistration,
                      long birthDate,
                      long acceptanceDate,
                      String doctorName,
                      String conclusion) {
        this.patientName = new PatientName(patientName);
        this.addressOfRegistration = new AddressOfRegistration(addressOfRegistration);
        this.birthDate = new BirthDate(birthDate);
        this.acceptanceDate = new AcceptanceDate(acceptanceDate);
        this.doctorName = new DoctorName(doctorName);
        this.conclusion = new Conclusion(conclusion);
    }

    @Override
    public String getPatientName() {
        return patientName.getStringValue();
    }

    @Override
    public String getPatientAddressOfRegistration() {
        return addressOfRegistration.getStringValue();
    }

    @Override
    public Date getPatientBirthDate() {
        return birthDate;
    }

    @Override
    public Date getPatientAcceptanceDate() {
        return acceptanceDate;
    }

    @Override
    public String getDoctorName() {
        return doctorName.getStringValue();
    }

    @Override
    public String getConclusion() {
        return conclusion.getStringValue();
    }
}
