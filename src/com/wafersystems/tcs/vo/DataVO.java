package com.wafersystems.tcs.vo;

public class DataVO {
	
	private String AliasID = "";
	private String Name = "";
	private boolean SystemAlias = false;
	private String Owner = "";
	private String E164Alias = "";
	private String H323Alias = "";
	private String SipURI = "";
	private String SipDisplayName = "";
	private boolean ShowCountdown = false;
	private boolean SendEmail = false;
	private String EmailAddress = "";
	private String RecordingTitle = "";
	private String RecordingDescription = "";
	private String RecordingSpeaker = "";
	private String RecordingKeywords = "";
	private String RecordingCopyright = "";
	private String RecordingLocation = "";
	private String RecordingCategory = "";
	private String RecordingOwner = "";
	private boolean AutomaticallyPublishRecordings = false;
	private boolean EnableEndpointPlaybackForRecordings = false;
	private boolean AllUsersAccess = false;
	private String [] Users;
	private String [] Editors;
	private String Password = "";
	private String RecordingPin = "";
	
	public DataVO() {
		super();
	}
	/**
	 * @return the aliasID
	 */
	public String getAliasID() {
		return AliasID;
	}
	/**
	 * @param aliasID the aliasID to set
	 */
	public void setAliasID(String aliasID) {
		AliasID = aliasID;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return Name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		Name = name;
	}
	/**
	 * @return the systemAlias
	 */
	public boolean isSystemAlias() {
		return SystemAlias;
	}
	/**
	 * @param systemAlias the systemAlias to set
	 */
	public void setSystemAlias(boolean systemAlias) {
		SystemAlias = systemAlias;
	}
	/**
	 * @return the owner
	 */
	public String getOwner() {
		return Owner;
	}
	/**
	 * @param owner the owner to set
	 */
	public void setOwner(String owner) {
		Owner = owner;
	}
	/**
	 * @return the e164Alias
	 */
	public String getE164Alias() {
		return E164Alias;
	}
	/**
	 * @param e164Alias the e164Alias to set
	 */
	public void setE164Alias(String e164Alias) {
		E164Alias = e164Alias;
	}
	/**
	 * @return the h323Alias
	 */
	public String getH323Alias() {
		return H323Alias;
	}
	/**
	 * @param h323Alias the h323Alias to set
	 */
	public void setH323Alias(String h323Alias) {
		H323Alias = h323Alias;
	}
	/**
	 * @return the sipURI
	 */
	public String getSipURI() {
		return SipURI;
	}
	/**
	 * @param sipURI the sipURI to set
	 */
	public void setSipURI(String sipURI) {
		SipURI = sipURI;
	}
	/**
	 * @return the sipDisplayName
	 */
	public String getSipDisplayName() {
		return SipDisplayName;
	}
	/**
	 * @param sipDisplayName the sipDisplayName to set
	 */
	public void setSipDisplayName(String sipDisplayName) {
		SipDisplayName = sipDisplayName;
	}
	/**
	 * @return the showCountdown
	 */
	public boolean isShowCountdown() {
		return ShowCountdown;
	}
	/**
	 * @param showCountdown the showCountdown to set
	 */
	public void setShowCountdown(boolean showCountdown) {
		ShowCountdown = showCountdown;
	}
	/**
	 * @return the sendEmail
	 */
	public boolean isSendEmail() {
		return SendEmail;
	}
	/**
	 * @param sendEmail the sendEmail to set
	 */
	public void setSendEmail(boolean sendEmail) {
		SendEmail = sendEmail;
	}
	/**
	 * @return the emailAddress
	 */
	public String getEmailAddress() {
		return EmailAddress;
	}
	/**
	 * @param emailAddress the emailAddress to set
	 */
	public void setEmailAddress(String emailAddress) {
		EmailAddress = emailAddress;
	}
	/**
	 * @return the recordingTitle
	 */
	public String getRecordingTitle() {
		return RecordingTitle;
	}
	/**
	 * @param recordingTitle the recordingTitle to set
	 */
	public void setRecordingTitle(String recordingTitle) {
		RecordingTitle = recordingTitle;
	}
	/**
	 * @return the recordingDescription
	 */
	public String getRecordingDescription() {
		return RecordingDescription;
	}
	/**
	 * @param recordingDescription the recordingDescription to set
	 */
	public void setRecordingDescription(String recordingDescription) {
		RecordingDescription = recordingDescription;
	}
	/**
	 * @return the recordingSpeaker
	 */
	public String getRecordingSpeaker() {
		return RecordingSpeaker;
	}
	/**
	 * @param recordingSpeaker the recordingSpeaker to set
	 */
	public void setRecordingSpeaker(String recordingSpeaker) {
		RecordingSpeaker = recordingSpeaker;
	}
	/**
	 * @return the recordingKeywords
	 */
	public String getRecordingKeywords() {
		return RecordingKeywords;
	}
	/**
	 * @param recordingKeywords the recordingKeywords to set
	 */
	public void setRecordingKeywords(String recordingKeywords) {
		RecordingKeywords = recordingKeywords;
	}
	/**
	 * @return the recordingCopyright
	 */
	public String getRecordingCopyright() {
		return RecordingCopyright;
	}
	/**
	 * @param recordingCopyright the recordingCopyright to set
	 */
	public void setRecordingCopyright(String recordingCopyright) {
		RecordingCopyright = recordingCopyright;
	}
	/**
	 * @return the recordingLocation
	 */
	public String getRecordingLocation() {
		return RecordingLocation;
	}
	/**
	 * @param recordingLocation the recordingLocation to set
	 */
	public void setRecordingLocation(String recordingLocation) {
		RecordingLocation = recordingLocation;
	}
	/**
	 * @return the recordingCategory
	 */
	public String getRecordingCategory() {
		return RecordingCategory;
	}
	/**
	 * @param recordingCategory the recordingCategory to set
	 */
	public void setRecordingCategory(String recordingCategory) {
		RecordingCategory = recordingCategory;
	}
	/**
	 * @return the recordingOwner
	 */
	public String getRecordingOwner() {
		return RecordingOwner;
	}
	/**
	 * @param recordingOwner the recordingOwner to set
	 */
	public void setRecordingOwner(String recordingOwner) {
		RecordingOwner = recordingOwner;
	}
	
	/**
	 * @return the automaticallyPublishRecordings
	 */
	public boolean isAutomaticallyPublishRecordings() {
		return AutomaticallyPublishRecordings;
	}
	/**
	 * @param automaticallyPublishRecordings the automaticallyPublishRecordings to set
	 */
	public void setAutomaticallyPublishRecordings(
			boolean automaticallyPublishRecordings) {
		AutomaticallyPublishRecordings = automaticallyPublishRecordings;
	}
	/**
	 * @return the enableEndpointPlaybackForRecordings
	 */
	public boolean isEnableEndpointPlaybackForRecordings() {
		return EnableEndpointPlaybackForRecordings;
	}
	/**
	 * @param enableEndpointPlaybackForRecordings the enableEndpointPlaybackForRecordings to set
	 */
	public void setEnableEndpointPlaybackForRecordings(
			boolean enableEndpointPlaybackForRecordings) {
		EnableEndpointPlaybackForRecordings = enableEndpointPlaybackForRecordings;
	}
	/**
	 * @return the allUsersAccess
	 */
	public boolean isAllUsersAccess() {
		return AllUsersAccess;
	}
	/**
	 * @param allUsersAccess the allUsersAccess to set
	 */
	public void setAllUsersAccess(boolean allUsersAccess) {
		AllUsersAccess = allUsersAccess;
	}
	/**
	 * @return the users
	 */
	public String[] getUsers() {
		return Users;
	}
	/**
	 * @param users the users to set
	 */
	public void setUsers(String[] users) {
		Users = users;
	}
	/**
	 * @return the editors
	 */
	public String[] getEditors() {
		return Editors;
	}
	/**
	 * @param editors the editors to set
	 */
	public void setEditors(String[] editors) {
		Editors = editors;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return Password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		Password = password;
	}
	/**
	 * @return the recordingPin
	 */
	public String getRecordingPin() {
		return RecordingPin;
	}
	/**
	 * @param recordingPin the recordingPin to set
	 */
	public void setRecordingPin(String recordingPin) {
		RecordingPin = recordingPin;
	}
	
}
