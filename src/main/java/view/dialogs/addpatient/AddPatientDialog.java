package view.dialogs.addpatient;

import controller.implementation.AddPatientPanelController;

import javax.swing.*;

public class AddPatientDialog extends JFrame {
    public AddPatientDialog(AddPatientPanelController controller) {
        this.add(new AddPatientPanel(controller));
    }

//    private class PatientInfoPane extends AbstractPatientInfoPanel {
//
//        PatientInfoPane(){
//            init();
//        }
//
//        @Override
//        protected void initConfirmButton() {
//            setGrayColor();
//            confirmButton.addActionListener(e -> {
//                if (patientNameField.getText().isEmpty()) {
//                    patientNameField.setBackground(Color.red);
//                } else if (patientAddressOfRegistrationField.getText().isEmpty()) {
//                    patientAddressOfRegistrationField.setBackground(Color.red);
//                } else if (doctorNameField.getText().isEmpty()) {
//                    doctorNameField.setBackground(Color.red);
//                } else if (conclusionField.getText().isEmpty()) {
//                    conclusionField.setBackground(Color.red);
//                } else {
//                    addPatient();
//                    AddPatientDialog.this.dispose();
//                }
//            });
//        }
//
//
//        private void setGrayColor(){
//            patientNameField.setBackground(Color.lightGray);
//            patientAddressOfRegistrationField.setBackground(Color.lightGray);
//            doctorNameField.setBackground(Color.lightGray);
//            conclusionField.setBackground(Color.lightGray);
//
//        }
//
//
//        @Override
//
//        protected void initCancelButton() {
//            cancelButton.addActionListener(e -> {
//                AddPatientDialog.this.dispose();
//            });
//        }
//

//    }

}
