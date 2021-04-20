package view;

import controller.abstractcontroller.AbstractHospitalTableController;
import controller.implementation.HospitalTablePaneController;
import model.abstractmodel.AbstractPatientDatabaseModel;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class HospitalTablePane extends JPanel {
    private final AbstractHospitalTableController controller;
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

        updatePagesAndDatabaseInformation();
    }

    private void initRowsAtPageField() {
        rowsCountAtThePageTextField.setText(String.valueOf(10));
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
            JOptionPane.showMessageDialog(new JFrame(), "Something wrong with buttons icons");
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
            updateTableData();
        });
    }

    private void initForwardButton() {
        if (!controller.isDatabaseConnect())
            forwardButton.setEnabled(false);
        forwardButton.setIcon(initButtonIcon("/icons/forward_arrow.png"));
        forwardButton.addActionListener(e -> {
            controller.onForwardPageButton();
            updateTableData();
        });

    }

    private void initStartPageButton() {
        if (!controller.isDatabaseConnect())
            startPageButton.setEnabled(false);
        startPageButton.setIcon(initButtonIcon("/icons/start_page.png"));
        startPageButton.addActionListener(e -> {
            controller.onStartPageButton();
            updateTableData();
        });
    }

    private void initEndPageButton() {
        if (!controller.isDatabaseConnect())
            endPageButton.setEnabled(false);
        endPageButton.setIcon(initButtonIcon("/icons/end_page.png"));
        endPageButton.addActionListener(e -> {
            controller.onEndPageButton();
            updateTableData();
        });
    }

    public void updateTableData() {
        updatePagesAndDatabaseInformation();
        String[][] database = new String[0][];
        try {
            database = controller.getDatabase();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < database.length; i++) {
            for (int j = 0; j < HospitalTable.COLUMN_NAMES.length; j++) {
                table.setValueAt(database[i][j], i, j);
            }
        }
    }

    public void updatePagesAndDatabaseInformation() {
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
            if (controller.isDatabaseConnect())
                updateTableData();
        } else
            rowsCountAtThePageTextField.setBackground(Color.red);
    }

    public void loadDatabaseFromFile(String filename) {
        if (controller.loadDatabaseFromFile(filename)) {
            controller.addModelActionListener(e -> {
                controller.updateDatabaseInfo();
                updateTableData();
            });
            enableButtons();
        } else
            JOptionPane.showMessageDialog(new JFrame(), "Something wrong with the xml database file");
        updateTableData();
    }

    public void saveDatabaseToFile(String fileName) {
        if (controller.isDatabaseConnect()) {
            if (!controller.saveDatabaseToFile(fileName))
                JOptionPane.showMessageDialog(new JFrame(), "Database not Saved!");
        } else
            JOptionPane.showMessageDialog(new JFrame(), "Database not connected");
    }

    public void setModel(AbstractPatientDatabaseModel model) {
        controller.setModel(model);
        if (controller.isDatabaseConnect()) {
            controller.addModelActionListener(e -> {
                controller.updateDatabaseInfo();
                updateTableData();
            });
            enableButtons();
        } else
            disableButtons();
        updateTableData();
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

}
