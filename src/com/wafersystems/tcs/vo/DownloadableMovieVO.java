package com.wafersystems.tcs.vo;

public class DownloadableMovieVO {
	private String Display; 
	private String URL; 
	private int Width; 
	private int Height;
	
	public DownloadableMovieVO() {
		super();
	}
	/**
	 * @return the display
	 */
	public String getDisplay() {
		return Display;
	}
	/**
	 * @param display the display to set
	 */
	public void setDisplay(String display) {
		Display = display;
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
	 * @return the width
	 */
	public int getWidth() {
		return Width;
	}
	/**
	 * @param width the width to set
	 */
	public void setWidth(int width) {
		Width = width;
	}
	/**
	 * @return the height
	 */
	public int getHeight() {
		return Height;
	}
	/**
	 * @param height the height to set
	 */
	public void setHeight(int height) {
		Height = height;
	} 
	
	
}
