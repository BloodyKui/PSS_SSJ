package cn.lkk.pss.web.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.lkk.pss.domain.Employee;
import cn.lkk.pss.domain.Menu;
import cn.lkk.pss.service.IMenuService;

//用作页面跳转，能否访问到WEB-INF中的页面
@Controller
@Scope("prototype")
public class MainAction extends BaseAction {
	@Autowired
	IMenuService menuService;
	
	@Override
	public String execute() throws Exception {
		//根据当前登录用户拿到对应的菜单
		//这里先模拟一个
//		Employee employee = new Employee();
//		employee.setId(1L);
		//从session中拿到当前登录用户
		Employee employee = super.getLoginUserInSession();
		//从后台动态获取菜单，并返回到前台
		List<Menu> menus_parent = menuService.findByLoginUser(employee);
		putContext("menus_parent", menus_parent);
		return SUCCESS;
	}
	
	public String right() throws Exception {
		return "right";
	}

}
