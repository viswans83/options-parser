package com.sankar.optparse;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

public class OptionsParser {
	
	private Options opts;  

	public OptionsParser(Options opts) {
		this.opts = opts;
	}

	public ParsedOptions parse(String input) {
		return new Parser(input).parse();
	}
	
	private class Parser {
		
		private StringTokenizer tokenizer;
		
		private Set<Option> optionsFound = new HashSet<>();
		private List<String> arguments = new ArrayList<>();
		
		public Parser(String input) {
			this.tokenizer = new StringTokenizer(input);
		}
		
		public ParsedOptions parse() {
			while(tokenizer.hasMoreTokens()) {
				handleToken(tokenizer.nextToken());
			}
			
			return new ParsedOptions(optionsFound, arguments.toArray(new String[0]));
		}

		private void handleToken(String token) {
			if (token.startsWith("--"))
				handleExpansion(token.substring(2));
			
			else if (token.startsWith("-"))
				handleFlags(token.substring(1));
			
			else
				arguments.add(token);
		}

		private void handleExpansion(String name) {
			if (!opts.supports(name)) throw new UnsupportedOptionException(name);
			handleOption(opts.get(name), true);
		}
		
		private void handleFlags(String flags) {
			for(int indx = 0; indx < flags.length(); indx++) {
				if (!opts.supports(flags.charAt(indx))) throw new UnsupportedOptionException(flags.charAt(indx));
				handleOption(opts.get(flags.charAt(indx)), indx == flags.length() - 1);
			}	
		}
		
		private void handleOption(Option opt, boolean argAllowed) {
			if (!opt.requiresArgument())
				optionsFound.add(opt);
			
			else if (argAllowed) {
				
				if (tokenizer.hasMoreTokens())
					optionsFound.add(new ParsedOption(opt, tokenizer.nextToken()));
				
				else
					throw new ArgumentExpectedException(opt);
			}
			
			else
				throw new ParseException("Illegal syntax");
		}
		
	}

}
