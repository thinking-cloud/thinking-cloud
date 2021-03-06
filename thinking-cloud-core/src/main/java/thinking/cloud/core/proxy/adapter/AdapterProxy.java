package thinking.cloud.core.proxy.adapter;

import java.util.List;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import thinking.cloud.core.proxy.Model;
import thinking.cloud.core.proxy.ProxyHandler;

/**
 * Adapter的统一代理类
 * <P>
 * 	Adapter的统一代理类 
 * </P>
 * @author think
 * @date 2020年12月1日
 */
@Aspect
@Component
public class AdapterProxy {
	@Autowired(required = false)
	private List<AdapterBefore> beforeList;
	@Autowired(required = false)
	private List<AdapterAfter> afterList;
	@Autowired(required = false)
	private List<AdapterAfterReturning> afterRuturningList;
	@Autowired(required = false)
	private List<AdapterAfterThrowing> afterThrowList;
	
	@Pointcut("within(*..adapter..*)")
	private void  proxy(){
		
	}
	
	@Before("proxy()")
	public void before(JoinPoint point) {
		if(point.getTarget() instanceof ProxyHandler) return ;
		if(beforeList != null && beforeList.size()>0) {
			for (AdapterBefore before : beforeList) {
				Model model = Model.builder().point(point).build();
				before.handler(model);
			}
		}
	}
	

	@AfterReturning(value = "proxy()", returning = "returnVal")
	public void afterRuturning(JoinPoint point, Object returnVal) {
		if(point.getTarget() instanceof ProxyHandler) return ;
		if(afterRuturningList != null && afterRuturningList.size()>0) {
			for (AdapterAfterReturning runtuning : afterRuturningList) {
				Model model = Model.builder().point(point).returnObj(returnVal).build();
				runtuning.handler(model);
			}
		}
	}
	@AfterThrowing(value="proxy()", throwing = "throwable")
	public void afterThrow(JoinPoint point,Throwable throwable) {
		if(point.getTarget() instanceof ProxyHandler) return ;
		if(afterThrowList != null && afterThrowList.size()>0) {
			for (AdapterAfterThrowing throwing : afterThrowList) {
				Model model = Model.builder().point(point).throwable(throwable).build();
				throwing.handler(model);
			}
		}
	}
	
	@After("proxy()")
	public void after(JoinPoint point) {
		if(point.getTarget() instanceof ProxyHandler) return ;
		if(afterList != null && afterList.size()>0) {
			for (AdapterAfter after : afterList) {
				Model model = Model.builder().point(point).build();
				after.handler(model);
			}
		}
	}
}
