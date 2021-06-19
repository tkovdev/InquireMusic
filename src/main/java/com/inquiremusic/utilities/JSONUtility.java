package com.inquiremusic.utilities;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.ArrayList;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonException;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.stream.JsonParsingException;

import org.apache.logging.log4j.Logger;

/**
 * JSONUtility provides helper methods to parse JSON
 * @author Thomas P. Kovalchuk
 *
 */
public class JSONUtility {
	private final Logger _logger;
	
	/**
	 * Takes 1 dependency to create a new JSONUtlity object, results in a valid object to be used for JSON parsing
	 * @param logger
	 */
	public JSONUtility (Logger logger) {
		this._logger = logger;
		this._logger.debug(">> new JSONUtility(...)");
		this._logger.debug("<< new JSONUtility(...)");
	}
	
	/**
	 * toJSON provides the ability to take valid JSON as a String and return a JsonObject
	 * @param input - String of the JSON that will be parsed to a JSON Object
	 * @return JSONObject of the parsed string
	 */
	public JsonObject toJSON(String input) {
		this._logger.debug(">> JSONUtility.toJSON(String)");
		StringReader sr = new StringReader(input);
		BufferedReader br = new BufferedReader(sr);
		JsonReader jr = Json.createReader(br);
		JsonObject json = null;
		try {
			json = jr.readObject();
		}catch(JsonParsingException e) {
			this._logger.error("issue parsing input string to JSON");
			jr.close();
			System.exit(1);
		}
		catch(JsonException e) {
			this._logger.error("JSON object could not be created");
			jr.close();
			System.exit(1);
		}
		jr.close();
		this._logger.debug("<< JSONUtility.toJSON(String)");
		return json;
	}
	
	/**
	 * jsonArrayToStringArrayList provides the ability to take a JsonArray and return an ArrayList<String>
	 * @param array - JsonArray of the items to be converted
	 * @return ArrayList<String> of the parsed JsonArray
	 */
	public ArrayList<String> jsonArrayToStringArrayList(JsonArray array){
		ArrayList<String> list = new ArrayList<String>();
		if(array != null) {
			for(Object item : array) {
				String converted = String.valueOf(item); 
				if(converted != null) {
					list.add(converted);
				}
			}
		}
		return list;
	}
	
}
