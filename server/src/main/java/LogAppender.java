import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.layout.PatternLayout;

public class LogAppender extends AbstractAppender {

    public LogAppender(){
        super("MyAppender", null, PatternLayout.createDefaultLayout());
    }
    @Override
    public void append(LogEvent event) {
        System.out.println("MyMessenger:  " + event.getMessage());
    }
}