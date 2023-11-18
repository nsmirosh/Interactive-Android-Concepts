package nick.mirosh.androidsamples.ui.audio;

import android.media.MediaPlayer;

import java.io.IOException;

public class AudioPlayer {

    private MediaPlayer mediaPlayer;

    public void startPlaying(String audioFilePath) {
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(audioFilePath);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            // Handle exceptions
        }
    }

    public void stopPlaying() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    // Other necessary methods
}