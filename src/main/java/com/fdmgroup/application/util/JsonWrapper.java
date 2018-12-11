package com.fdmgroup.application.util;

public class JsonWrapper {
	public static String wrap(String fieldName, String fieldValue) {
		return "{\"" + fieldName + "\":\"" + fieldValue + "\"}";
	}

	public static String wrap(String fieldName, int fieldValue) {
		return "{\"" + fieldName + "\":" + fieldValue + "}";
	}
}
