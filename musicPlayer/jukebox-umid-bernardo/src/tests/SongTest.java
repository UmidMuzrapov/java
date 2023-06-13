/**
 * 
 * @author Umid Muzrapov, Second Author Jose Bernardo Montano Peralta
 *
 */

package tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import model.Song;

class SongTest {

	@Test
	void testFullConstructor() {
		Song song2 = new Song();
		Song song1 = new Song("Lose Yourself", "Eminem", "2:30", "songs/playlist");
		assertEquals(song1.getClass(), song2.getClass());
		assertEquals("Lose Yourself", song1.getTitle());
		assertEquals("Eminem", song1.getArtist());
		assertEquals("2:30", song1.getTime());
		assertEquals("songs/playlist", song1.getURL());
	}

	@Test
	void testWithoutTime() {
		Song song1 = new Song("Lose Yourself", "Eminem", "songs/playlist");
		assertEquals("Lose Yourself", song1.getTitle());
		assertEquals("Eminem", song1.getArtist());
		assertEquals("songs/playlist", song1.getURL());
	}

	@Test
	void testSetters() {
		Song song1 = new Song();
		song1.setTitle("Lose Yourself");
		assertEquals("Lose Yourself", song1.getTitle());
		song1.setArtist("Eminem");
		assertEquals("Eminem", song1.getArtist());
		song1.setTime("2:00");
		assertEquals("2:00", song1.getTime());
	}

	@Test
	void testToString() {
		Song song1 = new Song();
		song1.setTitle("Lose Yourself");
		assertEquals("Lose Yourself", song1.getTitle());
		song1.setArtist("Eminem");
		assertEquals("Eminem", song1.getArtist());
		song1.setTime("2:00");
		assertEquals("2:00", song1.getTime());

		assertEquals(song1.toString(), song1.getTitle());
	}
}
