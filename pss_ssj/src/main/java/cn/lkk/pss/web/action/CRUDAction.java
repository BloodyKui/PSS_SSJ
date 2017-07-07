package cn.lkk.pss.web.action;

import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;

public abstract class CRUDAction<T> extends BaseAction implements ModelDriven<T>, Preparable {
	protected Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String execute() throws Exception {
		// 利用钩子回调子类的方法
		list();
		return SUCCESS;
	}

	protected abstract void list();

	//规范子类的书写方式
	public abstract void prepareInput() throws Exception;

	public abstract void prepareSave() throws Exception;

	public abstract String save() throws Exception;

	public abstract String delete() throws Exception;

	@Override
	public void prepare() throws Exception {

	}

	// 这里不覆写getModel方法，交给子类去处理，这样可以返回具体的对象
}
