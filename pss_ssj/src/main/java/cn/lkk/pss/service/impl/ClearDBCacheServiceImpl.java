package cn.lkk.pss.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.lkk.pss.domain.ForbidIP;
import cn.lkk.pss.service.IClearDBCacheService;
import cn.lkk.pss.service.IForbidIPService;
@Service("dBCache")
public class ClearDBCacheServiceImpl implements IClearDBCacheService{
	@Autowired
	private IForbidIPService forbidIPService;
	@Override
	public void clearCache() {
		//拿到所有的ip对象，然后遍历删除
		List<ForbidIP> all = forbidIPService.getAll();
		for (ForbidIP forbidIP : all) {
			forbidIPService.delete(forbidIP.getId());
		}
	}

}
