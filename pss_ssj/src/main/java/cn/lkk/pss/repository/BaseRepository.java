package cn.lkk.pss.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import cn.lkk.pss.page.PageList;
import cn.lkk.pss.query.BaseQuery;

/***
 * 持久层基类接口
 */
@NoRepositoryBean
public interface BaseRepository<T, ID extends Serializable> extends JpaRepository<T, ID> {

	List findByJpql(String jpql, Object... values);

	List findCacheByJpql(String cacheJpql, Object... values);
	
	PageList<T> findPageByQuery(BaseQuery baseQuery);
}
