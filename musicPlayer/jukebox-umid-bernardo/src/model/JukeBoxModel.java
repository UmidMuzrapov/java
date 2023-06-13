/**
 * 
 * @author Umid Muzrapov, Second Author Jose Bernardo Montano Peralta
 *
 */

package model;

import java.io.Serializable;
import java.util.ArrayList;

import controller_view.JukeboxGUI;

import java.io.File;
import java.net.URI;
import javafx.scene.media.Media;

import javafx.scene.media.MediaPlayer;

public class JukeBoxModel {
	private MusicLibrary library;
	private PlayList playlist;
	private Song currentSong;
	private MediaPlayer mediaPlayer;
	private boolean donePlaying = true;
	private JukeboxGUI gui;
	private boolean playing;

	public JukeBoxModel(MusicLibrary libraryMusic, PlayList playlistMusic, JukeboxGUI gui) {
		library = libraryMusic;
		playlist = playlistMusic;
		this.gui = gui;

	}

	public synchronized void addToPlayList(Song song) {
		playlist.queueUpNextSong(song);
	}

	public synchronized boolean isAddingPossible() {
		if (playlist.size() < 3) {
			return true;
		}

		else
			return false;
	}

	public synchronized void addToLibrary(Song song) {
		library.add(song);
	}

	public synchronized PlayList getPlayList() {
		return playlist;
	}

	public synchronized void setPlayList(PlayList newPlaylist) {
		this.playlist = newPlaylist;
	}

	public synchronized MusicLibrary getLibrary() {
		return library;
	}

	private synchronized Song remove() {
		return playlist.getNextSong();
	}

	public void logOutUser() {
		if (mediaPlayer != null) {
			pause();
		}

		currentSong = null;
		donePlaying = true;
	}

	public synchronized void play() {

		if (currentSong == null && playlist.size() > 0) {
			playing = true;
			currentSong = playlist.peek();
			gui.update();
			donePlaying = false;
			File file = new File(currentSong.getURL());
			URI uri = file.toURI();
			Media media = new Media(uri.toString());
			mediaPlayer = new MediaPlayer(media);
			mediaPlayer.play();
			mediaPlayer.setOnEndOfMedia(new Waiter());
		}

		else if (currentSong != null) {
			playing = true;
			gui.update();
			mediaPlayer.play();
		}

		else {
			return;
		}

	}

	public synchronized void pause() {
		playing = false;
		mediaPlayer.pause();

	}

	private class Waiter implements Runnable {

		@Override
		public void run() {

			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (playlist.size() > 0) {
				remove();
				if (playlist.size() > 0) {
					currentSong = playlist.peek();
					File file = new File(currentSong.getURL());
					URI uri = file.toURI();
					Media media = new Media(uri.toString());
					mediaPlayer = new MediaPlayer(media);
					mediaPlayer.play();
					mediaPlayer.setOnEndOfMedia(new Waiter());
					play();
				}

				gui.update();

			}

			else {

				donePlaying = true;
			}
		}

	}

	public synchronized void playSelected(Song selectedSong) {
		if (selectedSong == null)
			return;
		File file = new File(selectedSong.getURL());
		URI uri = file.toURI();
		Media media = new Media(uri.toString());
		mediaPlayer = new MediaPlayer(media);
		mediaPlayer.play();
		mediaPlayer.setOnEndOfMedia(new Thread(new Waiter()));
	}

	public synchronized boolean isPlaying() {
		return playing;
	}
}
