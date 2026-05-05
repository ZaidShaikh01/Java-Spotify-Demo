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

        CLIHandler cli = new CLIHandler();
        cli.start();


    }
}