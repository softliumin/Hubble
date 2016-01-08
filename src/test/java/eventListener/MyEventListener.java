package eventListener;

import java.util.EventListener;

/**
 * Created by lizhitao on 16-1-7.
 */
public interface MyEventListener extends EventListener{
    void onMyEventListener(MyEventObject eventObject);
}
