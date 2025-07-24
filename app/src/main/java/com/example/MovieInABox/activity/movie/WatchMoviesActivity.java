package com.example.MovieInABox.activity.movie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;

import android.os.Handler;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.MovieInABox.R;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;


import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

import wseemann.media.FFmpegMediaMetadataRetriever;


public class WatchMoviesActivity extends AppCompatActivity {
    private PlayerView videoView;
    private SeekBar seekBar_video, seek_volume;
    private TextView textView_duration, textView_current_duration, text_show_volume, movies_name;
    private ImageView btn_play_pause, btn_replay, btn_forward, btn_back, imageView_volume;
    private LinearLayout linearLayoutController;
    private FrameLayout frameLayout;
    private Uri video;
    private SimpleExoPlayer exoPlayer;
    private AudioManager audioManager;
    private boolean checkSH = true, checkPlayPause = true;
    private Handler mHandler;
    private DatabaseReference mDatabase;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch_movies);

        initUi();
        showhideControl();
        play();
        startPlayer();
        btn_play_pause_OnClick();
        btnForwardOnClick();
        btnReplayOnClick();
        btnBackOnClick();
        updateSeekBar();
        setSeekBar_video();
        seekBarVolume();
        muteVolumOnClick();


        exoPlayer.addListener(new Player.EventListener() {
            @Override
            public void onPlaybackStateChanged(int state) {
                if (state == exoPlayer.STATE_READY) {
                    long realDurationMillis = exoPlayer.getDuration();
                    seekBar_video.setMax((int)realDurationMillis);
                    textView_duration.setText(getDuration());
                }
            }
        });
    }

    // Additional methods would be implemented here
    // For brevity, I'm including the basic structure
    private void initUi() {
        // Initialize UI components
    }

    private void showhideControl() {
        // Show/hide video controls
    }

    private void play() {
        // Start video playback
    }

    private void startPlayer() {
        // Initialize ExoPlayer
    }

    private void btn_play_pause_OnClick() {
        // Play/pause button functionality
    }

    private void btnForwardOnClick() {
        // Forward button functionality
    }

    private void btnReplayOnClick() {
        // Replay button functionality
    }

    private void btnBackOnClick() {
        // Back button functionality
    }

    private void updateSeekBar() {
        // Update seek bar progress
    }

    private void setSeekBar_video() {
        // Set up video seek bar
    }

    private void seekBarVolume() {
        // Set up volume seek bar
    }

    private void muteVolumOnClick() {
        // Mute volume functionality
    }

    public String getDuration() {
        // Get video duration
        return "";
    }
} 