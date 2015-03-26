package com.wafersystems.mrbs.web.report.vo;

import java.util.List;

import com.wafersystems.mrbs.vo.user.UserInfo;

public class StatisticalDetailVO {

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
	 * 专家科室
	 */
	private String clinicalDepartmentName;

	/**
	 * 专家姓名
	 */
	private String expertsName;

	/**
	 * 参加社区及听课人数
	 */
	private List<CommAndCountVo> comms;
	/**
	 * 总人数
	 */
	private String count;
	
	/**
	 * 病历提交单位
	 */
	private UserInfo submitCompany;

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getVideoDate() {
		return videoDate;
	}

	public void setVideoDate(String videoDate) {
		this.videoDate = videoDate;
	}

	public String getVideoContent() {
		return videoContent;
	}

	public void setVideoContent(String videoContent) {
		this.videoContent = videoContent;
	}

	public String getClinicalDepartmentName() {
		return clinicalDepartmentName;
	}

	public void setClinicalDepartmentName(String clinicalDepartmentName) {
		this.clinicalDepartmentName = clinicalDepartmentName;
	}

	public String getExpertsName() {
		return expertsName;
	}

	public void setExpertsName(String expertsName) {
		this.expertsName = expertsName;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public List<CommAndCountVo> getComms() {
		return comms;
	}

	public void setComms(List<CommAndCountVo> comms) {
		this.comms = comms;
	}

	/**
	 * @return the submitCompany
	 */
	public UserInfo getSubmitCompany() {
		return submitCompany;
	}
	
	/**
	 * @return the numId
	 */
	public Integer getNumId() {
		return numId;
	}

	/**
	 * @param numId the numId to set
	 */
	public void setNumId(Integer numId) {
		this.numId = numId;
	}

	/**
	 * @param submitCompany the submitCompany to set
	 */
	public void setSubmitCompany(UserInfo submitCompany) {
		this.submitCompany = submitCompany;
	}

	public static class CommAndCountVo {
		/**
		 * 社区
		 */
		private String community;

		/**
		 * 听课人数
		 */
		private String lecturesPeopleCount;

		public String getCommunity() {
			return community;
		}

		public void setCommunity(String community) {
			this.community = community;
		}

		public String getLecturesPeopleCount() {
			return lecturesPeopleCount;
		}

		public void setLecturesPeopleCount(String lecturesPeopleCount) {
			this.lecturesPeopleCount = lecturesPeopleCount;
		}

		
	}
}
