package com.zaid.music.service;

import com.zaid.music.model.Song;

public class PlayerService {
    private Song currentSong;
    private boolean isPlaying;

    // Initialising the music player at start there is no song hence, null value and isPlaying is also false
    public PlayerService() {
        this.currentSong = null;
        this.isPlaying = false;
    }
    // To see which song is currently played
    public Song getCurrentSong() {
        return currentSong;
    }
    // To check the status of play/pause
    public boolean isPlaying() {
        return isPlaying;
    }

    // To start the song play
    public void play(Song song){
        // To see if song is null or not
        if(song == null){
            throw new IllegalArgumentException("Song cannot be null");
        }

        // Updating the values
        this.isPlaying = true;
        this.currentSong = song;

        System.out.println("Playing " + currentSong.getTitle());
    }

    public void pause(){
        // To see if the song is null or not
        if(currentSong == null){
            System.out.println("No song to stop");
            return;
        }
        // Updating the value
        this.isPlaying = false;
        System.out.println("Paused " + currentSong.getTitle());
    }

    public void resume(){
        // To see if song is null or not
        if (currentSong == null){
            System.out.println("No song to resume");
            return;
        }
        // To see if song is playing or not
        if(isPlaying){
            System.out.println("Song already playing");
            return;
        }
        // Updating the values
        isPlaying = true;
        System.out.println("Resumed "+currentSong.getTitle());
    }

}
