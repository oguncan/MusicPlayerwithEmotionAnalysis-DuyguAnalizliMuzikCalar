package com.example.joousope.bakdinle.Model.Fragment;

public class Song {
    public String ImageSong;
    public String NameSong;
    public Song(){
    }
    public Song(String image, String name) {
        ImageSong = image;
        NameSong = name;
    }

    public String getImage() {
        return ImageSong;
    }

    public void setImage(String image) {
        ImageSong = image;
    }

    public String getName() {
        return NameSong;
    }

    public void setName(String name) {
        NameSong = name;
    }
}
