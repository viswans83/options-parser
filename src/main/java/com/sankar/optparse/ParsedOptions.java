package com.sankar.optparse;

import java.util.Set;

public class ParsedOptions extends Options {
	
	private String[] arguments;
	
	public ParsedOptions(Set<Option> optionsFound, String[] arguments) {
		super(optionsFound);
		this.arguments = arguments;
	}

	public boolean contains(char c) {
		return supports(c);
	}
	
	public boolean contains(String s) {
		return supports(s);
	}
	
	public String getValue(char c) {
		return get(c).getValue();
	}
	
	public String getValue(String s) {
		return get(s).getValue();
	}
	
	public String[] getArguments() {
		return arguments;
	}

}
