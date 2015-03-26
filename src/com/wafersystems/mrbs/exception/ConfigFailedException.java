package com.wafersystems.mrbs.exception;

public class ConfigFailedException extends BaseException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5631647952249712856L;

	public ConfigFailedException(){
		super();
	}
	
	public ConfigFailedException(String message){
		super(ConfigFailedException.class, message);
	}

}
