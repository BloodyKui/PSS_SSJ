package cn.lkk.pss.service;

import java.util.List;

import cn.lkk.pss.domain.ProductType;

public interface IProductTypeService extends IBaseService<ProductType, Long> {
	/**
	 * 获取所有的父类型，即parent_id为空的类型
	 * @return
	 */
	List<ProductType> findAllParents();
	
	/**
	 * 根据父类型的id找出其所有子类型
	 * @param id 父类型的id
	 * @return
	 */
	List<ProductType> findChildren(Long id);
}
