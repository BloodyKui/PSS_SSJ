package cn.lkk.pss.web.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;

import cn.lkk.pss.domain.Employee;
import cn.lkk.pss.page.PageList;
import cn.lkk.pss.query.EmployeeQuery;
import cn.lkk.pss.service.IEmployeeService;

@Controller
@Scope("prototype")
public class EmployeeAction_bak extends BaseAction implements ModelDriven<Employee>, Preparable {

	@Autowired
	private IEmployeeService employeeService;
	/*
	 * 哪些数据放List栈，哪些数据放map栈 我们说的是外面的基本要求：
	 * 当前Action中的数据都放在list栈(即XxxAction，那么所有的跟Xxx有关的数据都放入List栈中) 其他的数据放入map栈中
	 */
	// 通过get方法传到前端页面
	// private List<Employee> employees;

	/*
	 * 创建PageList对象用于将数据返回到页面
	 */
	private PageList<Employee> pageList = new PageList<>();

	/*
	 * 查询条件对象，用于封装从前台传过来的查询条件
	 */
	private EmployeeQuery baseQuery = new EmployeeQuery();
	// 实体类
	private Employee employee;

	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String execute() throws Exception {
		// 将数据通过map栈传到前端页面
		// putContext("employees", employeeService.getAll());
		// employees = employeeService.getAll();
		pageList = employeeService.findPageByQuery(baseQuery);
		return SUCCESS;
	}

	//////////////////// 进行增删改操作//////////////////////////////
	// 跳转到新增或者修改页面
	@Override
	public String input() throws Exception {
		return INPUT;
	}

	// 保存或修改方法
	public String save() throws Exception {
		// save方法中集成了保存和修改
		employeeService.save(employee);
		return RELOAD;
	}

	// 删除方法
	public String delete() throws Exception {
		employeeService.delete(id);
		return RELOAD;
	}

	/***** Action中接收参数的getter与setter ************/

	// 因为已经new了，所以只用提供get方法
	public PageList<Employee> getPageList() {

		return pageList;
	}

	public EmployeeQuery getBaseQuery() {

		return baseQuery;
	}

	public Employee getEmployee() {

		return employee;
	}

	// 必须覆写 在执行方法前会执行
	@Override
	public void prepare() throws Exception {

	}
	//在执行input方法前会执行
	public void prepareInput() throws Exception {
		if (id != null) {
			// 这里的get方式调用的是getOne 但是这是得到的是代理对象，这样会造成懒加载问题
			// 所以持久层要使用findOne方法
			// employee = employeeService.get(employee.getId());
			employee = employeeService.get(id);
		}
	}
	//在执行save方法前会执行
	public void prepareSave() throws Exception {
		if (id != null) {
			employee = employeeService.get(id);
		} else {
			employee = new Employee();
		}
	}

	//
	@Override
	public Employee getModel() {
		return employee;
	}

}
