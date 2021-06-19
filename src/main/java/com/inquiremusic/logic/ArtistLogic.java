package com.inquiremusic.logic;

import javax.json.JsonObject;

import org.apache.logging.log4j.Logger;

import com.inquiremusic.data.Artist;
import com.inquiremusic.utilities.APIManager;
import com.inquiremusic.utilities.ConfigurationSettings;
import com.inquiremusic.utilities.JSONUtility;

/**
 * ArtistLogic handles API requests and provides interfaces to retrieve Artist related information
 * @author Thomas P. Kovalchuk
 *
 */
public class ArtistLogic {
	private final APIManager _apiManager;
	private final ConfigurationSettings _settings;
	private final Logger _logger;
	private final JSONUtility _jsonUtility;
	private final String _endpoint;
	
	/**
	 * Takes 4 dependencies to create a new ArtistLogic object, results in a valid object to run API queries for Artists
	 * @param apiManager
	 * @param settings
	 * @param logger
	 * @param jsonUtility
	 */
	public ArtistLogic(APIManager apiManager, ConfigurationSettings settings, Logger logger, JSONUtility jsonUtility) {
		this._logger = logger;
		this._logger.debug(">> new ArtistLogic(...)");
		this._apiManager = apiManager;
		this._settings = settings;
		this._endpoint = this._settings.getSetting("APIEndpoint");
		this._jsonUtility = jsonUtility;
		this._logger.debug("<< new ArtistLogic(...)");
	}
	/**
	 * Returns the result of the query sent to the API, returns a new Artist.
	 * @param id
	 * @return Artist - result of the query from the API
	 */
	public Artist Get(String id) {
		this._logger.debug(">> ArtistLogic.Get("+id+")");
		Artist result = null;
		JsonObject res = this._apiManager.sendGet(this._endpoint + "artists/" + id);
		
		if(res.isEmpty()) {
			return result;
		}
		
		result = new Artist();
		result.setId(res.getString("id"));
		result.setName(res.getString("name"));
		result.setPopularity(Double.parseDouble(res.get("popularity").toString()));
		result.setGenres(_jsonUtility.jsonArrayToStringArrayList(res.getJsonArray("genres")));
				
		this._logger.debug("<< ArtistLogic.Get("+id+")");
		return result;
	}

}
