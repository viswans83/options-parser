package com.sankar.optparse;

@SuppressWarnings("serial")
public class ArgumentExpectedException extends ParseException {
	
	public ArgumentExpectedException(Option opt) {
		super(opt.getUsageString());
	}

}
