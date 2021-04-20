import gui.MainWindow;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;
import saver.XmlLoader;
import servercontroller.Server;

import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Main {


    public static void main(String[] args) {
        final Logger logger = LogManager.getLogger(Main.class);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) (screenSize.getWidth() * 0.4);
        int height = (int) (screenSize.getHeight() * 0.6);

        MainWindow mainWindow;
        try {
            mainWindow = new MainWindow(new Server(XmlLoader.load(new File("/home/artemy/терапия4.xml"))));
            mainWindow.setTitle("Durka-Server");
            mainWindow.setBounds((screenSize.width - width) / 2, (screenSize.height - height) / 2, width, height);
            mainWindow.setVisible(true);
        } catch (IOException e) {
            logger.error("Error at creating a server", e);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
    }

}
