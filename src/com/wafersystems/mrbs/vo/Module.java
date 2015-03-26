package com.wafersystems.mrbs.vo;

import java.io.Serializable;

public class Module implements Serializable {

	private static final long serialVersionUID = 1L;
	private String id;
	private String nodeNo;
	private String parentNodeNo;
	private String name;
	private String url;
	private String remark;
	private String imageUrl;
	private String swapUrl;

	/**
	 * @return Returns the id.
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            The id to set.
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            The name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return Returns the nodeNo.
	 */
	public String getNodeNo() {
		return nodeNo;
	}

	/**
	 * @param nodeNo
	 *            The nodeNo to set.
	 */
	public void setNodeNo(String nodeNo) {
		this.nodeNo = nodeNo;
	}

	/**
	 * @return Returns the parentNodeNo.
	 */
	public String getParentNodeNo() {
		return parentNodeNo;
	}

	/**
	 * @param parentNodeNo
	 *            The parentNodeNo to set.
	 */
	public void setParentNodeNo(String parentNodeNo) {
		this.parentNodeNo = parentNodeNo;
	}

	/**
	 * @return Returns the remark.
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * @param remark
	 *            The remark to set.
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * @return Returns the url.
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url
	 *            The url to set.
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getSwapUrl() {
		return swapUrl;
	}

	public void setSwapUrl(String swapUrl) {
		this.swapUrl = swapUrl;
	}

}
