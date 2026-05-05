package com.zaid.music;

import com.zaid.music.exception.PlaylistNotFoundException;
import com.zaid.music.exception.SongNotFoundException;
import com.zaid.music.model.Playlist;
import com.zaid.music.model.Song;
import com.zaid.music.model.User;
import com.zaid.music.repository.SongRepository;
import com.zaid.music.service.MusicService;
import com.zaid.music.service.PlayerService;
import com.zaid.music.service.PlaylistService;
import sun.awt.windows.ThemeReader;


import java.util.InputMismatchException;
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

    // To get correct input value otherwise catch an error
    private int getIntValue(){
        // This loop will run until a valid input is given
        while(true){
            try{
                int value = scanner.nextInt();
                // nextInt() gives an empty line, hence to clear it we use scanner.nextLine()
                scanner.nextLine();
                // When we get the valid input, we will break out of this loop and return a valid input
                return value;
            }
            // It will catch Input mismatch exception
            catch (InputMismatchException e){
                System.out.println("Invalid input enter a numeric value");
                // To reset the scanner
                scanner.nextLine();
            }
        }
    }

    // To get correct input value otherwise catch an error
    private String getStringValue(){


        // This will run until and unless a valid input is given
        while (true){
            String string = scanner.nextLine();
            // It will check for the white spaces, it will trim it
            if(!string.trim().isEmpty()){
                // we will break out of this as soon as we will get a valid string input
                return string;
            }
            else {
                System.out.println("Input cannot be empty! Try again");
            }
        }


    }

    // To start the menu
    public void start(){
        System.out.println("Welcome to my music player");
        // Showing the menu items
        while (true){
            showMenu();
            int choice = getIntValue();
            handleChoice(choice);
        }
    }

    // To show all the available options
    private void showMenu(){
        System.out.println();
        System.out.println("Select an option to proceed "+ user.getUserName() +" : ");
        System.out.println("1. Add song to song repository  ");
        System.out.println("2. View songs in repository");
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

    // Switch case to handle choices
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
                System.out.println("Invalid Choice, Try again!");
            }
        }
    }

    // To Add the songs in repository
    private void addSong(){
        System.out.println();
        // Getting all the input values
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

        System.out.println("Song Added !");
    }

    // View all the songs in repository
    private void viewSongs(){

        System.out.println();
        // Getting all the songs from repository

        if (songRepository.getAllSongs().isEmpty()){
            System.out.println("No songs added yet");
        }
        else {
            System.out.println("Available songs: ");
            for (Song song: songRepository.getAllSongs()){
                System.out.println(song);
            }
        }

    }

    // To create the playlist in the user
    private void createPlaylist(){
        System.out.println();
        System.out.println("Enter playlist name");
        String name = getStringValue();

        // Creating a playlist using playlist service
        Playlist playlist = playlistService.createPlaylist(user, name);
        System.out.println("Created playlist: " + playlist);
    }

    // Adding a song to a particular playlist
    private void addSongToPlaylist(){
        System.out.println();


        // Showing all the available playlists
        viewPlaylists();


        // Taking input if only any playlist is available
        if (!user.getPlaylists().isEmpty()){
            System.out.println();

            // Taking all the arguments
            System.out.println("Enter playlist ID from above list: ");
            int playlistId = getIntValue();

            // Showing all the available songs in song repository
            viewSongs();

            // Taking input if only any song is available
            if(!songRepository.getAllSongs().isEmpty()){
                System.out.println();
                System.out.println("Enter song ID from above list: ");
                int songId = getIntValue();


                // Adding try catch to add the song
                try {
                    // It throws an error of song not found
                    playlistService.addSongToPlaylist(user,playlistId,songId);
                    System.out.println("Song Added successfully!!");
                }
                catch (SongNotFoundException e){
                    System.out.println(e.getMessage());
                } catch (PlaylistNotFoundException e) {
                    System.out.println(e.getMessage());
                }
            }
            else {
                System.out.println("No songs are available!");
            }

        }

    }

    // Removing a song from a particular playlist
    private void removeSongFromPlaylist(){
        System.out.println();
        viewPlaylists();
        if(!user.getPlaylists().isEmpty()){
            // Taking all the arguments
            System.out.println("Enter playlist ID from above list: ");
            int playlistId = getIntValue();
            // Showing all the songs
            viewSongs();
            // Taking song input if only any available
            if(!songRepository.getAllSongs().isEmpty()){
                System.out.println();
                System.out.println("Enter song ID from above list: ");
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
                catch (PlaylistNotFoundException e) {
                    System.out.println(e.getMessage());
                }
            }
            else {
                System.out.println("No songs are available");
            }
        }

    }

    // To see all the available playlist that user has created
    private void viewPlaylists(){
        System.out.println();
        // Looping through all the to get all playlist
        if(user.getPlaylists().isEmpty()){
            System.out.println("There are no playlists.");
        }
        else{
            System.out.println("Available playlists: ");
            for(Playlist playlist: user.getPlaylists()){
                System.out.println(playlist);
            }
        }

    }

    // To play a song
    private void playSong(){
        System.out.println();
        // To see all the songs that are available
        viewSongs();
        // Will take input if only any song is present
        if (!songRepository.getAllSongs().isEmpty()){
            System.out.println("Enter songID from above list: ");
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

    // To pause a song
    private void pauseSong(){
        System.out.println();
        playerService.pause();
    }

    // To resume a song
    private void resumeSong(){
        System.out.println();
        playerService.resume();
    }

    // To list all the songs from a particular playlist
    private void listAllSongsFromPlaylist(){
        System.out.println();

        // To show all the playlist that are available
        viewPlaylists();

        // To show if only there are any playlist created
        if(!user.getPlaylists().isEmpty()){
            System.out.println("Enter Playlist ID from above list: ");
            int playlistID = getIntValue();

            // To check if playlist is available or not.
            try {
                Playlist playlist = playlistService.getPlaylist(user,playlistID);
                if (playlist.getSongs().isEmpty()){
                    System.out.println("No songs in this playlist");
                    return;
                }

                    System.out.println("All the songs from playlist "+ playlist.getTitle());

                    for (Song song: playlist.getSongs()){
                        System.out.println(song);
                    }



            }
            catch (PlaylistNotFoundException e){
                System.out.println(e.getMessage());
            }


        }

    }

}
