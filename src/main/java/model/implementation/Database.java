package model.implementation;

import model.abstractmodel.AbstractPatientDataStruct;
import model.abstractmodel.AbstractPatientDatabaseModel;
import model.abstractmodel.DatabaseChangeListener;

import java.util.Date;
import java.util.Iterator;
import java.util.function.Consumer;

public class Database extends AbstractPatientDatabaseModel implements Iterable<AbstractPatientDataStruct> {

    public Database() {
        //do nothing
    }

    public Database(int size) {
        database.ensureCapacity(size);
    }

    @Override
    public Iterator<AbstractPatientDataStruct> iterator() {
        return database.iterator();
    }

    @Override
    public void forEach(Consumer<? super AbstractPatientDataStruct> action) {
        database.forEach(action);
    }

    @Override
    public AbstractPatientDatabaseModel searchPatientName(String name) {
        Database result = new Database();
        database.forEach(e -> {
            if (e.getPatientName().equals(name))
                result.add(e);
        });
        return result;
    }

    @Override
    public AbstractPatientDatabaseModel searchAddressOfRegistration(String address) {
        Database result = new Database();
        database.forEach(e -> {
            if (e.getPatientAddressOfRegistration().equals(address)) {
                result.add(e);
            }
        });
        return result;
    }

    @Override
    public AbstractPatientDatabaseModel searchBirthDate(Date date) {
        Database result = new Database();
        database.forEach(e -> {
            if (e.getPatientBirthDate().equals(date)) {
                result.add(e);
            }
        });
        return result;
    }

    @Override
    public AbstractPatientDatabaseModel searchAcceptanceDate(Date date) {
        Database result = new Database();
        database.forEach(e -> {
            if (e.getPatientAcceptanceDate().equals(date)) {
                result.add(e);
            }
        });
        return result;
    }

    @Override
    public AbstractPatientDatabaseModel searchDoctorName(String name) {
        Database result = new Database();
        database.forEach(e -> {
            if (e.getDoctorName().equals(name)) {
                result.add(e);
            }
        });
        return result;
    }

    @Override
    public AbstractPatientDatabaseModel searchConclusion(String conclusion) {
        Database result = new Database();
        database.forEach(e -> {
            System.out.println(e.getConclusion() + (e.getConclusion().equals(conclusion)?" == ":" != " + conclusion));
            if (e.getConclusion().equals(conclusion)) {
                result.add(e);
            }
        });
        System.out.println("result size = " + result.getDatabaseSize());
        return result;
    }

    @Override
    public void add(AbstractPatientDataStruct element) {
        database.add(element);
        for (DatabaseChangeListener listener : listeners) {
            listener.onContentChange();
        }
    }

    @Override
    public void remove(AbstractPatientDataStruct element) {
        database.remove(element);
        for (DatabaseChangeListener listener : listeners) {
            listener.onContentChange();
        }
    }

    @Override
    public void remove(int index) {
        database.remove(index);
        for (DatabaseChangeListener listener : listeners) {
            listener.onContentChange();
        }
    }

    @Override
    public AbstractPatientDataStruct get(int index) {
        return database.get(index);
    }
}
