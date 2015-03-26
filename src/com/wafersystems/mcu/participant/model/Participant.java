package com.wafersystems.mcu.participant.model;

import java.io.Serializable;

public class Participant implements Serializable{

	private static final long serialVersionUID = 1L;
	private String enumerateID;
	private String authenticationUser;
	private String authenticationPassword;
	
	/**
	 * 参与人地址
	 * 地址必须包括前缀h323:或者sip:，网真服务器将会使用默认的H.323，但是不需要输入校验码
	 * 最大长度为80，包括前缀
	 * 当你邀请一组参与人时，可以用逗号 ， 分隔开四个地址，每个地址都需要一个前缀，例如：
	 * h323:leftmost_endpoint@domain.com,h323:rightmost_endpoint@domain.com, 
	 * 每一个地址最大长度是80字节，总长度最大为323字节。
	 */
	private String address;
	
	/**
	 * 指定终端的类型：
	 * t3：Cisco TelePresence System T3
	 * cts：任何标记有“telepresence”的网真系统终端，包括单屏或者三屏，例如500、1300、3000
	 * cts1：任何标记有“telepresence”的网真系统终端单屏系列，例如500和1300系列
	 * cts3：任何标记有“telepresence”的网真系统终端三屏系列，例如3000系列
	 */
	private String type;
	
	/**
	 * true：会议主参与人/主设备终端
	 * false：不是主要终端（默认）
	 */
	private Boolean master;
	
	/**
	 * 在一个OneTable 会议中，终端显示屏的位置，适用于<code>type</code>为 t3
	 */
	private Integer oneTableIndex;
	
	/**
	 * 会议和参会人之间的最大传输比特率（kbps）
	 */
	private Integer maxBitRate;
	
	/**
	 * true：当前终端被设置成一个记录设备。
	 * false（默认）：一个普通的设备终端
	 */
	private Boolean recordingDevice;
	
	private String dtmf;
	
	private Integer audioContentIndex;
	private Integer contentIndex;
	private Boolean camerasCrossed;
	private String txAspectRatio;
	private Boolean autoReconnect;
	private Boolean alwaysReconnect;
	private Boolean deferConnect;
	private Boolean autoDisconnect;
	
	private String defaultLayoutSingleScreen;
	private String defaultLayoutMultiScreen;
	
	private Boolean forceDefaultLayout;
	private Boolean automaticGainControl;
	private Boolean allowStarSixMuting;
	
	/**--------------------------------------- participant.set -----------------------------**/
	
	private String participantGUID;
	@Deprecated
	private Integer participantID;
	private Boolean txAudioMute;
	private Boolean rxAudioMute;
	private Boolean txVideoMute;
	private Boolean rxVideoMute;
	private Boolean isImportant;
//	private String defaultLayoutSingleScreen;
//	private String defaultLayoutMultiScreen;
//	private Boolean forceDefaultLayout;
//	private Boolean automaticGainControl;
	/**
	 * @return the authenticationUser
	 */
	public String getAuthenticationUser() {
		return authenticationUser;
	}
	/**
	 * @param authenticationUser the authenticationUser to set
	 */
	public void setAuthenticationUser(String authenticationUser) {
		this.authenticationUser = authenticationUser;
	}
	/**
	 * @return the authenticationPassword
	 */
	public String getAuthenticationPassword() {
		return authenticationPassword;
	}
	/**
	 * @param authenticationPassword the authenticationPassword to set
	 */
	public void setAuthenticationPassword(String authenticationPassword) {
		this.authenticationPassword = authenticationPassword;
	}
	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * @return the master
	 */
	public Boolean getMaster() {
		return master;
	}
	/**
	 * @param master the master to set
	 */
	public void setMaster(Boolean master) {
		this.master = master;
	}
	/**
	 * @return the oneTableIndex
	 */
	public Integer getOneTableIndex() {
		return oneTableIndex;
	}
	/**
	 * @param oneTableIndex the oneTableIndex to set
	 */
	public void setOneTableIndex(Integer oneTableIndex) {
		this.oneTableIndex = oneTableIndex;
	}
	/**
	 * @return the maxBitRate
	 */
	public Integer getMaxBitRate() {
		return maxBitRate;
	}
	/**
	 * @param maxBitRate the maxBitRate to set
	 */
	public void setMaxBitRate(Integer maxBitRate) {
		this.maxBitRate = maxBitRate;
	}
	/**
	 * @return the recordingDevice
	 */
	public Boolean getRecordingDevice() {
		return recordingDevice;
	}
	/**
	 * @param recordingDevice the recordingDevice to set
	 */
	public void setRecordingDevice(Boolean recordingDevice) {
		this.recordingDevice = recordingDevice;
	}
	/**
	 * @return the dtmf
	 */
	public String getDtmf() {
		return dtmf;
	}
	/**
	 * @param dtmf the dtmf to set
	 */
	public void setDtmf(String dtmf) {
		this.dtmf = dtmf;
	}
	/**
	 * @return the audioContentIndex
	 */
	public Integer getAudioContentIndex() {
		return audioContentIndex;
	}
	/**
	 * @param audioContentIndex the audioContentIndex to set
	 */
	public void setAudioContentIndex(Integer audioContentIndex) {
		this.audioContentIndex = audioContentIndex;
	}
	/**
	 * @return the contentIndex
	 */
	public Integer getContentIndex() {
		return contentIndex;
	}
	/**
	 * @param contentIndex the contentIndex to set
	 */
	public void setContentIndex(Integer contentIndex) {
		this.contentIndex = contentIndex;
	}
	/**
	 * @return the camerasCrossed
	 */
	public Boolean getCamerasCrossed() {
		return camerasCrossed;
	}
	/**
	 * @param camerasCrossed the camerasCrossed to set
	 */
	public void setCamerasCrossed(Boolean camerasCrossed) {
		this.camerasCrossed = camerasCrossed;
	}
	/**
	 * @return the txAspectRatio
	 */
	public String getTxAspectRatio() {
		return txAspectRatio;
	}
	/**
	 * @param txAspectRatio the txAspectRatio to set
	 */
	public void setTxAspectRatio(String txAspectRatio) {
		this.txAspectRatio = txAspectRatio;
	}
	/**
	 * @return the autoReconnect
	 */
	public Boolean getAutoReconnect() {
		return autoReconnect;
	}
	/**
	 * @param autoReconnect the autoReconnect to set
	 */
	public void setAutoReconnect(Boolean autoReconnect) {
		this.autoReconnect = autoReconnect;
	}
	/**
	 * @return the alwaysReconnect
	 */
	public Boolean getAlwaysReconnect() {
		return alwaysReconnect;
	}
	/**
	 * @param alwaysReconnect the alwaysReconnect to set
	 */
	public void setAlwaysReconnect(Boolean alwaysReconnect) {
		this.alwaysReconnect = alwaysReconnect;
	}
	/**
	 * @return the deferConnect
	 */
	public Boolean getDeferConnect() {
		return deferConnect;
	}
	/**
	 * @param deferConnect the deferConnect to set
	 */
	public void setDeferConnect(Boolean deferConnect) {
		this.deferConnect = deferConnect;
	}
	/**
	 * @return the autoDisconnect
	 */
	public Boolean getAutoDisconnect() {
		return autoDisconnect;
	}
	/**
	 * @param autoDisconnect the autoDisconnect to set
	 */
	public void setAutoDisconnect(Boolean autoDisconnect) {
		this.autoDisconnect = autoDisconnect;
	}
	/**
	 * @return the defaultLayoutSingleScreen
	 */
	public String getDefaultLayoutSingleScreen() {
		return defaultLayoutSingleScreen;
	}
	/**
	 * @param defaultLayoutSingleScreen the defaultLayoutSingleScreen to set
	 */
	public void setDefaultLayoutSingleScreen(String defaultLayoutSingleScreen) {
		this.defaultLayoutSingleScreen = defaultLayoutSingleScreen;
	}
	/**
	 * @return the defaultLayoutMultiScreen
	 */
	public String getDefaultLayoutMultiScreen() {
		return defaultLayoutMultiScreen;
	}
	/**
	 * @param defaultLayoutMultiScreen the defaultLayoutMultiScreen to set
	 */
	public void setDefaultLayoutMultiScreen(String defaultLayoutMultiScreen) {
		this.defaultLayoutMultiScreen = defaultLayoutMultiScreen;
	}
	/**
	 * @return the forceDefaultLayout
	 */
	public Boolean getForceDefaultLayout() {
		return forceDefaultLayout;
	}
	/**
	 * @param forceDefaultLayout the forceDefaultLayout to set
	 */
	public void setForceDefaultLayout(Boolean forceDefaultLayout) {
		this.forceDefaultLayout = forceDefaultLayout;
	}
	/**
	 * @return the automaticGainControl
	 */
	public Boolean getAutomaticGainControl() {
		return automaticGainControl;
	}
	/**
	 * @param automaticGainControl the automaticGainControl to set
	 */
	public void setAutomaticGainControl(Boolean automaticGainControl) {
		this.automaticGainControl = automaticGainControl;
	}
	/**
	 * @return the allowStarSixMuting
	 */
	public Boolean getAllowStarSixMuting() {
		return allowStarSixMuting;
	}
	/**
	 * @param allowStarSixMuting the allowStarSixMuting to set
	 */
	public void setAllowStarSixMuting(Boolean allowStarSixMuting) {
		this.allowStarSixMuting = allowStarSixMuting;
	}
	/**
	 * @return the participantGUID
	 */
	public String getParticipantGUID() {
		return participantGUID;
	}
	/**
	 * @param participantGUID the participantGUID to set
	 */
	public void setParticipantGUID(String participantGUID) {
		this.participantGUID = participantGUID;
	}
	/**
	 * @return the participantID
	 */
	public Integer getParticipantID() {
		return participantID;
	}
	/**
	 * @param participantID the participantID to set
	 */
	public void setParticipantID(Integer participantID) {
		this.participantID = participantID;
	}
	/**
	 * @return the txAudioMute
	 */
	public Boolean getTxAudioMute() {
		return txAudioMute;
	}
	/**
	 * @param txAudioMute the txAudioMute to set
	 */
	public void setTxAudioMute(Boolean txAudioMute) {
		this.txAudioMute = txAudioMute;
	}
	/**
	 * @return the rxAudioMute
	 */
	public Boolean getRxAudioMute() {
		return rxAudioMute;
	}
	/**
	 * @param rxAudioMute the rxAudioMute to set
	 */
	public void setRxAudioMute(Boolean rxAudioMute) {
		this.rxAudioMute = rxAudioMute;
	}
	/**
	 * @return the txVideoMute
	 */
	public Boolean getTxVideoMute() {
		return txVideoMute;
	}
	/**
	 * @param txVideoMute the txVideoMute to set
	 */
	public void setTxVideoMute(Boolean txVideoMute) {
		this.txVideoMute = txVideoMute;
	}
	/**
	 * @return the rxVideoMute
	 */
	public Boolean getRxVideoMute() {
		return rxVideoMute;
	}
	/**
	 * @param rxVideoMute the rxVideoMute to set
	 */
	public void setRxVideoMute(Boolean rxVideoMute) {
		this.rxVideoMute = rxVideoMute;
	}
	/**
	 * @return the isImportant
	 */
	public Boolean getIsImportant() {
		return isImportant;
	}
	/**
	 * @param isImportant the isImportant to set
	 */
	public void setIsImportant(Boolean isImportant) {
		this.isImportant = isImportant;
	}
	/**
	 * @return the enumerateID
	 */
	public String getEnumerateID() {
		return enumerateID;
	}
	/**
	 * @param enumerateID the enumerateID to set
	 */
	public void setEnumerateID(String enumerateID) {
		this.enumerateID = enumerateID;
	}
	
}
