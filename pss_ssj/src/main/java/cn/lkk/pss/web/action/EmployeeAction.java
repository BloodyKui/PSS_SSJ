package cn.lkk.pss.web.action;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;

import cn.lkk.pss.domain.Department;
import cn.lkk.pss.domain.Employee;
import cn.lkk.pss.domain.Role;
import cn.lkk.pss.page.PageList;
import cn.lkk.pss.query.EmployeeQuery;
import cn.lkk.pss.service.IDepartmentService;
import cn.lkk.pss.service.IEmployeeService;
import cn.lkk.pss.service.IRoleService;

@Controller
@Scope("prototype")
public class EmployeeAction extends CRUDAction<Employee> {
	@Autowired
	private IEmployeeService employeeService;
	// TODO department对象
	@Autowired
	private IDepartmentService departmentService;
	
	@Autowired
	private IRoleService roleService;
	
	//定义下载文件时的输入流
	private InputStream fileDownloadInputStream;
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
	// 实体类 因为CRUDAction父类实现了ModelDriven方法，所以不用提供get方法，通过getModel拿值
	// 同时在拦截器ModelDriven中如果对象不为空，则将值压入值栈的栈顶。此时在页面上就可以直接使用对象中的字段进行取值了
	private Employee employee;

	@Override
	public void list() {
		// 将数据通过map栈传到前端页面
		// putContext("employees", employeeService.getAll());
		// employees = employeeService.getAll();
		// -1代表选择所有部门，那么就将查询条件对象中的部门id情况，去掉对部门的筛选
		System.out.println(baseQuery);
		if (baseQuery.getDeptId() != null && baseQuery.getDeptId() == -1) {
			baseQuery.setDeptId(null);
		}
		pageList = employeeService.findPageByQuery(baseQuery);
		// 拿到部门信息，并放到map栈中
		List<Department> allDept = departmentService.getAll();
		putContext("allDept", allDept);

	}

	//////////////////// 进行增删改操作//////////////////////////////
	// 跳转到新增或者修改页面
	@Override
	public String input() throws Exception {
		List<Department> allDept = departmentService.getAll();
		putContext("allDept", allDept);
		
		// 获取所有的角色
		List<Role> allRoles = roleService.getAll();
		putContext("allRoles", allRoles);
		return INPUT;
	}

	// 保存或修改方法
	@Override
	@InputConfig(methodName = INPUT)
	public String save() throws Exception {
		if (employee!=null && employee.getDepartment().getId()==-1) {
			employee.setDepartment(null);
		}
		// save方法中集成了保存和修改
		employeeService.save(employee);
		if (id == null) {
			baseQuery.setCurrentPage(Integer.MAX_VALUE);
		}
		// 保存后就可以获得这个id
		id = employee.getId();
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
			employeeService.delete(super.id);
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
			// employee = employeeService.get(employee.getId());
			employee = employeeService.get(id);
		}
	}

	// 在执行save方法前会执行
	@Override
	public void prepareSave() throws Exception {
		// 在执行修改操作之前，将department对象置空，否则会出现持久化状态不能修改OID的异常
		if (id != null) {
			employee = employeeService.get(id);
			// 在页面回显时使用department.name,这样就会去后台通过employee.getDepartment()查询;
			// 这样就使department成为了持久状态
			// 把所有有关联的对象干掉
			employee.setDepartment(null);
			employee.getRoles().clear();

		} else {
			employee = new Employee();
		}
	}
	// 对所有方法验证，即在所有方法执行前会执行
	/*
	 * @Override public void validate() {
	 * 
	 * }
	 */
	// 只验证save方法
	/*
	 * public void validateSave() { if
	 * (StringUtils.isBlank(employee.getUsername())) {
	 * addFieldError("nameIsNull", "用户名不能为空"); } if (employee.getAge()==null) {
	 * addFieldError("ageIsNull", "年龄不能为空"); } }
	 */

	private String username;

	public String checkName() throws Exception {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/json;charset=UTF-8");
		System.out.println(id);
		try {

			if (id != null) {
				Employee employee = employeeService.get(id);
				// 如果这个id对应的用户名与前台传过来的相同，则直接返回true
				if (employee.getUsername().equals(username)) {
					response.getWriter().print("{\"valid\":true}");
				} else {
					// 如果不同则按正常的检查方式进行检查
					// 正常的检查方式
					Boolean checkName = employeeService.checkName(username);
					response.getWriter().print("{\"valid\":" + checkName + "}");
				}
			} else {
				// 正常的检查方式
				Boolean checkName = employeeService.checkName(username);
				response.getWriter().print("{\"valid\":" + checkName + "}");
			}
		} catch (Exception e) {
			response.getWriter().print("{\"valid\":false, \"message\":\"代码出问题了\"}");
		}

		return NONE;
	}
	//定义下载excel文件的方法
	public String downloadExcel() throws Exception {
		//为了只要高级查询不要分页，所以将pageSize置为最大
		baseQuery.setPageSize(Integer.MAX_VALUE);
		//拿到高级查询后的结果
		List<Employee> employees = employeeService.findPageByQuery(baseQuery).getResults();
		//定义表头数据名称
		String[] heads = new String[]{"姓名","邮件","年龄","部门"};
		
		//将拿到的高级查询后的结果格式转换为List<String[]>
		List<String[]> rows = new ArrayList<>();
		for (Employee employee2 : employees) {
			//设置每一行的数据长度跟表头一致
			String[] datas = new String[heads.length];
			datas[0] = employee2.getUsername();
			datas[1] = employee2.getEmail();
			//如果年龄为空，则设置空串
			datas[2] = employee2.getAge() != null ? employee2.getAge().toString() :"";
			//如果部门为空，则不获取部门名称
			datas[3] = employee2.getDepartment() != null ? employee2.getDepartment().getName() :"";
			//将每一行数据加入到数据集合中
			rows.add(datas);
		}
		fileDownloadInputStream = employeeService.download(heads, rows);
		return "download";
	}
	/*
	 * 因为struts.xml中可以从值栈拿值，所以提供一个get方法，
	 * 设置 <param name="contentDisposition">filename=${downloadFileName}</param>
	 */
	public String getDownloadFileName() throws Exception{
		//通过编解码来解决中文乱码问题
		return new String("员工管理.xlsx".getBytes("GBK"), "ISO-8859-1");
	}
	
	//
	@Override
	public Employee getModel() {
		return employee;
	}


	/***** Action中接收参数的getter与setter ************/

	// 因为已经new了，所以只用提供get方法
	public PageList<Employee> getPageList() {
		return pageList;
	}

	public EmployeeQuery getBaseQuery() {

		return baseQuery;
	}
	
	// 从前端页面拿到字段值，需要设置set方法
	public void setUsername(String username) {
		this.username = username;
	}

	//struts需要拿到这个流让用户下载，所以只用提供get方法
	public InputStream getFileDownloadInputStream() {
		return fileDownloadInputStream;
	}

}
