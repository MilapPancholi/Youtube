package com.elitetechnocrats.dev.youtube;

/**
 * Created by dev on 3/22/2017.
 */

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.HashSet;
import java.util.Set;

import static android.webkit.WebSettings.LOAD_NO_CACHE;

public class Player extends WebView {
    @NonNull
    private final Handler mainThreadHandler;
    @NonNull
    private Set<Player.YouTubeListener> youTubeListeners;

    protected Player(Context context) {
        this(context, null);
    }

    protected Player(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    protected Player(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mainThreadHandler = new Handler(Looper.getMainLooper());
        this.youTubeListeners = new HashSet();
    }

    protected void initialize(@Nullable Player.YouTubeListener youTubeListener) {
        if (youTubeListener != null) {
            this.youTubeListeners.add(youTubeListener);
        }

        WebSettings settings = this.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setCacheMode(LOAD_NO_CACHE);
        settings.setMediaPlaybackRequiresUserGesture(false);
        this.addJavascriptInterface(new Bridge(this), "YouTubePlayerBridge");
        this.loadDataWithBaseURL("https://www.youtube.com", this.getVideoPlayerHTML(), "text/html", "utf-8", null);
        this.setWebChromeClient(new WebChromeClient() {
            public Bitmap getDefaultVideoPoster() {
                Bitmap result = null;

                try {
                    result = super.getDefaultVideoPoster();
                } catch (Exception var3) {
                }

                return result == null ? Bitmap.createBitmap(1, 1, Bitmap.Config.RGB_565) : result;
            }
        });
    }

    private String getVideoPlayerHTML() {
        try {
            InputStream e = this.getResources().openRawResource(com.pierfrancescosoffritti.youtubeplayer.R.raw.player);
            InputStreamReader inputStreamReader = new InputStreamReader(e, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder sb = new StringBuilder("");

            String read;
            while ((read = bufferedReader.readLine()) != null) {
                sb.append(read).append("\n");
            }

            e.close();
            return sb.toString();
        } catch (Exception var6) {
            var6.printStackTrace();
            return "";
        }
    }

    protected void loadVideo(final String videoId, final float startSeconds) {
        this.mainThreadHandler.post(new Runnable() {
            public void run() {
                Player.this.loadUrl("javascript:loadVideo(\'" + videoId + "\', " + startSeconds + ")");
            }
        });
    }

    protected void cueVideo(final String videoId, final float startSeconds) {
        this.mainThreadHandler.post(new Runnable() {
            public void run() {
                Player.this.loadUrl("javascript:cueVideo(\'" + videoId + "\', " + startSeconds + ")");
            }
        });
    }

    protected void play() {
        this.mainThreadHandler.post(new Runnable() {
            public void run() {
                Player.this.loadUrl("javascript:playVideo()");
            }
        });
    }

    protected void pause() {
        this.mainThreadHandler.post(new Runnable() {
            public void run() {
                Player.this.loadUrl("javascript:pauseVideo()");
            }
        });
    }

    protected void seekTo(final int time) {
        this.mainThreadHandler.post(new Runnable() {
            public void run() {
                Player.this.loadUrl("javascript:seekTo(" + time + ")");
            }
        });
    }

    @NonNull
    protected Set<Player.YouTubeListener> getListeners() {
        return this.youTubeListeners;
    }

    protected boolean addListener(Player.YouTubeListener listener) {
        return this.youTubeListeners.add(listener);
    }

    protected boolean removeListener(com.pierfrancescosoffritti.youtubeplayer.YouTubePlayer.YouTubeListener listener) {
        return this.youTubeListeners.remove(listener);
    }

    public interface YouTubeListener {
        void onReady();

        void onStateChange(int var1);

        void onPlaybackQualityChange(int var1);

        void onPlaybackRateChange(double var1);

        void onError(int var1);

        void onApiChange();

        void onCurrentSecond(float var1);

        void onVideoDuration(float var1);

        void onLog(String var1);

        void onVideoTitle(String var1);

        void onVideoId(String var1);
    }

    public static class Error {
        public static final int INVALID_PARAMENTER_IN_REQUEST = 0;
        public static final int HTML_5_PLAYER = 1;
        public static final int VIDEO_NOT_FOUND = 2;
        public static final int VIDEO_NOT_PLAYABLE_IN_EMBEDDED_PLAYER = 3;

        public Error() {
        }

        @Retention(RetentionPolicy.SOURCE)
        public @interface PlayerError {
        }
    }

    public static class PlaybackQuality {
        public static final int SMALL = 0;
        public static final int MEDIUM = 1;
        public static final int LARGE = 2;
        public static final int HD720 = 3;
        public static final int HD1080 = 4;
        public static final int HIGH_RES = 5;
        public static final int DEFAULT = -1;

        public PlaybackQuality() {
        }

        @Retention(RetentionPolicy.SOURCE)
        public @interface Quality {
        }
    }

    public static class State {
        public static final int UNSTARTED = -1;
        public static final int ENDED = 0;
        public static final int PLAYING = 1;
        public static final int PAUSED = 2;
        public static final int BUFFERING = 3;
        public static final int VIDEO_CUED = 5;

        public State() {
        }

        @Retention(RetentionPolicy.SOURCE)
        public @interface YouTubePlayerState {
        }
    }
}

