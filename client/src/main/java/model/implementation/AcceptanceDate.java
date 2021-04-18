package model.implementation;

import model.abstractmodel.PatientField;

import java.util.Calendar;
import java.util.GregorianCalendar;

class AcceptanceDate extends GregorianCalendar implements PatientField {

    AcceptanceDate(long date) {
        setTimeInMillis(date);
    }

    AcceptanceDate(Calendar date) {
        setTime(date.getTime());
    }

    @Override
    public String getStringValue() {
        return toString();
    }
}
