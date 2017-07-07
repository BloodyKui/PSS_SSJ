package cn.lkk.pss.service;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import cn.lkk.pss.domain.Product;
import cn.lkk.pss.query.ProductQuery;

public class ProductServiceTest extends BaseServiceTest{
	
	@Autowired
	IProductService productService;
	
	@Test
	public void testFindAll() throws Exception {
		List<Product> all = productService.getAll();
		System.out.println(productService);
		System.out.println(productService.getClass());
		System.out.println(all.size());
	}

}
