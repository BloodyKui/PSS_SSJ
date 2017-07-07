package cn.lkk.pss.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import cn.lkk.pss.domain.Department;
import cn.lkk.pss.service.IDepartmentService;

@Service
public class DepartmentServiceImpl extends BaseServiceImpl<Department, Long> implements IDepartmentService {

	@Override
	public Department findIdByName(String name) {
		String jpql = "SELECT d FROM Department d WHERE d.name = ?";
		List<Department> departments = super.findByJpql(jpql, name);
		// 因为根据部门名字查最多只能查到一个部门，直接返回第一个部门对象
		return departments.size() > 0 ? departments.get(0) : null;
	}

}
