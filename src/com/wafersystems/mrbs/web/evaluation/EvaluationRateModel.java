package com.wafersystems.mrbs.web.evaluation;

public class EvaluationRateModel {
	//评价ＩＤ
	private Integer evaluationId;
	//评价名称
	private String evaluationName;
	//评估得分
	private String evaluationRate;
	//评估得分与平均分数百分比
	private String evaluationRatePercent;
	//评估总会议数
	private String evaluationRateAllMN;
	//评分为1分的会议数
	private String evaluationRate1MN;
	//评分为1分会议数占总会议数的百分比
	private String evaluationRate1MNPercent;
	//评分为2分的会议数
	private String evaluationRate2MN;
	//评分为2分会议数占总会议数的百分比
	private String evaluationRate2MNPercent;
	//评分为3分的会议数
	private String evaluationRate3MN;
	//评分为3分会议数占总会议数的百分比
	private String evaluationRate3MNPercent;
	//评分为4分的会议数
	private String evaluationRate4MN;
	//评分为4分会议数占总会议数的百分比
	private String evaluationRate4MNPercent;
	//评分为5分的会议数
	private String evaluationRate5MN;
	//评分为5分会议数占总会议数的百分比
	private String evaluationRate5MNPercent;
	//比平均分标准高
	private boolean high;
	//星星评分
	private String starScore;

	public String getStarScore() {
		return starScore;
	}
	public void setStarScore(String starScore) {
		this.starScore = starScore;
	}
	public boolean isHigh() {
		return high;
	}
	public void setHigh(boolean high) {
		this.high = high;
	}
	public String getEvaluationRate() {
		return evaluationRate;
	}
	public void setEvaluationRate(String evaluationRate) {
		this.evaluationRate = evaluationRate;
	}
	public String getEvaluationRatePercent() {
		return evaluationRatePercent;
	}
	public void setEvaluationRatePercent(String evaluationRatePercent) {
		this.evaluationRatePercent = evaluationRatePercent;
	}
	public String getEvaluationRateAllMN() {
		return evaluationRateAllMN;
	}
	public void setEvaluationRateAllMN(String evaluationRateAllMN) {
		this.evaluationRateAllMN = evaluationRateAllMN;
	}
	public String getEvaluationRate1MN() {
		return evaluationRate1MN;
	}
	public void setEvaluationRate1MN(String evaluationRate1MN) {
		this.evaluationRate1MN = evaluationRate1MN;
	}
	public String getEvaluationRate1MNPercent() {
		return evaluationRate1MNPercent;
	}
	public void setEvaluationRate1MNPercent(String evaluationRate1MNPercent) {
		this.evaluationRate1MNPercent = evaluationRate1MNPercent;
	}
	public String getEvaluationRate2MN() {
		return evaluationRate2MN;
	}
	public void setEvaluationRate2MN(String evaluationRate2MN) {
		this.evaluationRate2MN = evaluationRate2MN;
	}
	public String getEvaluationRate2MNPercent() {
		return evaluationRate2MNPercent;
	}
	public void setEvaluationRate2MNPercent(String evaluationRate2MNPercent) {
		this.evaluationRate2MNPercent = evaluationRate2MNPercent;
	}
	public String getEvaluationRate3MN() {
		return evaluationRate3MN;
	}
	public void setEvaluationRate3MN(String evaluationRate3MN) {
		this.evaluationRate3MN = evaluationRate3MN;
	}
	public String getEvaluationRate3MNPercent() {
		return evaluationRate3MNPercent;
	}
	public void setEvaluationRate3MNPercent(String evaluationRate3MNPercent) {
		this.evaluationRate3MNPercent = evaluationRate3MNPercent;
	}
	public String getEvaluationRate4MN() {
		return evaluationRate4MN;
	}
	public void setEvaluationRate4MN(String evaluationRate4MN) {
		this.evaluationRate4MN = evaluationRate4MN;
	}
	public String getEvaluationRate4MNPercent() {
		return evaluationRate4MNPercent;
	}
	public void setEvaluationRate4MNPercent(String evaluationRate4MNPercent) {
		this.evaluationRate4MNPercent = evaluationRate4MNPercent;
	}
	public String getEvaluationRate5MN() {
		return evaluationRate5MN;
	}
	public void setEvaluationRate5MN(String evaluationRate5MN) {
		this.evaluationRate5MN = evaluationRate5MN;
	}
	public String getEvaluationRate5MNPercent() {
		return evaluationRate5MNPercent;
	}
	public void setEvaluationRate5MNPercent(String evaluationRate5MNPercent) {
		this.evaluationRate5MNPercent = evaluationRate5MNPercent;
	}
	public Integer getEvaluationId() {
		return evaluationId;
	}
	public void setEvaluationId(Integer evaluationId) {
		this.evaluationId = evaluationId;
	}
	public String getEvaluationName() {
		return evaluationName;
	}
	public void setEvaluationName(String evaluationName) {
		this.evaluationName = evaluationName;
	}

}
