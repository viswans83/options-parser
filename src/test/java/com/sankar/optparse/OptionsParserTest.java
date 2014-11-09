package com.sankar.optparse;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.sankar.optparse.ArgumentExpectedException;
import com.sankar.optparse.Options;
import com.sankar.optparse.OptionsParser;
import com.sankar.optparse.ParseException;
import com.sankar.optparse.ParsedOptions;

public class OptionsParserTest {
	
	private OptionsParser parser = new OptionsParser(Options.newWith()
			.option('a')
			.option('A')
			.option('b', "bold")
			.option("black")
			.optionWithArgument('c')
			.optionWithArgument("color")
			.optionWithArgument('d',"directory")
			.option('h', "help").withHelp("Show command help")
			.build());
	
	@Test public void
	parses_option_flag() {
		ParsedOptions parsedOpts = parser.parse("-aA");
		
		assertTrue(parsedOpts.contains('a'));
		assertTrue(parsedOpts.contains('A'));
	}
	
	@Test public void
	parses_option_names() {
		ParsedOptions parsedOpts = parser.parse("--bold");
		
		assertTrue(parsedOpts.contains('b'));
		assertTrue(parsedOpts.contains("bold"));
	}
	
	@Test public void
	parses_option_with_argument() {
		ParsedOptions parsedOpts = parser.parse("-d /home");
		
		assertTrue(parsedOpts.contains('d'));
		assertTrue(parsedOpts.contains("directory"));
		assertEquals("/home", parsedOpts.get("directory").getValue());
	}
	
	@Test public void
	parses_multiple_option_types() {
		ParsedOptions parsedOpts = parser.parse("-b --black");
		
		assertTrue(parsedOpts.contains('b'));
		assertTrue(parsedOpts.contains("black"));
	}
	
	@Test public void
	parses_option_flags_with_argument() {
		ParsedOptions parsedOpts = parser.parse("-bc 123");
		
		assertTrue(parsedOpts.contains('b'));
		assertTrue(parsedOpts.contains('c'));
		assertEquals("123", parsedOpts.get('c').getValue());
	}
	
	@Test(expected=ArgumentExpectedException.class) public void
	throws_exception_when_argument_not_provided1() {
		parser.parse("-bc");
	}
	
	@Test(expected=ArgumentExpectedException.class) public void
	throws_exception_when_argument_not_provided2() {
		parser.parse("--directory");
	}
	
	@Test(expected=ParseException.class) public void
	throws_exception_when_flag_argument_in_wrong_position() {
		parser.parse("-cb 123");
	}

}
