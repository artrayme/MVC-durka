package model.implementation;

import model.abstractmodel.PatientField;

import java.util.Date;

class BirthDate extends Date implements PatientField {
    BirthDate(long date) {
        super(date);
    }

    @Override
    public String getStringValue() {
        return toString();
    }
}
