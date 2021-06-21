package com.inquiremusic.utilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.Base64;
import java.util.concurrent.ExecutionException;

import javax.json.Json;
import javax.json.JsonException;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.json.JsonWriter;

import org.apache.logging.log4j.Logger;
/**
 * APIManager handles abstracting API calls and provides token life cycle management
 * @author Thomas P. Kovalchuk
 *
 */
public final class APIManager {
	private final ConfigurationSettings _settings;
	private final HttpClient _client;
	private final Logger _logger;
	private final JSONUtility _jsonUtility;
	
	private String _apiToken;
	private String _tokenType;
	private String _expiration;
	private long _dateRetrieved;
	
	/**
	 * Takes 4 dependencies to create a new APIManager object, results in a valid object to run abstracted API calls from, including token life cycle management
	 * @param settings
	 * @param client
	 * @param logger
	 * @param jsonUtility
	 */
	public APIManager(ConfigurationSettings settings, HttpClient client, Logger logger, JSONUtility jsonUtility) {
		this._logger =  logger;
		this._logger.debug(">> new APIManager(...)");
		this._settings = settings;
		this._client =  client;
		this._jsonUtility = jsonUtility;
		this.refreshToken();
		this._logger.debug("<< new APIManager(...)");
	}
	
	/**
	 * gets a new token from the API service for use later. Stores the token in the object instance.
	 */
	private void retrieveToken() {
		this._logger.debug(">> APIManager.retrieveToken()");
		// create a request
		String auth = "Basic ";
		auth = auth + Base64.getEncoder().encodeToString(
												(this._settings.getSetting("ClientID") + ":" + this._settings.getSetting("ClientSecret"))
												.getBytes()
											);
		
		HttpRequest request = HttpRequest.newBuilder(
		       URI.create("https://accounts.spotify.com/api/token"))
			   .header("Content-Type", "application/x-www-form-urlencoded")
			   .header("Authorization", auth)
			   .POST(BodyPublishers.ofString("grant_type=client_credentials"))
			   .build();
		HttpResponse<String> res; 
		JsonObject json = null;
		try {
			// use the client to send the request
			res = _client.sendAsync(request, BodyHandlers.ofString())
					.get();
			
			json = this._jsonUtility.toJSON(res.body());
		}catch(IllegalArgumentException e) {
			this._logger.error("arguments supplied illegal or invalid");
			System.exit(1);
		} catch (InterruptedException e) {
			this._logger.error("issue returning response");
			System.exit(1);
		} catch (ExecutionException e) {
			this._logger.error("issue executing request");
			System.exit(1);
		}
		
		try {
			this._apiToken = json.getString("access_token");
			this._tokenType = json.getString("token_type");
			this._expiration = String.valueOf(Long.valueOf(json.get("expires_in").toString().replace("\"", ""))* 1000);
		}catch(NullPointerException e) {
			this._logger.error("error when accessing specified field in token file");
		}
		this._dateRetrieved = System.currentTimeMillis();
		this._logger.debug("<< APIManager.retrieveToken()");
	}
	
	/**
	 * Reads the token from the local file where it is stored
	 * @return boolean of if the token was read from file
	 */
	private boolean readTokenFromFile() {
		this._logger.debug(">> APIManager.readTokenFromFile()");
		FileReader fr = null;
		File file = null;
		
		try {
			file = new File(this._settings.getSetting("TokenStorage"));
			fr = new FileReader(file);
		}
		catch(FileNotFoundException e) {
			this._logger.error("token file does not exist or could not be found");
			return false;
		}catch(NullPointerException e) {
			this._logger.error("token file not provided, or is null");
			return false;
		}
		
		
		//Read API properties
		BufferedReader br = new BufferedReader(fr);
		JsonReader jr = Json.createReader(br);
		JsonObject apiProperties = null;
		try {			
			apiProperties = jr.readObject();
		}catch(JsonException e) {
			this._logger.error("token file was malformed or could not be read to JSON format");
			return false;
		}catch(IllegalStateException e) {
			this._logger.error("token file was previously closed");
			return false;
		}
		
		try {			
	        jr.close();
		}catch(JsonException e) {
			this._logger.error("error when accessing specified token file");
			return false;
		}
		
		//Set each APIManager properties
		try {
			this._apiToken = apiProperties.getString("access_token");
			this._tokenType = apiProperties.getString("token_type");
			this._expiration = apiProperties.getString("expires_in");
			this._dateRetrieved = Long.valueOf(apiProperties.getString("date_retrieved"));
		}catch(NullPointerException e) {
			this._logger.error("error when accessing specified field in token file");
			return false;
		}
		this._logger.debug("<< APIManager.readTokenFromFile("+true+")");
		return true;
	}
	
	/**
	 * Handle saving token to local file for use later
	 */
	private void saveTokenToFile() {
		this._logger.debug(">> APIManager.saveTokenToFile()");
		File file = null;
		FileWriter fw = null;
		JsonWriter jw = null;
		try {
			file = new File(this._settings.getSetting("TokenStorage"));
			fw = new FileWriter(file);
			jw = Json.createWriter(fw);
		}catch(NullPointerException e) {
			this._logger.error("specified token file was null, does not exist, or the configuration setting does not exist");
			System.exit(1);
		}
		catch(IOException e){
			this._logger.error("error when accessing specified token file");
			System.exit(1);
		}
		//write token information to new JSON object
		JsonObjectBuilder jb = Json.createObjectBuilder();
		jb.add("access_token", this._apiToken);
		jb.add("token_type", this._tokenType);
		jb.add("expires_in", this._expiration);
		jb.add("date_retrieved", String.valueOf(this._dateRetrieved));
		JsonObject json = jb.build();
		try {
			//write json to file
			jw.writeObject(json);
		}catch(JsonException e) {
			this._logger.error("error when writing to specified token file");
		}catch(IllegalStateException e) {
			this._logger.error("token file is already closed or in a different state, file not written");
		}
		jw.close();
		this._logger.debug("<< APIManager.saveTokenToFile()");
	}

	/**
	 * @return String of the token stored in the object instance
	 */
	public String getApiToken() {
		this._logger.debug(">> APIManager.getApiToken()");
		this._logger.debug("<< APIManager.getApiToken()");
		return this._apiToken;
	}
	
	/**
	 * @return String of the token type stored in the object instance
	 */
	public String getTokenType() {
		this._logger.debug(">> APIManager.getTokenType()");
		this._logger.debug("<< APIManager.getTokenType()");
		return this._tokenType;
	}
	
	/**
	 * Check token is not expired based on local file, uses expiration time provided by previous token and compares to current time
	 */
	public boolean isTokenExpired() {
		this._logger.debug(">> APIManager.isTokenExpired()");
		boolean expired = System.currentTimeMillis() > this._dateRetrieved + (Long.valueOf(this._expiration));
		this._logger.debug("<< APIManager.isTokenExpired("+expired+")");
		return expired; 
	}
	
	/**
	 * Check token is not expired and token exists in local file, if either fail, get a new token and save to file
	 * This method should be used for every call to the API, just to ensure token is up to date and mitigate fail over
	 */
	private void refreshToken() {
		if(!this.readTokenFromFile() || this.isTokenExpired() ) {
			this._logger.debug(">> APIManager.refreshToken()");
			this.retrieveToken();
			this.saveTokenToFile();
			this._logger.debug("<< APIManager.refreshToken()");
		}
	}
	
	/**
	 * sendGet handles API requests for a given endpoint by sending a GET call to the specified endpoint.
	 * @param endpoint - String of the full endpoint to send request to
	 * @return JsonObject of resulting API call
	 */
	public JsonObject sendGet(String endpoint) {
		this._logger.debug(">> APIManager.sendGet('"+endpoint+"')");
		//make sure token is valid and refreshed
		this.refreshToken();
		// create a request
		String auth = this._tokenType + " " + this._apiToken;
		
		HttpRequest request = HttpRequest.newBuilder(
		       URI.create(endpoint))
				.header("Content-Type", "application/json")
				.header("Accept", "application/json")
				.header("Authorization", auth)
				.GET()
				.build();
		HttpResponse<String> res; 
		JsonObject json = null;
		try {
			// use the client to send the request
			res = _client.sendAsync(request, BodyHandlers.ofString())
					.get();
			
			json = this._jsonUtility.toJSON(res.body());
		}catch(IllegalArgumentException e) {
			this._logger.error(">>>> arguments supplied illegal or invalid");
			return null;
		} catch (InterruptedException e) {
			this._logger.error(">>>> issue returning response");
			return null;
		} catch (ExecutionException e) {
			this._logger.error(">>>> issue executing request");
			return null;
		}
		this._logger.debug("<< APIManager.sendGet('"+endpoint+"')");
		return json;
	}
}
