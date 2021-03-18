package gui;


import view.HospitalTableMainWindow;
import view.HospitalTablePane;
import view.ToolBar;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    public static final JTabbedPane tabBar = new JTabbedPane();

    public final MenuBar menuBar = new MenuBar();
    public final ToolBar toolBar = new ToolBar();
    public static int tabsCount = 0;

    public MainWindow() {
        setLayout(new BorderLayout());
        this.setJMenuBar(menuBar);
        tabBar.addTab("tab"+tabsCount++, new HospitalTableMainWindow(null, new HospitalTablePane()));

        this.add(toolBar, BorderLayout.NORTH);
        this.add(tabBar, BorderLayout.CENTER);
    }

}
