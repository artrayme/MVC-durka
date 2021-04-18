package model.implementation;

import model.abstractmodel.PatientField;

import java.util.Calendar;
import java.util.GregorianCalendar;

class BirthDate extends GregorianCalendar implements PatientField {

    BirthDate(long date) {
        setTimeInMillis(date);
    }

    BirthDate(Calendar date) {
        setTime(date.getTime());
    }

    @Override
    public String getStringValue() {
        return toString();
    }
}
