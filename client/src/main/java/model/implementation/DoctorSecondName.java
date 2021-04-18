package model.implementation;

import model.abstractmodel.PatientField;

class DoctorSecondName implements PatientField {
    private final String name;

    DoctorSecondName(String name) {
        this.name = name;
    }

    @Override
    public String getStringValue() {
        return name;
    }
}
