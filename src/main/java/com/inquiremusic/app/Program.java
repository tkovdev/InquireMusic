package com.inquiremusic.app;

import java.awt.EventQueue;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Redirect;
import java.net.http.HttpClient.Version;
import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.inquiremusic.app.controller.MainController;
import com.inquiremusic.app.view.MainView;
import com.inquiremusic.logic.AlbumLogic;
import com.inquiremusic.logic.ArtistLogic;
import com.inquiremusic.logic.SearchResultLogic;
import com.inquiremusic.logic.TrackLogic;
import com.inquiremusic.utilities.APIManager;
import com.inquiremusic.utilities.ConfigurationSettings;
import com.inquiremusic.utilities.JSONUtility;

/**
 * 
 * @author Thomas P. Kovalchuk
 *
 */
public class Program {

	/**
	 * Primary entry point to start the application. Creates the primary view, & controller.
	 * provides the primary controller & the services that were configured
	 * @param args
	 * @see MainView
	 * @see MainController
	 */
	public static void main(String[] args) {
		  EventQueue.invokeLater(new Runnable() {
			private HashMap<String, Object> _services = Program.configureServices();
			public void run() {
				try {
					MainView view = new MainView();
					new MainController(view, this._services);
					view.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
	}
	
	/**
	 * Provides service configuration via HashMap of configured services. Acts as a singleton factory.
	 * @return services - HashMap<String, Object> including all services that were configured
	 */
	protected static final HashMap<String, Object> configureServices(){
		HashMap<String, Object> services = new HashMap<String, Object>();

		//Base Services
		services.put("_logger", LogManager.getLogger());
		services.put("_jsonUtility", new JSONUtility((Logger)services.get("_logger")));
		services.put("_settings", new ConfigurationSettings((Logger)services.get("_logger")));
		services.put("_client", HttpClient.newBuilder()
							      .version(Version.HTTP_2)
							      .followRedirects(Redirect.ALWAYS)
							      .build()
							      );
		services.put("_apiManager", new APIManager((ConfigurationSettings)services.get("_settings"),
													(HttpClient)services.get("_client"),
													(Logger)services.get("_logger"),
													(JSONUtility)services.get("_jsonUtility")));
		
		//Logic Controllers as a service
		services.put("_trackLogic", new TrackLogic((APIManager)services.get("_apiManager"), 
													(ConfigurationSettings)services.get("_settings"),
													(Logger)services.get("_logger")));
		
		services.put("_artistLogic", new ArtistLogic((APIManager)services.get("_apiManager"), 
				(ConfigurationSettings)services.get("_settings"),
				(Logger)services.get("_logger"), (JSONUtility)services.get("_jsonUtility")));
		
		services.put("_albumLogic", new AlbumLogic((APIManager)services.get("_apiManager"), 
				(ConfigurationSettings)services.get("_settings"),
				(Logger)services.get("_logger")));
		
		services.put("_searchResultLogic", new SearchResultLogic((APIManager)services.get("_apiManager"), 
				(ConfigurationSettings)services.get("_settings"),
				(Logger)services.get("_logger")));
		
		return services;
	}
	
	
}
