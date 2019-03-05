package com.example.joousope.bakdinle;
import android.support.annotation.Keep;
import android.widget.Button;

import com.google.firebase.database.Exclude;
import java.util.HashMap;
import java.util.Map;


public class SongInfo {
    private String SongCategoryId;
    public String songName;
    public String artistName;
    public String songUrl;

    public SongInfo()
    {

    }
    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getSongUrl() {
        return songUrl;
    }

    public void setSongUrl(String songUrl) {
        this.songUrl = songUrl;
    }

    public String getSongCategoryId() {
        return SongCategoryId;
    }

    public void setSongCategoryId(String SongCategoryId) {
        SongCategoryId = SongCategoryId;
    }

    public SongInfo(String songName, String artistName, String songUrl, String SongCategoryId) {

        this.songName = songName;
        this.artistName = artistName;
        this.songUrl = songUrl;
        SongCategoryId = SongCategoryId;
    }

    /*public Map<String,Object> toMap(){
        HashMap<String,Object> result=new HashMap<>();
        result.put("SongName",songName);
        result.put("ArtistName",artistName);
        result.put("URL",songUrl);
        result.put("")
        return result;
    }*/
}
