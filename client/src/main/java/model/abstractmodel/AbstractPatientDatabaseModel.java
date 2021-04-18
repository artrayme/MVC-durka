package model.abstractmodel;

import java.util.Calendar;
import java.util.Iterator;
import java.util.function.Consumer;

public interface AbstractPatientDatabaseModel extends Iterable<AbstractPatientDataStruct> {
    int getDatabaseSize();

    void add(AbstractPatientDataStruct element);

    void remove(AbstractPatientDataStruct element);

    void remove(int index);

    AbstractPatientDataStruct get(int index);

    void setName(String name);

    String getName();

    void addListener(DatabaseChangeListener listener);

    void removeListener(DatabaseChangeListener listener);


    Iterator<AbstractPatientDataStruct> iterator();

    @Override
    void forEach(Consumer<? super AbstractPatientDataStruct> action);

    AbstractPatientDatabaseModel searchPatientName(String name);

    AbstractPatientDatabaseModel searchPatientSecondName(String name);

    AbstractPatientDatabaseModel searchPatientFatherName(String name);

    AbstractPatientDatabaseModel searchAddressOfRegistration(String address);

    AbstractPatientDatabaseModel searchBirthDate(Calendar date);

    AbstractPatientDatabaseModel searchAcceptanceDate(Calendar date);

    AbstractPatientDatabaseModel searchDoctorName(String name);

    AbstractPatientDatabaseModel searchDoctorSecondName(String name);

    AbstractPatientDatabaseModel searchDoctorFatherName(String name);

    AbstractPatientDatabaseModel searchConclusion(String conclusion);

    AbstractPatientDataStruct[] getDatabasePart(int start, int count);
}
