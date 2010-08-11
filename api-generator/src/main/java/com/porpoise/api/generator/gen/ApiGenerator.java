package com.porpoise.api.generator.gen;

import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.porpoise.generator.IGenerator;

/**
 * This generator functional class intends to keep separate any api classes from
 * the DAO layer
 * 
 * Typical usage is to generate a DAO project separately from an API project,
 * though both could exist within the same project
 * 
 * @author Aaron
 */
public class ApiGenerator {

	private static final Map<String, IGenerator> mainSourceTemplateByFilename;
	private static final Map<String, IGenerator> testSourceTemplateByFilename;

	static {
		mainSourceTemplateByFilename = ImmutableMap.of(//
				);

		testSourceTemplateByFilename = ImmutableMap.of(//
				);
	}

}
