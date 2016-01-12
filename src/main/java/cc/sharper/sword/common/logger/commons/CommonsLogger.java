package cc.sharper.sword.common.logger.commons;

import cc.sharper.sword.common.logger.Logger;
import org.apache.commons.logging.Log;

import java.io.Serializable;

/**
 * Created by lizhitao on 16-1-11.
 * apache commons logging
 * 适配CommonsLogging，依赖于commons-logging.jar
 */
public class CommonsLogger implements Logger, Serializable {
    private static final long serialVersionUID = 1L;

    private final Log log;

    public CommonsLogger(Log log) {
        this.log = log;
    }

    public void trace(String msg) {
        log.trace(msg);
    }

    public void trace(Throwable e) {
        log.trace(e);
    }

    public void trace(String msg, Throwable e) {
        log.trace(msg, e);
    }

    public void debug(String msg) {
        log.debug(msg);
    }

    public void debug(Throwable e) {
        log.debug(e);
    }

    public void debug(String msg, Throwable e) {
        log.debug(msg, e);
    }

    public void info(String msg) {
        log.info(msg);
    }

    public void info(Throwable e) {
        log.info(e);
    }

    public void info(String msg, Throwable e) {
        log.info(msg, e);
    }

    public void warn(String msg) {
        log.warn(msg);
    }

    public void warn(Throwable e) {
        log.warn(e);
    }

    public void warn(String msg, Throwable e) {
        log.warn(msg, e);
    }

    public void error(String msg) {
        log.error(msg);
    }

    public void error(Throwable e) {
        log.error(e);
    }

    public void error(String msg, Throwable e) {
        log.error(msg, e);
    }

    public boolean isTraceEnabled() {
        return log.isTraceEnabled();
    }

    public boolean isDebugEnabled() {
        return log.isDebugEnabled();
    }

    public boolean isInfoEnabled() {
        return log.isInfoEnabled();
    }

    public boolean isWarnEnabled() {
        return log.isWarnEnabled();
    }

    public boolean isErrorEnabled() {
        return log.isErrorEnabled();
    }
}
