package com.wafersystems.mrbs.web.criteriavo;
/**
 * 下拉选单公共类
 * @author wafer-rengeng
 *
 */
public class SelectItem {
	private String id;
	private String name;
	
	public SelectItem() {

	}
	
	public SelectItem(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
