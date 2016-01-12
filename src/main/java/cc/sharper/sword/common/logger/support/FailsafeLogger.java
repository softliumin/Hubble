package cc.sharper.sword.common.logger.support;

import cc.sharper.sword.common.logger.Logger;
import cc.sharper.sword.common.util.NetUtils;
import cc.sharper.sword.common.util.Version;

/**
 * Created by lizhitao on 16-1-11.
 */
public class FailsafeLogger implements Logger {
    private Logger logger;

    public FailsafeLogger(Logger logger) {
        this.logger = logger;
    }

    public Logger getLogger() {
        return logger;
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    private String appendContextMessage(String msg) {
        return " [SWORD] " + msg + ", sword version: " + Version.getVersion() + ", current host: " + NetUtils.getLogHost();
    }

    public void trace(String msg) {
        try {
            logger.trace(msg);
        } catch (Exception t) {
        }
    }

    public void trace(Throwable e) {
        try {
            logger.trace(e);
        } catch (Exception t) {
        }
    }

    public void trace(String msg, Throwable e) {
        try {
            logger.trace(appendContextMessage(msg), e);
        } catch (Exception t) {
        }
    }

    public void debug(String msg) {
        try {
            logger.debug(msg);
        } catch (Exception t) {
        }
    }

    public void debug(Throwable e) {
        try {
            logger.debug(e);
        } catch (Exception t) {
        }
    }

    public void debug(String msg, Throwable e) {
        try {
            logger.debug(appendContextMessage(msg), e);
        } catch (Exception t) {
        }
    }

    public void info(String msg) {
        try {
            logger.info(msg);
        } catch (Exception t) {
        }
    }

    public void info(Throwable e) {
        try {
            logger.info(e);
        } catch (Exception t) {
        }
    }

    public void info(String msg, Throwable e) {
        try {
            logger.info(appendContextMessage(msg), e);
        } catch (Exception t) {
        }
    }

    public void warn(String msg) {
        try {
            logger.warn(msg);
        } catch (Exception t) {
        }
    }

    public void warn(Throwable e) {
        try {
            logger.warn(e);
        } catch (Exception t) {
        }
    }

    public void warn(String msg, Throwable e) {
        try {
            logger.warn(appendContextMessage(msg), e);
        } catch (Exception t) {
        }
    }

    public void error(String msg) {
        try {
            logger.error(msg);
        } catch (Exception t) {
        }
    }

    public void error(Throwable e) {
        try {
            logger.error(e);
        } catch (Exception t) {
        }
    }

    public void error(String msg, Throwable e) {
        try {
            logger.error(appendContextMessage(msg), e);
        } catch (Exception t) {
        }
    }

    public boolean isTraceEnabled() {
        try {
            return logger.isTraceEnabled();
        } catch (Exception t) {
            return false;
        }
    }

    public boolean isDebugEnabled() {
        try {
            return logger.isDebugEnabled();
        } catch (Exception t) {
            return false;
        }
    }

    public boolean isInfoEnabled() {
        try {
            return logger.isInfoEnabled();
        } catch (Exception t) {
            return false;
        }
    }

    public boolean isWarnEnabled() {
        try {
            return logger.isWarnEnabled();
        } catch (Exception t) {
            return false;
        }
    }

    public boolean isErrorEnabled() {
        try {
            return logger.isErrorEnabled();
        } catch (Exception t) {
            return false;
        }
    }
}
