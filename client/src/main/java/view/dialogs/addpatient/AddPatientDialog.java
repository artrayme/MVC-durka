package view.dialogs.addpatient;

import controller.AddPatientPanelController;

import javax.swing.*;

public class AddPatientDialog extends JFrame {
    public AddPatientDialog(AddPatientPanelController controller) {
        this.add(new AddPatientPanel(controller));
    }

}
