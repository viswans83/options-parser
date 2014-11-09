package com.sankar.optparse;

@SuppressWarnings("serial")
public class UnsupportedOptionException extends ParseException {
	
	public UnsupportedOptionException(char c) {
		super("-" + c);
	}
	
	public UnsupportedOptionException(String s) {
		super("--" + s);
	}

}
