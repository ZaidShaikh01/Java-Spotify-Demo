package com.zaid.music;

import com.zaid.music.exception.SongNotFoundException;
import com.zaid.music.model.Playlist;
import com.zaid.music.model.Song;
import com.zaid.music.model.User;
import com.zaid.music.repository.SongRepository;
import com.zaid.music.service.MusicService;
import com.zaid.music.service.PlayerService;
import com.zaid.music.service.PlaylistService;


import java.util.Scanner;

public class CLIHandler {

    private Scanner scanner;
    private SongRepository songRepository;
    private MusicService musicService;
    private PlaylistService playlistService;
    private PlayerService playerService;
    private User user;

    //    Constructor
    public CLIHandler(){

        // Scanner for user scanner
         scanner = new Scanner(System.in);

        // Initializing things
         songRepository = new SongRepository();
         musicService = new MusicService(songRepository);
         playlistService = new PlaylistService(musicService);
         playerService = new PlayerService();

        // Initializing user
         user = new User(1,"Zaid");

    }
    private int getIntValue(){
        while(true){
            try{
                int value = scanner.nextInt();
                scanner.nextLine();
                return value;
            }
            catch (Exception e){
                System.out.println("Invalid input enter a numeric value");
                scanner.nextLine();
            }
        }
    }
    private String getStringValue(){
        String string = scanner.nextLine();
        if(string.trim().isEmpty()){
            System.out.println("Input cannot be empty!");
            return getStringValue();
        }
        return string;
    }
    public void start(){
        System.out.println("Welcome to my music player");
        while (true){
            showMenu();
            int choice = getIntValue();
            handleChoice(choice);
        }

    }

    private void showMenu(){
        System.out.println();
        System.out.println("Select an option to proceed "+ user.getUserName() +" : ");
        System.out.println("1. Add song ");
        System.out.println("2. View song");
        System.out.println("3. Create playlist");
        System.out.println("4. Add song to playlist");
        System.out.println("5. Remove song from playlist");
        System.out.println("6. View Playlists");
        System.out.println("7. Play song");
        System.out.println("8. Pause song");
        System.out.println("9. Resume song");
        System.out.println("10. List all the song in a particular playlist");
        System.out.println("0. Exit");
    }

    private void handleChoice(int choice){
        switch (choice){
            case 1: addSong(); break;
            case 2: viewSongs(); break;
            case 3: createPlaylist(); break;
            case 4: addSongToPlaylist(); break;
            case 5: removeSongFromPlaylist(); break;
            case 6: viewPlaylists();break;
            case 7: playSong(); break;
            case 8: pauseSong(); break;
            case 9: resumeSong(); break;
            case 10: listAllSongsFromPlaylist(); break;
            case 0: {
                System.out.println("GoodBye!!");
                scanner.close();
                System.exit(0);
            }
            default: {
                System.out.println("Invalid Choice");
            }
        }
    }
    private void addSong(){
        System.out.println();

        System.out.println("Enter songID: ");
        int songId = getIntValue();


        System.out.println("Enter song title: ");
        String title = getStringValue();

        System.out.println("Enter Duration: ");
        int duration = getIntValue();


        System.out.println("Enter artist name: ");
        String artist = getStringValue();

        // Creating song object
        Song song = new Song(songId,title,duration,artist);
        // Adding it to the repository using music service
        musicService.addSong(song);
        System.out.println("Song Added");
    }
    private void viewSongs(){
        System.out.println();
        // Getting all the songs from repository

        if (songRepository.getAllSongs().isEmpty()){
            System.out.println("No songs added yet");
        }
        else {
            for (Song song: songRepository.getAllSongs()){
                System.out.println(song);
            }
        }

    }
    private void createPlaylist(){
        System.out.println();
        System.out.println("Enter playlist name");
        String name = getStringValue();

        // Creating a playlist using playlist service
        Playlist playlist = playlistService.createPlaylist(user, name);
        System.out.println("Created playlist: " + playlist);
    }
    private void addSongToPlaylist(){
        System.out.println();
        viewPlaylists();
        if (!user.getPlaylists().isEmpty()){
            System.out.println();
            // Taking all the arguments
            System.out.println("Enter playlist ID: ");
            int playlistId = getIntValue();
            viewSongs();
            if(!songRepository.getAllSongs().isEmpty()){
                System.out.println();
                System.out.println("Enter song ID: ");
                int songId = getIntValue();


                // Adding try catch to add the song
                try {
                    // It throws an error of song not found
                    playlistService.addSongToPlaylist(user,playlistId,songId);
                    System.out.println("Song Added successfully!!");
                }
                catch (SongNotFoundException e){
                    System.out.println(e.getMessage());
                }
            }

        }

    }
    private void removeSongFromPlaylist(){
        System.out.println();
        viewPlaylists();
        if(!user.getPlaylists().isEmpty()){
            // Taking all the arguments
            System.out.println("Enter playlist ID: ");
            int playlistId = getIntValue();
            viewSongs();
            if(!songRepository.getAllSongs().isEmpty()){
                System.out.println();
                System.out.println("Enter song ID: ");
                int songId = getIntValue();
                // Adding try catch to add the song
                try {
                    // It throws an error of song not found
                    playlistService.removeSongFromPlaylist(user,playlistId,songId);
                    System.out.println("Song removed successfully!!");
                }
                catch (SongNotFoundException e){
                    System.out.println(e.getMessage());
                }
            }
        }

    }
    private void viewPlaylists(){
        System.out.println();
        // Looping through all the to get all playlist
        if(user.getPlaylists().isEmpty()){
            System.out.println("There are no playlists.");
        }
        else{
            for(Playlist playlist: user.getPlaylists()){
                System.out.println(playlist);
            }
        }

    }
    private void playSong(){
        System.out.println();
        viewSongs();
        if (!songRepository.getAllSongs().isEmpty()){
            System.out.println("Enter songID: ");
            int songId = getIntValue();
            // It can throw songNotFoundException
            try {
                Song song = musicService.getSongById(songId);
                playerService.play(song);
            }catch (SongNotFoundException e){
                System.out.println(e.getMessage());
            }
        }

    }
    private void pauseSong(){
        System.out.println();
        playerService.pause();
    }
    private void resumeSong(){
        System.out.println();
        playerService.resume();
    }
    private void listAllSongsFromPlaylist(){
        System.out.println();
        viewPlaylists();
        if(!user.getPlaylists().isEmpty()){
            System.out.println("Enter Playlist ID: ");
            int playlistID = getIntValue();
            Playlist playlist = playlistService.getPlaylist(user,playlistID);
            for (Song song: playlist.getSongs()){
                System.out.println(song);
            }
        }

    }
}
