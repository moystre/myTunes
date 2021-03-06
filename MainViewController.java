/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.mytunesapp.GUI.Controller;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableObjectValue;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
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
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import pl.mytunesapp.BE.PlayList;
import pl.mytunesapp.BE.Song;
import pl.mytunesapp.BLL.MainAppManager;
import pl.mytunesapp.MainApp;
import pl.mytunesapp.GUI.Controller.EditSongDialogController;



/**
 * FXML Controller class
 *
 * @author MSI GS40 6QE
 */

public class MainViewController implements Initializable {
private MainApp mainApp = new MainApp();
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
    public TableView<Song> allSongsTableView;
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

     ObservableList<Song> allSongsList = FXCollections.observableArrayList(Song ->
            new Observable[] {
                    Song.titleProperty(),
                    Song.artistProperty(),
                    Song.categoryProperty(),
                    Song.timeProperty(),
                    Song.filePathProperty() 
            });
    
     ObservableList<PlayList> allPlayListsList = FXCollections.observableArrayList();

   
     
     MediaPlayer mediaPlayer;
     Song currentSong;
     String path;
    
     private void setMediaPlayer()
     {
           currentSong = allSongsTableView.getSelectionModel().getSelectedItem();
           path = currentSong.getFilePath();
            
            File bip = new File(path);
            Media hit;
        
           try
           {
               hit = new Media(bip.toURI().toURL().toString());
               mediaPlayer = new MediaPlayer(hit);
           } catch (MalformedURLException ex)
           {
               Logger.getLogger(MainViewController.class.getName()).log(Level.SEVERE, null, ex);
           }
     }
     
    
    
   // private MainAppModel model = new MainAppModel();
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        disableButtons();
        fixTableViews();
        fixListeners();
        ViewController = this;
        
        
        
        
          volume.valueProperty().addListener(new ChangeListener<Number>() 
         {
             @Override 
             public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue)
             {
                    mediaPlayer.setVolume(volume.getValue()/100);
                    //System.out.println(mediaPlayer.getVolume());
             }
        });
          }    
    
    
    public void refreshSongs()
    {
        allSongsTableView.refresh();
    }
        @FXML
    private void addSongToPlaylist(ActionEvent event) {
    PlayList playlist = returnPlayListsTableSelection();
    Song song = returnAllSongsTableSelection();
           if(song!=null && playlist!=null) 
           playlist.addSong(song);
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
    private void play(ActionEvent event) 
    {
       
        mediaPlayer.setAutoPlay(true);
        
         if(allSongsTableView.getSelectionModel().getSelectedItem() == currentSong)
         {
            if(mediaPlayer.getStatus()==MediaPlayer.Status.PLAYING)
              mediaPlayer.pause();
            else if(mediaPlayer.getStatus()==MediaPlayer.Status.PAUSED)
           mediaPlayer.play();
           
         }
        else if(allSongsTableView.getSelectionModel().getSelectedItem() != currentSong)
         {
           if(mediaPlayer.getStatus()!=MediaPlayer.Status.PLAYING)
               {   mediaPlayer.stop();//WHY U NO
                   setMediaPlayer();
                   mediaPlayer.play();}
            else if(mediaPlayer.getStatus()!=MediaPlayer.Status.PAUSED)
            {       setMediaPlayer(); 
                   mediaPlayer.play();
                         }}
    }
         

    @FXML
    public void next(ActionEvent event) {
       mediaPlayer.stop();
       allSongsTableView.getSelectionModel().selectNext();
       setMediaPlayer();
       mediaPlayer.play();
    }

    @FXML
    private void previous(ActionEvent event) {
       mediaPlayer.stop();
       allSongsTableView.getSelectionModel().selectPrevious();
       setMediaPlayer();
       mediaPlayer.play();
    }

    @FXML
    private void deleteSongFromPlaylist(ActionEvent event) {
    }



    @FXML
    private void moveSongDown(ActionEvent event) {
        System.out.println(        allSongsTableView.getItems().get(0).getTitle()
);
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

    public void clearAllSongsTableSelection() {
        allSongsTableView.getSelectionModel().clearSelection();
    }
    public void clearPlayListsTableSelection() {
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
        PlayList playlist = returnPlayListsTableSelection();
        return playlist.getListOfSongs();
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
    
    public void addSong(Song song)
    {
        if(allSongsList.isEmpty()) allSongsTableView.getSelectionModel().select(song);
        this.allSongsList.add(song);
        setMediaPlayer();
    }
    
    public void addPlayList(PlayList playlist)
    {
        this.allPlayListsList.add(playlist);
        System.out.println("size po add Playlist"+allPlayListsList.size());
    }
    private void fixListeners(){
        allSongsList.addListener((ListChangeListener.Change<? extends Song> c) -> {
            while (c.next()) {
                  if (c.wasUpdated()) {
                   refreshSongs();
               }    
           }
        });
    }
    private void fixTableViews(){
 
                allSongsTableView.itemsProperty().setValue(allSongsList);
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
           
           playListTableView.itemsProperty().setValue(allPlayListsList);
           
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
