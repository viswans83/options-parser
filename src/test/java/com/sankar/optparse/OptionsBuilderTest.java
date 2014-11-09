package com.sankar.optparse;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

import com.sankar.optparse.Options;

public class OptionsBuilderTest {
	
	@Test public void
	allows_building_without_any_options() {
		@SuppressWarnings("unused")
		Options opts = new Options.Builder().build();
	}
	
	@Test public void
	allows_creating_a_simple_option() {
		Options opts = new Options.Builder()
			.option('f')
			.build();
		
		assertTrue(opts.supports('f'));
	}
	
	@Test public void
	allows_creating_a_simple_option_with_an_expansion() {
		Options opts = new Options.Builder()
			.option('f', "force")
			.build();
		
		assertTrue(opts.supports("force"));
	}
	
	@Test public void
	allows_creating_options_that_takes_an_argument() {
		Options opts = new Options.Builder()
			.optionWithArgument('f')
			.optionWithArgument('g', "go-mode")
			.build();
		
		assertTrue(opts.supports('f'));
		assertTrue(opts.supports('g'));
		assertTrue(opts.supports("go-mode"));
	}
	
	@Test(expected=IllegalArgumentException.class) public void
	throws_exception_on_duplicate_flag() {
		new Options.Builder()
			.optionWithArgument('f')
			.optionWithArgument('f')
			.build();
	}
	
	@Test(expected=IllegalArgumentException.class) public void
	throws_exception_on_duplicate_expansion() {
		new Options.Builder()
			.option("force")
			.optionWithArgument('f', "force")
			.build();
	}
	
	@Test public void
	does_not_support_flag_implicitly_for_expansion_only_option() {
		Options opts = new Options.Builder()
			.option("force")
			.build();
		
		assertFalse(opts.supports('f'));
	}
	
	@Test public void
	allows_creating_options_with_help_text() {
		Options opts = new Options.Builder()
			.optionWithArgument('f', "file").withHelp("A file name")
			.build();
		
		assertEquals("A file name", opts.getHelpText('f'));
	}

}
