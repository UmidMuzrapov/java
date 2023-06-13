/**
 * 
 * @author Umid Muzrapov, Second Author Jose Bernardo Montano Peralta
 *
 */

package model;

import java.io.Serializable;

public class Song implements Serializable {

	private String title;
	private String artist;
	private String time;
	private String location;
	private boolean selected;

	public Song() {

	}

	public Song(String title, String artist, String time, String location) {
		this.title = title;
		this.artist = artist;
		this.time = time;
		this.location = location;
	}

	public Song(String title, String artist, String location) {
		this.title = title;
		this.artist = artist;

		this.location = location;
	}

	public String getTitle() {
		return title;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public boolean isSelected() {

		return this.selected;
	}

	public String getArtist() {
		return artist;
	}

	public String getURL() {
		return this.location;
	}

	public String getTime() {
		return time;
	}

	public void setTitle(String newTitle) {
		title = newTitle;
	}

	public void setArtist(String newArtist) {
		artist = newArtist;
	}

	public void setTime(String newTime) {
		time = newTime;
	}

	@Override
	public String toString() {
		return title;
	}

}
