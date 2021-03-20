package controller.abstractcontroller;

import model.abstractmodel.AbstractPatientDataStruct;
import model.abstractmodel.AbstractPatientDatabaseModel;
import model.abstractmodel.DatabaseChangeListener;

import java.io.File;

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

    public abstract AbstractPatientDataStruct[] getDatabase();

    public abstract boolean loadDatabaseFromFile(File file);

    public abstract boolean saveDatabaseToFile(File file);

    public abstract void addModelActionListener(DatabaseChangeListener listener);
}
