package cc.sharper.sword.common.logger;

import java.io.File;

/**
 * Created by lizhitao on 16-1-11.
 * 日志适配器
 */
public interface LoggerAdapter {
    /**
     * 获取日志输出器
     *
     * @param key 分类键
     * @return
     */
    Logger getLogger(Class<?> key);

    /**
     * 获取日志输出器
     *
     * @param key 分类键
     * @return
     */
    Logger getLogger(String key);

    /**
     * 设置输出等级
     *
     * @param level 输出等级
     */
    void setLevel(Level level);

    /**
     * 获取当前日志等级
     *
     * @return 当前日志等级
     */
    Level getLevel();

    /**
     * 获取当前日志文件
     *
     * @return 当前日志文件
     */
    File getFile();

    /**
     * 设置输出日志文件
     *
     * @param file 输出日志文件
     */
    void setFile(File file);
}
