
package model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 
 * @author Umid Muzrapov, Second Author Jose Bernardo Montano Peralta
 *
 */
public class PlayList implements Serializable {

	ArrayList<Song> data;

	public PlayList() {
		data = new ArrayList<Song>();
	}

	public PlayList(ArrayList<Song> songs) {
		data = songs;
	}

	public void queueUpNextSong(Song newSong) {
		data.add(newSong);
	}

	public Song peek() {
		return data.get(0);
	}

	public Song getNextSong() {
		Song removedSong = data.remove(0);
//	  data.add(removedSong);
		return removedSong;
	}

	public synchronized int size() {
		return data.size();
	}

	public synchronized ArrayList<Song> getSongs() {
		return data;
	}

}
