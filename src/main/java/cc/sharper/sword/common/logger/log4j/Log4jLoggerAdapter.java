package cc.sharper.sword.common.logger.log4j;

import cc.sharper.sword.common.logger.Level;
import cc.sharper.sword.common.logger.Logger;
import cc.sharper.sword.common.logger.LoggerAdapter;
import org.apache.log4j.Appender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.LogManager;

import java.io.File;
import java.util.Enumeration;

/**
 * Created by lizhitao on 16-1-11.
 */
public class Log4jLoggerAdapter implements LoggerAdapter {
    private File file;

    public Log4jLoggerAdapter() {
        try {
            org.apache.log4j.Logger logger = LogManager.getRootLogger();
            if (null != logger) {
                Enumeration<Appender> appenders = logger.getAllAppenders();
                if (null != appenders) {
                    while (appenders.hasMoreElements()) {
                        Appender appender = appenders.nextElement();
                        if (appender instanceof FileAppender) {
                            FileAppender fileAppender = (FileAppender) appender;
                            String filename = fileAppender.getFile();
                            file = new File(filename);
                            break;
                        }
                    }
                }
            }
        } catch (Exception e) {
        }
    }


    public Logger getLogger(Class<?> key) {
        return new Log4jLogger(LogManager.getLogger(key));
    }

    public Logger getLogger(String key) {
        return new Log4jLogger(LogManager.getLogger(key));
    }

    public void setLevel(Level level) {
        LogManager.getRootLogger().setLevel(toLog4jLevel(level));
    }

    public Level getLevel() {
        return fromLog4jLevel(LogManager.getRootLogger().getLevel());
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {

    }

    private static org.apache.log4j.Level toLog4jLevel(Level level) {
        if (level == Level.ALL)
            return org.apache.log4j.Level.ALL;
        if (level == Level.TRACE)
            return org.apache.log4j.Level.TRACE;
        if (level == Level.DEBUG)
            return org.apache.log4j.Level.DEBUG;
        if (level == Level.INFO)
            return org.apache.log4j.Level.INFO;
        if (level == Level.WARN)
            return org.apache.log4j.Level.WARN;
        if (level == Level.ERROR)
            return org.apache.log4j.Level.ERROR;
        return org.apache.log4j.Level.OFF;
    }

    private static Level fromLog4jLevel(org.apache.log4j.Level level) {
        if (level == org.apache.log4j.Level.ALL)
            return Level.ALL;
        if (level == org.apache.log4j.Level.TRACE)
            return Level.TRACE;
        if (level == org.apache.log4j.Level.DEBUG)
            return Level.DEBUG;
        if (level == org.apache.log4j.Level.INFO)
            return Level.INFO;
        if (level == org.apache.log4j.Level.WARN)
            return Level.WARN;
        if (level == org.apache.log4j.Level.ERROR)
            return Level.ERROR;
        return Level.OFF;
    }
}
