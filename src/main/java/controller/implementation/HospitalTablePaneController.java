package controller.implementation;

import controller.abstractcontroller.AbstractHospitalTableController;
import model.abstractmodel.AbstractPatientDatabaseModel;
import model.abstractmodel.DatabaseChangeListener;
import org.xml.sax.SAXException;
import saver.XmlLoader;
import saver.XmlSaver;
import view.HospitalTable;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.IOException;
import java.util.GregorianCalendar;

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
    public String[][] getDatabase() {
        String[][] result = new String[rowsAtPage][HospitalTable.COLUMN_NAMES.length];
        var modelPart = model.getDatabasePart(pageNumber * rowsAtPage, rowsAtPage);
        for (int i = 0; i < modelPart.length && modelPart[i] != null; i++) {
            result[i][0] = modelPart[i].getPatientName() + " " + modelPart[i].getPatientSecondName() + " " + modelPart[i].getPatientFatherName();
            result[i][1] = (String) modelPart[i].getPatientAddressOfRegistration();
            result[i][2] = ((GregorianCalendar) modelPart[i].getPatientBirthDate()).getTime().toString();
            result[i][3] = ((GregorianCalendar) modelPart[i].getPatientAcceptanceDate()).getTime().toString();
            result[i][4] = modelPart[i].getDoctorName() + " " + modelPart[i].getDoctorSecondName() + " " + modelPart[i].getDoctorFatherName();
            result[i][5] = (String) modelPart[i].getConclusion();
        }
        return result;
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
    public boolean saveDatabaseToFile(File file) {
        if (isDatabaseConnect) {
            try {
                XmlSaver.save(file, model);
            } catch (ParserConfigurationException | TransformerException e) {
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