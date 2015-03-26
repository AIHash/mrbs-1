package com.wafersystems.mcu.conference.model;

import java.io.Serializable;

public class Conference implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String authenticationUser;
	private String authenticationPassword;

	/**------------------------------------------------ 创建会议输入 ------------------------------------------------**/
	
	/**
	 * 会议名称/主题
	 * 80 length
	 */
	private String conferenceName;
	/**
	 * 定义所有参与人挂断离开会议之后，此会议是否仍然存留。
	 * 存留的会议会被保存在配置文件中，当设备重启之后，会议仍然存在。
	 * true：会议将会被存留，不管参与人是否离开。
	 * false：所有参与人离开30秒之后，会议将被删除，或者当<code>duration</code>会议持续时间到期之后。
	 */
	private Boolean persistent;
	
	/**
	 * 会议的持续时间（秒），如果<code>persistent</code>是<tt>true</tt>，则此项不可用
	 */
	private Integer duration;
	/**
	 * （已过时），被<code>persistent</code>所代替
	 */
	@Deprecated
	private Boolean permanent;
	/**
	 * 定义会议是否被锁定，参与人/终端设备不能加入一个已经被锁定的会议，除非会议主动邀请。
	 * true：会议被锁定。
	 * false：会议没有锁定。
	 */
	private Boolean locked;
	/**
	 * 从现在开始一段时间（秒）内，直到会议锁定到期。
	 * 要求<code>locked</code>的值是<tt>true</tt>，否则将被忽略
	 */
	private Integer lockDuration;
	/**
	 * 会议数字ID，被用来使用H.323或者SIP注册，以拨号连接会议
	 * 80 length
	 */
	private String numericID;
	/**
	 * 定义<code>numericID</code>使用H.323协议进行注册
	 */
	private Boolean registerWithGatekeeper;
	/**
	 * 定义<code>numericID</code>使用SIP协议进行注册
	 */
	private Boolean registerWithSIPRegistrar;
	/**
	 * The address that Cisco TelePresence System T3 systems use to make API calls to the TelePresence Server.
	 * 格式： [<protocol>://]<address>[:<port>]
	 *  for example, http://mytps:80. 
	 */
	private String tsURI;
	/**
	 * 定义是否会议允许内容贡献（录播会议内容）。
	 * 这个参数定义了会议内容是否可以以任何内容协议的方式贡献出来，不仅限于 H.239 协议。
	 */
	private Boolean h239ContributionEnabled;
	/**
	 * 定义会议是否显示在大厅屏幕上。
	 */
	private Boolean useLobbyScreen;
	/**
	 * 大厅屏幕信息。
	 */
	private String lobbyMessage;
	/**
	 * 定义是否发送“会议即将结束”的警告。
	 */
	private Boolean useWarning;
	
	/**
	 * 定义是否启用<code>audioPortLimit</code>
	 */
	private Boolean audioPortLimitSet;
	/**
	 * 这次会议可能使用到的音频端口的限制数量
	 */
	private Integer audioPortLimit;
	/**
	 * 定义是否启用<code>videoPortLimit</code>
	 */
	private Boolean videoPortLimitSet;
	/**
	 * 这次会议可能使用到的视频端口的限制数量
	 */
	private Integer videoPortLimit;
	/**
	 * 定义是否启用了自动增益控制。如果没有指定,那么会议使用的是默认的。
	 */
	private Boolean automaticGainControl;
	/**
	 * 定义这个会议是否需要加密
	 * true：会议要求加密
	 * false：会议可选加密
	 */
	private Boolean encryptionRequired;
	/**
	 * 会议的 PIN 码，如果关联一个会议，则它是一个数值型的数字，用来输入以加入一个会议。
	 * 参考校验码
	 */
	private String pin;
	
	/**
	 * To set up one table mode
	 * 0: OneTableMode off
	 * 1: 4 person OneTableMode
	 */
	private Integer oneTableMode;
	
	
	/**------------------------------------------------ 创建会议返回 ------------------------------------------------**/
	
	/**
	 * 这个会议的全局唯一标示
	 */
	private String conferenceGUID;
	/**
	 * 会议标示符（已过时），被<code>conferenceGUID</code>所代替
	 */
	@Deprecated
	private Integer conferenceID;
	
	/**------------------------------------------------ 枚举会议返回 ------------------------------------------------**/
	
	/**
	 * 这个会议是否活跃中
	 */
	private Boolean active;

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
	 * @return the conferenceName
	 */
	public String getConferenceName() {
		return conferenceName;
	}

	/**
	 * @param conferenceName the conferenceName to set
	 */
	public void setConferenceName(String conferenceName) {
		this.conferenceName = conferenceName;
	}

	/**
	 * @return the persistent
	 */
	public Boolean getPersistent() {
		return persistent;
	}

	/**
	 * @param persistent the persistent to set
	 */
	public void setPersistent(Boolean persistent) {
		this.persistent = persistent;
	}

	/**
	 * @return the permanent
	 */
	public Boolean getPermanent() {
		return permanent;
	}

	/**
	 * @param permanent the permanent to set
	 */
	public void setPermanent(Boolean permanent) {
		this.permanent = permanent;
	}

	/**
	 * @return the locked
	 */
	public Boolean getLocked() {
		return locked;
	}

	/**
	 * @param locked the locked to set
	 */
	public void setLocked(Boolean locked) {
		this.locked = locked;
	}

	/**
	 * @return the lockDuration
	 */
	public Integer getLockDuration() {
		return lockDuration;
	}

	/**
	 * @param lockDuration the lockDuration to set
	 */
	public void setLockDuration(Integer lockDuration) {
		this.lockDuration = lockDuration;
	}

	/**
	 * @return the numericID
	 */
	public String getNumericID() {
		return numericID;
	}

	/**
	 * @param numericID the numericID to set
	 */
	public void setNumericID(String numericID) {
		this.numericID = numericID;
	}

	/**
	 * @return the registerWithGatekeeper
	 */
	public Boolean getRegisterWithGatekeeper() {
		return registerWithGatekeeper;
	}

	/**
	 * @param registerWithGatekeeper the registerWithGatekeeper to set
	 */
	public void setRegisterWithGatekeeper(Boolean registerWithGatekeeper) {
		this.registerWithGatekeeper = registerWithGatekeeper;
	}

	/**
	 * @return the registerWithSIPRegistrar
	 */
	public Boolean getRegisterWithSIPRegistrar() {
		return registerWithSIPRegistrar;
	}

	/**
	 * @param registerWithSIPRegistrar the registerWithSIPRegistrar to set
	 */
	public void setRegisterWithSIPRegistrar(Boolean registerWithSIPRegistrar) {
		this.registerWithSIPRegistrar = registerWithSIPRegistrar;
	}

	/**
	 * @return the tsURI
	 */
	public String getTsURI() {
		return tsURI;
	}

	/**
	 * @param tsURI the tsURI to set
	 */
	public void setTsURI(String tsURI) {
		this.tsURI = tsURI;
	}

	/**
	 * @return the h239ContributionEnabled
	 */
	public Boolean getH239ContributionEnabled() {
		return h239ContributionEnabled;
	}

	/**
	 * @param h239ContributionEnabled the h239ContributionEnabled to set
	 */
	public void setH239ContributionEnabled(Boolean h239ContributionEnabled) {
		this.h239ContributionEnabled = h239ContributionEnabled;
	}

	/**
	 * @return the useLobbyScreen
	 */
	public Boolean getUseLobbyScreen() {
		return useLobbyScreen;
	}

	/**
	 * @param useLobbyScreen the useLobbyScreen to set
	 */
	public void setUseLobbyScreen(Boolean useLobbyScreen) {
		this.useLobbyScreen = useLobbyScreen;
	}

	/**
	 * @return the lobbyMessage
	 */
	public String getLobbyMessage() {
		return lobbyMessage;
	}

	/**
	 * @param lobbyMessage the lobbyMessage to set
	 */
	public void setLobbyMessage(String lobbyMessage) {
		this.lobbyMessage = lobbyMessage;
	}

	/**
	 * @return the useWarning
	 */
	public Boolean getUseWarning() {
		return useWarning;
	}

	/**
	 * @param useWarning the useWarning to set
	 */
	public void setUseWarning(Boolean useWarning) {
		this.useWarning = useWarning;
	}

	/**
	 * @return the audioPortLimit
	 */
	public Integer getAudioPortLimit() {
		return audioPortLimit;
	}

	/**
	 * @param audioPortLimit the audioPortLimit to set
	 */
	public void setAudioPortLimit(Integer audioPortLimit) {
		this.audioPortLimit = audioPortLimit;
	}

	/**
	 * @return the videoPortLimit
	 */
	public Integer getVideoPortLimit() {
		return videoPortLimit;
	}

	/**
	 * @param videoPortLimit the videoPortLimit to set
	 */
	public void setVideoPortLimit(Integer videoPortLimit) {
		this.videoPortLimit = videoPortLimit;
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
	 * @return the encryptionRequired
	 */
	public Boolean getEncryptionRequired() {
		return encryptionRequired;
	}

	/**
	 * @param encryptionRequired the encryptionRequired to set
	 */
	public void setEncryptionRequired(Boolean encryptionRequired) {
		this.encryptionRequired = encryptionRequired;
	}

	/**
	 * @return the pin
	 */
	public String getPin() {
		return pin;
	}

	/**
	 * @param pin the pin to set
	 */
	public void setPin(String pin) {
		this.pin = pin;
	}

	/**
	 * @return the conferenceGUID
	 */
	public String getConferenceGUID() {
		return conferenceGUID;
	}

	/**
	 * @param conferenceGUID the conferenceGUID to set
	 */
	public void setConferenceGUID(String conferenceGUID) {
		this.conferenceGUID = conferenceGUID;
	}

	/**
	 * @return the conferenceID
	 */
	public Integer getConferenceID() {
		return conferenceID;
	}

	/**
	 * @param conferenceID the conferenceID to set
	 */
	public void setConferenceID(Integer conferenceID) {
		this.conferenceID = conferenceID;
	}

	/**
	 * @return the active
	 */
	public Boolean getActive() {
		return active;
	}

	/**
	 * @param active the active to set
	 */
	public void setActive(Boolean active) {
		this.active = active;
	}

	/**
	 * @return the duration
	 */
	public Integer getDuration() {
		return duration;
	}

	/**
	 * @param duration the duration to set
	 */
	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	/**
	 * @return the oneTableMode
	 */
	public Integer getOneTableMode() {
		return oneTableMode;
	}

	/**
	 * @param oneTableMode the oneTableMode to set
	 */
	public void setOneTableMode(Integer oneTableMode) {
		this.oneTableMode = oneTableMode;
	}

	/**
	 * @return the audioPortLimitSet
	 */
	public Boolean getAudioPortLimitSet() {
		return audioPortLimitSet;
	}

	/**
	 * @param audioPortLimitSet the audioPortLimitSet to set
	 */
	public void setAudioPortLimitSet(Boolean audioPortLimitSet) {
		this.audioPortLimitSet = audioPortLimitSet;
	}

	/**
	 * @return the videoPortLimitSet
	 */
	public Boolean getVideoPortLimitSet() {
		return videoPortLimitSet;
	}

	/**
	 * @param videoPortLimitSet the videoPortLimitSet to set
	 */
	public void setVideoPortLimitSet(Boolean videoPortLimitSet) {
		this.videoPortLimitSet = videoPortLimitSet;
	}
	
}
