package cc.sharper.sword.config.spring;

/**
 * Created by lizhitao on 16-1-11.
 */
public class ServiceBean {
    private String id;
    private String interfaceName;
    private String ref;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }
}
