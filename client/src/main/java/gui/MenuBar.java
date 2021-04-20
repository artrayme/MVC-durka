package gui;

import controller.AddPatientPanelController;
import controller.implementation.HospitalTablePaneController;
import controller.RemovePatientPanelController;
import controller.SearchPatientPanelController;
import model.abstractmodel.AbstractPatientDatabaseModel;
import view.HospitalTablePane;
import view.dialogs.addpatient.AddPatientDialog;
import view.dialogs.removepatient.RemovePatientDialog;
import view.dialogs.searchpatients.SearchPatientDialog;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class MenuBar extends JMenuBar {
    private final JMenu fileMenu = new JMenu("<html><font color='#d6d6d6'>File</font></html>");
    private final JMenu editMenu = new JMenu("<html><font color='#d6d6d6'>Edit</font></html>");
    private final JMenu helpMenu = new JMenu("<html><font color='#d6d6d6'>Help</font></html>");

    private final TextFieldDialog dialog = new TextFieldDialog();

    public MenuBar() {
        initFileMenu();
        initEditMenu();
        initHelpMenu();
        setBackground(Color.DARK_GRAY);
    }

    private void initFileMenu() {
        JMenuItem newTab = initNewTabItem();
        JMenuItem save = initSaveItem();
        JMenuItem load = initLoadItem();
        JMenuItem close = initCloseItem();
        JMenuItem exit = initExitItem();
        fileMenu.add(newTab);
        fileMenu.add(save);
        fileMenu.add(load);
        fileMenu.add(close);
        fileMenu.add(exit);
        this.add(fileMenu);
    }

    private JMenuItem initNewTabItem() {
        JMenuItem newTab = new JMenuItem("<html><font color='#d6d6d6'>New</font></html>");
        newTab.setBackground(Color.DARK_GRAY);
        newTab.addActionListener(actionEvent -> {
            MainWindow.tabBar.add("tab" + MainWindow.tabsCount++, new HospitalTablePane(new HospitalTablePaneController(null)));
        });
        return newTab;
    }


    private JMenuItem initSaveItem() {
        JMenuItem save = new JMenuItem("<html><font color='#d6d6d6'>Save</font></html>");
        save.setBackground(Color.DARK_GRAY);
        save.addActionListener(actionEvent -> {
            var temp = (HospitalTablePane) (MainWindow.tabBar.getSelectedComponent());
            temp.saveDatabaseToFile(dialog.getText());
        });
        return save;
    }

    private JMenuItem initLoadItem() {
        JMenuItem load = new JMenuItem("<html><font color='#d6d6d6'>Load</font></html>");
        load.setBackground(Color.darkGray);
        load.addActionListener(actionEvent -> {
            var temp = (HospitalTablePane) (MainWindow.tabBar.getSelectedComponent());
            temp.loadDatabaseFromFile(dialog.getText());
        });
        return load;
    }

    private JMenuItem initCloseItem() {
        JMenuItem close = new JMenuItem("<html><font color='#d6d6d6'>Close</font></html>");
        close.setBackground(Color.darkGray);
        close.addActionListener(actionEvent -> {
            MainWindow.tabBar.remove(MainWindow.tabBar.getSelectedComponent());
        });
        return close;
    }

    private JMenuItem initExitItem() {
        JMenuItem exit = new JMenuItem("<html><font color='#d6d6d6'>Exit</font></html>");
        exit.setBackground(Color.darkGray);
        exit.addActionListener(actionEvent -> {
            System.exit(0);
        });
        return exit;
    }

    private void initEditMenu() {
        JMenuItem newPatient = initNewPatientDialogItem();
        JMenuItem removePatient = initRemovePatientDialogItem();
        JMenuItem findPatient = initFindPatientItem();
        JMenuItem setFilename = initSetFilenameItem();
        editMenu.add(newPatient);
        editMenu.add(removePatient);
        editMenu.add(findPatient);
        editMenu.add(setFilename);
        this.add(editMenu);
    }

    private JMenuItem initNewPatientDialogItem() {
        JMenuItem newPatient = new JMenuItem("<html><font color='#d6d6d6'>New patient</font></html>");
        newPatient.setBackground(Color.DARK_GRAY);
        newPatient.addActionListener(actionEvent -> {
            var temp = (HospitalTablePane) (MainWindow.tabBar.getSelectedComponent());
            if (temp.isDatabaseConnected()) {
                createAddDialog(temp.getModel());
            } else {
                JOptionPane.showMessageDialog(new JFrame(), "Database not connected");
            }
        });
        return newPatient;
    }


    private JMenuItem initRemovePatientDialogItem() {
        JMenuItem removePatient = new JMenuItem("<html><font color='#d6d6d6'>Remove patient</font></html>");
        removePatient.setBackground(Color.DARK_GRAY);
        removePatient.addActionListener(actionEvent -> {
            var temp = (HospitalTablePane) (MainWindow.tabBar.getSelectedComponent());
            if (temp.isDatabaseConnected()) {
                createDeleteDialog(temp.getModel());
            } else {
                JOptionPane.showMessageDialog(new JFrame(), "Database not connected");
            }
        });
        return removePatient;
    }

    private JMenuItem initFindPatientItem() {
        JMenuItem findPatient = new JMenuItem("<html><font color='#d6d6d6'>Find patient</font></html>");
        findPatient.setBackground(Color.darkGray);
        findPatient.addActionListener(actionEvent -> {
            var temp = (HospitalTablePane) (MainWindow.tabBar.getSelectedComponent());
            if (temp.isDatabaseConnected()) {
                createSearchDialog(temp.getModel());
            } else {
                JOptionPane.showMessageDialog(new JFrame(), "Database not connected");
            }
        });
        return findPatient;
    }

    private JMenuItem initSetFilenameItem() {
        JMenuItem filename = new JMenuItem("<html><font color='#d6d6d6'>SetFilename</font></html>");
        filename.setBackground(Color.darkGray);
        filename.addActionListener(actionEvent -> {
            dialog.setVisible(true);
        });
        return filename;
    }

    private void createAddDialog(AbstractPatientDatabaseModel model) {
        AddPatientDialog dialog = new AddPatientDialog(new AddPatientPanelController(model));
        dialogInit(dialog);
    }

    private void createDeleteDialog(AbstractPatientDatabaseModel model) {
        RemovePatientDialog dialog = new RemovePatientDialog(new RemovePatientPanelController(model));
        dialogInit(dialog);
    }

    private void createSearchDialog(AbstractPatientDatabaseModel model) {
        SearchPatientDialog dialog = new SearchPatientDialog(new SearchPatientPanelController(model));
        dialogInit(dialog);
    }

    private void dialogInit(JFrame dialog) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) (screenSize.getWidth() * 0.4);
        int height = (int) (screenSize.getHeight() * 0.4);
        dialog.setBounds((screenSize.width - width) / 2, (screenSize.height - height) / 2, width, height);
        dialog.setVisible(true);
    }


    private void initHelpMenu() {
        JMenuItem about = initAboutItem();
        helpMenu.add(about);
        this.add(helpMenu);
    }

    private JMenuItem initAboutItem() {
        JMenuItem about = new JMenuItem("<html><font color='#d6d6d6'>About</font></html>");
        about.setBackground(Color.darkGray);
        about.addActionListener(actionEvent -> {
            new AboutWindow(this.getX(), this.getY());
        });
        return about;
    }

}
