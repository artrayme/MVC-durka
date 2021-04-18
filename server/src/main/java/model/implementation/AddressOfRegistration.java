package model.implementation;

import model.abstractmodel.PatientField;

class AddressOfRegistration implements PatientField {
    private final String address;

    AddressOfRegistration(String address) {
        this.address = address;
    }

    @Override
    public String getStringValue() {
        return address;
    }
}
