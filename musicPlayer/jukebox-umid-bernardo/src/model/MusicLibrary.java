/**
 * 
 * @author Umid Muzrapov, Second Author Jose Bernardo Montano Peralta
 *
 */

package model;

import java.io.Serializable;
import java.util.ArrayList;

public class MusicLibrary implements Serializable {

	ArrayList<Song> library;

	public MusicLibrary() {
		library = new ArrayList<Song>();
	}

	public MusicLibrary(ArrayList<Song> songs) {
		library = new ArrayList<Song>();
		library = songs;
	}

	public synchronized void add(Song newSong) {
		library.add(newSong);
	}

	public synchronized void remove(Song song) {
		library.remove(song);
	}

	public synchronized ArrayList<Song> getSongs() {
		return library;
	}
}
