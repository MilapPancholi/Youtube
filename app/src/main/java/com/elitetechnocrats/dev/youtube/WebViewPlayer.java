package com.elitetechnocrats.dev.youtube;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;


public class WebViewPlayer extends Activity {

    String video = "";

    //VideoView video_view;
    //WebView view;
    Temp youTubePlayerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view_player);
        //view=(WebView)findViewById(R.id.youtube_player_view);
//        video_view=(VideoView)findViewById(R.id.video_view);
//        view=(VideoView) findViewById(R.id.videoView);
        video = getIntent().getExtras().getString("Id", "");
        youTubePlayerView = (Temp) findViewById(R.id.player_view);
        youTubePlayerView.initialize(new AbstractYouTubeListner() {
            @Override
            public void onReady() {
                youTubePlayerView.loadVideo(video, 0);
            }
        }, true);
//        video="2Ur1yEv_yWw";
//        MediaController mediaController= new MediaController(this);
//        mediaController.setAnchorView(view);
//        view.setMediaController(mediaController);
//        view.setVideoURI(Uri.parse("rtsp://r6---sn-a5meknek.googlevideo.com/Cj0LENy73wIaNAlsyf9LyPVK2RMYDSANFC2EIs1YMOCoAUIASARgyaPvtpTI6ttYigELaldFUVpHQld5S2sM/28C310143A086A96D0BB19877EFDB03420DA3357.3B1FAF348340FC16EF9E68A46C6BB83B82EC2689/yt6/1/video.3gp"));
//        view.requestFocus();
//        view.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//            @Override
//            public void onPrepared(MediaPlayer mp) {
//                view.start();
//            }
//        });
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
//        view.getSettings().setJavaScriptEnabled(true);
//        WebSettings webSettings = view.getSettings();
//        webSettings.setJavaScriptEnabled(true);
//        view.loadData("<iframe width=\""+360+"\" height=\"320\" src=\"http://www.youtube.com/embed/"+video+"?wmode=transparent&rel=0&autohide=1&showinfo=0&modestbranding=1\" frameborder=\"0\"></iframe>","text/html","charset=UTF-8");
        //view.loadData("<object width=\"560\" height=\"349\"> <param name=\"movie\" value=\"https://www.youtube.com/v/VIDEOID?modestbranding=1&amp;version=3&amp;hl=en_US\"></param> <param name=\"allowFullScreen\" value=\"true\"></param> <param name=\"allowscriptaccess\" value=\"always\"></param> <embed src=\"https://www.youtube.com/v/VIDEOID?modestbranding=1&amp;version=3&amp;hl=en_US\" type=\"application/x-shockwave-flash\" width=\"560\" height=\"349\" allowscriptaccess=\"always\" allowfullscreen=\"true\"></embed> </object>\n","text/html","charset=UTF-8");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        view.stopLoading();
    }
}
