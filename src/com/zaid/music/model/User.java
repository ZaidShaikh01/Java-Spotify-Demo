package com.zaid.music.model;
import java.util.ArrayList;
import java.util.List;

public class User {
    private int id;
    private String userName;

    private List<Playlist> playlists;

    // Constructor for User
    public User(int id, String userName) {
        this.id = id;
        this.userName = userName;

        this.playlists = new ArrayList<>();
    }

    // Getter for id
    public int getId() {
        return id;
    }

    // Getter for userName
    public String getUserName() {
        return userName;
    }


    // Getter for playlists
    public List<Playlist> getPlaylists() {
        return playlists;
    }

    //    Method to add a playlist
    public void  addPlaylist(Playlist playlist){
        playlists.add(playlist);
    }
    // Method to remove a playlist from the list

    public void removePlaylist(Playlist playlist){
        playlists.remove(playlist);
    }

    @Override
    public String toString() {
        return "User " + id + ": " + userName +
                " | Playlists: " + playlists.size();
    }


}
