package cn.lkk.pss.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import cn.lkk.pss.domain.Employee;
/**
 * 这里主要是添加一些相对应的具体的业务方法，进行业务处理，而常规的CRUD则抽取到父类中进行实现
 */
import cn.lkk.pss.domain.Permission;
import cn.lkk.pss.service.IPermissionService;
@Service
public class PermissionServiceImpl extends BaseServiceImpl<Permission, Long> implements IPermissionService {

	@Override
	public List<String> getAllSystemPermission() {
		String jpql = "SELECT o.method FROM Permission o";
		List<String> allSystemPermissions = super.findByJpql(jpql, null);
		return allSystemPermissions;
	}

	@Override
	public List<String> getAllLoginUserPermission(Employee employee) {
		String jpql = "SELECT DISTINCT p.method FROM Employee e JOIN e.roles r JOIN r.permissions p WHERE e = ?";
		List<String> allLoginUserPermissions = super.findByJpql(jpql, employee);
		return allLoginUserPermissions;
	}

}
