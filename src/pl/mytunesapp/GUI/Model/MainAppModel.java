/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.mytunesapp.GUI.Model;

import java.util.List;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener.Change;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import pl.mytunesapp.BE.PlayList;
import pl.mytunesapp.BE.Song;
import pl.mytunesapp.GUI.Controller.MainViewController;

/**
 *
 * @author MSI GS40 6QE
 */
public class MainAppModel {
    

   
    
    private static MainAppModel instance;
    public static MainAppModel getInstance()
    {
        if(instance==null)
            instance = new MainAppModel();
        return instance;
    }
    
    private final ObservableList<Song> allSongsList;
    private final ObservableList<PlayList> allPlayListsList;
    private final ObservableList<Song> allSongsInPlaylist;
    public MainAppModel()
    {
        this.allSongsList = FXCollections.observableArrayList(Song ->
            new Observable[] {
                    Song.titleProperty(),
                    Song.artistProperty(),
                    Song.categoryProperty(),
                    Song.timeProperty(),
                    Song.filePathProperty() 
            });
        allSongsList.addListener((Change<? extends Song> c) -> {
            while (c.next()) {
                  if (c.wasUpdated()) {
                  MainViewController.ViewController.refreshSongsView();

               }
                  
           }
        });
            
        this.allPlayListsList = FXCollections.observableArrayList(PlayList ->
            new Observable[] {
                    PlayList.nameProperty(),
                    PlayList.durationProperty(),
                    PlayList.numberOfSongsProperty(),
                    PlayList.getListOfSongs()
            });
        allPlayListsList.addListener((Change<? extends PlayList> c) -> {
            while (c.next()) {
                  if (c.wasUpdated()) {
                  int numberOfSongsInPlaylist = MainViewController.ViewController.returnPlayListsTableSelection().getListOfSongs().size();
                  MainViewController.ViewController.returnPlayListsTableSelection().setNumberOfSongs(numberOfSongsInPlaylist);
                  MainViewController.ViewController.refreshPlaylistsView(); 
                  }
                  
                  
                 
                  
           }
        });
        
        this.allSongsInPlaylist = FXCollections.observableArrayList();
    }
    
    public ObservableList<Song> getAllSongsList()
    {
        return allSongsList;
    }
    
    public ObservableList<PlayList> getAllPlayListsList()
    {
        return allPlayListsList;
    }
    
    public ObservableList<Song> getAllSongsInPlayListsList()
    {
        return allSongsInPlaylist;
    }
    
    public void addSong(Song song)
    {
        allSongsList.add(song);
    }
    
    public void addPlayList(PlayList playlist)
    {
        allPlayListsList.add(playlist);
    }
    
    
    public ObservableList<Song> getAllSongsFromPlaylist(PlayList playlist)
    {
       return playlist.getListOfSongs();
    }
    
     public void addSongToPlaylistList(PlayList playlist, Song song)
    {
       if(song!=null && playlist!=null)
       playlist.addSong(song);
    }
}
