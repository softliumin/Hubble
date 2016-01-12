package cc.sharper.sword.common.logger.slf4j;

import cc.sharper.sword.common.logger.Level;
import cc.sharper.sword.common.logger.Logger;
import cc.sharper.sword.common.logger.LoggerAdapter;

import java.io.File;

/**
 * slf4j日志适配器
 */
public class Slf4jLoggerAdapter implements LoggerAdapter {

    public Logger getLogger(String key) {
        return new Slf4jLogger(org.slf4j.LoggerFactory.getLogger(key));
    }

    public Logger getLogger(Class<?> key) {
        return new Slf4jLogger(org.slf4j.LoggerFactory.getLogger(key));
    }

    private Level level;

    private File file;

    public void setLevel(Level level) {
        this.level = level;
    }

    public Level getLevel() {
        return level;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

}
