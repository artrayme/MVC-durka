package model.implementation;

import model.abstractmodel.PatientField;

class Conclusion implements PatientField {
    private final String conclusion;

    Conclusion(String conclusion) {
        this.conclusion = conclusion;
    }

    @Override
    public String getStringValue() {
        return conclusion;
    }
}
