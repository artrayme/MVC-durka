package view.dialogs.searchpatients;

import controller.SearchPatientPanelController;

import javax.swing.*;

public class SearchPatientDialog extends JFrame {

    public SearchPatientDialog(SearchPatientPanelController controller) {
        this.add(new SearchPatientPanel(controller));
    }
}
