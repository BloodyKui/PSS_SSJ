package cn.lkk.pss.service;

import java.util.List;

import cn.lkk.pss.domain.Employee;

public interface IEmployeeService extends IBaseService<Employee, Long> {
	//前台验证用户名是否重复
	Boolean checkName(String username);
	//验证用户登录
	Employee login(String username);
	
	//根据部门名称查找用户
	List<Employee> findByDeptName(String deptName);
}
