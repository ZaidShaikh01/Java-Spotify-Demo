package com.zaid.music.service;

import com.zaid.music.exception.SongNotFoundException;
import com.zaid.music.model.Playlist;
import com.zaid.music.model.Song;
import com.zaid.music.model.User;


public class PlaylistService {
    private MusicService service;
    private int playlistIdCounter = 1;


    // constructor
    public PlaylistService(MusicService service) {
        this.service = service;
    }


    // createPlaylist()
    public Playlist createPlaylist(User user, String name){
        // Checking whether all the inputs are valid or not
        if(user == null || name ==null || name.isEmpty()){
            throw new IllegalArgumentException("Invalid input");
        }
        // Initalizing a playlist and incrementing the playlistId counter
        Playlist playlist = new Playlist(playlistIdCounter++,name);
        // Adding the playlist to playlists list in user model
        user.addPlaylist(playlist);
        return playlist;
    }

    // addSongToPlaylist()
    public void addSongToPlaylist(User user,int playlistId,int songId) throws SongNotFoundException {
        // To see if Id is valid or not
        if (playlistId <= 0) {
            throw new IllegalArgumentException("Invalid playlist ID");
        }
        if(songId <=0){
            throw new IllegalArgumentException("Invalid song ID");
        }
        // Getting the playlist from user's playlists list
        Playlist playlist = getPlaylistOrThrow(user,playlistId);
        // Adding the song to the playlist
        Song song = service.getSongById(songId);
        playlist.addSong(song);
    }


    // removeSongFromPlaylist()
    public void removeSongFromPlaylist(User user,int playlistId,int songId) throws SongNotFoundException {
        // To see if ID is valid or not
        if (playlistId <= 0) {
            throw new IllegalArgumentException("Invalid playlist ID");
        }
        if(songId <=0){
            throw new IllegalArgumentException("Invalid song ID");
        }
        // Getting the playlist from user's playlists list
        Playlist playlist = getPlaylistOrThrow(user,playlistId);
        // Getting the song from song repo
        Song song = service.getSongById(songId);
        // Removing the song from playlist
        playlist.removeSong(song);
    }

    // Get playlist
    public Playlist getPlaylist(User user,int playlistId){
        // To see if ID is valid or not
        if (playlistId <= 0) {
            throw new IllegalArgumentException("Invalid playlist ID");
        }
        // Returning the playlist
        return getPlaylistOrThrow(user,playlistId);
    }

    // Get Playlist or throw
    private Playlist getPlaylistOrThrow(User user, int playlistId) {
        // To see if user value is null or not
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }

        // Iterating through playlists & searching for the playlist that matches the playlistId
        for (Playlist p : user.getPlaylists()) {
            if (p.getId() == playlistId) {
                return p;
            }
        }
        // if not playlist is available in user list
        throw new IllegalArgumentException("Playlist not found");
    }


}
