package cn.lkk.pss.service;

import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;

import cn.lkk.pss.page.PageList;
import cn.lkk.pss.query.BaseQuery;
//定义一个父接口，抽取出常用的CRUD方法
public interface IBaseService<T, ID extends Serializable> {
	void save(T t);

	void delete(ID id);

	T get(ID id);

	List<T> getAll();

	List findByJpql(final String jpql, Object... values);

	List findCacheByJpql(final String jpql, Object... values);

	PageList<T> findPageByQuery(BaseQuery baseQuery);
	/**
	 * 导出Excel
	 * @param heads 表的抬头，每一列的名称
	 * @param rows 表中的数据
	 * @return InputStream流
	 * @throws Exception
	 */
	InputStream download(String[] heads, List<String[]> rows) throws Exception;
	
	/**
	 * 导入Excel文件
	 * @param upload 文件源
	 * @return 除开表头以外的数据
	 * @throws Exception
	 */
	List<String[]> importXlsx(File upload) throws Exception;
}
