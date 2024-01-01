/**
 * @author Umid Muzrapov, Second Author Jose Bernardo Montano Peralta
 */
package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import model.PlayList;
import model.Song;

class PlayListTest {

  @Test
  void testPlaylistSize() {
    PlayList playlist = new PlayList();
    assertEquals(0, playlist.size());
    Song s1 = new Song("Till I Colapse", "Eminem", "2:30", "songs/playlist");
    Song s2 = new Song("Song 2", "Eminem", "3:00", "songs/playlist");
    Song s3 = new Song("Song 3", "Eminem", "3:00", "songs/playlist");
    playlist.queueUpNextSong(s1);
    playlist.queueUpNextSong(s2);
    playlist.queueUpNextSong(s3);
    assertEquals(3, playlist.size());
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
    PlayList playlist = new PlayList(list);
    assertEquals(3, playlist.size());
  }

  @Test
  void testGetNextSong() {
    PlayList playlist = new PlayList();
    assertEquals(0, playlist.size());
    Song s1 = new Song("Till I Colapse", "Eminem", "2:30", "songs/playlist");
    Song s2 = new Song("Song 2", "Eminem", "3:00", "songs/playlist");
    Song s3 = new Song("Song 3", "Eminem", "3:00", "songs/playlist");
    playlist.queueUpNextSong(s1);
    playlist.queueUpNextSong(s2);
    playlist.queueUpNextSong(s3);
    assertEquals(s1, playlist.getNextSong());
  }

  @Test
  void testGetArrayListSongs() {
    ArrayList<Song> list = new ArrayList<>();
    Song s1 = new Song("Till I Colapse", "Eminem", "2:30", "songs/playlist");
    Song s2 = new Song("Song 2", "Eminem", "3:00", "songs/playlist");
    Song s3 = new Song("Song 3", "Eminem", "3:00", "songs/playlist");
    list.add(s1);
    list.add(s2);
    list.add(s3);
    PlayList playlist = new PlayList(list);
    assertEquals(3, playlist.size());
    assertEquals(list, playlist.getSongs());
  }
}
