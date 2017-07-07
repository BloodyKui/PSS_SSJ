package cn.lkk.pss.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

/**
 * 这里主要是添加一些相对应的具体的业务方法，进行业务处理，而常规的CRUD则抽取到父类中进行实现
 */
import cn.lkk.pss.domain.ProductType;
import cn.lkk.pss.service.IProductTypeService;
@Service
public class ProductTypeServiceImpl extends BaseServiceImpl<ProductType, Long> implements IProductTypeService {
	@Override
	public List<ProductType> findAllParents(){
		String jpql = "SELECT o FROM ProductType o WHERE o.productTypeParent is null";
		List<ProductType> parents = super.findCacheByJpql(jpql);
		return parents;
	}
	
	
	@Override
	public List<ProductType> findChildren(Long id) {
		String jpql = "SELECT o FROM ProductType o WHERE o.productTypeParent.id = ?";
		List<ProductType> children = super.findByJpql(jpql, id);
		return children;
	}

}
