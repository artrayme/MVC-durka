package model.implementation;

import model.abstractmodel.AbstractPatientDatabaseModel;

import java.io.IOException;

public class BufferDatabase implements AbstractPatientDatabaseModel {
    private String[][] data;
    private final ServerDatabase serverDatabase;
    private final String[] paterns;

    public BufferDatabase(AbstractPatientDatabaseModel database, String[] paterns) {
        serverDatabase = (ServerDatabase) database;
        this.paterns = paterns;
        try {
            data = serverDatabase.search(paterns);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getDatabaseSize() {
        return serverDatabase.sizeOfSearched(paterns);
    }

    @Override
    public void add(String[] element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int remove(String[] element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void remove(int index) {
        throw new UnsupportedOperationException();

    }

    @Override
    public void setName(String name) {
        throw new UnsupportedOperationException();

    }

    @Override
    public String getName() {
        throw new UnsupportedOperationException();

    }

    @Override
    public String[][] search(String[] params) throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public String[][] getDatabasePart(int start, int count) throws IOException, ClassNotFoundException {
        String[][] result = new String[count][10];
        for (int i = 0; i < count && data.length>start+i; i++) {
            for (int j = 0; j < 10; j++) {
                result[i][j] = data[start+i][j];
            }
        }
        return result;
    }
}
