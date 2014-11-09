package com.sankar.optparse;

import com.google.common.base.Objects;

public class Option {
	
	private Character flag;
	private String flagExpansion;
	private String helpText;
	private boolean acceptsArgument;
	
	public Option(Character flag, String flagExpansion, boolean acceptsArgument) {
		this(flag, flagExpansion, "", acceptsArgument);
	}
	
	public Option(Character flag, String flagExpansion, String helpText, boolean acceptsArgument) {
		this.flag = flag;
		this.flagExpansion = flagExpansion;
		this.helpText = helpText;
		this.acceptsArgument = acceptsArgument;
	}
	
	protected Option(Option o) {
		this(o.flag, o.flagExpansion, o.helpText, o.acceptsArgument);
	}
	
	public Character getFlag() {
		return flag;
	}
	
	public String getExpansion() {
		return flagExpansion;
	}
	
	public String getHelpText() {
		return helpText;
	}
	
	public boolean requiresArgument() {
		return acceptsArgument;
	}
	
	public String getUsageString() {
		if (flag == null)
			return acceptsArgument ? String.format("Usage: -%c argument", flag) : String.format("Usage: -%c", flag);
		else
			return acceptsArgument ? String.format("Usage: --%s argument", flagExpansion) : String.format("Usage: -%s", flagExpansion);
	}
	
	public String getValue() {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == null) return false;
		if (getClass() != o.getClass()) return false;
		
		final Option other = (Option)o; 
		
		return Objects.equal(flag, other.flag) &&
				Objects.equal(flagExpansion, other.flagExpansion) &&
				Objects.equal(helpText, other.helpText) &&
				acceptsArgument == other.acceptsArgument;
	}
	
	@Override
	public int hashCode() {
		return Objects.hashCode(flag, flagExpansion, helpText, acceptsArgument);
	}

}
