package cc.sharper.sword.common.logger.log4j;


import cc.sharper.sword.common.logger.Logger;
import cc.sharper.sword.common.logger.support.FailsafeLogger;
import org.apache.log4j.Level;

/**
 * Created by lizhitao on 16-1-11.
 */
public class Log4jLogger implements Logger {
    private final org.apache.log4j.Logger logger;
    private static final String FQCN = FailsafeLogger.class.getName();

    public Log4jLogger(org.apache.log4j.Logger logger) {
        this.logger = logger;
    }

    public void trace(String msg) {
        logger.log(FQCN, Level.TRACE, msg, null);
    }

    public void trace(Throwable e) {
        logger.log(FQCN, Level.TRACE, e.getMessage(), e);
    }

    public void trace(String msg, Throwable e) {
        logger.log(FQCN, Level.TRACE, msg, e);
    }

    public void debug(String msg) {
        logger.log(FQCN, Level.DEBUG, msg, null);
    }

    public void debug(Throwable e) {
        logger.log(FQCN, Level.DEBUG, e.getMessage(), e);
    }

    public void debug(String msg, Throwable e) {
        logger.log(FQCN, Level.DEBUG, msg, e);
    }

    public void info(String msg) {
        logger.log(FQCN, Level.INFO, msg, null);
    }

    public void info(Throwable e) {
        logger.log(FQCN, Level.INFO, e.getMessage(), e);
    }

    public void info(String msg, Throwable e) {
        logger.log(FQCN, Level.INFO, msg, e);
    }

    public void warn(String msg) {
        logger.log(FQCN, Level.WARN, msg, null);
    }

    public void warn(Throwable e) {
        logger.log(FQCN, Level.WARN, e.getMessage(), e);
    }

    public void warn(String msg, Throwable e) {
        logger.log(FQCN, Level.WARN, msg, e);
    }

    public void error(String msg) {
        logger.log(FQCN, Level.ERROR, msg, null);
    }

    public void error(Throwable e) {
        logger.log(FQCN, Level.ERROR, e.getMessage(), e);
    }

    public void error(String msg, Throwable e) {
        logger.log(FQCN, Level.ERROR, msg, e);
    }

    public boolean isTraceEnabled() {
        return logger.isTraceEnabled();
    }

    public boolean isDebugEnabled() {
        return logger.isDebugEnabled();
    }

    public boolean isInfoEnabled() {
        return logger.isInfoEnabled();
    }

    public boolean isWarnEnabled() {
        return logger.isEnabledFor(Level.WARN);
    }

    public boolean isErrorEnabled() {
        return logger.isEnabledFor(Level.ERROR);
    }
}
