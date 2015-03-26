package com.wafersystems.mrbs.dao.base;

import java.io.Serializable;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.ecside.core.context.Context;
import org.ecside.core.context.HttpServletRequestContext;
import org.ecside.core.TableConstants;
import org.extremecomponents.table.filter.ExportFilterUtils;
import org.extremecomponents.table.limit.Limit;
import org.extremecomponents.table.limit.LimitFactory;
import org.extremecomponents.table.limit.TableLimit;
import org.extremecomponents.table.limit.TableLimitFactory;
import org.springframework.web.util.WebUtils;

public class PageSortModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3071134575762326186L;

	private int pageSize = 20;// 页面大小

	private String pageSizeStr = "20";

	private int pageNo = 1;// 页号

	private transient int totalRows = 0;// 总记录数

	private transient int pageCount = 0;// 总页数

	private boolean all = false;

	private Map<String, Object> orderMap = null;

	private Limit limit;

	private HttpServletRequest request;

	public PageSortModel() {

	}

	public PageSortModel(HttpServletRequest request) {
		this(request, null);
	}

	public PageSortModel(HttpServletRequest request, String tableId) {
		this.request = request;
		if (tableId == null)
			tableId = "ec";
		Context context = new HttpServletRequestContext(request);
		LimitFactory limitFactory = new TableLimitFactory(context, tableId);
		limit = new TableLimit(limitFactory);
		this.pageNo = limit.getPage();
		String rcdStr = context.getParameter(tableId + "_"
				+ TableConstants.CURRENT_ROWS_DISPLAYED);
		if (StringUtils.isNotBlank(rcdStr)) {
			this.pageSize = Integer.parseInt(rcdStr);
			this.pageSizeStr = Integer.toString(pageSize);
		}
		if (ExportFilterUtils.isExported(context))
			all = true;
		orderMap = WebUtils.getParametersStartingWith(request, tableId + "_"
				+ TableConstants.SORT);

	}

	public PageSortModel(int pageNo) {
		this.pageNo = pageNo;
	}

	public Map<String, Object> getOrderMap() {
		return orderMap;
	}

	public void setOrderMap(Map<String, Object> v) {
		this.orderMap = v;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public String getPageSizeStr() {
		return pageSizeStr;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
		this.pageSizeStr = Integer.toString(pageSize);
	}

	public int getTotalRows() {
		return totalRows;
	}

	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
		if (limit != null) {
			limit.setRowAttributes(totalRows, pageSize);
		}
		request.setAttribute("totalRows", new Integer(totalRows));
	}

	public int getRowStart() {
		return (pageNo - 1) * pageSize;

	}

	public boolean isAll() {
		return all;
	}

	public void setAll(boolean all) {
		this.all = all;
	}
}
