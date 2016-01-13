package cc.sharper.sword.config;

/**
 * Created by lizhitao on 16-1-13.
 */
public class ProviderConfig<T> {
    private String id;
    private String interfaceName;
    private T ref;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public T getRef() {
        return ref;
    }

    public void setRef(T ref) {
        this.ref = ref;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }
}
