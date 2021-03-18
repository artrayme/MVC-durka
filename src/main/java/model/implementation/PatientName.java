package model.implementation;

import model.abstractmodel.PatientField;

class PatientName implements PatientField {
    private final String name;
    PatientName(String name){
        this.name = name;
    }

    @Override
    public String getStringValue() {
        return name;
    }
}
