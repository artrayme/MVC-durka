package controller.implementation;

import controller.abstractcontroller.AbstractHospitalTableController;
import model.abstractmodel.AbstractPatientDataStruct;
import model.abstractmodel.AbstractPatientDatabaseModel;
import model.abstractmodel.DatabaseChangeListener;
import org.xml.sax.SAXException;
import saver.XmlLoader;
import saver.XmlSaver;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
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
    public AbstractPatientDataStruct[] getDatabase() {
        System.out.println("HospitalTablePaneController:getDatabase pageNumber = " + pageNumber + " rowsAtPage = " + rowsAtPage);
        return model.getDatabasePart(pageNumber * rowsAtPage, rowsAtPage);
    }

    @Override
    public boolean loadDatabaseFromFile(File file) {
        try {
            setModel(XmlLoader.load(file));
            return model != null;
        } catch (ParserConfigurationException | SAXException | IOException e) {
            JOptionPane.showMessageDialog(new JFrame(), "Something wrong with the xml database file");
        }
        return false;
    }

    @Override
    public boolean saveDatabaseFromFile(File file) {
        if (isDatabaseConnect) {
            try {
                XmlSaver.save(file, model);
            } catch (ParserConfigurationException e) {
                return false;
            }
            return true;
        }
        return false;
    }

    @Override
    public void addModelActionListener(DatabaseChangeListener listener) {
        model.addListener(listener);
    }

}