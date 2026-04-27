package com.zaid.music.service;

import com.zaid.music.model.Song;

public class PlayerService {
    private Song currentSong;
    private boolean isPlaying;

    public PlayerService() {
        this.currentSong = null;
        this.isPlaying = false;
    }

    public Song getCurrentSong() {
        return currentSong;
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public void play(Song song){
        if(song == null){
            throw new IllegalArgumentException("Song cannot be null");
        }
        this.isPlaying = true;
        this.currentSong = song;

        System.out.println("Playing " + currentSong.getTitle());
    }

    public void pause(){
        if(currentSong == null){
            System.out.println("No song to stop");
            return;
        }
        this.isPlaying = false;
        System.out.println("Paused " + currentSong.getTitle());
    }

    public void resume(){
        if (currentSong == null){
            System.out.println("No song to resume");
            return;
        }
        if(isPlaying){
            System.out.println("Song already playing");
            return;
        }
        isPlaying = true;
        System.out.println("Resumed "+currentSong.getTitle());
    }

}
