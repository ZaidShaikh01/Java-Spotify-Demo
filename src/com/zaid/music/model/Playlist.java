package com.zaid.music.model;

import java.util.ArrayList;
import java.util.List;

public class Playlist {
    private int id;
    private String title;
    private List<Song> songs;
    // Constructor for playlist
    public Playlist(int id, String title) {
        this.id = id;
        this.title = title;
        this.songs = new ArrayList<Song>();
    }
    // Getter for id
    public int getId() {
        return id;
    }


    // Getter for title
    public String getTitle() {
        return title;
    }


    // Getter for list of songs
    public List<Song> getSongs() {
        return songs;
    }


    // Add song method
    public void addSong(Song song){
        if (song == null) {
            throw new IllegalArgumentException("Song cannot be null");
        }
        songs.add(song);
    }
    // Remove song method
    public void removeSong(Song song){
        // Todo to check whether a song exist or not, try catch
        songs.remove(song);
    }
    // Overriding toStringMethod
    @Override
    public String toString() {
        return "Playlist " + id + ": " + title + " (" + songs.size() + " songs)";
    }


}
