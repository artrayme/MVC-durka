package controller.implementation;

import controller.abstractcontroller.AbstractHospitalTableController;
import model.abstractmodel.AbstractPatientDatabaseModel;
import model.abstractmodel.DatabaseChangeListener;
import model.implementation.ServerDatabase;
import view.HospitalTable;

import java.io.IOException;

public class HospitalTablePaneController extends AbstractHospitalTableController {

    public HospitalTablePaneController(AbstractPatientDatabaseModel model) {
        if (model != null) {
            this.model = model;
            pagesCount = (int) Math.ceil(((double) model.getDatabaseSize()) / rowsAtPage) - 1;
            pageNumber = 0;
            isDatabaseConnect = true;
        }
    }

    @Override
    public boolean onStartPageButton() {
        pageNumber = 0;
        return true;
    }

    @Override
    public boolean onEndPageButton() {
        pageNumber = pagesCount;
        return true;
    }

    @Override
    public boolean onBackPageButton() {
        if (pageNumber > 0) {
            pageNumber--;
            return true;
        }
        return false;
    }

    @Override
    public boolean onForwardPageButton() {
        if (pageNumber < pagesCount) {
            pageNumber++;
            return true;
        }
        return false;
    }


    @Override
    public boolean onRowsCountAtThePageTextField(int rowsCount) {
        if (rowsCount > 0 && rowsCount <= 15) {
            rowsAtPage = rowsCount;
            updateDatabaseInfo();
            return true;
        }
        return false;
    }

    @Override
    public String[][] getDatabase() throws IOException, ClassNotFoundException {
        String[][] result = new String[rowsAtPage][HospitalTable.COLUMN_NAMES.length];
        var modelPart = model.getDatabasePart(pageNumber * rowsAtPage, rowsAtPage);
        for (int i = 0; i < modelPart.length && modelPart[i] != null; i++) {
            if (modelPart[i][0]!=null && !modelPart[i][0].equals("null")) {
                result[i][0] = modelPart[i][0] + " " + modelPart[i][1] + " " + modelPart[i][2];
                result[i][1] = modelPart[i][3];
                result[i][2] = modelPart[i][4];
                result[i][3] = modelPart[i][5];
                result[i][4] = modelPart[i][6] + " " + modelPart[i][7] + " " + modelPart[i][8];
                result[i][5] = modelPart[i][9];
            }
        }
        return result;
    }

    @Override
    public boolean loadDatabaseFromFile(String filename) {
        return ((ServerDatabase) model).loadDatabaseFromTheFile(filename);
    }

    @Override
    public boolean saveDatabaseToFile(String filename) {
        return ((ServerDatabase) model).saveDatabaseToTheFile(filename);
    }

    @Override
    public void addModelActionListener(DatabaseChangeListener listener) {

    }

}