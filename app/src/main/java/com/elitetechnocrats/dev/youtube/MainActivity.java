package com.elitetechnocrats.dev.youtube;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class MainActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener, View.OnClickListener {

    LinearLayout layout;
    //Button btnprev,btnplay,btnfor;
    YouTubePlayer player;
    YouTubePlayerView player1;
    String video = "";
    SeekBar bar;
    private YouTubePlayer.PlaybackEventListener playbackEventListener = new YouTubePlayer.PlaybackEventListener() {
        @Override
        public void onBuffering(boolean arg0) {
        }

        @Override
        public void onPaused() {
        }

        @Override
        public void onPlaying() {
        }

        @Override
        public void onSeekTo(int arg0) {
        }

        @Override
        public void onStopped() {
        }
    };
    private YouTubePlayer.PlayerStateChangeListener playerStateChangeListener = new YouTubePlayer.PlayerStateChangeListener() {

        @Override
        public void onAdStarted() {
        }

        @Override
        public void onError(YouTubePlayer.ErrorReason arg0) {
        }

        @Override
        public void onLoaded(String arg0) {
        }

        @Override
        public void onLoading() {
        }

        @Override
        public void onVideoEnded() {
        }

        @Override
        public void onVideoStarted() {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        video = getIntent().getExtras().getString("Id", "");
        //=(LinearLayout)findViewById(R.id.layout);
//        btnprev=(Button)findViewById(R.id.btnprev);
//        btnfor=(Button)findViewById(R.id.btnfor);
//        btnplay=(Button)findViewById(R.id.btnpaus);
//        btnprev.setOnClickListener(this);
//        btnfor.setOnClickListener(this);
//        btnplay.setOnClickListener(this);
        player1 = (YouTubePlayerView) findViewById(R.id.player1);
        player1.initialize(getString(R.string.Key), this);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, final YouTubePlayer youTubePlayer, boolean b) {
        if (!b) {
            player = youTubePlayer;
            youTubePlayer.cueVideo(video);
            youTubePlayer.setPlayerStateChangeListener(playerStateChangeListener);
            youTubePlayer.setPlaybackEventListener(playbackEventListener);
            youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        if (youTubeInitializationResult.isUserRecoverableError()) {
            youTubeInitializationResult.getErrorDialog(this, 1).show();
        } else {
            String errorMessage = String.format("Error", youTubeInitializationResult.toString());
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            // Retry initialization if user performed a recovery action
            this.getYouTubePlayerProvider().initialize(getString(R.string.Key), this);
        }
    }

    private YouTubePlayer.Provider getYouTubePlayerProvider() {
        return (YouTubePlayerView) findViewById(R.id.player1);
    }

    @Override
    public void onClick(View v) {

//        if(v.getId()==R.id.btnfor){
//            player.seekToMillis(player.getCurrentTimeMillis()+10);
//        }else if(v.getId()==R.id.btnpaus){
//            if(player.isPlaying()){
//            player.pause();
//            }else{
//                player.play();
//            }
//        }else if(v.getId()==R.id.btnprev){
//            player.seekToMillis(player.getCurrentTimeMillis()-10);
//        }

    }
}
