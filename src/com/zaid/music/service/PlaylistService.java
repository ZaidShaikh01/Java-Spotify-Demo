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
        if(user == null || name ==null || name.isEmpty()){
            throw new IllegalArgumentException("Invalid input");
        }

        Playlist playlist = new Playlist(playlistIdCounter++,name);
        user.addPlaylist(playlist);
        return playlist;
    }

    // addSongToPlaylist()
    public void addSongToPlaylist(User user,int playlistId,int songId) throws SongNotFoundException {

        if (playlistId <= 0) {
            throw new IllegalArgumentException("Invalid playlist ID");
        }
        if(songId <=0){
            throw new IllegalArgumentException("Invalid song ID");
        }
        Playlist playlist = getPlaylistOrThrow(user,playlistId);
        Song song = service.getSongById(songId);
        playlist.addSong(song);
    }


    // removeSongFromPlaylist()
    public void removeSongFromPlaylist(User user,int playlistId,int songId) throws SongNotFoundException {
        if (playlistId <= 0) {
            throw new IllegalArgumentException("Invalid playlist ID");
        }
        if(songId <=0){
            throw new IllegalArgumentException("Invalid song ID");
        }
        Playlist playlist = getPlaylistOrThrow(user,playlistId);
        Song song = service.getSongById(songId);
        playlist.removeSong(song);
    }

    // Get playlist
    public Playlist getPlaylist(User user,int playlistId){
        if (playlistId <= 0) {
            throw new IllegalArgumentException("Invalid playlist ID");
        }
        return getPlaylistOrThrow(user,playlistId);
    }

    // Get Playlist or throw
    private Playlist getPlaylistOrThrow(User user, int playlistId) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }

        for (Playlist p : user.getPlaylists()) {
            if (p.getId() == playlistId) {
                return p;
            }
        }

        throw new IllegalArgumentException("Playlist not found");
    }


}
