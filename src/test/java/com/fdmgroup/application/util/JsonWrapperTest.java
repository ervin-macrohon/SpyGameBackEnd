package com.fdmgroup.application.util;

import static org.junit.Assert.*;

import org.junit.Test;

public class JsonWrapperTest {

	@Test
	public void returns_json_wrapped_string_for_string() {
		String json = JsonWrapper.wrap("fieldName", "fieldValue");
		
		assertEquals("{\"fieldName\":\"fieldValue\"}", json);
	}
	
	@Test
	public void returns_json_wrapped_string_for_int() {
		String json = JsonWrapper.wrap("fieldName", 1);
		
		assertEquals("{\"fieldName\":1}", json);
	}

}
