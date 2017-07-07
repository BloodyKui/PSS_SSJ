package cn.lkk.pss.service;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import cn.lkk.pss.domain.Supplier;
import cn.lkk.pss.query.SupplierQuery;

public class SupplierServiceTest extends BaseServiceTest{
	
	@Autowired
	ISupplierService supplierService;
	
	@Test
	public void testFindAll() throws Exception {
		List<Supplier> all = supplierService.getAll();
		System.out.println(supplierService);
		System.out.println(supplierService.getClass());
		System.out.println(all.size());
	}

}
