package cn.lkk.pss.web.action;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;

import cn.lkk.pss.domain.Permission;
import cn.lkk.pss.domain.Role;
import cn.lkk.pss.page.PageList;
import cn.lkk.pss.query.RoleQuery;
import cn.lkk.pss.service.IPermissionService;
import cn.lkk.pss.service.IRoleService;

@Controller
@Scope("prototype")
public class RoleAction extends CRUDAction<Role> {
	@Autowired
	private IRoleService roleService;
	
	@Autowired
	private IPermissionService permissionService;
	/*
	 * 哪些数据放List栈，哪些数据放map栈 我们说的是外面的基本要求：
	 * 当前Action中的数据都放在list栈(即XxxAction，那么所有的跟Xxx有关的数据都放入List栈中) 其他的数据放入map栈中
	 */
	// 通过get方法传到前端页面
	// private List<Role> roles;

	/*
	 * 创建PageList对象用于将数据返回到页面
	 */
	private PageList<Role> pageList = new PageList<>();

	/*
	 * 查询条件对象，用于封装从前台传过来的查询条件
	 */
	private RoleQuery baseQuery = new RoleQuery();
	// 实体类 因为CRUDAction父类实现了ModelDriven方法，所以不用提供get方法，通过getModel拿值
	// 同时在拦截器ModelDriven中如果对象不为空，则将值压入值栈的栈顶。此时在页面上就可以直接使用对象中的字段进行取值了
	private Role role;

	@Override
	public void list() {
		// 将数据通过map栈传到前端页面
		// putContext("roles", roleService.getAll());
		// roles = roleService.getAll();
		pageList = roleService.findPageByQuery(baseQuery);

	}

	//////////////////// 进行增删改操作//////////////////////////////
	// 跳转到新增或者修改页面
	@Override
	public String input() throws Exception {
		//拿到所有权限
		List<Permission> allPermissions = permissionService.getAll();
		putContext("allPermissions", allPermissions);
		return INPUT;
	}

	// 保存或修改方法
	@Override
	@InputConfig(methodName = INPUT)
	public String save() throws Exception {
		// save方法中集成了保存和修改
		roleService.save(role);
		if (id == null) {
			baseQuery.setCurrentPage(Integer.MAX_VALUE);
		}
		// 保存后就可以获得这个id
		id = role.getId();
		//因为重定向会丢失数据，所以id会保存在值栈中，同时也会保存在map栈中
		return RELOAD;
	}

	// 删除方法
	@Override
	public String delete() throws Exception {
		HttpServletResponse response = ServletActionContext.getResponse();
		//一定要设置返回的格式，否则前台拿到的就是字符串，不是json对象
		response.setContentType("text/json;charset=UTF-8");
		try {
			roleService.delete(super.id);
			//如果删除成功，则向前端返回一个true的信号
			response.getWriter().print("{\"ok\":true}");
		} catch (Exception e) {
			//如果删除出现异常，则进行捕获，并将错误信息打印到前端页面
			response.getWriter().print("{\"ok\":false,\"msg\": \"删除失败！"+e.getMessage()+"\"}");
		}
		//使用ajax后就不用在返回结果视图了
		return NONE;
	}

	// 在执行input方法前会执行
	@Override
	public void prepareInput() throws Exception {
		if (id != null) {
			// 这里的get方式调用的是getOne 但是这是得到的是代理对象，这样会造成懒加载问题
			// 所以持久层要使用findOne方法
			// role = roleService.get(role.getId());
			role = roleService.get(id);
		}
	}

	// 在执行save方法前会执行
	@Override
	public void prepareSave() throws Exception {
		if (id != null) {
			role = roleService.get(id);
			//当编辑时需要修改所属权限的时候，因为有二次参数拦截的缘故，所以权限只能增加不能减少
			//这个时候需要先将拿到的role对象中的permissions置空(有关系的对象都直接干掉)
			role.getPermissions().clear();
			//不推断这种写法，因为要保证permissions集合不能为空，否则可能出现空指针异常
			//role.setPermissions(null);
		} else {
			role = new Role();
		}
	}
	
	@Override
	public Role getModel() {
		return role;
	}


	/***** Action中接收参数的getter与setter ************/

	// 因为已经new了，所以只用提供get方法
	public PageList<Role> getPageList() {

		return pageList;
	}

	public RoleQuery getBaseQuery() {

		return baseQuery;
	}

}
