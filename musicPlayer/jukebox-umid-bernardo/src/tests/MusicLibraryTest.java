/**
 * 
 * @author Umid Muzrapov, Second Author Jose Bernardo Montano Peralta
 *
 */

package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import model.Song;
import model.MusicLibrary;

class MusicLibraryTest {

	@Test
	void testFirstConstructor() {
		MusicLibrary musicLib = new MusicLibrary();
		assertEquals(new ArrayList<Song>(), musicLib.getSongs());
	}

	@Test
	void testSecondConstructor() {
		ArrayList<Song> list = new ArrayList<>();
		Song s1 = new Song("Till I Colapse", "Eminem", "2:30", "songs/playlist");
		Song s2 = new Song("Song 2", "Eminem", "3:00", "songs/playlist");
		Song s3 = new Song("Song 3", "Eminem", "3:00", "songs/playlist");
		list.add(s1);
		list.add(s2);
		list.add(s3);
		MusicLibrary musicLib = new MusicLibrary(list);
		assertEquals(list, musicLib.getSongs());
	}

	@Test
	void testAddSong() {
		MusicLibrary musicLib = new MusicLibrary();
		musicLib.add(new Song("Till I Colapse", "Eminem", "2:30", "songs/playlist"));
		Song s1 = musicLib.getSongs().get(0);
		assertEquals(s1.toString(), "Till I Colapse");
	}

	@Test
	void testRemoveSong() {
		MusicLibrary musicLib = new MusicLibrary();
		musicLib.add(new Song("Till I Colapse", "Eminem", "2:30", "songs/playlist"));
		Song s1 = musicLib.getSongs().get(0);
		musicLib.remove(s1);
		assertEquals(0, musicLib.getSongs().size());
	}
}
