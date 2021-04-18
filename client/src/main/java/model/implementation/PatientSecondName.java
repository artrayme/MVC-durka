package model.implementation;

import model.abstractmodel.PatientField;

class PatientSecondName implements PatientField {
    private final String name;

    PatientSecondName(String name) {
        this.name = name;
    }

    @Override
    public String getStringValue() {
        return name;
    }
}
