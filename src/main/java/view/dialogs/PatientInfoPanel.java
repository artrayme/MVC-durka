package view.dialogs;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

public class PatientInfoPanel extends JPanel {

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

    public void init() {
        setLayout(new GridLayout(6, 2));

        initPatientNameField();
        initPatientAddressOfRegistrationField();
        initPatientBirthDatePicker();
        initPatientAcceptanceDatePicker();
        initDoctorNameField();
        initConclusionField();

        addAllComponentsOnPatientInformationLayout();
    }

    public String getPatientName() {
        return patientNameField.getText();
    }

    public String getPatientAddressOfRegistration() {
        return patientAddressOfRegistrationField.getText();
    }

    public Date getPatientBirthDate() {
        return (Date) patientBirthDatePicker.getModel().getValue();
    }

    public Date getAcceptanceDate() {
        return (Date) patientAcceptanceDatePicker.getModel().getValue();
    }

    public String getDoctorName() {
        return doctorNameField.getText();
    }

    public String getConclusion() {
        return conclusionField.getText();
    }

    private void addAllComponentsOnPatientInformationLayout() {
        add(patientNameLabel);
        add(patientNameField);
        add(patientAddressOfRegistrationLabel);
        add(patientAddressOfRegistrationField);
        add(patientBirthDateLabel);
        add(patientBirthDatePicker);
        add(patientAcceptanceDateLabel);
        add(patientAcceptanceDatePicker);
        add(doctorNameLabel);
        add(doctorNameField);
        add(conclusionLabel);
        add(conclusionField);
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

    private class DateLabelFormatter extends JFormattedTextField.AbstractFormatter {

        private final String datePattern = "dd-MM-yyyy";
        private final SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

        @Override
        public Object stringToValue(String text) throws ParseException {
            return dateFormatter.parseObject(text);
        }

        @Override
        public String valueToString(Object value) {
            if (value != null) {
                Calendar cal = (Calendar) value;
                return dateFormatter.format(cal.getTime());
            }
            return "";
        }

    }
}
