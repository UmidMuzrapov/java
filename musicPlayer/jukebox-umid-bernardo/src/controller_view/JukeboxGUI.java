
package controller_view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.AudioHeader;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import model.JukeBoxModel;
import model.JukeboxAccount;
import model.MusicLibrary;
import model.PlayList;
import model.Song;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * Class Definition
 * 
 * @author Umid Muzrapov, Second Author Jose Bernardo Montano
 *
 */
public class JukeboxGUI extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	private JukeboxAccount account;
	private LoginCreateAccountPane loginPane;
	private BorderPane everything;
	private PlaylistView playlistView;
	private StackPane funcPanePlaylist;
	private StackPane funcPaneLibrary;
	private PlaylistView libraryView;
	private JukeBoxModel model;
	private Button accountButton;
	private boolean isLoggedIn = false;
	private HashMap<String, JukeboxAccount> users;
	private JukeboxAccount currentUser;
	private boolean testMode;

	@Override
	public void start(Stage primaryStage) throws Exception {
		showPersistencePopUp();
		LayoutGUI();
		Scene scene = new Scene(everything, 600, 400);
		primaryStage.setScene(scene);
		primaryStage.show();
		primaryStage.setOnCloseRequest((e) -> {
			if (currentUser != null)
				saveChanges();
		});
	}

	private void showPersistencePopUp() {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Information Dialog");
		alert.setHeaderText(null);
		alert.setContentText("Choose between clean start or loading the database of users");

		ButtonType testingDataButton = new ButtonType("Clean Slate for Test.");
		ButtonType loadDataButton = new ButtonType("Load DB");
		alert.getButtonTypes().setAll(loadDataButton, testingDataButton);
		Optional<ButtonType> result = alert.showAndWait();
		if (result.isPresent()) {
			if (result.get() == testingDataButton) {
				openTest();
			} else if (result.get() == loadDataButton) {
				openApp();
			}
		}
	}

	private void openTest() {
		testMode = true;
	}

	private void openApp() {
		testMode = false;
	}

	private void LayoutGUI() {
		everything = new BorderPane();

		ArrayList<Song> songs = getSongs();
		getUsers();
		MusicLibrary libraryModel = new MusicLibrary(songs);
		PlayList playlistModel = new PlayList();
		model = new JukeBoxModel(libraryModel, playlistModel, this);
		libraryView = new PlaylistView(model.getLibrary().getSongs());

		setPlaylist();

	}

	private void setPlaylist() {
		loginPane = new LoginCreateAccountPane();
		playlistView = new PlaylistView(model.getPlayList().getSongs());
		createFuncPanePlayList();
		everything.setTop(funcPanePlaylist);
		everything.setCenter(playlistView);
		if (model.isPlaying()) {
			playlistView.highlightSong();
		}

		everything.setMargin(funcPanePlaylist, new Insets(5));

		if (!isLoggedIn) {
			everything.setBottom(createLogInPane());

		}

		else {
			everything.setBottom(createLogOutPane());
		}

	}

	private StackPane createLogOutPane() {
		StackPane logOutPane = new StackPane();
		Button logoutButton = new Button("log out");
		logoutButton.setAlignment(Pos.CENTER);
		logoutButton.setOnAction((e) -> {
			logout();
		});
		logOutPane.getChildren().add(logoutButton);
		logOutPane.setMargin(logoutButton, new Insets(5));
		return logOutPane;
	}

	private void logout() {
		saveChanges();
		isLoggedIn = false;
		currentUser = null;
		model.logOutUser();
		setPlaylist();
	}

	private ArrayList<Song> getSongs() {
		ArrayList<Song> songs = new ArrayList<Song>();
		String directoryPath = "songfiles";
		File directory = new File(directoryPath);
		File[] files = directory.listFiles();

		for (File file : files) {
			String fileName = file.getName();
			String fileNameOriginal = directoryPath + "/" + new String(fileName);
			String title = fileName.replace(".mp3", "");
			String artist = "";
			String durationString = "";

			try {
				AudioFile audioFile = AudioFileIO.read(file);
				Tag tag = audioFile.getTag();
				if (tag != null) {
					AudioHeader header = audioFile.getAudioHeader();
					int duration = header.getTrackLength();
					artist = tag.getFirst(FieldKey.ARTIST);
					durationString = String.format("%02d:%02d", getMinutes(duration), getSeconds(duration));
				}

				else {
					artist = "John";
					durationString = "Unknown";
				}

			}

			catch (CannotReadException | IOException | TagException | ReadOnlyFileException
					| InvalidAudioFrameException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			Song song = new Song(title, artist, durationString, fileNameOriginal);
			songs.add(song);

		}

		return songs;
	}

	private int getMinutes(int duration) {
		return duration / 60;
	}

	private int getSeconds(int duration) {
		int min = duration / 60;
		int sec = duration - 60 * min;

		return sec;
	}

	private void setLibraryView() {
		createFuncPaneLibraray();
		everything.setTop(funcPaneLibrary);
		libraryView = new PlaylistView(model.getLibrary().getSongs());
		everything.setCenter(libraryView);
		everything.setMargin(funcPaneLibrary, new Insets(5));
		everything.setBottom(null);
	}

	private synchronized void getUsers() {
		try {
			ObjectInputStream input = new ObjectInputStream(new FileInputStream("usersDB"));
			try {
				users = (HashMap<String, JukeboxAccount>) input.readObject();
				if (testMode) {
					HashMap<String, JukeboxAccount> testMap = new HashMap<String, JukeboxAccount>();
					for (String userName : users.keySet()) {
						testMap.put(userName, new JukeboxAccount(userName, users.get(userName).getPassword()));
					}

					users = testMap;
				}
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private synchronized void writeAccount(JukeboxAccount newAccount) {

		users.put(newAccount.getUsername(), newAccount);

		try {
			ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream("usersDB"));
			output.writeObject(users);
		}

		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private synchronized void saveChanges() {

		if (testMode)
			return;

		currentUser.setPlayList(model.getPlayList());

		users.put(currentUser.getUsername(), currentUser);

		try {
			ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream("usersDB"));
			output.writeObject(users);
		}

		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private StackPane createLogInPane() {
		StackPane loginPane = new StackPane();
		loginPane.setAlignment(Pos.CENTER);
		HBox loginOptions = new HBox(5);
		loginOptions.setAlignment(Pos.CENTER);

		Button login = new Button("Login");
		Button newAccount = new Button("New Account");

		login.setOnAction((e) -> {
			handleLogIn();
		});
		newAccount.setOnAction((e) -> {
			handleNewAccount();
		});

		loginOptions.getChildren().addAll(login, newAccount);
		loginPane.getChildren().addAll(loginOptions);

		loginPane.setMargin(loginOptions, new Insets(5));

		return loginPane;
	}

	private void handleLogIn() {
		everything.setCenter(createLogInExistingPane());
		everything.setTop(null);
		everything.setBottom(null);
	}

	private void handleNewAccount() {
		everything.setCenter(createLogInNewPane());
		everything.setTop(null);
		everything.setBottom(null);
	}

	private StackPane createLogInNewPane() {
		StackPane loginPane = new StackPane();
		loginPane.setAlignment(Pos.CENTER);
		VBox loginInput = new VBox(5);
		loginInput.setAlignment(Pos.CENTER);

		HBox userName = new HBox(5);
		HBox password = new HBox(5);
		HBox buttons = new HBox(5);

		Label userNameL = new Label("Username");
		Label passwordL = new Label("Password");

		Button login = new Button("Create");
		Button cancel = new Button("Go Back");

		TextField userNameT = new TextField();
		PasswordField passwordT = new PasswordField();

		userName.getChildren().addAll(userNameL, userNameT);
		password.getChildren().addAll(passwordL, passwordT);
		buttons.getChildren().addAll(login, cancel);
		buttons.setAlignment(Pos.CENTER);

		loginInput.getChildren().addAll(userName, password, buttons);
		loginPane.getChildren().add(loginInput);

		userName.setAlignment(Pos.CENTER);
		password.setAlignment(Pos.CENTER);

		login.setOnAction((e) -> {
			createNewUser(userNameT.getText(), passwordT.getText());
		});
		cancel.setOnAction((e) -> {
			setPlaylist();
		});
		return loginPane;
	}

	private void createNewUser(String username, String password) {
		JukeboxAccount newAccount = new JukeboxAccount(username, password);
		if (users.get(username) != null) {
			showWarning("The account already exists.");
			return;
		}

		writeAccount(newAccount);
		showWarning("Success. Your account is created");
		setPlaylist();
	}

	public void highlightSong() {

		playlistView.highlightSong();
	}

	private StackPane createLogInExistingPane() {
		StackPane loginPane = new StackPane();
		loginPane.setAlignment(Pos.CENTER);
		VBox loginInput = new VBox(5);
		loginInput.setAlignment(Pos.CENTER);

		HBox userName = new HBox(5);
		HBox password = new HBox(5);
		HBox buttons = new HBox(5);

		Label userNameL = new Label("Username");
		Label passwordL = new Label("Password");

		Button login = new Button("Login");
		Button cancel = new Button("Go Back");

		TextField userNameT = new TextField();
		PasswordField passwordT = new PasswordField();

		userName.getChildren().addAll(userNameL, userNameT);
		password.getChildren().addAll(passwordL, passwordT);
		buttons.getChildren().addAll(login, cancel);
		buttons.setAlignment(Pos.CENTER);

		loginInput.getChildren().addAll(userName, password, buttons);
		loginPane.getChildren().add(loginInput);

		userName.setAlignment(Pos.CENTER);
		password.setAlignment(Pos.CENTER);

		login.setOnAction((e) -> {
			verifyLogin(userNameT.getText(), passwordT.getText());
		});
		cancel.setOnAction((e) -> {
			setPlaylist();
		});
		return loginPane;
	}

	private void verifyLogin(String username, String password) {
		JukeboxAccount logedUser = users.get(username);
		if (logedUser != null) {

			if (logedUser.getPassword().equals(password)) {
				currentUser = logedUser;
				isLoggedIn = true;
				if (!currentUser.getLatestDate().equals(LocalDate.now())) {
					model.setPlayList(new PlayList());
					currentUser.resetLimit();
				} else {
					model.setPlayList(logedUser.getActiveSongs());
				}

				setPlaylist();
			}

			else {
				showWarning("Your credentials are not valid.");
			}
		}

		else {
			showWarning("User name does not exist.");

		}
	}

	private void createFuncPanePlayList() {
		funcPanePlaylist = new StackPane();

		HBox buttonRow = new HBox(5);

		Button addButton = new Button("Add from Library");
		addButton.setOnAction((e) -> {
			addPlaylist();
		});
		Button playButton = new Button("Play");
//		Button testButton = new Button("Play Selected Test.");
//		testButton.setOnAction((e) -> {
//			testHandle();
//		});
		playButton.setOnAction((e) -> {
			update();
			playHandle();
		});
		Button pauseButton = new Button("Pause");
		pauseButton.setOnAction((e) -> {
			if (isLoggedIn) {
				model.pause();
			} else
				showWarning("Please log in.");
		});
		buttonRow.getChildren().addAll(addButton, playButton, pauseButton);

		if (isLoggedIn) {
			Label userGreeting = new Label("Hello, " + currentUser.getUsername());
			if (testMode) {
				userGreeting = new Label(
						"Hello, " + currentUser.getUsername() + "." + " It is a clean slate for test.");
			}
			buttonRow.getChildren().add(userGreeting);
		}

		funcPanePlaylist.getChildren().add(buttonRow);
	}

	private void addPlaylist() {
		if (isLoggedIn && currentUser.hasAnyCredit()) {
			setLibraryView();
		}

		else if (!isLoggedIn) {
			showWarning("Please log in.");
		}

		else {
			showWarning("You have used your credits for today.");
		}
	}

	private void testHandle() {
		if (isLoggedIn) {
			model.playSelected(playlistView.getSelected());
		}

		else if (!isLoggedIn) {
			showWarning("Please log in.");
		}

	}

	private void playHandle() {
		if (model.getPlayList().size() == 0) {
			showWarning("Nothing to play.");
		}

		if (isLoggedIn) {
			model.play();
		}

		else if (!isLoggedIn) {
			showWarning("Please log in.");
		}
	}

	private void showWarning(String warning) {
		Dialog<String> dialog = new Dialog<String>();
		dialog.setTitle("Warning");
		ButtonType type = new ButtonType("OK", ButtonData.OK_DONE);
		dialog.setContentText(warning);
		dialog.getDialogPane().getButtonTypes().add(type);
		dialog.showAndWait();
	}

	private void createFuncPaneLibraray() {
		funcPaneLibrary = new StackPane();

		HBox buttonRow = new HBox(5);
		Button addNewButton = new Button("Add");
		addNewButton.setOnAction((e) -> {
			everything.setBottom(createNewSongIntake());
		});
		Button addButton = new Button("Move to Playlist");
		addButton.setOnAction((e) -> {
			HandleSelectedAdd();
		});
		Button doneButton = new Button("Done");
		doneButton.setOnAction((e) -> {
			setPlaylist();
		});
		buttonRow.getChildren().addAll(addNewButton, addButton, doneButton);

		funcPaneLibrary.getChildren().add(buttonRow);
	}

	private void HandleSelectedAdd() {
		Song selectedSong = libraryView.getSelected();

		if (selectedSong != null) {
			if (currentUser.hasAnyCredit()) {
				showWarning("Success. The song was added to your playlist.");
				currentUser.decrementLimit();
				model.addToPlayList(selectedSong);
				setLibraryView();

			}

			else {
				showWarning("You don't have any credits left.");
			}
		}

		else {
			showWarning("Please select a song.");
		}
	}

	private StackPane createNewSongIntake() {
		StackPane songIntakePane = new StackPane();
		songIntakePane.setAlignment(Pos.CENTER);

		HBox intakeInfo = new HBox(5);
		Label titleLabel = new Label("Title");
		Label artistLabel = new Label("Artist");
		Label urlLabel = new Label("Location");
		intakeInfo.setAlignment(Pos.CENTER);

		TextField titleText = new TextField();
		TextField artistText = new TextField();
		TextField urlText = new TextField();

		HBox intakeButtons = new HBox(5);
		Button saveButton = new Button("Save");
		saveButton.setOnAction((e) -> {
			saveNewSong(titleText.getText(), artistText.getText(), urlText.getText());
		});
		Button cancelButton = new Button("Cancel");
		intakeButtons.getChildren().addAll(saveButton, cancelButton);
		intakeInfo.getChildren().addAll(titleLabel, titleText, artistLabel, artistText, urlLabel, urlText);
		intakeButtons.setAlignment(Pos.CENTER);

		VBox rows = new VBox(5);
		rows.getChildren().addAll(intakeInfo, intakeButtons);
		songIntakePane.getChildren().add(rows);

		return songIntakePane;
	}

	private synchronized void saveNewSong(String title, String artist, String url) {
		Song newSong = new Song(title, artist, url);
		model.addToLibrary(newSong);
		setLibraryView();
	}

	public void update() {
		setPlaylist();
		highlightSong();
	}

}