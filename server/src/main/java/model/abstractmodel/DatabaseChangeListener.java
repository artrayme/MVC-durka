package model.abstractmodel;


import java.util.EventListener;

public interface DatabaseChangeListener extends EventListener {
    void onContentChange(AbstractPatientDatabaseModel model);
}
