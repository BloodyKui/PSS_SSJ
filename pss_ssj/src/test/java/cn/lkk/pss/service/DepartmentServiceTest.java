package cn.lkk.pss.service;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import cn.lkk.pss.domain.Department;
import cn.lkk.pss.query.DepartmentQuery;

public class DepartmentServiceTest extends BaseServiceTest{
	
	@Autowired
	IDepartmentService departmentService;
	
	@Test
	public void testFindAll() throws Exception {
		List<Department> all = departmentService.getAll();
		System.out.println(departmentService);
		System.out.println(departmentService.getClass());
		System.out.println(all.size());
	}

}
