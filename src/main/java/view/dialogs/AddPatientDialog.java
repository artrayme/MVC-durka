package view.dialogs;

import model.abstractmodel.AbstractPatientDatabaseModel;
import model.implementation.DataStruct;

import javax.swing.*;
import java.awt.*;
import java.util.Calendar;

public class AddPatientDialog extends JFrame {

    public AddPatientDialog(AbstractPatientDatabaseModel model) {
        PatientInfoPane patientInfoPane = new PatientInfoPane();
        patientInfoPane.setModel(model);
        this.setContentPane(patientInfoPane);
    }

    private class PatientInfoPane extends AbstractPatientInfoPanel {

        PatientInfoPane(){
            init();
        }

        @Override
        protected void initConfirmButton() {
            setGrayColor();
            confirmButton.addActionListener(e -> {
                if (patientNameField.getText().isEmpty()) {
                    patientNameField.setBackground(Color.red);
                } else if (patientAddressOfRegistrationField.getText().isEmpty()) {
                    patientAddressOfRegistrationField.setBackground(Color.red);
                } else if (doctorNameField.getText().isEmpty()) {
                    doctorNameField.setBackground(Color.red);
                } else if (conclusionField.getText().isEmpty()) {
                    conclusionField.setBackground(Color.red);
                } else {
                    addPatient();
                    AddPatientDialog.this.dispose();
                }
            });
        }


        private void setGrayColor(){
            patientNameField.setBackground(Color.lightGray);
            patientAddressOfRegistrationField.setBackground(Color.lightGray);
            doctorNameField.setBackground(Color.lightGray);
            conclusionField.setBackground(Color.lightGray);

        }


        @Override

        protected void initCancelButton() {
            cancelButton.addActionListener(e -> {
                AddPatientDialog.this.dispose();
            });
        }

        private void addPatient() {
            Calendar birthDate = Calendar.getInstance();

            birthDate.set(patientBirthDatePicker.getModel().getYear(),
                    patientBirthDatePicker.getModel().getMonth(),
                    patientBirthDatePicker.getModel().getDay(),
                    0, 0, 0);

            Calendar acceptanceDate = Calendar.getInstance();
            birthDate.set(patientAcceptanceDatePicker.getModel().getYear(),
                    patientAcceptanceDatePicker.getModel().getMonth(),
                    patientAcceptanceDatePicker.getModel().getDay(),
                    0, 0, 0);

            model.add(new DataStruct(patientNameField.getText(),
                    patientAddressOfRegistrationField.getText(),
                    birthDate.getTimeInMillis(),
                    acceptanceDate.getTimeInMillis(),
                    doctorNameField.getText(),
                    conclusionField.getText()));
        }
    }

}
