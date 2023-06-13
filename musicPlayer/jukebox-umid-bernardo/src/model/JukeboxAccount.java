package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Class Definition
 * 
 * @author Umid Muzrapov, Second Author Jose Bernardo Montano
 */
public class JukeboxAccount implements Serializable {

	private LocalDate today;
	private String username;
	private String password;
	private PlayList activeSongs;
	private int limit;

	public JukeboxAccount(String name, String password) {
		this.username = name;
		this.password = password;
		today = LocalDate.now();
		this.activeSongs = new PlayList();
		this.limit = 3;

	}

	public String getUsername() {
		return this.username;
	}

	public String getPassword() {
		return this.password;
	}

//    public void setUpJukeBox(MusicLibrary libraryMusic, PlayList playlistMusic) {
//    	this.jukebox = new JukeBoxModel(libraryMusic, playlistMusic);
//    }

	public void setPlayList(PlayList newActiveSongs) {
		activeSongs = newActiveSongs;

	}

	public PlayList getActiveSongs() {
		return activeSongs;

	}

	public LocalDate getLatestDate() {
		return today;
	}

	public void resetLimit() {
		limit = 3;
	}

	public void decrementLimit() {
		limit -= 1;
	}

	public void getLimit() {

	}

	public boolean hasAnyCredit() {
		if (limit > 0) {
			return true;
		} else
			return false;
	}
}
