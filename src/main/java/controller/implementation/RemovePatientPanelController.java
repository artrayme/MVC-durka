package controller.implementation;

import model.abstractmodel.AbstractPatientDataStruct;
import model.abstractmodel.AbstractPatientDatabaseModel;

public class RemovePatientPanelController extends SearchPatientPanelController {

    public RemovePatientPanelController(AbstractPatientDatabaseModel model) {
        super(model);
    }

    public int removePatients(AbstractPatientDatabaseModel database) {
        for (AbstractPatientDataStruct patient : database)
            model.remove(patient);
        return database.getDatabaseSize();
    }
}
