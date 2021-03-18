package view;

import controller.abstractcontroller.AbstractHospitalTableController;
import model.abstractmodel.AbstractPatientDataStruct;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.io.IOException;

public class HospitalTablePane extends JPanel {
    public static String DATABASE_NOT_CONNECTED = "Database not connected";
    public static String DATABASE_CONNECTED = "Database connected";
    private final Box buttonsLayout = Box.createHorizontalBox();
    private final JButton startPageButton = new JButton();
    private final JButton backButton = new JButton();
    private final JButton forwardButton = new JButton();
    private final JButton endPageButton = new JButton();
    private final JLabel databaseStatusLabel = new JLabel();
    private final JLabel currentPageNumberLabel = new JLabel();
    private final JLabel pagesCountLabel = new JLabel();
    private final JTextField rowsCountAtThePageTextField = new JTextField();

    private final HospitalTable table = new HospitalTable(10, 6);
    private final JTable headerTable = new JTable(1, HospitalTable.COLUMN_NAMES.length);

    public HospitalTablePane() {
        init();
    }

    private void init() {
        setLayout(new BorderLayout());
        add(table, BorderLayout.CENTER);
        add(buttonsLayout, BorderLayout.NORTH);
        add(headerTable, BorderLayout.SOUTH);

        initColumnsNames();
        initButtonsLayout();
    }

    private void initButtonsLayout() {
        initStartPageButton();
        initBackButton();
        initForwardButton();
        initEndPageButton();
        initRowsAtPageField();

        buttonsLayout.add(startPageButton);
        buttonsLayout.add(backButton);
        buttonsLayout.add(forwardButton);
        buttonsLayout.add(endPageButton);
        buttonsLayout.add(databaseStatusLabel);
        buttonsLayout.add(rowsCountAtThePageTextField);
        buttonsLayout.add(currentPageNumberLabel);
        buttonsLayout.add(pagesCountLabel);
    }

    private void initRowsAtPageField() {
        rowsCountAtThePageTextField.setText(String.valueOf(0));
        rowsCountAtThePageTextField.setMaximumSize(new Dimension(110, 32));
        rowsCountAtThePageTextField.setAlignmentX(1);
        rowsCountAtThePageTextField.setAlignmentX(1);
    }

    public void resizeTable(int rowsCount, int columnsCount) {
        table.resizeTable(rowsCount, columnsCount);
    }

    private void initColumnsNames() {
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < HospitalTable.COLUMN_NAMES.length; i++) {
            headerTable.setValueAt(HospitalTable.COLUMN_NAMES[i], 0, i);
            headerTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        headerTable.setBackground(Color.lightGray);
        headerTable.setShowGrid(false);
    }

    private ImageIcon initButtonIcon(String path) {
        Image dimg = null;
        try {
            Image img = ImageIO.read(getClass().getResource(path));
            dimg = img.getScaledInstance(32, 32, Image.SCALE_SMOOTH);
        } catch (IOException exception) {
            //ToDO normal catch
            exception.printStackTrace();
        }
        assert dimg != null;
        return new ImageIcon(dimg);
    }

    private void initBackButton() {
        backButton.setIcon(initButtonIcon("/icons/back_arrow.png"));
    }

    private void initForwardButton() {
        forwardButton.setIcon(initButtonIcon("/icons/forward_arrow.png"));

    }

    private void initStartPageButton() {
        startPageButton.setIcon(initButtonIcon("/icons/start_page.png"));

    }

    private void initEndPageButton() {
        endPageButton.setIcon(initButtonIcon("/icons/end_page.png"));
    }

    public void setTableData(AbstractPatientDataStruct[] database) {
        for (int i = 0; i < database.length; i++) {
            if (database[i] != null) {
                table.setValueAt(database[i].getPatientName(), i, 0);
                table.setValueAt(database[i].getPatientAddressOfRegistration(), i, 1);
                table.setValueAt(database[i].getPatientBirthDate(), i, 2);
                table.setValueAt(database[i].getPatientAcceptanceDate(), i, 3);
                table.setValueAt(database[i].getDoctorName(), i, 4);
                table.setValueAt(database[i].getConclusion(), i, 5);
            } else {
                for (int j = 0; j < HospitalTable.COLUMN_NAMES.length; j++) {
                    table.setValueAt("", i, j);
                }
            }
        }
    }

    public void setController(AbstractHospitalTableController controller) {
        startPageButton.addActionListener(controller);
        backButton.addActionListener(controller);
        forwardButton.addActionListener(controller);
        endPageButton.addActionListener(controller);
        rowsCountAtThePageTextField.addActionListener(controller);
    }

    public JTextField getRowsCountAtThePageTextField() {
        return rowsCountAtThePageTextField;
    }

    public Box getButtonsLayout() {
        return buttonsLayout;
    }

    public JButton getStartPageButton() {
        return startPageButton;
    }

    public JButton getBackButton() {
        return backButton;
    }

    public JButton getForwardButton() {
        return forwardButton;
    }

    public JButton getEndPageButton() {
        return endPageButton;
    }

    public JLabel getDatabaseStatusLabel() {
        return databaseStatusLabel;
    }

    public JLabel getCurrentPageNumberLabel() {
        return currentPageNumberLabel;
    }

    public JLabel getPagesCountLabel() {
        return pagesCountLabel;
    }
}
