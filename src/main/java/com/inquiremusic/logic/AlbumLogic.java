/**
 * 
 */
package com.inquiremusic.logic;

import java.time.LocalDate;
import java.util.ArrayList;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;

import org.apache.logging.log4j.Logger;

import com.inquiremusic.data.Album;
import com.inquiremusic.data.Artist;
import com.inquiremusic.utilities.APIManager;
import com.inquiremusic.utilities.ConfigurationSettings;

/**
 * AlbumLogic handles API requests and provides interfaces to retrieve Artist related information
 * @author Thomas P. Kovalchuk
 *
 */
public class AlbumLogic {
	private final APIManager _apiManager;
	private final ConfigurationSettings _settings;
	private final Logger _logger;
	private final String _endpoint;
	/**
	 * Takes 3 dependencies to create a new AlbumLogic object, results in a valid object to run API queries for Albums
	 * @param apiManager
	 * @param settings
	 * @param logger
	 */
	public AlbumLogic(APIManager apiManager, ConfigurationSettings settings, Logger logger) {
		this._logger = logger;
		this._logger.debug(">> new AlbumLogic(...)");
		this._apiManager = apiManager;
		this._settings = settings;
		this._endpoint = this._settings.getSetting("APIEndpoint");
		this._logger.debug("<< new AlbumLogic(...)");
	}
	/**
	 * Returns the result of the query sent to the API, returns a new Album.
	 * @param id
	 * @return Album - result of the query from the API
	 */
	public Album Get(String id) {
		this._logger.debug(">> AlbumLogic.Get("+id+")");
		Album result = null;
		JsonObject res = this._apiManager.sendGet(this._endpoint + "albums/" + id + "/?market=US");
		
		if(res.isEmpty()) {
			return result;
		}
		
		result = new Album();
		result.setId(res.getString("id"));
		result.setName(res.getString("name"));
		result.setLabel(res.getString("label"));
		result.setType(res.getString("type"));
		//when parsing the release_date, if the precision returned is only a year, set to first day of the year
		String release_precision = res.getString("release_date_precision");
		if(release_precision.equals("year")) {
			result.setReleaseDate(LocalDate.parse(res.getString("release_date") + "-01-01"));
		}else if (release_precision.equals("day")){
			result.setReleaseDate(LocalDate.parse(res.getString("release_date")));				
		}
		result.setTotalTracks(res.getInt("total_tracks"));
		
		Artist artist = new Artist();
		try {			
			JsonObject artistJson = res.get("artists").asJsonArray().get(0).asJsonObject();
			artist.setId(artistJson.getString("id"));
			artist.setName(artistJson.getString("name"));
			result.setArtist(artist);
		}catch(Exception e) {
		}
		
				
		this._logger.debug("<< AlbumLogic.Get("+id+")");
		return result;
	}
	/**
	 * Returns the result of the query sent to the API, returns a new ArrayList<Album>.
	 * @param id - id of the artist to return all albums associated
	 * @return ArrayList<Album> - result of the query from the API
	 */
	public ArrayList<Album> GetAllByArtistId(String id) {
		this._logger.debug(">> AlbumLogic.GetAllByArtistId("+id+")");
		ArrayList<Album> result = new ArrayList<Album>();
		JsonObject res = this._apiManager.sendGet(this._endpoint + "artists/" + id + "/albums?limit=50&include_groups=album,single&market=US");
		
		if(res.isEmpty()) {
			return result;
		}
		JsonArray jsonResults = null;
		try {			
			jsonResults = res.getJsonArray("items");
		}catch(NullPointerException e) {
			this._logger.error("AlbumLogic.GetAllByArtistId("+id+") results are null");
			return null;
		}catch(ClassCastException e) {
			this._logger.error("AlbumLogic.GetAllByArtistId("+id+") results cast error");
			return null;
		}
		
		for(JsonValue jsonResult : jsonResults) {
			JsonObject item = jsonResult.asJsonObject();
			Album album = new Album();
			album.setId(item.getString("id"));
			album.setName(item.getString("name"));
			album.setType(item.getString("album_type"));
			album.setTotalTracks(item.getInt("total_tracks"));
			//when parsing the release_date, if the precision returned is only a year, set to first day of the year
			String release_precision = item.getString("release_date_precision");
			if(release_precision.equals("year")) {
				album.setReleaseDate(LocalDate.parse(item.getString("release_date") + "-01-01"));
			}else if (release_precision.equals("day")) {
				album.setReleaseDate(LocalDate.parse(item.getString("release_date")));				
			}
			album.setTotalTracks(item.getInt("total_tracks"));
			result.add(album);
		}
		
				
		this._logger.debug("<< AlbumLogic.GetAllByArtistId("+id+")");
		return result;
	}
}
