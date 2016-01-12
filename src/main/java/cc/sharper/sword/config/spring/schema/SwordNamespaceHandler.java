package cc.sharper.sword.config.spring.schema;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * Created by lizhitao on 16-1-11.
 */
public class SwordNamespaceHandler extends NamespaceHandlerSupport {
    public void init() {
        this.registerBeanDefinitionParser("provider", new SwordBeanDefinitionParser());
    }
}