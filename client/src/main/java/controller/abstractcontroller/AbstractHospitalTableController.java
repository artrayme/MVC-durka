package controller.abstractcontroller;

import model.abstractmodel.AbstractPatientDatabaseModel;
import model.abstractmodel.DatabaseChangeListener;

import java.io.IOException;

public abstract class AbstractHospitalTableController {

    protected AbstractPatientDatabaseModel model;

    protected int pageNumber;
    protected int pagesCount;
    protected int rowsAtPage = 10;
    protected boolean isDatabaseConnect;

    public abstract boolean onStartPageButton();

    public abstract boolean onEndPageButton();

    public abstract boolean onBackPageButton();

    public abstract boolean onForwardPageButton();

    public abstract boolean onRowsCountAtThePageTextField(int rowsCount);

    public AbstractPatientDatabaseModel getModel() {
        return model;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public int getPagesCount() {
        return pagesCount;
    }

    public int getRowsAtPage() {
        return rowsAtPage;
    }

    public boolean isDatabaseConnect() {
        return isDatabaseConnect;
    }

    public void setModel(AbstractPatientDatabaseModel model) {
        this.model = model;
        updateDatabaseInfo();
    }

    public void updateDatabaseInfo() {
        if (model != null) {
            if (model.getDatabaseSize() == 0)
                pagesCount = 0;
            else
                pagesCount = (int) Math.ceil(((double) model.getDatabaseSize()) / rowsAtPage) - 1;
            pageNumber = 0;
        }
        isDatabaseConnect = model != null;
    }

    public abstract String[][] getDatabase() throws IOException, ClassNotFoundException;

    public abstract boolean loadDatabaseFromFile(String filename);

    public abstract boolean saveDatabaseToFile(String filename);

    public abstract void addModelActionListener(DatabaseChangeListener listener);

}
