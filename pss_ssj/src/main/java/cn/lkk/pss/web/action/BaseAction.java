package cn.lkk.pss.web.action;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import cn.lkk.pss.domain.Employee;

public abstract class BaseAction extends ActionSupport {
	// 重定向视图
	public static final String RELOAD = "reload";
	public static final String LOGINUSER = "LOGINUSER_IN_SESSION";

	// 将数据放到值栈的Map中的方法
	public void putContext(String key, Object value) {
		ActionContext.getContext().put(key, value);
	}

	public Employee getLoginUserInSession() {
		return (Employee) ActionContext.getContext().getSession().get(LOGINUSER);
	}

}
