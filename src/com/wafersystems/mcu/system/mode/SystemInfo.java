package com.wafersystems.mcu.system.mode;

public class SystemInfo {

	private String platform;
	private String operationMode;
	private String licenseMode;
	private Integer numControlledServers;
	private String clusterType;
	private String gateKeeperOK;
	private Integer tpsNumberOK;
	private String tpdVersion;
	private String tpdName;
	private Integer tpdUptime;
	private String tpdSerial;
	/**
	 * 检查系统是否有足够的资源发起一个会话，如果满足返回 true
	 */
	private Boolean makeCallsOK;
	private Integer portsVideoTotal;
	private Integer portsVideoFree;
	private Integer portsAudioTotal;
	private Integer portsAudioFree;
	private Integer portsContentTotal;
	private Integer portsContentFree;
	private Integer maxConferenceSizeVideo;
	private Integer maxConferenceSizeAudio;
	private Integer maxConferenceSizeContent;
	private String softwareVersion;
	/**
	 * @return the platform
	 */
	public String getPlatform() {
		return platform;
	}
	/**
	 * @param platform the platform to set
	 */
	public void setPlatform(String platform) {
		this.platform = platform;
	}
	/**
	 * @return the operationMode
	 */
	public String getOperationMode() {
		return operationMode;
	}
	/**
	 * @param operationMode the operationMode to set
	 */
	public void setOperationMode(String operationMode) {
		this.operationMode = operationMode;
	}
	/**
	 * @return the licenseMode
	 */
	public String getLicenseMode() {
		return licenseMode;
	}
	/**
	 * @param licenseMode the licenseMode to set
	 */
	public void setLicenseMode(String licenseMode) {
		this.licenseMode = licenseMode;
	}
	/**
	 * @return the numControlledServers
	 */
	public Integer getNumControlledServers() {
		return numControlledServers;
	}
	/**
	 * @param numControlledServers the numControlledServers to set
	 */
	public void setNumControlledServers(Integer numControlledServers) {
		this.numControlledServers = numControlledServers;
	}
	/**
	 * @return the clusterType
	 */
	public String getClusterType() {
		return clusterType;
	}
	/**
	 * @param clusterType the clusterType to set
	 */
	public void setClusterType(String clusterType) {
		this.clusterType = clusterType;
	}
	/**
	 * @return the gateKeeperOK
	 */
	public String getGateKeeperOK() {
		return gateKeeperOK;
	}
	/**
	 * @param gateKeeperOK the gateKeeperOK to set
	 */
	public void setGateKeeperOK(String gateKeeperOK) {
		this.gateKeeperOK = gateKeeperOK;
	}
	/**
	 * @return the tpsNumberOK
	 */
	public Integer getTpsNumberOK() {
		return tpsNumberOK;
	}
	/**
	 * @param tpsNumberOK the tpsNumberOK to set
	 */
	public void setTpsNumberOK(Integer tpsNumberOK) {
		this.tpsNumberOK = tpsNumberOK;
	}
	/**
	 * @return the tpdVersion
	 */
	public String getTpdVersion() {
		return tpdVersion;
	}
	/**
	 * @param tpdVersion the tpdVersion to set
	 */
	public void setTpdVersion(String tpdVersion) {
		this.tpdVersion = tpdVersion;
	}
	/**
	 * @return the tpdName
	 */
	public String getTpdName() {
		return tpdName;
	}
	/**
	 * @param tpdName the tpdName to set
	 */
	public void setTpdName(String tpdName) {
		this.tpdName = tpdName;
	}
	/**
	 * @return the tpdUptime
	 */
	public Integer getTpdUptime() {
		return tpdUptime;
	}
	/**
	 * @param tpdUptime the tpdUptime to set
	 */
	public void setTpdUptime(Integer tpdUptime) {
		this.tpdUptime = tpdUptime;
	}
	/**
	 * @return the tpdSerial
	 */
	public String getTpdSerial() {
		return tpdSerial;
	}
	/**
	 * @param tpdSerial the tpdSerial to set
	 */
	public void setTpdSerial(String tpdSerial) {
		this.tpdSerial = tpdSerial;
	}
	/**
	 * @return the makeCallsOK
	 */
	public Boolean getMakeCallsOK() {
		return makeCallsOK;
	}
	/**
	 * @param makeCallsOK the makeCallsOK to set
	 */
	public void setMakeCallsOK(Boolean makeCallsOK) {
		this.makeCallsOK = makeCallsOK;
	}
	/**
	 * @return the portsVideoTotal
	 */
	public Integer getPortsVideoTotal() {
		return portsVideoTotal;
	}
	/**
	 * @param portsVideoTotal the portsVideoTotal to set
	 */
	public void setPortsVideoTotal(Integer portsVideoTotal) {
		this.portsVideoTotal = portsVideoTotal;
	}
	/**
	 * @return the portsVideoFree
	 */
	public Integer getPortsVideoFree() {
		return portsVideoFree;
	}
	/**
	 * @param portsVideoFree the portsVideoFree to set
	 */
	public void setPortsVideoFree(Integer portsVideoFree) {
		this.portsVideoFree = portsVideoFree;
	}
	/**
	 * @return the portsAudioTotal
	 */
	public Integer getPortsAudioTotal() {
		return portsAudioTotal;
	}
	/**
	 * @param portsAudioTotal the portsAudioTotal to set
	 */
	public void setPortsAudioTotal(Integer portsAudioTotal) {
		this.portsAudioTotal = portsAudioTotal;
	}
	/**
	 * @return the portsAudioFree
	 */
	public Integer getPortsAudioFree() {
		return portsAudioFree;
	}
	/**
	 * @param portsAudioFree the portsAudioFree to set
	 */
	public void setPortsAudioFree(Integer portsAudioFree) {
		this.portsAudioFree = portsAudioFree;
	}
	/**
	 * @return the portsContentTotal
	 */
	public Integer getPortsContentTotal() {
		return portsContentTotal;
	}
	/**
	 * @param portsContentTotal the portsContentTotal to set
	 */
	public void setPortsContentTotal(Integer portsContentTotal) {
		this.portsContentTotal = portsContentTotal;
	}
	/**
	 * @return the portsContentFree
	 */
	public Integer getPortsContentFree() {
		return portsContentFree;
	}
	/**
	 * @param portsContentFree the portsContentFree to set
	 */
	public void setPortsContentFree(Integer portsContentFree) {
		this.portsContentFree = portsContentFree;
	}
	/**
	 * @return the maxConferenceSizeVideo
	 */
	public Integer getMaxConferenceSizeVideo() {
		return maxConferenceSizeVideo;
	}
	/**
	 * @param maxConferenceSizeVideo the maxConferenceSizeVideo to set
	 */
	public void setMaxConferenceSizeVideo(Integer maxConferenceSizeVideo) {
		this.maxConferenceSizeVideo = maxConferenceSizeVideo;
	}
	/**
	 * @return the maxConferenceSizeAudio
	 */
	public Integer getMaxConferenceSizeAudio() {
		return maxConferenceSizeAudio;
	}
	/**
	 * @param maxConferenceSizeAudio the maxConferenceSizeAudio to set
	 */
	public void setMaxConferenceSizeAudio(Integer maxConferenceSizeAudio) {
		this.maxConferenceSizeAudio = maxConferenceSizeAudio;
	}
	/**
	 * @return the maxConferenceSizeContent
	 */
	public Integer getMaxConferenceSizeContent() {
		return maxConferenceSizeContent;
	}
	/**
	 * @param maxConferenceSizeContent the maxConferenceSizeContent to set
	 */
	public void setMaxConferenceSizeContent(Integer maxConferenceSizeContent) {
		this.maxConferenceSizeContent = maxConferenceSizeContent;
	}
	/**
	 * @return the softwareVersion
	 */
	public String getSoftwareVersion() {
		return softwareVersion;
	}
	/**
	 * @param softwareVersion the softwareVersion to set
	 */
	public void setSoftwareVersion(String softwareVersion) {
		this.softwareVersion = softwareVersion;
	}
	
	
}
