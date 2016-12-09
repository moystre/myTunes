/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.mytunesapp.BLL;

import java.util.ArrayList;
import java.util.List;
import pl.mytunesapp.BE.Song;
import pl.mytunesapp.GUI.Model.MainAppModel;

/**
 *
 * @author MSI GS40 6QE
 */
public class MainAppManager {

private Song newSong;
   
   private static MainAppManager instance;
    public static MainAppManager getInstance()
    {
        if(instance==null)
            instance = new MainAppManager();
        return instance;
    }
    
    private MainAppManager(){
        
    }
    
    public void addSong(String artist, String category, String title, String fpath, int time)
    {
       newSong = Song.getInstance();
       newSong.setArtist(artist);
       newSong.setTime(time);
       newSong.setCategory(category);
       newSong.setTitle(title);
       newSong.setFilePath(fpath);
       MainAppModel.getInstance().addSong(newSong);
       
       //bindowanie zeby wyswietlac w liscie z miejsca
       
    }
    
    
   
    
   
}
