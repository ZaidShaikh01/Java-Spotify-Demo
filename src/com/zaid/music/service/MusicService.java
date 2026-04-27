package com.zaid.music.service;

import com.zaid.music.model.Song;
import com.zaid.music.repository.SongRepository;
import com.zaid.music.exception.SongNotFoundException;


public class MusicService {

    private SongRepository repo;

    public MusicService(SongRepository repo) {
       this.repo = repo;
    }

    // Get song by ID
    public Song getSongById(int id) throws SongNotFoundException{
        if(id<=0){
            throw new IllegalArgumentException("Enter a valid ID");
        }

        Song song = repo.getSongById(id);
        if(song == null){
            throw new SongNotFoundException("Song with ID " + id + " not found");
        }
        return song;
    }

    // Add Song
    public void addSong(Song song){
        if(song == null){
            throw new IllegalArgumentException("Song cannot be null");
        }
        repo.addSong(song);
    }

    // Delete Song
    public void deleteSong(int id) throws SongNotFoundException{
        if (id<=0){
            throw new IllegalArgumentException("Enter a valid ID");
        }

        Song song = repo.getSongById(id);
        if(song == null){
            throw new SongNotFoundException("Song with ID "+ id +" not found");
        }

        repo.deleteSongById(id);
    }



}
