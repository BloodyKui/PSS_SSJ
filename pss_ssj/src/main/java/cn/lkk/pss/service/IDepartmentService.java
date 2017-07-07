package cn.lkk.pss.service;

import cn.lkk.pss.domain.Department;

public interface IDepartmentService extends IBaseService<Department, Long> {
	Department findIdByName(String name);
}
