package cn.lkk.pss.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.lkk.pss.domain.Employee;
import cn.lkk.pss.repository.EmployeeRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class InitTest{
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Test
	public void testFindAll() throws Exception {
		List<Employee> list = employeeRepository.findAll();
		for (Employee employee : list) {
			System.out.println(employee);
		}
	}
}
