package com.android.httplib.exception;

public class ClientErrorException extends Exception {

	private static final long serialVersionUID = 1L;

	public ClientErrorException(int statusCode){
		
		super("client error statusCode = "+statusCode);
	}
	
	public ClientErrorException(String info){
		super(info);
	}
}
