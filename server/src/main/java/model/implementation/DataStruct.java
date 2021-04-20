package model.implementation;

import model.abstractmodel.AbstractPatientDataStruct;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class DataStruct extends AbstractPatientDataStruct {
    private final PatientName patientName;
    private final PatientSecondName patientSecondName;
    private final PatientFatherName patientFatherName;
    private final AddressOfRegistration addressOfRegistration;
    private final BirthDate birthDate;
    private final AcceptanceDate acceptanceDate;
    private final DoctorName doctorName;
    private final DoctorSecondName doctorSecondName;
    private final DoctorFatherName doctorFatherName;
    private final Conclusion conclusion;

    public DataStruct(String patientName,
                      String patientSecondName,
                      String patientFatherName,
                      String addressOfRegistration,
                      Calendar birthDate,
                      Calendar acceptanceDate,
                      String doctorName,
                      String doctorSecondName,
                      String doctorFatherName,
                      String conclusion) {
        this.patientName = new PatientName(patientName);
        this.patientSecondName = new PatientSecondName(patientSecondName);
        this.patientFatherName = new PatientFatherName(patientFatherName);
        this.addressOfRegistration = new AddressOfRegistration(addressOfRegistration);
        this.birthDate = new BirthDate(birthDate);
        this.acceptanceDate = new AcceptanceDate(acceptanceDate);
        this.doctorName = new DoctorName(doctorName);
        this.doctorSecondName = new DoctorSecondName(doctorSecondName);
        this.doctorFatherName = new DoctorFatherName(doctorFatherName);
        this.conclusion = new Conclusion(conclusion);
    }

    public DataStruct(String patientName,
                      String patientSecondName,
                      String patientFatherName,
                      String addressOfRegistration,
                      String birthDate,
                      String acceptanceDate,
                      String doctorName,
                      String doctorSecondName,
                      String doctorFatherName,
                      String conclusion) {
        this.patientName = new PatientName(patientName);
        this.patientSecondName = new PatientSecondName(patientSecondName);
        this.patientFatherName = new PatientFatherName(patientFatherName);
        this.addressOfRegistration = new AddressOfRegistration(addressOfRegistration);
        Calendar birth = new GregorianCalendar();
        birth.setTimeInMillis(Long.parseLong(birthDate));
        this.birthDate = new BirthDate(birth);
        Calendar acceptance = new GregorianCalendar();
        acceptance.setTimeInMillis(Long.parseLong(acceptanceDate));
        this.acceptanceDate = new AcceptanceDate(acceptance);
        this.doctorName = new DoctorName(doctorName);
        this.doctorSecondName = new DoctorSecondName(doctorSecondName);
        this.doctorFatherName = new DoctorFatherName(doctorFatherName);
        this.conclusion = new Conclusion(conclusion);
    }

    @Override
    public String getPatientName() {
        return patientName.getStringValue();
    }

    @Override
    public Object getPatientSecondName() {
        return patientSecondName.getStringValue();
    }

    @Override
    public Object getPatientFatherName() {
        return patientFatherName.getStringValue();
    }

    @Override
    public String getPatientAddressOfRegistration() {
        return addressOfRegistration.getStringValue();
    }

    @Override
    public Calendar getPatientBirthDate() {
        return birthDate;
    }

    @Override
    public Calendar getPatientAcceptanceDate() {
        return acceptanceDate;
    }

    @Override
    public String getDoctorName() {
        return doctorName.getStringValue();
    }

    @Override
    public Object getDoctorSecondName() {
        return doctorSecondName.getStringValue();
    }

    @Override
    public Object getDoctorFatherName() {
        return doctorFatherName.getStringValue();
    }

    @Override
    public String getConclusion() {
        return conclusion.getStringValue();
    }
}
