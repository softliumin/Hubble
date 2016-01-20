package cc.sharper.sword.config.spring;

import cc.sharper.sword.config.ProviderConfig;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.support.AbstractApplicationContext;

import java.io.Serializable;
import java.lang.reflect.Method;

/**
 * Created by lizhitao on 16-1-13.
 */
public class ProviderBean<T> extends ProviderConfig<T> implements InitializingBean, ApplicationContextAware, DisposableBean, ApplicationListener, BeanNameAware, Serializable {
    private transient ApplicationContext applicationContext;
    private transient boolean supportedApplicationListener;
    private transient String beanName;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
        try {
            Method t = applicationContext.getClass().getMethod("addApplicationListener", new Class[]{ApplicationListener.class});
            t.invoke(applicationContext, new Object[]{this});
            this.supportedApplicationListener = true;
        } catch (Throwable var5) {
            if (applicationContext instanceof AbstractApplicationContext) {
                try {
                    Method method = AbstractApplicationContext.class.getDeclaredMethod("addListener", new Class[]{ApplicationListener.class});
                    if (!method.isAccessible()) {
                        method.setAccessible(true);
                    }

                    method.invoke(applicationContext, new Object[]{this});
                    this.supportedApplicationListener = true;
                } catch (Throwable var4) {
                    ;
                }
            }
        }
    }

    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        if (applicationEvent instanceof ContextRefreshedEvent){

        }
    }

    @Override
    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    @Override
    public void destroy() throws Exception {

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (null != this.applicationContext) {

        }
    }
}
