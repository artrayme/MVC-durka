package gui;


import controller.implementation.HospitalTablePaneController;
import view.HospitalTablePane;
import view.ToolBar;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    public static final JTabbedPane tabBar = new JTabbedPane();

    public final MenuBar menu = new MenuBar();
    public final ToolBar toolBar = new ToolBar();
    public static int tabsCount = 0;

    public MainWindow() {
        setLayout(new BorderLayout());
        this.setJMenuBar(menu);
        tabBar.addTab("tab" + tabsCount++, new HospitalTablePane(new HospitalTablePaneController(null)));

        this.add(toolBar, BorderLayout.NORTH);
        this.add(tabBar, BorderLayout.CENTER);
    }

}
