package thinking.cloud.core.proxy.service;

import org.aspectj.lang.JoinPoint;

import io.swagger.models.Model;
import thinking.cloud.core.proxy.ProxyHandler;

/**
 * Service异常通知的接口
 * <P>
 * Service异常通知的接口
 * </P>
 * @author zhouxinke
 * @date 2020年12月1日
 */
public interface ServiceAfterThrowing extends ProxyHandler{
	public void handler(Model model);
}
