package cn.lkk.pss.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

/**
 * 这里主要是添加一些相对应的具体的业务方法，进行业务处理，而常规的CRUD则抽取到父类中进行实现
 */
import cn.lkk.pss.domain.Employee;
import cn.lkk.pss.service.IEmployeeService;
@Service
public class EmployeeServiceImpl extends BaseServiceImpl<Employee, Long> implements IEmployeeService {

	@Override
	public Boolean checkName(String username) {
		String jpql = "SELECT count(o) FROM Employee o WHERE o.username = ?";
		List<Long> findByJpql = super.findByJpql(jpql, username);
		
		return !(findByJpql.get(0)>0);
	}

	@Override
	public Employee login(String username) {
		String jpql = "SELECT o FROM Employee o WHERE o.username = ?";
		Employee employee = null;
		//根据用户名查找到相应的用户对象
		List<Employee> emList = super.findByJpql(jpql, username);
		//如果集合中有值，则表示有该用户
		if (emList.size()>0) {
			employee = emList.get(0);
		}
		
		return employee;
	}

	@Override
	public List<Employee> findByDeptName(String deptName) {
		String jpql = "SELECT o FROM Employee o WHERE o.department.name = ?";
		List<Employee> employeesInDept = super.findByJpql(jpql, deptName);
		return employeesInDept;
	}

}
