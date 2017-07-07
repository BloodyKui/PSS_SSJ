package cn.lkk.pss.service;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import cn.lkk.pss.domain.Employee;
import cn.lkk.pss.query.EmployeeQuery;

public class EmployeeServiceTest extends BaseServiceTest{
	
	@Autowired
	IEmployeeService employeeService;
	
	@Test
	public void testFindAll() throws Exception {
		List<Employee> all = employeeService.getAll();
		System.out.println(employeeService);
		System.out.println(employeeService.getClass());
		System.out.println(all.size());
	}
	
	@Test
	public void testFindPage() throws Exception {
		EmployeeQuery baseQuery = new EmployeeQuery();
		baseQuery.setCurrentPage(2);
		employeeService.findPageByQuery(baseQuery);
	}
	
}
