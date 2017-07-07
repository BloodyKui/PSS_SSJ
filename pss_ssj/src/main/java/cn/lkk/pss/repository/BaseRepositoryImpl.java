package cn.lkk.pss.repository;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.jpa.QueryHints;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import cn.lkk.pss.page.PageList;
import cn.lkk.pss.query.BaseQuery;

/**
 * 持久层基类接口实现
 */
public class BaseRepositoryImpl<T, ID extends Serializable> extends SimpleJpaRepository<T, ID>
		implements BaseRepository<T, ID> {

	private final EntityManager entityManager;

	// 父类没有不带参数的构造方法，这里手动构造父类
	public BaseRepositoryImpl(Class<T> domainClass, EntityManager entityManager) {
		super(domainClass, entityManager);
		this.entityManager = entityManager;
	}

	// 通过jpql去查找
	@Override
	public List findByJpql(String jpql, Object... values) {
		Query query = entityManager.createQuery(jpql);
		builderJpaParameter(query, values);
		return query.getResultList();
	}

	// 通过设置查询缓存去查找
	@Override
	public List findCacheByJpql(String cacheJpql, Object... values) {
		// 实现查询缓存核心代码setHint("org.hibernate.cacheable", true);
		// public static final java.lang.String HINT_CACHEABLE =
		// "org.hibernate.cacheable";
		Query query = entityManager.createQuery(cacheJpql).setHint(QueryHints.HINT_CACHEABLE, true);
		builderJpaParameter(query, values);
		return query.getResultList();
	}

	@Override
	public PageList<T> findPageByQuery(BaseQuery baseQuery) {
		// 进行查询操作，先查询总条数
		Query query = entityManager.createQuery(baseQuery.getCountjpql());
		//集合传入可变参数中只能算作一个值，所以需要将集合转换为数组在进行传递
		builderJpaParameter(query, baseQuery.getParams().toArray());
		Long countLong = (Long) query.getSingleResult();
		System.out.println("countLong:" + countLong);
		if (countLong < 1) {
			//如果根据条件查询出来的总条数小于1，那么就不用在去查具体数据了
			return new PageList<>();
		}
		//再查询分页数据
		query = entityManager.createQuery(baseQuery.getLimitjpql());
		builderJpaParameter(query, baseQuery.getParams().toArray());
		PageList<T> pageList = new PageList<>(baseQuery.getCurrentPage(), baseQuery.getPageSize(),
				countLong.intValue());
		//这里一定要使用pageList中的数据，因为pageList中已经处理了边界问题
		int first = (pageList.getCurrentPage() - 1) * pageList.getPageSize();
		int quantity = pageList.getPageSize();
		query.setFirstResult(first).setMaxResults(quantity);
		pageList.setResults(query.getResultList());
		return pageList;
	}

	// 设置查询参数
	private void builderJpaParameter(Query query, Object... values) {
		if (values != null) {
			// jpa索引从1开始
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i + 1, values[i]);
			}
		}
	}

}
