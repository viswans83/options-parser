package com.sankar.optparse;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Options {
	
	private Map<Object, Option> optionMapping = new HashMap<>(); 
	
	public Options(Set<Option> opts) {
		for(Option o : opts) {
			prepareMapping(o);
		}
	}
	
	public static Builder newWith() {
		return new Builder();
	}
	
	private void prepareMapping(Option opt) {
		if (optionMapping.containsKey(opt.getFlag()) || optionMapping.containsKey(opt.getExpansion()))
			throw new IllegalArgumentException("Duplicate option found");
		
		if (opt.getFlag() != null) optionMapping.put(opt.getFlag(), opt);
		if (opt.getExpansion() != null) optionMapping.put(opt.getExpansion(), opt);
	}
	
	public boolean supports(char flag) {
		return optionMapping.containsKey(flag);
	}
	
	public boolean supports(String expansion) {
		return optionMapping.containsKey(expansion);
	}
	
	public Option get(char flag) {
		return optionMapping.get(flag);
	}
	
	public Option get(String expansion) {
		return optionMapping.get(expansion);
	}
	
	public String getHelpText(char flag) {
		if (!supports(flag)) throw new UnsupportedOperationException("Unrecognized option");
		return optionMapping.get(flag).getHelpText();
	}
	
	public String getHelpText(String expansion) {
		if (!supports(expansion)) throw new UnsupportedOperationException("Unrecognized option");
		return optionMapping.get(expansion).getHelpText();
	}
	
	public static class Builder {
		
		private Option lastAdded;
		private Set<Object> encounters = new HashSet<>();
		private Set<Option> opts = new HashSet<>();
		
		public Options build() {
			return new Options(opts);
		}

		public Builder option(char f) {
			register(new Option(f, null, false));
			return this;
		}
		
		public Builder option(String expansion) {
			register(new Option(null, expansion, false));
			return this;
		}

		public Builder option(char f, String expansion) {
			register(new Option(f, expansion, false));
			return this;
		}

		public Builder optionWithArgument(char f) {
			register(new Option(f, null, true));
			return this;
		}
		
		public Builder optionWithArgument(String flagExpansion) {
			register(new Option(null, flagExpansion, true));
			return this;
		}
		
		public Builder optionWithArgument(char f, String expansion) {
			register(new Option(f, expansion, true));
			return this;
		}
		
		public Builder withHelp(String text) {
			if (lastAdded == null)
				throw new IllegalArgumentException("Cannot supply help text to non existing option");
			
			unregister(lastAdded);
			register(new Option(lastAdded.getFlag(), lastAdded.getExpansion(), text, lastAdded.requiresArgument()));
			lastAdded = null;
			
			return this;
		}
		
		private void register(Option opt) {
			if (encounters.contains(opt.getFlag()) || encounters.contains(opt.getExpansion()))
				throw new IllegalArgumentException(String.format("Duplicates not allowed: %s", opt.getFlag() == null ? opt.getExpansion() : opt.getFlag()));
			
			if (opt.getFlag() != null) encounters.add(opt.getFlag());
			if (opt.getExpansion() != null) encounters.add(opt.getExpansion());
			
			opts.add(lastAdded = opt);
		}
		
		private void unregister(Option opt) {
			opts.remove(opt);
			if (opt.getFlag() != null) encounters.remove(opt.getFlag());
			if (opt.getExpansion() != null) encounters.remove(opt.getExpansion());
		}
		
	}	

}
