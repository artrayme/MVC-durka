package view.dialogs.removepatient;

import controller.implementation.HospitalTablePaneController;
import controller.RemovePatientPanelController;
import gui.Main;
import model.abstractmodel.AbstractPatientDatabaseModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import view.HospitalTablePane;
import view.dialogs.PatientInfoPanel;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class RemovePatientPanel extends JPanel {

    private static final Logger logger = LogManager.getLogger(Main.class);

    private final RemovePatientPanelController controller;

    private final BorderLayout mainLayout = new BorderLayout();
    private final JPanel buttonsLayout = new JPanel(new GridLayout(1, 2));
    private final JButton confirmButton = new JButton("Remove");
    private final JButton cancelButton = new JButton("Cancel");
    private final PatientInfoPanel infoPanel = new PatientInfoPanel();

    private final HospitalTablePane tablePane = new HospitalTablePane(new HospitalTablePaneController(null));

    public RemovePatientPanel(RemovePatientPanelController controller) {
        setLayout(mainLayout);
        this.controller = controller;
        infoPanel.init();
        initConfirmButton();
        initCancelButton();
        buttonsLayout.add(confirmButton);
        buttonsLayout.add(cancelButton);
        add(infoPanel, BorderLayout.WEST);
        add(tablePane, BorderLayout.CENTER);
        add(buttonsLayout, BorderLayout.SOUTH);
    }

    private void initConfirmButton() {
        confirmButton.addActionListener(e -> {
            AbstractPatientDatabaseModel foundPatients;
            try {
                foundPatients = controller.findPatients(infoPanel.getPatientName(),
                        infoPanel.getPatientSecondName(),
                        infoPanel.getPatientFatherName(),
                        infoPanel.getPatientAddressOfRegistration(),
                        infoPanel.getPatientBirthDate(),
                        infoPanel.getAcceptanceDate(),
                        infoPanel.getDoctorName(),
                        infoPanel.getDoctorSecondName(),
                        infoPanel.getDoctorFatherName(),
                        infoPanel.getConclusion());
                tablePane.setModel(foundPatients);
                int removedCount = controller.removeFoundedPatients();
                JOptionPane.showMessageDialog(new JFrame(), removedCount + " patients were removed");
            } catch (IOException ioException) {
                logger.warn("[RemoveDialog] Something wrong in patient search");
                JOptionPane.showMessageDialog(new JFrame(), "Error in patient search");
            }

        });
    }

    private void initCancelButton() {
        cancelButton.addActionListener(e -> {
            ((Window) getRootPane().getParent()).dispose();
        });
    }

}

