package com.example.demo.utils;
/*
 * Copyright (c) 2017 TraceLink, Inc All Rights Reserved
 * User: kwu
 * Date: 01/25/18
 */

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;

import java.io.IOException;

public class PrettyPrint {

	private static ObjectMapper mapper = new ObjectMapper();
	public static String printFromJsonString(String jsonString) {


		String json = null;

		try {
			json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(mapper.readValue(jsonString,Object.class));
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}

		return json;
	}

	public static String printFromJsonObject(JSONObject jsonOb) {

		String json = null;

		try {
			json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(mapper.readValue(jsonOb.toString(),Object.class));
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}

		return json;
	}

	public JSONObject getObject(String jsonString){
		return new JSONObject(jsonString);
	}
}