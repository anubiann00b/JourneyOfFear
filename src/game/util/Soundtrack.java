package game.util;

import game.util.resource.MusicLibrary;
import game.util.resource.MusicPlayer;

public class Soundtrack {
    
    private static int currentTrack = -1;
    private static MusicLibrary[] music;
    
    private static int timer = -1;
            
    public static void init() {
        music = MusicLibrary.values();
        playNewTrack();
    }
    
    public static void update() {
        if (!music[currentTrack].getMusic().playing())
            playNewTrack();
    }
    
    public static void playNext() {
        music[currentTrack].getMusic().stop();
        playNewTrack();
    }
    
    private static void playNewTrack() {
        int newTrack = (int)(music.length*Math.random());
        
        while (newTrack == currentTrack) {
            newTrack = (int)(music.length*Math.random());
        }
        
        currentTrack = newTrack;
        MusicPlayer.play(music[newTrack]);
    }
}
