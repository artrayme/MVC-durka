package view.dialogs;

import model.abstractmodel.AbstractPatientDataStruct;
import model.abstractmodel.AbstractPatientDatabaseModel;

import javax.swing.*;

public class DeletePatientDialog extends SearchPatientDialog {
    private AbstractPatientDatabaseModel model;
    public DeletePatientDialog(AbstractPatientDatabaseModel model) {
        super(model);
        this.model = model;
    }

    @Override
    protected void otherActionOnConfirmButton() {
        delPatients();
    }

    private void delPatients() {
        if (resultModel.getDatabaseSize()>0) {
            JOptionPane.showMessageDialog(new JFrame(), resultModel.getDatabaseSize()+" patient was removed");
            for (AbstractPatientDataStruct i : resultModel) {
                model.remove(i);
            }
        }
    }
}
