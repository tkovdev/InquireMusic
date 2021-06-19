package com.inquiremusic.app.model;

/**
 * ListItem provides a consistent data model for the front-end for containing List items. Primarily used to store JList items
 * @author Thomas P. Kovalchuk
 *
 */
public class ListItem {
	private String id;
	private String title;
	private String subtitle;
	private String type;
	
	/**
	 * ListItem creates an empty ListItem object for variables to be set later
	 */
	public ListItem() {
		
	}
	
	/**
	 * ListItem creates a ListItem of given parameters for id, title, subtitle, and type
	 * @param id
	 * @param title
	 * @param subtitle
	 * @param type
	 */
	public ListItem(String id, String title, String subtitle, String type) {
		this.id = id;
		this.title = title;
		this.subtitle = subtitle;
		this.type = type;
	}
	
	@Override
	public String toString() {
		String item = "<html>";
		item += "<h3>" + this.title + "&nbsp;&nbsp;<small>("+this.type+")</small></h3>";
		if(this.subtitle != null) {
			item += "<h4><b>" + this.subtitle + "</b></h4>";
		}
		item += "</html>";
		return item;
	}
	
	public String getId() {
		return id;
	}
	public String getTitle() {
		return title;
	}
	public String getSubtitle() {
		return subtitle;
	}
	public String getType() {
		return type;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}
	public void setType(String type) {
		this.type = type;
	}
}
