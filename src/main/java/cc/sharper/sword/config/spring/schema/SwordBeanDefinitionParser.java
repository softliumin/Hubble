package cc.sharper.sword.config.spring.schema;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * Created by lizhitao on 16-1-12.
 */
public class SwordBeanDefinitionParser implements BeanDefinitionParser {

    private final Class<?> beanClass;
    private final boolean required;

    public SwordBeanDefinitionParser(Class<?> beanClass, boolean required) {
        this.beanClass = beanClass;
        this.required = required;
    }

    public BeanDefinition parse(Element element, ParserContext parserContext) {
        RootBeanDefinition beanDefinition = new RootBeanDefinition();
        beanDefinition.setBeanClass(beanClass);
        beanDefinition.setLazyInit(false);// 设置为非懒加载bean
        String id = element.getAttribute("id");
        if (!StringUtils.hasText(id) && required) {
            throw new IllegalArgumentException("Bean attribute id can not be null or empty");
        } else {
            if (required) {
                if (parserContext.getRegistry().containsBeanDefinition(id)) {
                    throw new IllegalStateException("Duplicate spring bean id " + id);
                }

                parserContext.getRegistry().registerBeanDefinition(id, beanDefinition);
            }

            Method[] methods = beanClass.getMethods();

            for (Method method : methods) {
                Method setter = method;
                if (isProperty(setter, beanClass)) {
                    String name = setter.getName();
                    String property = name.substring(3, 4).toLowerCase() + name.substring(4);
                    String value = element.getAttribute(property);

                    RuntimeBeanReference reference;
                    if (property.equals("id")) {
                        beanDefinition.getPropertyValues().addPropertyValue("id", value);
                    } else if (property.equals("interfaceName")) {
                        beanDefinition.getPropertyValues().addPropertyValue("interfaceName", element.getAttribute("interface"));
                    } else if (property.equals("ref")) {
                        if (StringUtils.hasText(value)) {
                            BeanDefinition definition = parserContext.getRegistry().getBeanDefinition(value);
                            if (!definition.isSingleton()) {
                                throw new IllegalStateException("The exported provider ref " + value + " must be singleton! Please set the " + value + " bean scope to singleton, eg: <bean id=\"" + value + "\" scope=\"singleton\" ...>");
                            }

                            reference = new RuntimeBeanReference(value);
                        } else {
                            reference = null;
                        }
                        beanDefinition.getPropertyValues().addPropertyValue("ref", reference);
                    }
                }
            }
            return beanDefinition;
        }
    }

    private boolean isProperty(Method method, Class<?> beanClass) {
        String methodName = method.getName();
        // 是否为javabean规范的setter方法
        boolean flag = methodName.length() > 3 && methodName.startsWith("set") && Modifier.isPublic(method.getModifiers()) && method.getParameterTypes().length == 1;
        Method getter = null;
        if (flag) {
            Class<?> type = method.getParameterTypes()[0];
            try {
                getter = beanClass.getMethod("get" + methodName.substring(3), new Class[0]);
            } catch (NoSuchMethodException var10) {
                try {
                    getter = beanClass.getMethod("is" + methodName.substring(3), new Class[0]);
                } catch (NoSuchMethodException var9) {
                    ;
                }
            }

            flag = getter != null && Modifier.isPublic(getter.getModifiers()) && type.equals(getter.getReturnType());
        }

        return flag;
    }
}
