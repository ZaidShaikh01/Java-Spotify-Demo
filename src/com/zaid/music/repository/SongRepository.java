package com.zaid.music.repository;

import com.zaid.music.model.Song;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SongRepository {
    private Map<Integer, Song> songMap;


    //  Constructor for SongRepository
    public SongRepository(){
        songMap = new HashMap<>();
    }

    //Add songs to repository
    public void addSong(Song song) {
        // Checking if song is null
        if (song == null) {
            throw new IllegalArgumentException("Song cannot be null");
        }
        // If song is already present in repository
        if(songMap.containsKey(song.getSongId())){
            System.out.println("Song already exists!!");
            return;
        }
        songMap.put(song.getSongId(),song);
    }

    //Get song by ID
    public Song getSongById(int id){
        return songMap.get(id);
    }

    //Get all songs
    public List<Song> getAllSongs(){
        return new ArrayList<>(songMap.values());
    }

    //Delete a song
    public void deleteSongById(int id){
        if(songMap.remove(id) == null){
            System.out.println("Song not found!!");
        }
    }


}
