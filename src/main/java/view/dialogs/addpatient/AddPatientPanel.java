package view.dialogs.addpatient;

import controller.implementation.AddPatientPanelController;
import model.abstractmodel.AbstractPatientDataStruct;
import model.implementation.DataStruct;
import view.dialogs.PatientInfoPanel;

import javax.swing.*;
import java.awt.*;
import java.util.Date;

public class AddPatientPanel extends JPanel {
    private final AddPatientPanelController controller;

    private final BorderLayout mainLayout = new BorderLayout();
    private final PatientInfoPanel infoPanel = new PatientInfoPanel();
    private final JPanel buttonsLayout = new JPanel(new GridLayout(1, 2));
    private final JButton confirmButton = new JButton("Confirm");
    private final JButton cancelButton = new JButton("Cancel");

    public AddPatientPanel(AddPatientPanelController controller) {
        setLayout(mainLayout);
        this.controller = controller;
        infoPanel.init();
        initConfirmButton();
        initCancelButton();
        buttonsLayout.add(confirmButton);
        buttonsLayout.add(cancelButton);
        add(infoPanel, BorderLayout.CENTER);
        add(buttonsLayout, BorderLayout.SOUTH);
    }

    private void initConfirmButton() {
        confirmButton.addActionListener(e -> {
            addPatient();
        });
    }

    private void initCancelButton() {
        cancelButton.addActionListener(e -> {
            ((Window) getRootPane().getParent()).dispose();
        });
    }

    private void addPatient() {
        Date birthDate = infoPanel.getPatientBirthDate();

        Date acceptanceDate = infoPanel.getAcceptanceDate();
        if (infoPanel.getPatientName().isEmpty()) {
            JOptionPane.showMessageDialog(new JFrame(), "You should set the patient's name");
        } else if (infoPanel.getPatientAddressOfRegistration().isEmpty()) {
            JOptionPane.showMessageDialog(new JFrame(), "You should set the address of registration");
        } else if (birthDate == null) {
            JOptionPane.showMessageDialog(new JFrame(), "You should set the birth date");
        } else if (acceptanceDate == null) {
            JOptionPane.showMessageDialog(new JFrame(), "You should set the acceptance date");
        } else if (infoPanel.getDoctorName().isEmpty()) {
            JOptionPane.showMessageDialog(new JFrame(), "You should set the doctor's name");
        } else if (infoPanel.getConclusion().isEmpty()) {
            JOptionPane.showMessageDialog(new JFrame(), "You should set the conclusion");
        } else {
            AbstractPatientDataStruct dataStruct = new DataStruct(infoPanel.getPatientName(),
                    infoPanel.getPatientAddressOfRegistration(),
                    birthDate.getTime(),
                    acceptanceDate.getTime(),
                    infoPanel.getDoctorName(),
                    infoPanel.getConclusion());
            if (!controller.addNewPatient(dataStruct)) {
                JOptionPane.showMessageDialog(new JFrame(), "This patient cannot be added");
            }
        }
    }

}
