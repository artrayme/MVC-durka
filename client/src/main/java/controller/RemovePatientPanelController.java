package controller;

import model.abstractmodel.AbstractPatientDatabaseModel;

public class RemovePatientPanelController extends SearchPatientPanelController {

    public RemovePatientPanelController(AbstractPatientDatabaseModel model) {
        super(model);
    }

    public int removeFoundedPatients() {
        return model.remove(params);
    }
}
