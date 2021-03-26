package model.implementation;

import model.abstractmodel.PatientField;

class PatientFatherName implements PatientField {
    private final String name;

    PatientFatherName(String name) {
        this.name = name;
    }

    @Override
    public String getStringValue() {
        return name;
    }
}
