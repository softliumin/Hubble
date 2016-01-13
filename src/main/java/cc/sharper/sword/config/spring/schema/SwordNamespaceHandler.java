package cc.sharper.sword.config.spring.schema;

import cc.sharper.sword.config.ProviderConfig;
import cc.sharper.sword.config.spring.ProviderBean;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * Created by lizhitao on 16-1-11.
 */
public class SwordNamespaceHandler extends NamespaceHandlerSupport {
    public void init() {
        this.registerBeanDefinitionParser("provider", new SwordBeanDefinitionParser(ProviderBean.class, true));
    }
}
