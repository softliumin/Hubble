package cc.sharper.sword.common.logger.commons;

import cc.sharper.sword.common.logger.Level;
import cc.sharper.sword.common.logger.Logger;
import cc.sharper.sword.common.logger.LoggerAdapter;
import org.apache.commons.logging.LogFactory;

import java.io.File;

/**
 * Created by lizhitao on 16-1-11.
 */
public class CommonsLoggerAdapter implements LoggerAdapter {
    private Level level;
    private File file;

    public Logger getLogger(Class<?> key) {
        return new CommonsLogger(LogFactory.getLog(key));
    }

    public Logger getLogger(String key) {
        return new CommonsLogger(LogFactory.getLog(key));
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public Level getLevel() {
        return this.level;
    }

    public File getFile() {
        return this.file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}
