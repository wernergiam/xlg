package com.xlg.pagination;

import org.aspectj.lang.ProceedingJoinPoint;

/**
 * This advice provide the mechanism to selectively decorate the persistence api to
 * inject the pagination behavior. It need to work in conjunction with {@link PaginationFilter}. 
 * Generally, we don't specified the pointcut at the persistence layer as we might use the same api
 * for different usage scenario, e.g. batch processing. The pointcut is usually define in the
 * service layer serving the web request.
 * 
 * @author wernergiam
 *
 */
public class PaginationAdvice {
	/**
	 * To determine the no of entry per page.
	 */
	private int pageSize = 0;
	
	/**
	 * To include total no of record.
	 */
	private boolean includeTotal = false;
	
	/**
	 * @return the pageSize
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * @param pageSize the pageSize to set
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	public boolean isIncludeTotal() {
		return includeTotal;
	}

	public void setIncludeTotal(boolean includeTotal) {
		this.includeTotal = includeTotal;
	}

	public Object paginate(ProceedingJoinPoint pjp) throws Throwable {
		PagingConfig pagingConfig = PagingConfig.getPagingConfig();
		if (pagingConfig != null) {
			pagingConfig.setPaginate(true);
			pagingConfig.setPageSize(pageSize);
			pagingConfig.setIncludeTotal(includeTotal);
		}
		Object retVal = pjp.proceed();
		if (pagingConfig != null) {
			pagingConfig.setPaginate(false);
			pagingConfig.setIncludeTotal(false);;
		}
		return retVal;
	}
}
