package model.implementation;

import model.abstractmodel.AbstractPatientDataStruct;
import model.abstractmodel.AbstractPatientDatabaseModel;
import model.abstractmodel.DatabaseChangeListener;

import java.util.Calendar;
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
    public AbstractPatientDatabaseModel searchPatientSecondName(String name) {
        Database result = new Database();
        database.forEach(e -> {
            if (e.getPatientSecondName().equals(name))
                result.add(e);
        });
        return result;
    }

    @Override
    public AbstractPatientDatabaseModel searchPatientFatherName(String name) {
        Database result = new Database();
        database.forEach(e -> {
            if (e.getPatientFatherName().equals(name))
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
    public AbstractPatientDatabaseModel searchBirthDate(Calendar date) {
        Database result = new Database();
        database.forEach(e -> {
            if (compareDates((Calendar) e.getPatientBirthDate(), date)) {
                result.add(e);
            }
        });
        return result;
    }

    @Override
    public AbstractPatientDatabaseModel searchAcceptanceDate(Calendar date) {
        Database result = new Database();
        database.forEach(e -> {
            if (compareDates((Calendar) e.getPatientAcceptanceDate(), date)) {
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
    public AbstractPatientDatabaseModel searchDoctorSecondName(String name) {
        Database result = new Database();
        database.forEach(e -> {
            if (e.getDoctorSecondName().equals(name)) {
                result.add(e);
            }
        });
        return result;
    }

    @Override
    public AbstractPatientDatabaseModel searchDoctorFatherName(String name) {
        Database result = new Database();
        database.forEach(e -> {
            if (e.getDoctorFatherName().equals(name)) {
                result.add(e);
            }
        });
        return result;
    }

    @Override
    public AbstractPatientDatabaseModel searchConclusion(String conclusion) {
        Database result = new Database();
        database.forEach(e -> {
            if (e.getConclusion().equals(conclusion)) {
                result.add(e);
            }
        });
        return result;
    }

    @Override
    public AbstractPatientDataStruct[] getDatabasePart(int start, int count) {
        AbstractPatientDataStruct[] result = new AbstractPatientDataStruct[count];
        for (int i = 0; i + start < database.size() && i < count; i++) {
            result[i] = database.get(i + start);
        }
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

    private boolean compareDates(Calendar date1, Calendar date2) {
        return date1.get(Calendar.YEAR) == date2.get(Calendar.YEAR)
                && date1.get(Calendar.MONTH) == date2.get(Calendar.MONTH)
                && date1.get(Calendar.DAY_OF_MONTH) == date2.get(Calendar.DAY_OF_MONTH);
    }}
