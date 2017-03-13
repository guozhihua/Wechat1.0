package hxs.weixin.parent.util;


import java.util.List;

/**
 * 分页
 * 
 * @author hechen
 *
 * @param <T>
 */
public class Pager <T> {
	
	//------------------------------------------ 属性 --------------------------------------------------------
	
	/** 记录结果集 */
	private List<T> lists;
	
	private T entity;
	/** 总记录数 */
	private Long total = 0L; // 总记录数
	
	/** 每页显示记录数 */
	private Long pageSize = 20L; // 默认20
	
	/** 总页数 */
	private Long totalPages = 1L; // 总页数
	
	/** 当前页 */
	private Long pageNumber = 1L; // 当前页

	/** 是否为第一页 */
	private boolean isFirstPage = false; // 是否为第一页
	
	/** 是否为最后一页 */
	private boolean isLastPage = false; // 是否为最后一页
	
	/** 是否有前一页 */
	private boolean hasPreviousPage = false; // 是否有前一页
	
	/** 是否有下一页 */
	private boolean hasNextPage = false; // 是否有下一页
	
	//--------------------------------------------- 构造器 ----------------------------------------------------------------

	public Pager(Long pageNumber, Long pageSize, Long total, List<T> lists) {
		init( total ,pageNumber, pageSize);
		this.lists = lists;
	}
	
	public Pager(Long pageNumber, Long pageSize, Long total, T entity) {
		init( total ,pageNumber, pageSize);
		this.entity = entity;
	}

	/** 设置基本参数 */
	private void init(Long total, Long pageNumber, Long pageSize) {
		// 设置基本参数
		this.total = total;
		this.pageSize = pageSize;
		this.totalPages = (this.total - 1) / this.pageSize + 1;

		// 根据输入可能错误的当前号码进行自动纠正
		if (pageNumber < 1) {
			this.pageNumber = 1L;
		} else if (pageNumber > this.totalPages) {
			this.pageNumber = this.totalPages;
		} else {
			this.pageNumber = pageNumber;
		}
		judgePageBoudary();
	}

	/**
	 * 判定页面边界
	 */
	private void judgePageBoudary() {
		isFirstPage = pageNumber == 1;
		isLastPage = pageNumber == totalPages && pageNumber != 1;
		hasPreviousPage = pageNumber > 1;
		hasNextPage = pageNumber < totalPages;
	}
//-----------------------------------------------------get set 方法-----------------------------------------------------------------------
	
	public List<T> getLists() {
		return lists;
	}

	public void setLists(List<T> lists) {
		this.lists = lists;
	}
	
//--------------------------------------------------------------------------------
	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	//--------------------------------------------------------------------------------
	public Long getPageSize() {
		return pageSize;
	}

	public void setPageSize(Long pageSize) {
		this.pageSize = pageSize;
	}

	//--------------------------------------------------------------------------------
	public Long getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(Long totalPages) {
		this.totalPages = totalPages;
	}
	
	//--------------------------------------------------------------------------------
	public Long getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(Long pageNumber) {
		this.pageNumber = pageNumber;
	}
	
	//--------------------------------------------------------------------------------
	public boolean isFirstPage() {
		return isFirstPage;
	}

	public void setFirstPage(boolean isFirstPage) {
		this.isFirstPage = isFirstPage;
	}

	//--------------------------------------------------------------------------------
	public boolean isLastPage() {
		return isLastPage;
	}

	public void setLastPage(boolean isLastPage) {
		this.isLastPage = isLastPage;
	}

	//--------------------------------------------------------------------------------
	public boolean isHasPreviousPage() {
		return hasPreviousPage;
	}

	public void setHasPreviousPage(boolean hasPreviousPage) {
		this.hasPreviousPage = hasPreviousPage;
	}

	//--------------------------------------------------------------------------------
	public boolean isHasNextPage() {
		return hasNextPage;
	}

	public void setHasNextPage(boolean hasNextPage) {
		this.hasNextPage = hasNextPage;
	}

	public T getEntity() {
		return entity;
	}

	public void setEntity(T entity) {
		this.entity = entity;
	}

 //--------------------------------------------------------------------------------------------------------------------------------

}