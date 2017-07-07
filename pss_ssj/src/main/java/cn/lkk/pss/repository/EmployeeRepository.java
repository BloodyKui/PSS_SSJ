package cn.lkk.pss.repository;

import cn.lkk.pss.domain.Employee;

/**
 * T:需要操作的entity实体
 * Serializable: 操作的entity实体中的主键类型
 */
public interface EmployeeRepository extends BaseRepository<Employee, Long> {

}
