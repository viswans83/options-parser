package com.sankar.optparse;

public class ParsedOption extends Option {
	
	private String value;
	
	public ParsedOption(Option o, String value) {
		super(o);
		this.value = value;
	}
	
	@Override
	public String getValue() {
		return value;
	}

}
