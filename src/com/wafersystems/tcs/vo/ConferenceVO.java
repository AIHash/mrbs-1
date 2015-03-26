package com.wafersystems.tcs.vo;

public class ConferenceVO {

	private String ConferenceID; 
	private String Title; 
	private String Description; 
	private String URL; 
	private String Speaker; 
	private String Keywords;  
	private String Copyright; 
	private String Location; 
	private MoviesVO Movies; 
	private int DateTime; 
	private int UpdateTime; 
	private int Duration; 
	private String Owner; 
	private OwnerVO OwnerDetails; 
	private boolean Deleted; 
	private LabelVO[] Labels; 
	private boolean GuestAccess; 
	private boolean HasWatchableMovie; 
	private WatchableMovieVO[][] WatchableMovies; 
	private boolean HasDownloadableMovie; 
	private DownloadableMovieVO[] DownloadableMovies; 
	private boolean HasPending; 
	private int PercentTranscoded; 
	
	public ConferenceVO() {
		super();
	}

	/**
	 * @return the conferenceID
	 */
	public String getConferenceID() {
		return ConferenceID;
	}

	/**
	 * @param conferenceID the conferenceID to set
	 */
	public void setConferenceID(String conferenceID) {
		ConferenceID = conferenceID;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return Title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		Title = title;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return Description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		Description = description;
	}

	/**
	 * @return the uRL
	 */
	public String getURL() {
		return URL;
	}

	/**
	 * @param uRL the uRL to set
	 */
	public void setURL(String uRL) {
		URL = uRL;
	}

	/**
	 * @return the speaker
	 */
	public String getSpeaker() {
		return Speaker;
	}

	/**
	 * @param speaker the speaker to set
	 */
	public void setSpeaker(String speaker) {
		Speaker = speaker;
	}

	/**
	 * @return the keywords
	 */
	public String getKeywords() {
		return Keywords;
	}

	/**
	 * @param keywords the keywords to set
	 */
	public void setKeywords(String keywords) {
		Keywords = keywords;
	}

	/**
	 * @return the copyright
	 */
	public String getCopyright() {
		return Copyright;
	}

	/**
	 * @param copyright the copyright to set
	 */
	public void setCopyright(String copyright) {
		Copyright = copyright;
	}

	/**
	 * @return the location
	 */
	public String getLocation() {
		return Location;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		Location = location;
	}

	/**
	 * @return the movies
	 */
	public MoviesVO getMovies() {
		return Movies;
	}

	/**
	 * @param movies the movies to set
	 */
	public void setMovies(MoviesVO movies) {
		Movies = movies;
	}

	/**
	 * @return the dateTime
	 */
	public int getDateTime() {
		return DateTime;
	}

	/**
	 * @param dateTime the dateTime to set
	 */
	public void setDateTime(int dateTime) {
		DateTime = dateTime;
	}

	/**
	 * @return the updateTime
	 */
	public int getUpdateTime() {
		return UpdateTime;
	}

	/**
	 * @param updateTime the updateTime to set
	 */
	public void setUpdateTime(int updateTime) {
		UpdateTime = updateTime;
	}

	/**
	 * @return the duration
	 */
	public int getDuration() {
		return Duration;
	}

	/**
	 * @param duration the duration to set
	 */
	public void setDuration(int duration) {
		Duration = duration;
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
	 * @return the ownerDetails
	 */
	public OwnerVO getOwnerDetails() {
		return OwnerDetails;
	}

	/**
	 * @param ownerDetails the ownerDetails to set
	 */
	public void setOwnerDetails(OwnerVO ownerDetails) {
		OwnerDetails = ownerDetails;
	}

	/**
	 * @return the deleted
	 */
	public boolean isDeleted() {
		return Deleted;
	}

	/**
	 * @param deleted the deleted to set
	 */
	public void setDeleted(boolean deleted) {
		Deleted = deleted;
	}

	/**
	 * @return the labels
	 */
	public LabelVO[] getLabels() {
		return Labels;
	}

	/**
	 * @param labels the labels to set
	 */
	public void setLabels(LabelVO[] labels) {
		Labels = labels;
	}

	/**
	 * @return the guestAccess
	 */
	public boolean isGuestAccess() {
		return GuestAccess;
	}

	/**
	 * @param guestAccess the guestAccess to set
	 */
	public void setGuestAccess(boolean guestAccess) {
		GuestAccess = guestAccess;
	}

	/**
	 * @return the hasWatchableMovie
	 */
	public boolean isHasWatchableMovie() {
		return HasWatchableMovie;
	}

	/**
	 * @param hasWatchableMovie the hasWatchableMovie to set
	 */
	public void setHasWatchableMovie(boolean hasWatchableMovie) {
		HasWatchableMovie = hasWatchableMovie;
	}

	/**
	 * @return the watchableMovies
	 */
	public WatchableMovieVO[][] getWatchableMovies() {
		return WatchableMovies;
	}

	/**
	 * @param watchableMovies the watchableMovies to set
	 */
	public void setWatchableMovies(WatchableMovieVO[][] watchableMovies) {
		WatchableMovies = watchableMovies;
	}

	/**
	 * @return the hasDownloadableMovie
	 */
	public boolean isHasDownloadableMovie() {
		return HasDownloadableMovie;
	}

	/**
	 * @param hasDownloadableMovie the hasDownloadableMovie to set
	 */
	public void setHasDownloadableMovie(boolean hasDownloadableMovie) {
		HasDownloadableMovie = hasDownloadableMovie;
	}

	/**
	 * @return the downloadableMovies
	 */
	public DownloadableMovieVO[] getDownloadableMovies() {
		return DownloadableMovies;
	}

	/**
	 * @param downloadableMovies the downloadableMovies to set
	 */
	public void setDownloadableMovies(DownloadableMovieVO[] downloadableMovies) {
		DownloadableMovies = downloadableMovies;
	}

	/**
	 * @return the hasPending
	 */
	public boolean isHasPending() {
		return HasPending;
	}

	/**
	 * @param hasPending the hasPending to set
	 */
	public void setHasPending(boolean hasPending) {
		HasPending = hasPending;
	}

	/**
	 * @return the percentTranscoded
	 */
	public int getPercentTranscoded() {
		return PercentTranscoded;
	}

	/**
	 * @param percentTranscoded the percentTranscoded to set
	 */
	public void setPercentTranscoded(int percentTranscoded) {
		PercentTranscoded = percentTranscoded;
	}
	
}
