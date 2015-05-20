

package common.ecp.utils;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;

/**
 * 
* @Title: SpringBeanUtil.java 
* @Package common.ecp.utils 
* @Description: Spring容器的Bean获取工具类
* @author 陈定凯 
* @date 2015年5月13日 下午5:11:38 
* @version V1.0
 */
public class SpringBeanUtil {

    /**
     * 自动装配注解会让Spring通过类型匹配为beanFactory注入示例
     */
    private BeanFactory beanFactory;

    private SpringBeanUtil() {
    }
    private static Object lock = new Object();
    private static SpringBeanUtil instance;

//    static {
//        // 静态块，初始化实例
//        instance = new SpringBeanUtil();
//    }

    /**
     * 实例方法
     * 使用的时候先通过getInstance方法获取实例
     * 
     * @param beanId
     * @return
     */
    public Object getBeanById(String beanId) {
        return getBeanFactory().getBean(beanId);
    }

    public static SpringBeanUtil getInstance() {
    	if(instance==null){
    		synchronized (lock) {
    			initWithFile();
			}
    	}
        return instance;
    }
    
    /**
     * 根据文件实现读取spring配置
     */
    private static void initWithFile() {
		throw new RuntimeException("未实现");
	}

	/**
	 * 注入ApplicationContext
	 * @param ac
	 */
	public static void init(ApplicationContext ac){
    	if(instance==null){
    		instance = new SpringBeanUtil();
    		instance.beanFactory = ac;
    	}
    }

    /**
     * @return the beanFactory
     */
    public BeanFactory getBeanFactory() {
        return beanFactory;
    }

    /**
     * @param beanFactory the beanFactory to set
     */
    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }
    
    /**
     * 获取bean
     * @param c
     * @return
     */
    public <T> T getBean(Class<T> c){
    	return this.beanFactory.getBean(c);
    }
    /**
     * 获取bean
     * @param c
     * @return
     */
    public Object getBean(String name){
    	return this.beanFactory.getBean(name);
    }
}
