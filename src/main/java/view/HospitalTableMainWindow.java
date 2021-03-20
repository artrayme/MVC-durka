//package view;
//
//import controller.abstractcontroller.AbstractHospitalTableController;
//import model.abstractmodel.AbstractPatientDataStruct;
//import model.abstractmodel.AbstractPatientDatabaseModel;
//import model.abstractmodel.DatabaseChangeListener;
//import org.xml.sax.SAXException;
//import saver.XmlLoader;
//import saver.XmlSaver;
//
//import javax.swing.*;
//import javax.xml.parsers.ParserConfigurationException;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.io.File;
//import java.io.IOException;
//
//public class HospitalTableMainWindow extends JPanel {
//    private AbstractPatientDatabaseModel model;
////    private HospitalTablePane view;
//
//    private int pageNumber = 0;
//    private int pagesCount = 0;
//    private int rowsAtPage = 10;
//    private boolean isDatabaseConnect = false;
//
////    public HospitalTableMainWindow(AbstractPatientDatabaseModel model, HospitalTablePane view) {
////        this.model = model;
////        if (model != null) {
////            model.addListener(new ModelChangeListener());
////        }
////        this.view = view;
////        AbstractHospitalTableController controller = new HospitalTableController();
////        this.view.setController(controller);
////        setLayout(new BorderLayout());
////        add(view);
////        view.getRowsCountAtThePageTextField().setEditable(false);
////        updateDatabaseInformationLabels();
////    }
//
//
//
//    public void loadDatabaseFromFile(File file) {
//        try {
//            model = XmlLoader.load(file);
//            if (model == null) {
//                JOptionPane.showMessageDialog(new JFrame(), "Something wrong with the xml database file");
//                return;
//            }
//            model.addListener(new ModelChangeListener());
//            initModelInfo();
//            showDatabasePart(0);
//            view.getRowsCountAtThePageTextField().setEditable(true);
//        } catch (ParserConfigurationException | SAXException | IOException e) {
//            JOptionPane.showMessageDialog(new JFrame(), "Something wrong with the xml database file");
//        }
//    }
//
//    private void initModelInfo() {
//        if (model != null) {
//            pageNumber = 0;
//            pagesCount = (int) Math.ceil(((double) model.getDatabaseSize()) / rowsAtPage) - 1;
//            isDatabaseConnect = true;
//        } else {
//            pageNumber = 0;
//            pagesCount = 0;
//            isDatabaseConnect = false;
//        }
//    }
//
//    public void saveDatabaseToFile(File file) {
//        if (isDatabaseConnect) {
//            try {
//                XmlSaver.save(file, model);
//            } catch (ParserConfigurationException e) {
//                e.printStackTrace();
//            }
//        } else {
//            JOptionPane.showMessageDialog(new JFrame(), "Database not connected");
//        }
//    }
//
//    public void updateDatabaseInformationLabels() {
//        if (isDatabaseConnect) {
//            view.getDatabaseStatusLabel().setText(HospitalTablePane.DATABASE_CONNECTED);
//            view.getDatabaseStatusLabel().setForeground(Color.GREEN);
//            view.getCurrentPageNumberLabel().setText(" page " + (pageNumber + 1));
//            view.getPagesCountLabel().setText(" of " + (pagesCount + 1));
//        } else {
//            view.getDatabaseStatusLabel().setText(HospitalTablePane.DATABASE_NOT_CONNECTED);
//            view.getDatabaseStatusLabel().setForeground(Color.RED);
//            view.getCurrentPageNumberLabel().setText("");
//            view.getPagesCountLabel().setText("");
//        }
//        view.getRowsCountAtThePageTextField().setText(String.valueOf(rowsAtPage));
//
//    }
//
//    public void showDatabasePart(int startIndex) {
//        if (model != null) {
//            AbstractPatientDataStruct[] databasePart = new AbstractPatientDataStruct[rowsAtPage];
//            for (int i = 0; i < databasePart.length; i++) {
//                if (i + startIndex < model.getDatabaseSize()) {
//                    databasePart[i] = model.get(startIndex + i);
//                }
//            }
//            view.setTableData();
//            updateDatabaseInformationLabels();
//        } else
//            JOptionPane.showMessageDialog(new JFrame(), "Database not connected");
//    }
//
//    public void setModel(AbstractPatientDatabaseModel model) {
//        this.model = model;
//        if (model != null) {
//            model.addListener(new ModelChangeListener());
//            view.getRowsCountAtThePageTextField().setEditable(true);
//        }
//        initModelInfo();
//        showDatabasePart(0);
//    }
//
//    public AbstractPatientDatabaseModel getModel() {
//        return model;
//    }
//
//    public boolean getIsDatabaseConnect() {
//        return isDatabaseConnect;
//    }
//
//    public void setView(HospitalTablePane view) {
//        this.view = view;
//    }
//
//
//
//    private class ModelChangeListener implements DatabaseChangeListener {
//
//        @Override
//        public void onContentChange() {
//            initModelInfo();
//            updateDatabaseInformationLabels();
//            showDatabasePart(pageNumber * rowsAtPage);
//        }
//
//        @Override
//        public void onNameChange() {
//        }
//    }
//
//}
