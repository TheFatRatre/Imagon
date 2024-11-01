package com.cyc.imagon.utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

@Slf4j
public final class SpringUtil implements ApplicationContextAware {
	private static ApplicationContext applicationContext = null;
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		// TODO Auto-generated method stub
		if (SpringUtil.applicationContext == null) {
			SpringUtil.applicationContext = applicationContext;
		}
	}
	
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}
	
	//通过
	public static Object getBean(String name) {
		return getApplicationContext().getBean(name);
	}
	
	//通过class获取Bean.
    public static <T> T getBean(Class<T> clazz){
           return getApplicationContext().getBean(clazz);
    }
}
