package cc.sharper.sword.registry;

import cc.sharper.sword.common.URL;

/**
 * Created by lizhitao on 16-1-4.
 */
public interface Registry {
    void register(URL url);
    void unRegister(URL url);
}
