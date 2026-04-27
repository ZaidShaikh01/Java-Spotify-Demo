package com.zaid.music.model;

public class Song {
    private int songId;
    private String title;
    private int duration;
    private String artist;

    // Constructor
    public Song(int songId, String title, int duration, String artist) {
        this.songId = songId;
        this.title = title;
        this.duration = duration;
        this.artist = artist;
    }

    // Getter for songId
    public int getSongId() {
        return songId;
    }

    // Getter for title
    public String getTitle() {
        return title;
    }

    // Getter for duration
    public int getDuration() {
        return duration;
    }

    // Getter for artist
    public String getArtist() {
        return artist;
    }

    @Override
    public String toString() {
        return songId + " - " + title + " by " + artist + " (" + duration + "s)";
    }
}
