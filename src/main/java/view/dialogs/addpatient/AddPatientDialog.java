package view.dialogs.addpatient;

import controller.implementation.AddPatientPanelController;

import javax.swing.*;

public class AddPatientDialog extends JFrame {
    public AddPatientDialog(AddPatientPanelController controller) {
        this.add(new AddPatientPanel(controller));
    }

}
