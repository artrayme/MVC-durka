package view.dialogs;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Properties;

public class PatientInfoPanel extends JPanel {

    protected final JLabel patientNameLabel = new JLabel("Patient's name");
    protected final JLabel patientSecondNameLabel = new JLabel("Patient's second name");
    protected final JLabel patientFatherNameLabel = new JLabel("Patient's father name");
    protected final JLabel patientAddressOfRegistrationLabel = new JLabel("Address of registration");
    protected final JLabel patientBirthDateLabel = new JLabel("Birth date");
    protected final JLabel patientAcceptanceDateLabel = new JLabel("Acceptance date");
    protected final JLabel doctorNameLabel = new JLabel("Doctor's name");
    protected final JLabel doctorSecondNameLabel = new JLabel("Doctor's second name");
    protected final JLabel doctorFatherNameLabel = new JLabel("Doctor's father name");
    protected final JLabel conclusionLabel = new JLabel("Conclusion");

    protected final JTextField patientNameField = new JTextField();
    protected final JTextField patientSecondNameField = new JTextField();
    protected final JTextField patientFatherNameField = new JTextField();
    protected final JTextField patientAddressOfRegistrationField = new JTextField();
    protected JDatePickerImpl patientBirthDatePicker;
    protected JDatePickerImpl patientAcceptanceDatePicker;
    protected final JTextField doctorNameField = new JTextField();
    protected final JTextField doctorSecondNameField = new JTextField();
    protected final JTextField doctorFatherNameField = new JTextField();
    protected final JTextField conclusionField = new JTextField();

    public void init() {
        setLayout(new GridLayout(10, 2));

        initPatientBirthDatePicker();
        initPatientAcceptanceDatePicker();

        addAllComponentsOnPatientInformationLayout();
    }

    private void addAllComponentsOnPatientInformationLayout() {
        add(patientNameLabel);
        add(patientNameField);
        add(patientSecondNameLabel);
        add(patientSecondNameField);
        add(patientFatherNameLabel);
        add(patientFatherNameField);
        add(patientAddressOfRegistrationLabel);
        add(patientAddressOfRegistrationField);
        add(patientBirthDateLabel);
        add(patientBirthDatePicker);
        add(patientAcceptanceDateLabel);
        add(patientAcceptanceDatePicker);
        add(doctorNameLabel);
        add(doctorNameField);
        add(doctorSecondNameLabel);
        add(doctorSecondNameField);
        add(doctorFatherNameLabel);
        add(doctorFatherNameField);
        add(conclusionLabel);
        add(conclusionField);
    }

    public String getPatientName() {
        return patientNameField.getText();
    }

    public String getPatientSecondName() {
        return patientSecondNameField.getText();
    }

    public String getPatientFatherName() {
        return patientFatherNameField.getText();
    }

    public String getPatientAddressOfRegistration() {
        return patientAddressOfRegistrationField.getText();
    }

    public GregorianCalendar getPatientBirthDate() {
        if (patientBirthDatePicker.getModel().getValue() == null) return null;
        return new GregorianCalendar(patientBirthDatePicker.getModel().getYear(),
                patientBirthDatePicker.getModel().getMonth(),
                patientBirthDatePicker.getModel().getDay());
    }

    public GregorianCalendar getAcceptanceDate() {
        if (patientAcceptanceDatePicker.getModel().getValue() == null) return null;
        return new GregorianCalendar(patientAcceptanceDatePicker.getModel().getYear(),
                patientAcceptanceDatePicker.getModel().getMonth(),
                patientAcceptanceDatePicker.getModel().getDay());
    }

    public String getDoctorName() {
        return doctorNameField.getText();
    }


    public String getDoctorSecondName() {
        return doctorSecondNameField.getText();
    }


    public String getDoctorFatherName() {
        return doctorFatherNameField.getText();
    }

    public String getConclusion() {
        return conclusionField.getText();
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
                Calendar cal = (GregorianCalendar) value;
                return dateFormatter.format(cal.getTime());
            }
            return "";
        }

    }
}
