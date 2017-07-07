package cn.lkk.pss.service;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import cn.lkk.pss.domain.ProductType;
import cn.lkk.pss.query.ProductTypeQuery;

public class ProductTypeServiceTest extends BaseServiceTest{
	
	@Autowired
	IProductTypeService productTypeService;
	
	@Test
	public void testFindAll() throws Exception {
		List<ProductType> all = productTypeService.getAll();
		System.out.println(productTypeService);
		System.out.println(productTypeService.getClass());
		System.out.println(all.size());
	}

}
