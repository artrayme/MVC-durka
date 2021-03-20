package controller.implementation;

import model.abstractmodel.AbstractPatientDataStruct;
import model.abstractmodel.AbstractPatientDatabaseModel;

public class AddPatientPanelController {

    AbstractPatientDatabaseModel model;

    public AddPatientPanelController(AbstractPatientDatabaseModel model){
        this.model = model;
    }

    public boolean addNewPatient(AbstractPatientDataStruct patient) {
        System.out.println("AddPatientPanelController: patient = " + patient);
        if (patient != null) {
            model.add(patient);
            return true;
        }
        return false;
    }

}
