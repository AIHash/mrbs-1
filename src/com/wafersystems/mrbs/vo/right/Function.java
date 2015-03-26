package com.wafersystems.mrbs.vo.right;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "system_function")
public class Function implements Serializable {
	private static final long serialVersionUID = 2561077047074227237L;

	private Integer id;
	private String nodeNo;
	private String parentNodeNo;
	private String name;
	private String url;
	private String remark;
	private String imageUrl;
	private String swapUrl;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNodeNo() {
		return nodeNo;
	}

	public void setNodeNo(String nodeNo) {
		this.nodeNo = nodeNo;
	}

	public String getParentNodeNo() {
		return parentNodeNo;
	}

	public void setParentNodeNo(String parentNodeNo) {
		this.parentNodeNo = parentNodeNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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
