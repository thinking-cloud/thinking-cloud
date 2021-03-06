package thinking.cloud.core.proxy.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
 * Controller的统一代理类
 * <P>
 * 	Controller的统一代理类 
 * </P>
 * @author zhouxinke
 * @date 2020年12月1日
 */
@Aspect
@Component
public class ControllerProxy {
	@Autowired(required = false)
	private List<ControllerBefore> beforeList;
	@Autowired(required = false)
	private List<ControllerAfter> afterList;
	@Autowired(required = false)
	private List<ControllerAfterReturning> afterRuturningList;
	@Autowired(required = false)
	private List<ControllerAfterThrowing> afterThrowList;
	
	public final static ThreadLocal<HttpServletRequest> httpRequest = new ThreadLocal<>();
	public final static ThreadLocal<HttpServletResponse> httpResponse = new ThreadLocal<>();

	@Pointcut("within(*..controller..*)")
	private void  proxy(){
		
	}
	
	@Before("proxy()")
	public void before(JoinPoint point) {
		if(beforeList != null && beforeList.size()>0) {
			if(point.getTarget() instanceof ProxyHandler) return ;
			for (ControllerBefore before : beforeList) {
				Model model = Model.builder().request(httpRequest.get()).response(httpResponse.get()).point(point).build();
				before.handler(model);
			}
		}
	}
	

	@AfterReturning(value = "proxy()", returning = "returnVal")
	public void afterRuturning(JoinPoint point, Object returnVal) {
		if(point.getTarget() instanceof ProxyHandler) return ;
		if(afterRuturningList != null && afterRuturningList.size()>0) {
			for (ControllerAfterReturning runtuning : afterRuturningList) {
				Model model = Model.builder().request(httpRequest.get()).response(httpResponse.get()).point(point).returnObj(returnVal).build();
				runtuning.handler(model);
			}
		}
	}
	@AfterThrowing(value="proxy()", throwing = "throwable")
	public void afterThrow(JoinPoint point,Throwable throwable) {
		if(point.getTarget() instanceof ProxyHandler) return ;
		if(afterThrowList != null && afterThrowList.size()>0) {
			for (ControllerAfterThrowing throwing : afterThrowList) {
				Model model = Model.builder().request(httpRequest.get()).response(httpResponse.get()).point(point).throwable(throwable).build();
				throwing.handler(model);
			}
		}
	}
	
	@After("proxy()")
	public void after(JoinPoint point) {
		if(afterList != null && afterList.size()>0) {
			if(point.getTarget() instanceof ProxyHandler) return ;
			for (ControllerAfter after : afterList) {
				Model model = Model.builder().request(httpRequest.get()).response(httpResponse.get()).point(point).build();
				after.handler(model);
			}
		}
	}

	@Override
	protected void finalize() throws Throwable {
		httpRequest.remove();
		httpRequest.remove();
	}	
}
