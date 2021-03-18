package view.dialogs;

import model.abstractmodel.AbstractPatientDatabaseModel;
import model.implementation.DataStruct;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;

public abstract class AbstractPatientInfoPanel extends JPanel {
    protected final BorderLayout mainLayout = new BorderLayout();
    protected final JPanel patientInformationLayout = new JPanel(new GridLayout(6, 2));
    protected final JPanel buttonsLayout = new JPanel(new GridLayout(1, 2));
    protected final JButton confirmButton = new JButton("Confirm");
    protected final JButton cancelButton = new JButton("Cancel");

    protected final JLabel patientNameLabel = new JLabel("Patient's name");
    protected final JLabel patientAddressOfRegistrationLabel = new JLabel("Address of registration");
    protected final JLabel patientBirthDateLabel = new JLabel("Birth date");
    protected final JLabel patientAcceptanceDateLabel = new JLabel("Acceptance date");
    protected final JLabel doctorNameLabel = new JLabel("Doctor's name");
    protected final JLabel conclusionLabel = new JLabel("Conclusion");

    protected final JTextField patientNameField = new JTextField();
    protected final JTextField patientAddressOfRegistrationField = new JTextField();
    protected JDatePickerImpl patientBirthDatePicker;
    protected JDatePickerImpl patientAcceptanceDatePicker;
    protected final JTextField doctorNameField = new JTextField();
    protected final JTextField conclusionField = new JTextField();

    protected AbstractPatientDatabaseModel model;

    public void init() {
        setLayout(mainLayout);
        add(patientInformationLayout, BorderLayout.CENTER);
        add(buttonsLayout, BorderLayout.SOUTH);

        initPatientNameField();
        initPatientAddressOfRegistrationField();
        initPatientBirthDatePicker();
        initPatientAcceptanceDatePicker();
        initDoctorNameField();
        initConclusionField();

        initConfirmButton();
        initCancelButton();

        addAllComponentsOnPatientInformationLayout();
        buttonsLayout.add(confirmButton);
        buttonsLayout.add(cancelButton);
    }

    private void addAllComponentsOnPatientInformationLayout() {
        patientInformationLayout.add(patientNameLabel);
        patientInformationLayout.add(patientNameField);
        patientInformationLayout.add(patientAddressOfRegistrationLabel);
        patientInformationLayout.add(patientAddressOfRegistrationField);
        patientInformationLayout.add(patientBirthDateLabel);
        patientInformationLayout.add(patientBirthDatePicker);
        patientInformationLayout.add(patientAcceptanceDateLabel);
        patientInformationLayout.add(patientAcceptanceDatePicker);
        patientInformationLayout.add(doctorNameLabel);
        patientInformationLayout.add(doctorNameField);
        patientInformationLayout.add(conclusionLabel);
        patientInformationLayout.add(conclusionField);
    }

    private void initPatientNameField() {

    }

    private void initPatientAddressOfRegistrationField() {

    }

    private void initPatientBirthDatePicker() {
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        JDatePanelImpl datePanel = new JDatePanelImpl(new UtilDateModel(), p);
        patientBirthDatePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
    }

    private void initPatientAcceptanceDatePicker() {
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        JDatePanelImpl datePanel = new JDatePanelImpl(new UtilDateModel(), p);
        patientAcceptanceDatePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
    }

    private void initDoctorNameField() {

    }

    private void initConclusionField() {

    }

    protected abstract void initConfirmButton();


    protected abstract void initCancelButton();

    public void setModel(AbstractPatientDatabaseModel model){
        this.model = model;
    }


    private class DateLabelFormatter extends JFormattedTextField.AbstractFormatter {

        private final String datePattern = "dd-MM-yyyy";
        private final SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

        @Override
        public Object stringToValue(String text) throws ParseException {
            return dateFormatter.parseObject(text);
        }

        @Override
        public String valueToString(Object value) throws ParseException {
            if (value != null) {
                Calendar cal = (Calendar) value;
                return dateFormatter.format(cal.getTime());
            }
            return "";
        }

    }
}
