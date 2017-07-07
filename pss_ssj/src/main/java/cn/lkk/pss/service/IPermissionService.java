package cn.lkk.pss.service;

import java.util.List;

import cn.lkk.pss.domain.Employee;
import cn.lkk.pss.domain.Permission;

public interface IPermissionService extends IBaseService<Permission, Long> {
	/**
	 * 获取本系统中所有的权限名称
	 * @return
	 */
	List<String> getAllSystemPermission();
	
	/**
	 * 获取当前登录用户所拥有的所有权限
	 * @return
	 */
	List<String> getAllLoginUserPermission(Employee employee);
	
}
