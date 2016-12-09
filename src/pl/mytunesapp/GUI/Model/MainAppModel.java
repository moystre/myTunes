/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.mytunesapp.GUI.Model;

import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pl.mytunesapp.BE.Song;

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
    public MainAppModel()
    {
        this.allSongsList = FXCollections.observableArrayList();
    }
     
    public ObservableList<Song> getAllSongsList()
    {
        return allSongsList;
    }
    
    public void addSong(Song song)
    {
        allSongsList.add(song);
 
    }
}
