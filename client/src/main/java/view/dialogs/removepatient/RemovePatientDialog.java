package view.dialogs.removepatient;

import controller.RemovePatientPanelController;

import javax.swing.*;

public class RemovePatientDialog extends JFrame {
    public RemovePatientDialog(RemovePatientPanelController controller) {
        this.add(new RemovePatientPanel(controller));
    }

}
