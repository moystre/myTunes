/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.mytunesapp.GUI.Controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ObservableObjectValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import pl.mytunesapp.BE.PlayList;
import pl.mytunesapp.BE.Song;
import pl.mytunesapp.BLL.MainAppManager;
import pl.mytunesapp.GUI.Model.MainAppModel;
import pl.mytunesapp.MainApp;
import pl.mytunesapp.GUI.Controller.EditSongDialogController;


/**
 * FXML Controller class
 *
 * @author MSI GS40 6QE
 */

public class MainViewController implements Initializable {
private MainApp mainApp = new MainApp();
private MainAppModel model = new MainAppModel();
public static MainViewController ViewController;

    @FXML
    private Label playListTxtHolder;
    @FXML
    private Label songTxtHolder;
    @FXML
    private TableView<PlayList> playListTableView;
    @FXML
    private TableColumn<PlayList, String> playlistNameColumn;
    @FXML
    private TableColumn<PlayList, Integer> playlistSongsAmountColumn;
    @FXML
    private TableColumn<PlayList, String> playlistTimeColumn;
    @FXML
    private Button newPlaylistButton;
    @FXML
    private Button editPlaylistButton;
    @FXML
    private Button deletePlaylistButton;
    @FXML
    private Button playButton;
    @FXML
    private Button nextButton;
    @FXML
    private Button previousButton;
    @FXML
    private Slider volume;
    @FXML
    private ListView<Song> listOfSongsInPlaylistView;
    @FXML
    private Button deleteSongFromPlaylistButton;
    @FXML
    private Label songPlayedLabel;
    @FXML
    private ProgressBar songProgress;
    @FXML
    private Button addSongToPlaylistButton;
    
    @FXML
    private Button moveSongDownButton;
    @FXML
    private Button moveSongUpButton;
    @FXML
    private TextField searchField;
    @FXML
    private TableView<Song> allSongsTableView;
    @FXML
    private TableColumn<Song, String> titleColumn;
    @FXML
    private TableColumn<Song, String> artistColumn;
    @FXML
    private TableColumn<Song, String> categoryColumn;
    @FXML
    private TableColumn<Song, Integer> timeColumn;
    @FXML
    private Button newSongButton;
    @FXML
    private Button deleteSongButton;
    @FXML
    private Button editSongButton;
    @FXML
    private Button closeButton;
    @FXML
    private Button searchButton;


    ObservableList<Song> tableAllSongsContent =  MainAppModel.getInstance().getAllSongsList();
    ObservableList<PlayList> tableAllPlayListsContent =  MainAppModel.getInstance().getAllPlayListsList();


    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        disableButtons();
        fixTableViews();
        ViewController=this;

    }    
    public void refreshSongsView()
    {
        allSongsTableView.refresh();
    }
    public void refreshPlaylistsView()
    {
        playListTableView.refresh();
    }
        @FXML
    private void addSongToPlaylist(ActionEvent event) {
    model.addSongToPlaylistList(returnPlayListsTableSelection(), returnAllSongsTableSelection());

    }

    @FXML
    private void newPlaylist(ActionEvent event) {
         try {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/pl/mytunesapp/GUI/View/AddPlaylistDialogFXML.fxml"));
                Parent root1 = (Parent) fxmlLoader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root1));  
                stage.show();
        } catch(Exception e) {
           e.printStackTrace();
          }
    }

    @FXML
    private void editPlaylist(ActionEvent event) {
         try {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/pl/mytunesapp/GUI/View/EditPlaylistDialogFXML.fxml"));
               // fxmlLoader.setController(new EditController(allSongsTableView.getSelectionModel().getSelectedItem()));
               
                Parent root2 = (Parent) fxmlLoader.load();
                
                EditPlaylistDialogController editcontroller = fxmlLoader.getController();
                editcontroller.setPlayList(playListTableView.getSelectionModel().getSelectedItem());
                Stage stage = new Stage();
                Scene scene = new Scene(root2);
                stage.setScene(scene);
                stage.show();
                
        } catch(Exception e) {
           e.printStackTrace();
          }
    }
    
    @FXML
    private void deletePlaylist(ActionEvent event) {
        PlayList selectedPlayList = playListTableView.getSelectionModel().getSelectedItem();
        if(selectedPlayList!=null)
        playListTableView.getItems().remove(selectedPlayList);
    }
    
    @FXML
    private void play(ActionEvent event) {

    }

    @FXML
    private void next(ActionEvent event) {
    }

    @FXML
    private void previous(ActionEvent event) {
    }

    @FXML
    private void deleteSongFromPlaylist(ActionEvent event) {
    }



    @FXML
    private void moveSongDown(ActionEvent event) {
    }

    @FXML
    private void moveSongUp(ActionEvent event) {
    }

    @FXML
    private void newSong(ActionEvent event) {               
       try {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/pl/mytunesapp/GUI/View/AddSongDialogFXML.fxml"));
                Parent root1 = (Parent) fxmlLoader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root1));  
                stage.show();
        } catch(Exception e) {
           e.printStackTrace();
          }
    }

    private void clearAllSongsTableSelection() {
        allSongsTableView.getSelectionModel().clearSelection();
    }
    private void clearPlayListsTableSelection() {
        playListTableView.getSelectionModel().clearSelection();
    }
    public PlayList returnPlayListsTableSelection() {
        return playListTableView.getSelectionModel().getSelectedItem();
    }    
    public Song returnAllSongsTableSelection() {
        return allSongsTableView.getSelectionModel().getSelectedItem();
    }    
    public ObservableList<Song> getSongsInPlaylist()
            {
               return model.getAllSongsFromPlaylist(returnPlayListsTableSelection());
            }
    @FXML
    private void deleteSong(ActionEvent event) {
        Song selectedSong = allSongsTableView.getSelectionModel().getSelectedItem();
        if(selectedSong!=null)
        allSongsTableView.getItems().remove(selectedSong);
    }
    
    

    @FXML
    private void editSong(ActionEvent event) {
         try {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/pl/mytunesapp/GUI/View/EditSongDialogFXML.fxml"));
               // fxmlLoader.setController(new EditController(allSongsTableView.getSelectionModel().getSelectedItem()));
               
                Parent root1 = (Parent) fxmlLoader.load();
                
                EditSongDialogController editcontroller = fxmlLoader.getController();
                editcontroller.setSong(allSongsTableView.getSelectionModel().getSelectedItem());
                Stage stage = new Stage();
                Scene scene = new Scene(root1);
                stage.setScene(scene);
                stage.show();
        } catch(Exception e) {
           e.printStackTrace();
          }
             
    }
    

    @FXML
    private void close(ActionEvent event) {
    }

    @FXML
    private void search(ActionEvent event) {
    }
    
    
    
    
    private void fixTableViews(){
                allSongsTableView.itemsProperty().setValue(tableAllSongsContent);
        titleColumn.setCellValueFactory(
                new PropertyValueFactory<Song, String>("Title")
        );
         artistColumn.setCellValueFactory(
                new PropertyValueFactory<Song, String>("Artist")
        );
          categoryColumn.setCellValueFactory(
                new PropertyValueFactory<Song, String>("Category")
        );
           timeColumn.setCellValueFactory(
                new PropertyValueFactory<Song, Integer>("Time")
        );
           
           playListTableView.itemsProperty().setValue(tableAllPlayListsContent);
           
           playlistNameColumn.setCellValueFactory(
                   new PropertyValueFactory<PlayList, String>("Name"));
           playlistTimeColumn.setCellValueFactory(
                   new PropertyValueFactory<PlayList, String>("Duration"));
           playlistSongsAmountColumn.setCellValueFactory(
                   new PropertyValueFactory<PlayList, Integer>("NumberOfSongs"));
    }
    private void disableButtons(){
        editPlaylistButton.disableProperty().bind(Bindings.isEmpty(playListTableView.getSelectionModel().getSelectedItems()));
        deletePlaylistButton.disableProperty().bind(Bindings.isEmpty(playListTableView.getSelectionModel().getSelectedItems()));
        editSongButton.disableProperty().bind(Bindings.isEmpty(allSongsTableView.getSelectionModel().getSelectedItems()));
        deleteSongButton.disableProperty().bind(Bindings.isEmpty(allSongsTableView.getSelectionModel().getSelectedItems()));
    }
}
