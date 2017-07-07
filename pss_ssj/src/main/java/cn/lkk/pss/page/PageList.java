package cn.lkk.pss.page;

import java.util.List;

/**
 * 创建一个PageList(分页类)里面有currentPage,pageSize,totalCount,totalPage,results(每页的数据)，
 * 用于将查询的结果返回到前端页面显示。
 * 
 * @author Quid_Lee
 */
public class PageList<T> {
	// 当前页
	private int currentPage;
	// 每页条数
	private int pageSize;
	// 总条数
	private int totalCount;
	// 总页数(通过计算)
	private int totalPage;
	// 每页查询得到的数据
	private List<T> results;

	private int begin;
	private int quantity;

	public PageList() {
	}

	public PageList(int currentPage, int pageSize, int totalCount) {
		this.currentPage = currentPage < 1 ? 1 : currentPage;
		this.pageSize = pageSize < 1 ? 10 : pageSize;
		this.totalCount = totalCount;
		// this.totalPage = (this.totalCount + this.pageSize - 1) /
		// this.pageSize;
		// 先强转为double是防止自动类型转换为int导致数据缺失
		this.totalPage = (int) Math.ceil((double) this.totalCount / this.pageSize);
		// 处理当前页大于总页数
		this.currentPage = this.currentPage < this.totalPage ? this.currentPage : this.totalPage;
	}

	public String getPage() {
		StringBuilder page = new StringBuilder();
		/*
		 * <li class="prev disabled"><a href="#"><i
		 * class="icon-double-angle-left"></i></a></li> <li class="active"><a
		 * href="#">1</a></li> <li><a href="#">2</a></li> <li><a
		 * href="#">3</a></li> <li class="next"><a href="#"><i
		 * class="icon-double-angle-right"></i></a></li>
		 */
		if (currentPage == 1) {
			// 如果到了首页，那么首页和上一页的按钮就应该不能被点击了
			page.append("<li class='disabled'><a href='#'>首页</a></li>");
			page.append("<li class='disabled'><a href='#'>上一页</a></li>");
		} else {
			page.append("<li><a href='javascript:goPage(1)'>首页</a></li>");
			page.append("<li><a href='javascript:goPage(" + (currentPage - 1) + ")'>上一页</a></li>");
		}
		/**
		 * 调用第三方工具类实现只显示部分页数的效果
		 * 
		 * @param totalIndexCount
		 *            --显示的页数个数
		 * @param currentPage
		 *            --当前页
		 * @param totalPage
		 *            --总页数
		 */
		PageIndex pageIndex = PageIndex.getPageIndex(5, currentPage, totalPage);
		// 显示页数按钮
		// for (int i = 1; i <= totalPage; i++) {
		for (int i = pageIndex.getBeginIndex(); i <= pageIndex.getEndIndex(); i++) {
			// 当没有从第一页开始显示的时候，就在前面加上省略号
			if (i == pageIndex.getBeginIndex() && pageIndex.getBeginIndex() != 1) {
				page.append("<li><a href='javascript:goPage(" + (currentPage - 5) + ")'>...</a></li>");
			}
			// 如果是当前所在页数，则不能在被点击了
			if (i == currentPage) {
				page.append("<li class='active'><a >" + i + "</a></li>");
			} else {
				page.append("<li><a href='javascript:goPage(" + i + ")'>" + i + "</a></li>");
			}
			// 当没有以最后一页结束显示的时候，就在后面加上省略号
			if (i == pageIndex.getEndIndex() && pageIndex.getEndIndex() != totalPage) {
				page.append("<li><a href='javascript:goPage(" + (currentPage + 5) + ")'>...</a></li>");
			}
		}

		if (currentPage == totalPage) {
			// 如果到了末页，那么末页和下一页的按钮就应该不能被点击了
			page.append("<li class='disabled'><a href='#'>下一页</a></li>");
			page.append("<li class='disabled'><a href='#'>末页</a></li>");
		} else {
			page.append("<li><a href='javascript:goPage(" + (currentPage + 1) + ")'>下一页</a></li>");
			page.append("<li><a href='javascript:goPage(" + totalPage + ")'>末页</a></li>");
		}

		return page.toString();
	}

	public int getBegin() {
		return (currentPage - 1) * pageSize + 1;
	}

	public void setBegin(int begin) {
		this.begin = begin;
	}

	public int getQuantity() {
		quantity = currentPage * pageSize;
		// 防止最后一页显示的条数超过当页剩余条数
		quantity = quantity > totalCount ? totalCount : quantity;
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public List<T> getResults() {
		return results;
	}

	public void setResults(List<T> results) {
		this.results = results;
	}

}
