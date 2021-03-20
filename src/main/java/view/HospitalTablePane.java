package view;

import controller.abstractcontroller.AbstractHospitalTableController;
import model.abstractmodel.AbstractPatientDatabaseModel;
import model.abstractmodel.DatabaseChangeListener;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class HospitalTablePane extends JPanel {
    private AbstractHospitalTableController controller;
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

    public HospitalTablePane(AbstractHospitalTableController controller) {
        this.controller = controller;
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
        rowsCountAtThePageTextField.addActionListener(e -> {
            setRowsAtPageCount();
        });
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
        if (!controller.isDatabaseConnect())
            backButton.setEnabled(false);
        backButton.setIcon(initButtonIcon("/icons/back_arrow.png"));
        backButton.addActionListener(e -> {
            controller.onBackPageButton();
            setTableData();
        });
    }

    private void initForwardButton() {
        if (!controller.isDatabaseConnect())
            forwardButton.setEnabled(false);
        forwardButton.setIcon(initButtonIcon("/icons/forward_arrow.png"));
        forwardButton.addActionListener(e -> {
            controller.onForwardPageButton();
            setTableData();
        });

    }

    private void initStartPageButton() {
        if (!controller.isDatabaseConnect())
            startPageButton.setEnabled(false);
        startPageButton.setIcon(initButtonIcon("/icons/start_page.png"));
        startPageButton.addActionListener(e -> {
            controller.onStartPageButton();
            setTableData();
        });
    }

    private void initEndPageButton() {
        if (!controller.isDatabaseConnect())
            endPageButton.setEnabled(false);
        endPageButton.setIcon(initButtonIcon("/icons/end_page.png"));
        endPageButton.addActionListener(e -> {
            controller.onEndPageButton();
            setTableData();
        });
    }

    public void setTableData() {
        updatePagesInformation();
        var database = controller.getDatabase();
        System.out.println("HospitalTablePane:setTableData " + Arrays.toString(database));
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

    public void updatePagesInformation() {
        pagesCountLabel.setText(" of " + (controller.getPagesCount() + 1));
        currentPageNumberLabel.setText("page " + (controller.getPageNumber() + 1));
        if (controller.isDatabaseConnect()) {
            databaseStatusLabel.setText("Database connected");
            databaseStatusLabel.setForeground(Color.GREEN);
        } else {
            databaseStatusLabel.setText("Database not connected");
            databaseStatusLabel.setForeground(Color.RED);
        }
    }

    private void setRowsAtPageCount() {
        int result = 1;
        try {
            result = Integer.parseInt(rowsCountAtThePageTextField.getText());
            rowsCountAtThePageTextField.setBackground(Color.white);
        } catch (NumberFormatException e) {
            rowsCountAtThePageTextField.setBackground(Color.red);
        }
        if (controller.onRowsCountAtThePageTextField(result)) {
            resizeTable(controller.getRowsAtPage(), HospitalTable.COLUMN_NAMES.length);
            setTableData();
        } else
            rowsCountAtThePageTextField.setBackground(Color.red);
    }

    public void loadDatabaseFromFile(File file) {
        if (controller.loadDatabaseFromFile(file)) {
            controller.addModelActionListener(new ModelChangeListener());
            enableButtons();
        } else
            JOptionPane.showMessageDialog(new JFrame(), "Something wrong with the xml database file");
        setTableData();
    }


    public void saveDatabaseToFile(File file) {
        if (controller.isDatabaseConnect()) {
            if (!controller.saveDatabaseFromFile(file))
                JOptionPane.showMessageDialog(new JFrame(), "Database not Saved!");
        } else
            JOptionPane.showMessageDialog(new JFrame(), "Database not connected");
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

    public void setModel(AbstractPatientDatabaseModel model) {
        controller.setModel(model);
        if (controller.isDatabaseConnect()) {
            controller.addModelActionListener(new ModelChangeListener());
            enableButtons();
        } else disableButtons();
        setTableData();
    }

    private void enableButtons() {
        backButton.setEnabled(true);
        forwardButton.setEnabled(true);
        startPageButton.setEnabled(true);
        endPageButton.setEnabled(true);
    }

    private void disableButtons() {
        backButton.setEnabled(false);
        forwardButton.setEnabled(false);
        startPageButton.setEnabled(false);
        endPageButton.setEnabled(false);
    }

    public boolean isDatabaseConnected() {
        return controller.isDatabaseConnect();
    }

    public AbstractPatientDatabaseModel getModel() {
        return controller.getModel();
    }


    private class ModelChangeListener implements DatabaseChangeListener {

        @Override
        public void onContentChange() {
            System.out.println("HospitalTablePane:ModelChangeListener hear");
            controller.updateDatabaseInfo();
            setTableData();
        }

        @Override
        public void onNameChange() {
        }
    }
}
