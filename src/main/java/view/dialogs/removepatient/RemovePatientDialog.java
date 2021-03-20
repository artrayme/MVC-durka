package view.dialogs.removepatient;

import controller.implementation.AddPatientPanelController;
import controller.implementation.RemovePatientPanelController;
import model.abstractmodel.AbstractPatientDataStruct;
import model.abstractmodel.AbstractPatientDatabaseModel;
import view.dialogs.searchpatients.SearchPatientDialog;
import view.dialogs.searchpatients.SearchPatientPanel;

import javax.swing.*;

public class RemovePatientDialog extends JFrame {
    public RemovePatientDialog(RemovePatientPanelController controller) {
        this.add(new RemovePatientPanel(controller));

    }
//
//    private void delPatients() {
//        if (resultModel.getDatabaseSize()>0) {
//            JOptionPane.showMessageDialog(new JFrame(), resultModel.getDatabaseSize()+" patient was removed");
//            for (AbstractPatientDataStruct i : resultModel) {
////                model.remove(i);
//            }
//        }
//    }
}
