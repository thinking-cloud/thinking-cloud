package thinking.cloud.core.proxy.service;

import org.aspectj.lang.JoinPoint;

import io.swagger.models.Model;
import thinking.cloud.core.proxy.ProxyHandler;

/**
 * service后置通知的接口
 * <P>
 * service后置通知的接口
 * </P>
 * @author zhouxinke
 * @date 2020年12月1日
 */

public interface ServiceAfterReturning extends ProxyHandler{
	
	public void handler(Model model);
		
}
