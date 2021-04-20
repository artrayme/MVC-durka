package gui;

import javax.swing.*;
import java.awt.*;

public class TextFieldDialog extends JFrame {
    private final JLabel filenameLabel = new JLabel("Write filename");
    private final JTextField filenameField = new JTextField();
    private final JButton confirmButton = new JButton("Confirm");
    public TextFieldDialog(){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) (screenSize.getWidth() * 0.2);
        int height = (int) (screenSize.getHeight() * 0.1);

        setBounds((screenSize.width - width) / 2, (screenSize.height - height) / 2, width, height);
        setLayout(new BorderLayout());
        add(filenameLabel, BorderLayout.NORTH);
        add(filenameField, BorderLayout.CENTER);
        add(confirmButton, BorderLayout.SOUTH);
        confirmButton.addActionListener(e->{
            this.setVisible(false);
        });
    }

    public String getText(){
        return filenameField.getText();
    }
}
