package com.inquiremusic.logic;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;

import org.apache.logging.log4j.Logger;

import com.inquiremusic.data.Album;
import com.inquiremusic.data.Artist;
import com.inquiremusic.data.SearchResult;
import com.inquiremusic.data.Track;
import com.inquiremusic.utilities.APIManager;
import com.inquiremusic.utilities.ConfigurationSettings;

/**
 * SearchResultLogic handles API requests and provides interfaces to retrieve SearchResult related information
 * @author Thomas P. Kovalchuk
 *
 */
public class SearchResultLogic {
	private final APIManager _apiManager;
	private final ConfigurationSettings _settings;
	private final Logger _logger;
	private final String _endpoint;
	/**
	 * Takes 3 dependencies to create a new SearchResultLogic object, results in a valid object to run API queries for SearchResults
	 * @param apiManager
	 * @param settings
	 * @param logger
	 */
	public SearchResultLogic(APIManager apiManager, ConfigurationSettings settings, Logger logger) {
		this._logger = logger;
		this._logger.debug(">> new SearchResultLogic(...)");
		this._apiManager = apiManager;
		this._settings = settings;
		this._endpoint = this._settings.getSetting("APIEndpoint");
		this._logger.debug("<< new SearchResultLogic(...)");
	}
	/**
	 * Returns the result of the query sent to the API, returns a new SearchResult.
	 * @param term - String of the query (usually a name)
	 * @param type - either Artist, Album, or Track to search for
	 * @return SearchResult - result of the query from the API
	 */
	public SearchResult Get(String term, String type) {
		this._logger.debug(">> SearchResultLogic.Get("+term+", "+type+")");
		SearchResult result = null;
		type = type.toLowerCase();
		
		JsonObject res = this._apiManager.sendGet(this._endpoint + "search/?q=" + term.replaceAll(" ", "%20") + "&type=" + type + "&market=US&limit=50");

		if(res == null || res.isEmpty()) {
			this._logger.error(">>>> results are empty");
			return result;
		}
		
		result = new SearchResult();
		JsonArray jsonResults = null;
		try {			
			jsonResults = res.getJsonObject(type+"s").getJsonArray("items");
		}catch(NullPointerException e) {
			this._logger.error("SearchResultLogic.Get("+term+", "+type+") results are null");
			return null;
		}catch(ClassCastException e) {
			this._logger.error("SearchResultLogic.Get("+term+", "+type+") results cast error");
			return null;
		}
		result.setType(type);
		//Parse each type differently with separate helper methods, then add each parsed object to result
		switch(type) {
			case "artist":
				for(JsonValue item : jsonResults) {
					result.addArtist(parseJsonObjectAsArtist(item.asJsonObject()));
				}
				break;
			case "album":
				for(JsonValue item : jsonResults) {
					result.addAlbum(parseJsonObjectAsAlbum(item.asJsonObject()));
				}
				break;
			case "track":
				for(JsonValue item : jsonResults) {
					result.addTrack(parseJsonObjectAsTrack(item.asJsonObject()));
				}
				break;
			default:
				break;
			
		}

		this._logger.debug("<< SearchResultLogic.Get("+term+", "+type+")");
		return result;
	}
	
	/**
	 * Helper method to convert JsonObjects to corresponding application specific object (Artist)
	 * Note: Returns a basic version of the object, since not all fields are available.
	 * @param json - JsonObject to be parsed
	 * @return Artist - parsed Artist object from given JsonObject param
	 */
	private Artist parseJsonObjectAsArtist(JsonObject json) {
		Artist artist = new Artist();
		artist.setId(json.getString("id"));
		artist.setName(json.getString("name"));
		return artist;
	}
	
	/**
	 * Helper method to convert JsonObjects to corresponding application specific object (Album)
	 * Note: Returns a basic version of the object, since not all fields are available.
	 * @param json - JsonObject to be parsed
	 * @return Album - parsed Album object from given JsonObject param
	 */
	private Album parseJsonObjectAsAlbum(JsonObject json) {
		Album album = new Album();
			album.setId(json.getString("id"));
			album.setName(json.getString("name"));
			album.setType(json.getString("album_type"));
		
		try {
			//add basic artist info
			Artist artist = new Artist();
			artist.setName(json.get("artists").asJsonArray().get(0).asJsonObject().getString("name"));
			album.setArtist(artist);
		}catch(NullPointerException e) {
			this._logger.error("SearchResultLogic.parseJsonObjectAsTrack() artist info null");
		}
		
		return album;
	}
	
	/**
	 * Helper method to convert JsonObjects to corresponding application specific object (Track)
	 * Note: Returns a basic version of the object, since not all fields are available.
	 * @param json - JsonObject to be parsed
	 * @return Track - parsed Track object from given JsonObject param
	 */
	private Track parseJsonObjectAsTrack(JsonObject json) {
		Track track = new Track();
		track.setId(json.getString("id"));
		track.setName(json.getString("name"));
		
		//add basic artist info
		try {
			Artist artist = new Artist();
			artist.setName(json.get("artists").asJsonArray().get(0).asJsonObject().getString("name"));
			track.setArtist(artist);
		}catch(NullPointerException e) {
			this._logger.error("SearchResultLogic.parseJsonObjectAsTrack() artist info null");
		}
		return track;
	}
}
