package view.dialogs;

import controller.abstractcontroller.AbstractHospitalTableController;
import model.abstractmodel.AbstractPatientDataStruct;
import model.abstractmodel.AbstractPatientDatabaseModel;
import view.HospitalTable;
import view.HospitalTablePane;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Calendar;
import java.util.Date;

public class SearchPatientDialog extends JFrame {

    protected AbstractPatientDatabaseModel resultModel;
    private final HospitalTablePane tablePane = new HospitalTablePane();

    private int pageNumber = 0;
    private int pagesCount = 0;
    private int rowsAtPage = 4;

    public SearchPatientDialog(AbstractPatientDatabaseModel model) {
        setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
        PatientInfoPane infoPane = new PatientInfoPane(model);
        infoPane.setMaximumSize(new Dimension(Integer.MAX_VALUE, 300));
        add(infoPane);
        tablePane.setPreferredSize(new Dimension(100, 100));
        tablePane.resizeTable(rowsAtPage, 1);
        add(tablePane);
        tablePane.setController(new HospitalTableController());
    }

    public void updateDatabaseInformationLabels() {
        tablePane.getCurrentPageNumberLabel().setText(" page " + (pageNumber + 1));
        tablePane.getPagesCountLabel().setText(" of " + (pagesCount + 1));
        tablePane.getRowsCountAtThePageTextField().setText(String.valueOf(rowsAtPage));
    }

    public void showDatabasePart(int startIndex) {
        if (resultModel != null) {
            AbstractPatientDataStruct[] databasePart = new AbstractPatientDataStruct[rowsAtPage];
            for (int i = 0; i < databasePart.length; i++) {
                if (i + startIndex < resultModel.getDatabaseSize()) {
                    databasePart[i] = resultModel.get(startIndex + i);
                }
            }
            tablePane.setTableData(databasePart);
            updateDatabaseInformationLabels();
        } else
            JOptionPane.showMessageDialog(new JFrame(), "Database not connected");
    }

    protected void otherActionOnConfirmButton(){ }


    private class PatientInfoPane extends AbstractPatientInfoPanel {

        PatientInfoPane(AbstractPatientDatabaseModel model) {
            this.model = model;
            init();
        }

        @Override
        protected void initConfirmButton() {
            confirmButton.setText("Search");
            confirmButton.addActionListener(e -> {
                fillBackground();
                if (model != null) {
                    resultModel = model;
                    onAction();

                } else
                    JOptionPane.showMessageDialog(new JFrame(), "Database not connected");
                otherActionOnConfirmButton();

            });
        }

        private void onAction() {
            checkPatientNameField();
            checkPatientAddressOfRegistrationField();
            checkDoctorNameField();
            checkConclusionField();
            checkBirthDatePicker();
            checkAcceptanceDatePicker();
            elementNotExist();
        }

        private void checkAcceptanceDatePicker() {
            if (patientAcceptanceDatePicker.getModel().getValue() != null &&
                    !patientAcceptanceDatePicker.getModel().getValue().equals(Calendar.getInstance().getTime())) {
                resultModel = resultModel.searchAcceptanceDate((Date) patientAcceptanceDatePicker.getModel().getValue());
            }
        }

        private void checkBirthDatePicker() {
            if (patientBirthDatePicker.getModel().getValue() != null &&
                    !patientBirthDatePicker.getModel().getValue().equals(Calendar.getInstance().getTime())) {
                resultModel = resultModel.searchBirthDate((Date) patientBirthDatePicker.getModel().getValue());
            }
        }

        private void checkConclusionField() {
            if (!conclusionField.getText().isEmpty()) {
                conclusionField.setBackground(Color.green);
                resultModel = resultModel.searchConclusion(conclusionField.getText());
            }
        }

        private void checkDoctorNameField() {
            if (!doctorNameField.getText().isEmpty()) {
                doctorNameField.setBackground(Color.green);
                resultModel = resultModel.searchDoctorName(doctorNameField.getText());
            }
        }

        private void checkPatientAddressOfRegistrationField() {
            if (!patientAddressOfRegistrationField.getText().isEmpty()) {
                patientAddressOfRegistrationField.setBackground(Color.green);
                resultModel = resultModel.searchAddressOfRegistration(patientAddressOfRegistrationField.getText());
            }
        }

        private void checkPatientNameField() {
            if (!patientNameField.getText().isEmpty()) {
                patientNameField.setBackground(Color.green);
                resultModel = resultModel.searchPatientName(patientNameField.getText());
            }
        }

        private void elementNotExist() {
            if (resultModel == null || resultModel.getDatabaseSize() == 0) {
                JOptionPane.showMessageDialog(new JFrame(), "Not found");
            } else {
                initModelInfo();
                updateDatabaseInformationLabels();
                showDatabasePart(0);
            }
        }

        private void initModelInfo() {
            if (resultModel != null) {
                pageNumber = 0;
                pagesCount = (int) Math.ceil(((double) resultModel.getDatabaseSize()) / rowsAtPage) - 1;
            } else {
                pageNumber = 0;
                pagesCount = 0;
            }
        }

        private void fillBackground() {
            patientNameField.setBackground(Color.white);
            patientAddressOfRegistrationField.setBackground(Color.white);
            doctorNameField.setBackground(Color.white);
            conclusionField.setBackground(Color.white);

        }

        @Override
        protected void initCancelButton() {
            cancelButton.addActionListener(e -> {
                SearchPatientDialog.this.dispose();
            });
        }

    }

    private class HospitalTableController extends AbstractHospitalTableController {

        @Override
        public void actionPerformed(ActionEvent mouseEvent) {
            if (mouseEvent.getSource() == tablePane.getBackButton()) {
                if (pageNumber > 0)
                    pageNumber--;
            } else if (mouseEvent.getSource() == tablePane.getForwardButton()) {
                if (pageNumber < pagesCount)
                    pageNumber++;
            } else if (mouseEvent.getSource() == tablePane.getStartPageButton()) {
                pageNumber = 0;
            } else if (mouseEvent.getSource() == tablePane.getEndPageButton()) {
                pageNumber = pagesCount;
            } else if (mouseEvent.getSource() == tablePane.getRowsCountAtThePageTextField()) {
                setRowsAtPageCount();
            }
            showDatabasePart(pageNumber * rowsAtPage);
        }

        private void setRowsAtPageCount() {
            int result = 1;
            try {
                result = Integer.parseInt(tablePane.getRowsCountAtThePageTextField().getText());
                tablePane.getRowsCountAtThePageTextField().setBackground(Color.white);
            } catch (NumberFormatException e) {
                tablePane.getRowsCountAtThePageTextField().setBackground(Color.red);
            }
            if (result > 0 && result < 6) {
                rowsAtPage = result;
                pagesCount = (int) Math.ceil(((double) resultModel.getDatabaseSize()) / rowsAtPage) - 1;
                pageNumber = 0;
                tablePane.resizeTable(rowsAtPage, HospitalTable.COLUMN_NAMES.length);
            } else
                tablePane.getRowsCountAtThePageTextField().setBackground(Color.red);
        }
    }
}
