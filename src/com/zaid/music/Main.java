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

public class Main {
    public static void main(String[] args) {
        // Scanner for user scanner
        Scanner scanner = new Scanner(System.in);

        // Initializing things
        SongRepository songRepository = new SongRepository();
        MusicService musicService = new MusicService(songRepository);
        PlaylistService playlistService = new PlaylistService(musicService);
        PlayerService playerService = new PlayerService();

        // Initializing user
        User user = new User(1,"Zaid");

        // CLI menu
        System.out.println("Welcome to my music player");
        while(true){
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

            System.out.println("0. Exit");
            int choice = scanner.nextInt();

            switch (choice){
                case 1:{
                    System.out.println("Enter songID: ");
                    int songId = scanner.nextInt();
                    scanner.nextLine();

                    System.out.println("Enter song title: ");
                    String title = scanner.nextLine();

                    System.out.println("Enter Duration: ");
                    int duration = scanner.nextInt();
                    scanner.nextLine();

                    System.out.println("Enter artist name: ");
                    String artist = scanner.nextLine();

                    Song song = new Song(songId,title,duration,artist);
                    musicService.addSong(song);
                    break;
                }
                case 2:{
                    for (Song song: songRepository.getAllSongs()){
                        System.out.println(song);
                    }
                    break;
                }
                case 3:{
                    scanner.nextLine();
                    System.out.println("Enter playlist name");
                    String name = scanner.nextLine();

                    Playlist playlist = playlistService.createPlaylist(user, name);
                    System.out.println("Created playlist: " + playlist);
                    break;
                }
                case 4:{
                    System.out.println("Enter playlist ID: ");
                    int playlistId = scanner.nextInt();
                    System.out.println("Enter song ID: ");
                    int songId = scanner.nextInt();

                    try {
                        playlistService.addSongToPlaylist(user,playlistId,songId);
                    }
                    catch (SongNotFoundException e){
                        System.out.println(e.getMessage());
                    }
                    break;
                }
                case 5:{
                    System.out.println("Enter playlist ID: ");
                    int playlistId = scanner.nextInt();
                    System.out.println("Enter song ID: ");
                    int songId = scanner.nextInt();

                    try {
                        playlistService.removeSongFromPlaylist(user,playlistId,songId);
                    }
                    catch (SongNotFoundException e){
                        System.out.println(e.getMessage());
                    }
                    break;
                }
                case 6:{
                    for(Playlist playlist: user.getPlaylists()){
                        System.out.println(playlist);
                    }
                    break;
                }
                case 7:{
                    System.out.println("Enter songID: ");
                    int songId = scanner.nextInt();

                    try {
                        Song song = musicService.getSongById(songId);
                        playerService.play(song);
                    }catch (SongNotFoundException e){
                        System.out.println(e.getMessage());
                    }

                    break;
                }
                case 8:{
                    playerService.pause();
                    break;
                }
                case 9:{
                    playerService.resume();
                    break;
                }
                case 0:{
                    System.out.println("Goodbye!");
                    return;
                }
                default:
                    System.out.println("Invalid choice");
            }
            System.out.println();
        }


    }
}