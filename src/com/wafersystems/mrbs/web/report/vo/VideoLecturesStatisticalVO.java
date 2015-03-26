package com.wafersystems.mrbs.web.report.vo;

import java.util.Date;

import com.wafersystems.mrbs.vo.user.UserInfo;
public class VideoLecturesStatisticalVO {

	/**
	 * 编号
	 */
	private String number;
	
	/**
	 * 序号
	 */
	private Integer numId;
	
	/**
	 * 视频日期
	 */
	private String videoDate;
	
	/**
	 * 视频内容
	 */
	private String videoContent;
	
	/**
	 * 临床科室
	 */
	private String clinicalDepartmentName;
	
	/**
	 * 会诊专家
	 */
	private String expertsName;
	
	/**
	 * 社区
	 */
	private String community;
	
	/**
	 * 听课人数
	 */
	private String lecturesPeopleCount;
	
	/**
	 * 病历提交单位
	 */
	private UserInfo submitCompany;
    /**
     * 开始时间
     */
	private String startTime;
	/**
	 * 结束时间
	 */
	private String endTime;
	/**
	 * 构造器
	 */
	public VideoLecturesStatisticalVO() {
		super();
	}

	/**
	 * @return the number
	 */
	public String getNumber() {
		return number;
	}

	/**
	 * @param number the number to set
	 */
	public void setNumber(String number) {
		this.number = number;
	}

	/**
	 * @return the videoDate
	 */
	public String getVideoDate() {
		return videoDate;
	}

	/**
	 * @param videoDate the videoDate to set
	 */
	public void setVideoDate(String videoDate) {
		this.videoDate = videoDate;
	}

	/**
	 * @return the videoContent
	 */
	public String getVideoContent() {
		return videoContent;
	}

	/**
	 * @param videoContent the videoContent to set
	 */
	public void setVideoContent(String videoContent) {
		this.videoContent = videoContent;
	}

	/**
	 * @return the clinicalDepartmentName
	 */
	public String getClinicalDepartmentName() {
		return clinicalDepartmentName;
	}

	/**
	 * @param clinicalDepartmentName the clinicalDepartmentName to set
	 */
	public void setClinicalDepartmentName(String clinicalDepartmentName) {
		this.clinicalDepartmentName = clinicalDepartmentName;
	}

	/**
	 * @return the expertsName
	 */
	public String getExpertsName() {
		return expertsName;
	}

	/**
	 * @param expertsName the expertsName to set
	 */
	public void setExpertsName(String expertsName) {
		this.expertsName = expertsName;
	}

	/**
	 * @return the community
	 */
	public String getCommunity() {
		return community;
	}

	/**
	 * @param community the community to set
	 */
	public void setCommunity(String community) {
		this.community = community;
	}

	/**
	 * @return the lecturesPeopleCount
	 */
	public String getLecturesPeopleCount() {
		return lecturesPeopleCount;
	}

	/**
	 * @param lecturesPeopleCount the lecturesPeopleCount to set
	 */
	public void setLecturesPeopleCount(String lecturesPeopleCount) {
		this.lecturesPeopleCount = lecturesPeopleCount;
	}
	
	/**
	 * @return the submitCompany
	 */
	public UserInfo getSubmitCompany() {
		return submitCompany;
	}

	/**
	 * @param submitCompany the submitCompany to set
	 */
	public void setSubmitCompany(UserInfo submitCompany) {
		this.submitCompany = submitCompany;
	}

	public Integer getNumId() {
		return numId;
	}

	public void setNumId(Integer numId) {
		this.numId = numId;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

}
