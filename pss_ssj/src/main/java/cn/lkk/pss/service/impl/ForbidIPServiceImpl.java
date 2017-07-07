package cn.lkk.pss.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import cn.lkk.pss.domain.ForbidIP;
import cn.lkk.pss.service.IForbidIPService;
@Service
public class ForbidIPServiceImpl extends BaseServiceImpl<ForbidIP, Long> implements IForbidIPService {

	@Override
	public ForbidIP findByUsername(String realAddr,String username) {
		String jpql = "SELECT o FROM ForbidIP o WHERE o.path_IP = ? AND o.dangerEmp.username = ?";
		try {
			List<ForbidIP> list = super.findByJpql(jpql, realAddr, username);
			return list.get(0);
		} catch (Exception e) {
			return null;
		}
		
	}

	@Override
	public List<String> findIPEqul5() {
		String jpql = "SELECT o.path_IP FROM ForbidIP o WHERE o.wrong <= 0";
		List<String> paths = super.findByJpql(jpql);
		for (String string : paths) {
			System.out.println(paths);
		}
		return paths;
	}
	
}
