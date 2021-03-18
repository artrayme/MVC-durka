package model.implementation;

import model.abstractmodel.PatientField;

class DoctorName implements PatientField {
    private final String name;

    DoctorName(String name){
        this.name = name;
    }

    @Override
    public String getStringValue() {
        return name;
    }
}
