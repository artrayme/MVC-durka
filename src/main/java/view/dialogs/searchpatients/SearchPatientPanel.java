package view.dialogs.searchpatients;

import controller.implementation.HospitalTablePaneController;
import controller.implementation.SearchPatientPanelController;
import model.abstractmodel.AbstractPatientDataStruct;
import model.implementation.DataStruct;
import model.implementation.Database;
import view.HospitalTablePane;
import view.dialogs.PatientInfoPanel;

import javax.swing.*;
import java.awt.*;
import java.util.Date;

public class SearchPatientPanel extends JPanel {

    private final SearchPatientPanelController controller;

    private final BorderLayout mainLayout = new BorderLayout();
    private final JPanel buttonsLayout = new JPanel(new GridLayout(1, 2));
    private final JButton confirmButton = new JButton("Search");
    private final JButton cancelButton = new JButton("Cancel");
    private final PatientInfoPanel infoPanel = new PatientInfoPanel();

    private final HospitalTablePane tablePane = new HospitalTablePane(new HospitalTablePaneController(null));

    public SearchPatientPanel(SearchPatientPanelController controller) {
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
//            tablePane.setModel(new Database());
            tablePane.setModel(controller.findPatients(createPatient()));
        });
    }

    private void initCancelButton() {
        cancelButton.addActionListener(e -> {
            ((Window) getRootPane().getParent()).dispose();
        });
    }

    private AbstractPatientDataStruct createPatient() {
        Date birthDate = new Date(0);
        if (infoPanel.getPatientBirthDate()!=null){
            birthDate = infoPanel.getPatientBirthDate();
        }

        Date acceptanceDate = new Date(0);
        if (infoPanel.getPatientBirthDate()!=null){
            acceptanceDate = infoPanel.getAcceptanceDate();
        }

        AbstractPatientDataStruct dataStruct = new DataStruct(infoPanel.getPatientName(),
                infoPanel.getPatientAddressOfRegistration(),
                birthDate.getTime(),
                acceptanceDate.getTime(),
                infoPanel.getDoctorName(),
                infoPanel.getConclusion());

        return dataStruct;
    }
}