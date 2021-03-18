package view;

import gui.MainWindow;
import model.abstractmodel.AbstractPatientDatabaseModel;
import model.implementation.Database;
import view.dialogs.AddPatientDialog;
import view.dialogs.DeletePatientDialog;
import view.dialogs.SearchPatientDialog;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;

public class ToolBar extends JPanel {
    private final JButton newDatabaseButton = new JButton();
    private final JButton newElementButton = new JButton();
    private final JButton findElementButton = new JButton();
    private final JButton deleteElementButton = new JButton();

    private JButton lastClicked = newDatabaseButton;

    public ToolBar() {
        setBackground(Color.darkGray);
        initToolBar();
    }

    private void initToolBar() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        initAll();
        addAll();
    }

    private void initAll() {
        initNewDatabaseButton();
        initNewElementButton();
        initFindElementButton();
        initDeleteElementButton();
    }

    private void addAll() {
        add(newDatabaseButton);
        add(new JToolBar.Separator());
        add(newElementButton);
        add(deleteElementButton);
        add(findElementButton);
    }

    private ImageIcon initButtonIcon(String path) {
        Image dimg = null;
        try {
            Image img = ImageIO.read(getClass().getResource(path));
            dimg = img.getScaledInstance(32, 32, Image.SCALE_SMOOTH);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        assert dimg != null;
        return new ImageIcon(dimg);
    }

    private void initNewDatabaseButton() {
        newDatabaseButton.setIcon(initButtonIcon("/icons/new_file.png"));
        newDatabaseButton.setBackground(new Color(220, 220, 220));
        newDatabaseButton.addActionListener(actionEvent -> {
            ((HospitalTableMainWindow) (MainWindow.tabBar.getSelectedComponent())).setModel(new Database());
            lastClicked.setBackground(new Color(220, 220, 220));
            lastClicked = newDatabaseButton;
            newDatabaseButton.setBackground(new Color(118, 246, 74));
        });
    }

    private void initNewElementButton() {
        newElementButton.setIcon(initButtonIcon("/icons/new_element.png"));
        newElementButton.setBackground(new Color(220, 220, 220));
        newElementButton.addActionListener(actionEvent -> {
            lastClicked.setBackground(new Color(220, 220, 220));
            lastClicked = newElementButton;
            var temp = (HospitalTableMainWindow) (MainWindow.tabBar.getSelectedComponent());
            if (temp.getIsDatabaseConnect()) {
                newElementButton.setBackground(new Color(118, 246, 74));
                createAddDialog(temp.getModel());
            } else {
                newElementButton.setBackground(new Color(246, 74, 74));
                JOptionPane.showMessageDialog(new JFrame(), "Database not connected");
            }
        });
    }

    private void createAddDialog(AbstractPatientDatabaseModel model) {
        AddPatientDialog dialog = new AddPatientDialog(model);
        dialogInit(dialog);
    }

    private void createDeleteDialog(AbstractPatientDatabaseModel model) {
        DeletePatientDialog dialog = new DeletePatientDialog(model);
        dialogInit(dialog);
    }

    private void createSearchDialog(AbstractPatientDatabaseModel model) {
        SearchPatientDialog dialog = new SearchPatientDialog(model);
        dialogInit(dialog);
    }

    private void dialogInit(JFrame dialog) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) (screenSize.getWidth() * 0.4);
        int height = (int) (screenSize.getHeight() * 0.4);
        dialog.setBounds((screenSize.width - width) / 2, (screenSize.height - height) / 2, width, height);
        dialog.setVisible(true);
    }

    private void initDeleteElementButton() {
        deleteElementButton.setIcon(initButtonIcon("/icons/delete_element.png"));
        deleteElementButton.setBackground(new Color(220, 220, 220));
        deleteElementButton.addActionListener(actionEvent -> {
            lastClicked.setBackground(new Color(220, 220, 220));
            lastClicked = deleteElementButton;
            deleteElementButton.setBackground(new Color(118, 246, 74));
            var temp = (HospitalTableMainWindow) (MainWindow.tabBar.getSelectedComponent());
            if (temp.getIsDatabaseConnect()) {
                deleteElementButton.setBackground(new Color(118, 246, 74));
                createDeleteDialog(temp.getModel());
            } else {
                deleteElementButton.setBackground(new Color(246, 74, 74));
                JOptionPane.showMessageDialog(new JFrame(), "Database not connected");
            }
        });
    }

    private void initFindElementButton() {
        findElementButton.setIcon(initButtonIcon("/icons/find_element.png"));
        findElementButton.setBackground(new Color(220, 220, 220));
        findElementButton.addActionListener(actionEvent -> {
            lastClicked.setBackground(new Color(220, 220, 220));
            lastClicked = findElementButton;
            findElementButton.setBackground(new Color(118, 246, 74));
            var temp = (HospitalTableMainWindow) (MainWindow.tabBar.getSelectedComponent());
            if (temp.getIsDatabaseConnect()) {
                findElementButton.setBackground(new Color(118, 246, 74));
                createSearchDialog(temp.getModel());
            } else {
                findElementButton.setBackground(new Color(246, 74, 74));
                JOptionPane.showMessageDialog(new JFrame(), "Database not connected");
            }
        });
    }

}
