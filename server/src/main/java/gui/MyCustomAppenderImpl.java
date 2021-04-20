package gui;//
import java.io.Serializable;
import java.util.concurrent.locks.*;
import org.apache.logging.log4j.core.*;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.appender.AppenderLoggingException;
import org.apache.logging.log4j.core.config.plugins.*;
import org.apache.logging.log4j.core.layout.PatternLayout;

@Plugin(name="MyCustomAppender", category="Core", elementType="appender", printObject=true)

public final class MyCustomAppenderImpl extends AbstractAppender {

//    private final ReadWriteLock rwLock = new ReentrantReadWriteLock();
//    private final Lock readLock = rwLock.readLock();

    protected MyCustomAppenderImpl(String name, Filter filter,
                                   Layout<? extends Serializable> layout, final boolean ignoreExceptions) {
        super(name, filter, layout, ignoreExceptions);
    }

    @Override
    public void append(LogEvent event) {
//        readLock.lock();
//        try {
//            final byte[] bytes = getLayout().toByteArray(event);
//            System.out.print("!!!!!!!!  ");
//            System.out.write(bytes);
//        } catch (Exception ex) {
//            if (!ignoreExceptions()) {
//                throw new AppenderLoggingException(ex);
//            }
//        } finally {
//            readLock.unlock();
//        }

//        System.out.println("&&&&& " + event.getMessage().getFormattedMessage());
        MainWindow.addLog(event.getMessage().getFormattedMessage() + "\n");
    }

    @PluginFactory
    public static MyCustomAppenderImpl createAppender(
            @PluginAttribute("name") String name,
            @PluginElement("Layout") Layout<? extends Serializable> layout,
            @PluginElement("Filter") final Filter filter,
            @PluginAttribute("otherAttribute") String otherAttribute) {
        if (name == null) {
            LOGGER.error("No name provided for gui.MyCustomAppenderImpl");
            return null;
        }
        if (layout == null) {
            layout = PatternLayout.createDefaultLayout();
        }
        return new MyCustomAppenderImpl(name, filter, layout, true);
    }
}