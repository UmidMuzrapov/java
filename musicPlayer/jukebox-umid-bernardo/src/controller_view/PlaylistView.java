/**
 * @author Umid Muzrapov, Second Author Jose Bernardo Montano Peralta
 */
package controller_view;

import java.util.ArrayList;

import javafx.scene.control.SelectionModel;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import model.Song;

public class PlaylistView extends StackPane {

  private TableView<Song> songTable;

  public PlaylistView(ArrayList<Song> songs) {
    super();
    getChildren().add(createTable(songs));
  }

  public Song getSelected() {
    SelectionModel<Song> selectionModel = songTable.getSelectionModel();

    if (!selectionModel.isEmpty()) {
      return selectionModel.getSelectedItem();
    } else return null;
  }

  private TableView createTable(ArrayList<Song> songs) {

    songTable = new TableView<Song>();
    songTable = new TableView<Song>();

    TableColumn<Song, String> column1 = new TableColumn<>("Title");
    TableColumn<Song, String> column2 = new TableColumn<>("Artist");
    TableColumn<Song, String> column3 = new TableColumn<>("Time");

    column1.setMinWidth(120);
    column2.setMinWidth(120);
    column3.setMinWidth(60);

    column1.setCellValueFactory(new PropertyValueFactory<>("title"));
    column2.setCellValueFactory(new PropertyValueFactory<>("artist"));
    column3.setCellValueFactory(new PropertyValueFactory<>("time"));

    songTable.getColumns().addAll(column1, column2, column3);
    StackPane pane = new StackPane();

    songTable.setRowFactory(
        tv -> {
          TableRow<Song> row = new TableRow<>();
          row.selectedProperty()
              .addListener(
                  (obs, wasSelected, isNowSelected) -> {
                    if (isNowSelected) {
                      row.setStyle("-fx-background-color: gray;");
                    } else {
                      row.setStyle("");
                    }
                  });
          return row;
        });

    songTable.getItems().addAll(songs);
    songTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

    return songTable;
  }

  public void highlightSong() {
    // Find the index of the song in the table view
    SelectionModel<Song> selectionModel = songTable.getSelectionModel();
    selectionModel.select(0);
  }
}
