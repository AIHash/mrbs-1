package com.wafersystems.tcs.vo;

public class WatchableMovieVO {
	private int ClipStart; 
	private int ClipEnd; 
	private String Format; 
	private String Quality; 
	private boolean OfflineTranscoded; 
	private int TotalBandwidth; 
	private String MainURL; 
	private int MainWidth; 
	private int MainHeight; 
	private String DualURL; 
	private int DualWidth; 
	private int DualHeight;
	
	/**
	 * 
	 */
	public WatchableMovieVO() {
		super();
	}

	/**
	 * @return the clipStart
	 */
	public int getClipStart() {
		return ClipStart;
	}

	/**
	 * @param clipStart the clipStart to set
	 */
	public void setClipStart(int clipStart) {
		ClipStart = clipStart;
	}

	/**
	 * @return the clipEnd
	 */
	public int getClipEnd() {
		return ClipEnd;
	}

	/**
	 * @param clipEnd the clipEnd to set
	 */
	public void setClipEnd(int clipEnd) {
		ClipEnd = clipEnd;
	}

	/**
	 * @return the format
	 */
	public String getFormat() {
		return Format;
	}

	/**
	 * @param format the format to set
	 */
	public void setFormat(String format) {
		Format = format;
	}

	/**
	 * @return the quality
	 */
	public String getQuality() {
		return Quality;
	}

	/**
	 * @param quality the quality to set
	 */
	public void setQuality(String quality) {
		Quality = quality;
	}

	/**
	 * @return the offlineTranscoded
	 */
	public boolean isOfflineTranscoded() {
		return OfflineTranscoded;
	}

	/**
	 * @param offlineTranscoded the offlineTranscoded to set
	 */
	public void setOfflineTranscoded(boolean offlineTranscoded) {
		OfflineTranscoded = offlineTranscoded;
	}

	/**
	 * @return the totalBandwidth
	 */
	public int getTotalBandwidth() {
		return TotalBandwidth;
	}

	/**
	 * @param totalBandwidth the totalBandwidth to set
	 */
	public void setTotalBandwidth(int totalBandwidth) {
		TotalBandwidth = totalBandwidth;
	}

	/**
	 * @return the mainURL
	 */
	public String getMainURL() {
		return MainURL;
	}

	/**
	 * @param mainURL the mainURL to set
	 */
	public void setMainURL(String mainURL) {
		MainURL = mainURL;
	}

	/**
	 * @return the mainWidth
	 */
	public int getMainWidth() {
		return MainWidth;
	}

	/**
	 * @param mainWidth the mainWidth to set
	 */
	public void setMainWidth(int mainWidth) {
		MainWidth = mainWidth;
	}

	/**
	 * @return the mainHeight
	 */
	public int getMainHeight() {
		return MainHeight;
	}

	/**
	 * @param mainHeight the mainHeight to set
	 */
	public void setMainHeight(int mainHeight) {
		MainHeight = mainHeight;
	}

	/**
	 * @return the dualURL
	 */
	public String getDualURL() {
		return DualURL;
	}

	/**
	 * @param dualURL the dualURL to set
	 */
	public void setDualURL(String dualURL) {
		DualURL = dualURL;
	}

	/**
	 * @return the dualWidth
	 */
	public int getDualWidth() {
		return DualWidth;
	}

	/**
	 * @param dualWidth the dualWidth to set
	 */
	public void setDualWidth(int dualWidth) {
		DualWidth = dualWidth;
	}

	/**
	 * @return the dualHeight
	 */
	public int getDualHeight() {
		return DualHeight;
	}

	/**
	 * @param dualHeight the dualHeight to set
	 */
	public void setDualHeight(int dualHeight) {
		DualHeight = dualHeight;
	} 
	
}
