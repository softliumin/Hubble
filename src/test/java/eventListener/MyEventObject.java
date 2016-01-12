package eventListener;

import java.util.EventObject;

/**
 * Created by lizhitao on 16-1-7.
 */
public class MyEventObject extends EventObject{
    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public MyEventObject(Object source) {
        super(source);
    }
}
