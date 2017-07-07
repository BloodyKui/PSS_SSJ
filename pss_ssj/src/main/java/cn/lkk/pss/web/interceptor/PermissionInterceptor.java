package cn.lkk.pss.web.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

import cn.lkk.pss.domain.Employee;
import cn.lkk.pss.service.IForbidIPService;
import cn.lkk.pss.service.IPermissionService;
import cn.lkk.pss.web.action.BaseAction;

/**
 * 权限拦截器
 * 
 * @author Quid_Lee 2017年6月29日
 */
public class PermissionInterceptor extends AbstractInterceptor {
	// 拿到struts.xml中配置的参数
	private String excludeActions;

	// spring可以在拦截器中注入bean
	@Autowired
	private IPermissionService permissionService;

	@Autowired
	private IForbidIPService forbidIPService;

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		//拿到当前访问的action名称
		String actionName = invocation.getAction().getClass().getSimpleName();
		
		// 判断当前访问的ip是否被禁止,同时放行验证码
		if (!"RandomCodeAction".equals(actionName)&&isFobbidenIP()) {
			return "forbiden";
		}
		// 如果不排除掉登录页面，则登录页面永远会被拦截，造成登录不成功。
		// 两种解决方式，方式一：在struts中对loginAction使用默认拦截器栈设置
		// 方式二： 在struts.xml配置好参数，这边加载，只要是未指定action的，就放弃拦截(推荐)
		if (excludeActions.contains(actionName)) {
			// 如果配置了放行的action，则直接放行
			return invocation.invoke();
		}
		// 拿到session中的值，判断是否为空
		Employee employee = (Employee) ActionContext.getContext().getSession().get(BaseAction.LOGINUSER);
		// 如果为空则返回登录页面
		if (employee == null) {
			return Action.LOGIN;
		}

		// 验证登录权限过后再进行访问权限
		// 拿到当前action要访问的方法
		String method = invocation.getProxy().getMethod();
		// 加载系统中所有的权限
		List<String> allSystemPermission = permissionService.getAllSystemPermission();
		// 拼接成方法级别的权限
		String methodPermission = actionName + "." + method;
		// 拼接成action级别的权限
		String actionPermission = actionName + ".ALL";
		// 判断当前访问的action或者方法是否是需要权限验证的，如果不需要则直接放行
		if (allSystemPermission.contains(methodPermission) || allSystemPermission.contains(actionPermission)) {
			// 如果是需要权限验证的，则进行当前用户是否有该权限的判断,先拿到当前登录用户的所有权限
			List<String> allLoginUserPermission = permissionService.getAllLoginUserPermission(employee);
			// 判断该用户是否有访问权限，如果有则放行，如果没有则跳转到没有权限的提示页面
			if (allLoginUserPermission.contains(methodPermission)
					|| allLoginUserPermission.contains(actionPermission)) {
				return invocation.invoke();
			} else {
				return "noAuthority";
			}
		}
		// 放行
		return invocation.invoke();
	}

	// 判断当前访问的ip是否是被禁止访问的ip
	private Boolean isFobbidenIP() {
		List<String> paths = forbidIPService.findIPEqul5();
		String ipAddr = getIpAddr(ServletActionContext.getRequest());
		// 如果包含了当前ip则返回true
		if (paths.contains(ipAddr)) {
			return true;
		}
		return false;
	}

	// 获得客户端真实IP地址
	private String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	// struts会自动set注入配置的参数
	public void setExcludeActions(String excludeActions) {
		this.excludeActions = excludeActions;
	}

}
