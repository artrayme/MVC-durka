package view;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class HospitalTable extends JTable {
    public static final String[] COLUMN_NAMES = {"Patient's name", "Address of registration", "Birth date", "Acceptance date", "Doctor's name", "Conclusion"};

    public HospitalTable(int rowsCount, int columnCount) {
        super(10, 7);
        this.setRowHeight(50);
        resizeTable(rowsCount, columnCount);
    }

    public void resizeTable(int rowsCount, int columnsCount) {
        DefaultTableModel tableModel = new DefaultTableModel(new Object[rowsCount][columnsCount], COLUMN_NAMES) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        setModel(tableModel);
    }

}
