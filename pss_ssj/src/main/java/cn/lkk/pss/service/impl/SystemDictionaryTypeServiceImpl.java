package cn.lkk.pss.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

/**
 * 这里主要是添加一些相对应的具体的业务方法，进行业务处理，而常规的CRUD则抽取到父类中进行实现
 */
import cn.lkk.pss.domain.SystemDictionaryType;
import cn.lkk.pss.service.ISystemDictionaryTypeService;
@Service
public class SystemDictionaryTypeServiceImpl extends BaseServiceImpl<SystemDictionaryType, Long> implements ISystemDictionaryTypeService {

}
