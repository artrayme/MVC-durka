package model.implementation;

import model.abstractmodel.PatientField;

import java.util.Date;

class AcceptanceDate extends Date implements PatientField {
    AcceptanceDate(long date) {
        super(date);
    }

    @Override
    public String getStringValue() {
        return toString();
    }
}
