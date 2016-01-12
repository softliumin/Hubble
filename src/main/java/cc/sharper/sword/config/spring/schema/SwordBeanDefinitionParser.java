package cc.sharper.sword.config.spring.schema;

import cc.sharper.sword.config.spring.ServiceBean;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.w3c.dom.Element;

/**
 * Created by lizhitao on 16-1-11.
 */
public class SwordBeanDefinitionParser extends AbstractSingleBeanDefinitionParser {

    @Override
    protected Class<?> getBeanClass(Element element) {
        return ServiceBean.class;
    }

    @Override
    protected void doParse(Element element, BeanDefinitionBuilder bean) {
        String id = element.getAttribute("id");
        String interfaceName = element.getAttribute("interface");
        String ref = element.getAttribute("ref");
        bean.addPropertyValue("id", id).addPropertyValue("interfaceName", interfaceName).addPropertyValue("ref", ref);
    }
}
