package model.abstractmodel;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Predicate;

public abstract class AbstractPatientDatabaseModel implements Iterable<AbstractPatientDataStruct> {
    protected final List<DatabaseChangeListener> listeners = new ArrayList<>();
    protected final ArrayList<AbstractPatientDataStruct> database = new ArrayList<>();
    protected String name = "";

    public int getDatabaseSize() {
        return database.size();
    }

    public abstract void add(AbstractPatientDataStruct element);

    public abstract void remove(AbstractPatientDataStruct element);

    public abstract void remove(int index);

    public abstract AbstractPatientDataStruct get(int index);

    public void setName(String name) {
        this.name = name;
        for (DatabaseChangeListener listener : listeners) {
            listener.onNameChange();
        }
    }

    public String getName() {
        return name;
    }

    public void addListener(DatabaseChangeListener listener) {
        if (!listeners.contains(listener))
            listeners.add(listener);
    }

    public void removeListener(DatabaseChangeListener listener) {
        listeners.remove(listener);
    }


    public Iterator<AbstractPatientDataStruct> iterator() {
        return database.iterator();
    }

    public void forEach(Consumer<? super AbstractPatientDataStruct> action) {
        database.forEach(action);
    }

    public abstract AbstractPatientDatabaseModel searchPatientName(String name);

    public abstract AbstractPatientDatabaseModel searchAddressOfRegistration(String address);

    public abstract AbstractPatientDatabaseModel searchBirthDate(Date date);

    public abstract AbstractPatientDatabaseModel searchAcceptanceDate(Date date);

    public abstract AbstractPatientDatabaseModel searchDoctorName(String name);

    public abstract AbstractPatientDatabaseModel searchConclusion(String conclusion);
}
