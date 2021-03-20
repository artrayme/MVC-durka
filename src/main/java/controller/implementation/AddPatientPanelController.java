package controller.implementation;

import model.abstractmodel.AbstractPatientDataStruct;
import model.abstractmodel.AbstractPatientDatabaseModel;

public class AddPatientPanelController {

    protected AbstractPatientDatabaseModel model;

    public AddPatientPanelController(AbstractPatientDatabaseModel model){
        this.model = model;
    }

    public boolean addNewPatient(AbstractPatientDataStruct patient) {
        if (patient != null) {
            model.add(patient);
            return true;
        }
        return false;
    }

}
