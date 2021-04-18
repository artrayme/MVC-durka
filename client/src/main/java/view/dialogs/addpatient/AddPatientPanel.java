package view.dialogs.addpatient;

import controller.AddPatientPanelController;
import view.dialogs.PatientInfoPanel;

import javax.swing.*;
import java.awt.*;
import java.util.Calendar;

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
        Calendar birthDate = infoPanel.getPatientBirthDate();

        Calendar acceptanceDate = infoPanel.getAcceptanceDate();

        if (infoPanel.getPatientName().isEmpty()) {
            JOptionPane.showMessageDialog(new JFrame(), "You should set the patient's name");
        } else if (infoPanel.getPatientSecondName().isEmpty()) {
            JOptionPane.showMessageDialog(new JFrame(), "You should set the patient's second name");
        } else if (infoPanel.getPatientFatherName().isEmpty()) {
            JOptionPane.showMessageDialog(new JFrame(), "You should set the patient's father name");
        } else if (infoPanel.getPatientAddressOfRegistration().isEmpty()) {
            JOptionPane.showMessageDialog(new JFrame(), "You should set the address of registration");
        } else if (birthDate == null) {
            JOptionPane.showMessageDialog(new JFrame(), "You should set the birth date");
        } else if (acceptanceDate == null) {
            JOptionPane.showMessageDialog(new JFrame(), "You should set the acceptance date");
        } else if (infoPanel.getDoctorName().isEmpty()) {
            JOptionPane.showMessageDialog(new JFrame(), "You should set the doctor's name");
        } else if (infoPanel.getDoctorSecondName().isEmpty()) {
            JOptionPane.showMessageDialog(new JFrame(), "You should set the doctor's second name");
        } else if (infoPanel.getDoctorFatherName().isEmpty()) {
            JOptionPane.showMessageDialog(new JFrame(), "You should set the doctor's father name");
        } else if (infoPanel.getConclusion().isEmpty()) {
            JOptionPane.showMessageDialog(new JFrame(), "You should set the conclusion");
        } else {
            if (!controller.addNewPatient(infoPanel.getPatientName(),
                    infoPanel.getPatientSecondName(),
                    infoPanel.getPatientFatherName(),
                    infoPanel.getPatientAddressOfRegistration(),
                    birthDate,
                    acceptanceDate,
                    infoPanel.getDoctorName(),
                    infoPanel.getDoctorSecondName(),
                    infoPanel.getDoctorFatherName(),
                    infoPanel.getConclusion())) {
                JOptionPane.showMessageDialog(new JFrame(), "This patient cannot be added");
            }
        }
    }

}
