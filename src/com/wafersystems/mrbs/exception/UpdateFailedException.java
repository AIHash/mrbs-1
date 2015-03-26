package com.wafersystems.mrbs.exception;

public class UpdateFailedException extends BaseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8280852855015965989L;

	public UpdateFailedException(){
		super();
	}
	
	public UpdateFailedException(String message){
		super(UpdateFailedException.class, message);
	}
	
}
