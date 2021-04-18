package model.implementation;

import model.abstractmodel.PatientField;

class DoctorFatherName implements PatientField {
    private final String name;

    DoctorFatherName(String name) {
        this.name = name;
    }

    @Override
    public String getStringValue() {
        return name;
    }
}
