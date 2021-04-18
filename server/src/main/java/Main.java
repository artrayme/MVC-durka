import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.status.StatusConsoleListener;
import org.apache.logging.log4j.status.StatusLogger;

public class Main {
//
//    public static void main(String[] args) {
//        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
//        int width = (int) (screenSize.getWidth() * 0.4);
//        int height = (int) (screenSize.getHeight() * 0.6);
//
//        MainWindow mainWindow = null;
//        try {
//            mainWindow = new MainWindow(new Server(null));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        mainWindow.setTitle("Durka-Server");
//        mainWindow.setBounds((screenSize.width - width) / 2, (screenSize.height - height) / 2, width, height);
//        mainWindow.setVisible(true);
//    }


    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {

        Main obj = new Main();
        obj.runMe("mkyong");

    }

    private void runMe(String parameter){

        logger.info("INFOOOOOOOOO");

        if(logger.isDebugEnabled()){
            logger.debug("This is debug : " + parameter);
        }

        if(logger.isInfoEnabled()){
            logger.info("This is info : " + parameter);
        }

        logger.warn("This is warn : " + parameter);
        logger.error("This is error : " + parameter);
        logger.fatal("This is fatal : " + parameter);

    }
}
