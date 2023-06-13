/**
 * 
 * @author Umid Muzrapov, Second Author Jose Bernardo Montano Peralta
 *
 */

package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import model.JukeboxAccount;
import model.PlayList;
import model.Song;

class JukeBoxAccountTest {

	@Test
	void testJukeboxAccountUserNameGetter() {
		JukeboxAccount account1 = new JukeboxAccount("Berny", "1234");
		assertEquals("Berny", account1.getUsername());

	}

	@Test
	void testJukeboxAccountPasswordGetter() {
		JukeboxAccount account1 = new JukeboxAccount("Berny", "1234");
		assertEquals("1234", account1.getPassword());

	}

	@Test
	void testJukeboxAccountDateGetter() {
		JukeboxAccount account1 = new JukeboxAccount("Berny", "1234");
		LocalDate today = LocalDate.now();
		assertEquals(today, account1.getLatestDate());
	}

	@Test
	void testAccountHasCredit() {
		JukeboxAccount account1 = new JukeboxAccount("Umid", "123");
		assertTrue(account1.hasAnyCredit());
	}

	@Test
	void testAccountThreeLimit() {
		JukeboxAccount account1 = new JukeboxAccount("Umid", "12334");
		assertTrue(account1.hasAnyCredit());
		account1.decrementLimit();
		account1.decrementLimit();
		account1.decrementLimit();
		assertFalse(account1.hasAnyCredit());
		account1.decrementLimit();
	}

	@Test
	void testAccountResetLimit() {
		JukeboxAccount account1 = new JukeboxAccount("Berny", "1234");
		assertTrue(account1.hasAnyCredit());
		account1.decrementLimit();
		account1.decrementLimit();
		account1.decrementLimit();
		assertFalse(account1.hasAnyCredit());
		account1.resetLimit();
		assertTrue(account1.hasAnyCredit());
	}

	@Test
	void testAccountActiveSongs() {
		JukeboxAccount account1 = new JukeboxAccount("Berny", "1234");
		PlayList playlist = new PlayList();
		Song s1 = new Song("Till I Colapse", "Eminem", "2:30", "songs/playlist");
		Song s2 = new Song("Song 2", "Eminem", "3:00", "songs/playlist");
		Song s3 = new Song("Song 3", "Eminem", "3:00", "songs/playlist");
		playlist.queueUpNextSong(s1);
		playlist.queueUpNextSong(s2);
		playlist.queueUpNextSong(s3);
		account1.setPlayList(playlist);
		assertEquals(account1.getActiveSongs(), playlist);

	}

}
