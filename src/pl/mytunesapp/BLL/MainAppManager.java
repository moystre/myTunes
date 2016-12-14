/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.mytunesapp.BLL;

import java.util.ArrayList;
import java.util.List;
import pl.mytunesapp.BE.PlayList;
import pl.mytunesapp.BE.Song;
import pl.mytunesapp.GUI.Controller.MainViewController;


/**
 *
 * @author MSI GS40 6QE
 */
public class MainAppManager {

    public MainAppManager(){
        
    }
    
    public void addSong(String artist, String category, String title, String fpath, int time)
    {
       Song newSong = new Song(title, artist, category, time, fpath);
       MainViewController.ViewController.addSong(newSong);
       //bindowanie zeby wyswietlac w liscie z miejsca
    }
    
    public void addPlayList(String name)
    {
       PlayList playlist = new PlayList();
       playlist.setName(name);
       playlist.setDuration("0");
       playlist.setNumberOfSongs(0);
       playlist.setListOfSongs(null);
       if(name.length()!=0)
       MainViewController.ViewController.addPlayList(playlist);
      // MainViewController.getInstance().addPlayList(playlist);
       
       //bindowanie zeby wyswietlac w liscie z miejsca
    }

   
}
