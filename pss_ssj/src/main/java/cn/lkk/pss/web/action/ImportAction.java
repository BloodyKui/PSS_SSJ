package cn.lkk.pss.web.action;

import java.io.File;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.lkk.pss.domain.Department;
import cn.lkk.pss.domain.Employee;
import cn.lkk.pss.service.IDepartmentService;
import cn.lkk.pss.service.IEmployeeService;

@Controller
@Scope("prototype")
public class ImportAction extends BaseAction {
	@Autowired
	private IEmployeeService employeeService;
	@Autowired
	private IDepartmentService departmentService;

	private File upload;

	@Override
	public String execute() throws Exception {
		// 定义一个计数器，用来记录本次操作导入了多少条数据
		int count = 0;
		// 如果upload不为空，则说明有上传
		if (upload != null) {

			// 获取除开表头以外的数据集合
			List<String[]> importXlsx = employeeService.importXlsx(upload);
			System.out.println(importXlsx.size());
			// 解析这个数据集合，将里面的值封装到相对应的domain中
			for (int i = 0; i < importXlsx.size(); i++) {
				// 创建一个封装对象
				Employee employee = new Employee();
				// 拿到每一行的数据
				String[] datas = importXlsx.get(i);
				// 因为Excel文件中的字段顺序是约定好的，所以这里可以直接取值
				// 封装用户名，密码，邮箱，年龄
				// 防止用户名重复，使得数据库中的变化效果不明显
				employee.setUsername(datas[0] + UUID.randomUUID().toString());
				employee.setPassword(datas[1]);
				employee.setEmail(datas[2]);
				// 判断年龄是否为空，有可能不会输入年龄，所以要进行非空判断
				String age = datas[3] != null ? datas[3] : "";
				employee.setAge(Integer.parseInt(age));
				// 先根据部门名称查询这个部门
				Department department = departmentService.findIdByName(datas[4]);
				if (department != null) {
					employee.setDepartment(department);
				}
				// 将封装好的employee对象持久化
				employeeService.save(employee);
				count++;
			}
			// 将导入结果放入map栈中方便前端页面获取
			putContext("importResult", "成功导入" + count + "条数据");
		}
		return SUCCESS;
	}
	// since we are using <s:file name="upload" ... /> the File itself will be
	// obtained through getter/setter of <file-tag-name>
	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

}
