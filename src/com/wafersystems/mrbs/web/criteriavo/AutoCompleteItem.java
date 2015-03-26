package com.wafersystems.mrbs.web.criteriavo;

/**
 * AutoComplete插件显示所用的组件
 * label 代表用户可以前台展示项
 * value 代表提交到后台处理值
 * @author yangsf
 */
public class AutoCompleteItem {
	private String label;
	private String value;

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public AutoCompleteItem() {
		super();
	}

	public AutoCompleteItem(String label, String value) {
		super();
		this.label = label;
		this.value = value;
	}
}
