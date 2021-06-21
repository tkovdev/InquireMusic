package com.inquiremusic.utilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import javax.json.Json;
import javax.json.JsonException;
import javax.json.JsonObject;
import javax.json.JsonReader;

import org.apache.logging.log4j.Logger;
/**
 * ConfigurationSettings handles configuration settings retrieval and access for various persistent application configuration settings
 * @author Thomas P. Kovalchuk
 *
 */
public class ConfigurationSettings {
	private final Logger _logger;
	private final String environmentFilePath;
	private final File environmentFile;
	private Map<String, String> settings;
	
	/**
	 * Takes 1 dependency to create a new ConfigurationSettings object, results in a valid object to use for retrieving application configuration settings.
	 * @param logger - Logger object
	 */
	public ConfigurationSettings(Logger logger) {
		this._logger = logger;
		this._logger.debug(">> new ConfigurationSettings(...)");
		this.environmentFilePath = ".\\src\\main\\resources\\configuration.settings";
		this.environmentFile = new File(this.environmentFilePath);
		this.settings = this.readConfigurationFile(this.environmentFile);
		this._logger.debug("<< new ConfigurationSettings(...)");
	}
	/**
	 * Takes 1 dependency & 1 file path as a String to create a new ConfigurationSettings object, results in a valid object to use for retrieving application configuration settings.
	 * @param environmentFilePath - String file path to retrieve configuration settings from
	 * @param logger - Logger object
	 */
	public ConfigurationSettings(String environmentFilePath, Logger logger) throws FileNotFoundException {
		this._logger = logger;
		this._logger.debug(">> new ConfigurationSettings(...,"+environmentFilePath+")");
		this.environmentFilePath = environmentFilePath;
		File tempFile = null;
		try {
			tempFile = new File(this.environmentFilePath);
		}catch(NullPointerException e) {
			this._logger.error("path to file is null");
			throw new NullPointerException("path to file is null");
		}
		this.environmentFile = tempFile;			
		this.settings = this.readConfigurationFile(this.environmentFile);
		if(this.settings == null) {
			this._logger.error("path to file is null or not found");
			throw new FileNotFoundException("path to file is null or not found");
		}
		this._logger.debug("<< new ConfigurationSettings(...,"+environmentFilePath+")");
	}
	
	/**
	 * 
	 * @param key - String of the specific setting to return
	 * @return String of the retrieved setting
	 */
	public String getSetting(String key) throws NullPointerException, ClassCastException {
		this._logger.debug(">> ConfigruationSettings.getSetting('"+key+"')");
		String setting = null;
		try {
			setting = this.settings.get(key);
			if(setting == null) {
				throw new NullPointerException();
			}
		}catch(NullPointerException e) {
			this._logger.error("setting was not found or malformed");
			throw new NullPointerException();
		}catch(ClassCastException e) {
			this._logger.error("setting was expected as string, received different data type");
			throw new ClassCastException();
		}
		this._logger.debug("<< ConfigruationSettings.getSetting('"+setting+"')");
		return setting;
	}
	
	/**
	 * 
	 * @param file - File object to read
	 * @return HashMap<String, String> of all the read settings
	 */
	private HashMap<String, String> readConfigurationFile(File file) {
		this._logger.debug(">> ConfigruationSettings.readConfigurationFile(File)");
		HashMap<String, String> settings = new HashMap<String, String>();
		FileReader fr = null;
		
		try {
			fr = new FileReader(file);
		}
		catch(FileNotFoundException e) {
			this._logger.error("env file does not exist or could not be found");
			return null;
		}
		
		
		//Read file, insert each KeyValue into HashMap
		BufferedReader br = new BufferedReader(fr);
		JsonReader jr = Json.createReader(br);
		JsonObject config = null;
		try {			
			config = jr.readObject();
		}catch(JsonException e) {
			this._logger.error("env file was malformed or could not be read to JSON format");
		}catch(IllegalStateException e) {
			this._logger.error("env file was previously closed");
		}
		
		try {			
	        jr.close();
		}catch(JsonException e) {
			this._logger.error("error when accessing specified settings file");
		}

        
        for(String settingKey : config.keySet()){
        	try {        		
        		String settingValue = config.getString(settingKey);
        		settings.put(settingKey, settingValue);
        	}catch(NullPointerException e) {
        		this._logger.error("error when parsing the JSON file, setting does not exist");
        		throw new NullPointerException();
        	}catch(ClassCastException e) {
        		this._logger.error("error when parsing the JSON file, malformed JSON, please ensure all settings are String");
        	}
        }
        this._logger.debug("<< ConfigruationSettings.readConfigurationFile(HashMap<String, String>)");
		return settings;
	}

}
