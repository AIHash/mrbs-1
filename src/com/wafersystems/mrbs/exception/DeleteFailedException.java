package com.wafersystems.mrbs.exception;

public class DeleteFailedException extends BaseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8280852855015965989L;

	public DeleteFailedException(){
		super();
	}
	
	public DeleteFailedException(String message){
		super(DeleteFailedException.class, message);
	}
	
}
