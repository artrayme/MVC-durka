package model.abstractmodel;

import java.io.IOException;
import java.util.Calendar;
import java.util.Iterator;
import java.util.function.Consumer;

public interface AbstractPatientDatabaseModel {

    int getDatabaseSize();

    void add(String[] element);

    int remove(String[] element);

    void remove(int index);

    void setName(String name);

    String getName();

    AbstractPatientDatabaseModel search(String[] params) throws IOException;


    String[][] getDatabasePart(int start, int count) throws IOException, ClassNotFoundException;
}
