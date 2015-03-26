package com.wafersystems.mrbs.web.report.vo;

public class VideoCount implements Comparable<VideoCount>{

	private String fillFormDate;
	private String fillFormPerson;
	private String statisticalTime;
	private String itemId;
	private String itemName;
	private String itemCount;

	public String getFillFormDate() {
		return fillFormDate;
	}

	public void setFillFormDate(String fillFormDate) {
		this.fillFormDate = fillFormDate;
	}

	public String getFillFormPerson() {
		return fillFormPerson;
	}

	public void setFillFormPerson(String fillFormPerson) {
		this.fillFormPerson = fillFormPerson;
	}

	public String getStatisticalTime() {
		return statisticalTime;
	}

	public void setStatisticalTime(String statisticalTime) {
		this.statisticalTime = statisticalTime;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemCount() {
		return itemCount;
	}

	public void setItemCount(String itemCount) {
		this.itemCount = itemCount;
	}

	@Override
	public int compareTo(VideoCount o) {
		int value = Integer.parseInt(o.getItemCount()) - Integer.parseInt(this.getItemCount());
		return value == 0 ? o.getItemName().hashCode() - this.getItemName().hashCode() : value;
	}

}
