package gui;

import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.ErrorHandler;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.layout.PatternLayout;

import javax.swing.*;
import java.io.Serializable;

public class LogTextArea extends JScrollPane {
    private final JTextArea logArea = new JTextArea();

    LogTextArea(){
        init();
    }

    private void init() {

    }

}
