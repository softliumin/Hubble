package cc.sharper.sword.common.logger.jdk;

import cc.sharper.sword.common.logger.Level;
import cc.sharper.sword.common.logger.Logger;
import cc.sharper.sword.common.logger.LoggerAdapter;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.LogManager;

/**
 * Created by lizhitao on 16-1-11.
 * jdk日志适配器
 */
public class JdkLoggerAdapter implements LoggerAdapter {
    private static final String GLOBAL_LOGGER_NAME = "global";
    private File file;

    public JdkLoggerAdapter() {
        try {
            InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("logging.properties");
            if (null != in)
                LogManager.getLogManager().readConfiguration(in);
            else
                System.err.println("No such file logging.properties in classpath for jdk logging config!");
        } catch (IOException e) {
            System.err.println("Failed to load logging.properties in classpath for jdk logging config, cause: " + e.getMessage());
        }

        try {
            Handler[] handlers = java.util.logging.Logger.getLogger(GLOBAL_LOGGER_NAME).getHandlers();
            for (Handler handler : handlers) {
                if (handler instanceof FileHandler) {
                    FileHandler fileHandler = (FileHandler) handler;
                    Field field = fileHandler.getClass().getField("files");
                    File[] files = (File[]) field.get(fileHandler);
                    if (null != files && files.length > 0)
                        file = files[0];
                }
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public Logger getLogger(Class<?> key) {
        return new JdkLogger(java.util.logging.Logger.getLogger(key == null ? "" : key.getName()));
    }

    public Logger getLogger(String key) {
        return new JdkLogger(java.util.logging.Logger.getLogger(key));
    }

    public void setLevel(Level level) {
        java.util.logging.Logger.getLogger(GLOBAL_LOGGER_NAME).setLevel(toJdkLevel(level));
    }

    public Level getLevel() {
        return fromJdkLevel(java.util.logging.Logger.getLogger(GLOBAL_LOGGER_NAME).getLevel());
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {

    }

    /**
     * 将Level转换为jdk logging的level
     * @param level
     * @return
     */
    private static java.util.logging.Level toJdkLevel(Level level) {
        switch (level) {
            case ALL:
                return java.util.logging.Level.ALL;
            case TRACE:
                return java.util.logging.Level.FINER;
            case DEBUG:
                return java.util.logging.Level.FINE;
            case ERROR:
                return java.util.logging.Level.SEVERE;
            case INFO:
                return java.util.logging.Level.INFO;
            case WARN:
                return java.util.logging.Level.WARNING;
            default:
                return java.util.logging.Level.OFF;
        }
    }

    private static Level fromJdkLevel(java.util.logging.Level level) {
        if (level == java.util.logging.Level.ALL)
            return Level.ALL;
        if (level == java.util.logging.Level.FINER)
            return Level.TRACE;
        if (level == java.util.logging.Level.FINE)
            return Level.DEBUG;
        if (level == java.util.logging.Level.SEVERE)
            return Level.ERROR;
        if (level == java.util.logging.Level.INFO)
            return Level.INFO;
        if (level == java.util.logging.Level.WARNING)
            return Level.WARN;
        return Level.OFF;
    }
}
