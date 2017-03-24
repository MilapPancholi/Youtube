package com.elitetechnocrats.dev.youtube;
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import android.content.Context;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.pierfrancescosoffritti.youtubeplayer.Callable;
import com.pierfrancescosoffritti.youtubeplayer.NetworkReceiver;
import com.pierfrancescosoffritti.youtubeplayer.Utils;
import com.pierfrancescosoffritti.youtubeplayer.YouTubePlayerFullScreenListener;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Temp extends FrameLayout implements NetworkReceiver.NetworkListener {
    @NonNull
    private final NetworkReceiver networkReceiver;
    @NonNull
    private final Player youTubePlayer;
    @NonNull
    private final View playerControls;
    @NonNull
    private final wrapper wrapper;
    @NonNull
    private final PlaybackResumer playbackResumer;
    private final Set<YouTubePlayerFullScreenListener> fullScreenListeners;
    private boolean isFullScreen;
    private boolean initialized;
    private Callable onNetworkAvailableCallback;

    public Temp(Context context) {
        this(context, null);
    }

    public Temp(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Temp(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.initialized = false;
        this.isFullScreen = false;
        this.youTubePlayer = new Player(context);
        this.addView(this.youTubePlayer, new LayoutParams(-1, -1));
        this.playerControls = inflate(context, com.pierfrancescosoffritti.youtubeplayer.R.layout.player_controls, this);
        this.wrapper = new wrapper(this, this.playerControls);
        this.playbackResumer = new PlaybackResumer(this);
        this.fullScreenListeners = new HashSet();
        this.wrapper.setYoutubeButtonVisible(View.INVISIBLE);
        this.fullScreenListeners.add(this.wrapper);
        this.youTubePlayer.addListener(this.wrapper);
        this.youTubePlayer.addListener(this.playbackResumer);
        this.networkReceiver = new NetworkReceiver(this);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (this.getLayoutParams().height == -2) {
            int sixteenNineHeight = MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(widthMeasureSpec) * 9 / 16, 1073741824);
            super.onMeasure(widthMeasureSpec, sixteenNineHeight);
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }

    }

    public void onFullScreenButtonListener(OnClickListener listener) {
        this.wrapper.setOnFullScreenButtonListener(listener);
    }

    public boolean isFullScreen() {
        return this.isFullScreen;
    }

    public void enterFullScreen() {
        if (!this.isFullScreen) {
            android.view.ViewGroup.LayoutParams viewParams = this.getLayoutParams();
            viewParams.height = -1;
            viewParams.width = -1;
            this.setLayoutParams(viewParams);
            this.isFullScreen = true;
            Iterator var2 = this.fullScreenListeners.iterator();

            while (var2.hasNext()) {
                YouTubePlayerFullScreenListener fullScreenListener = (YouTubePlayerFullScreenListener) var2.next();
                fullScreenListener.onYouTubePlayerEnterFullScreen();
            }

        }
    }

    public void exitFullScreen() {
        if (this.isFullScreen) {
            android.view.ViewGroup.LayoutParams viewParams = this.getLayoutParams();
            viewParams.height = -2;
            viewParams.width = -1;
            this.setLayoutParams(viewParams);
            this.isFullScreen = false;
            Iterator var2 = this.fullScreenListeners.iterator();

            while (var2.hasNext()) {
                YouTubePlayerFullScreenListener fullScreenListener = (YouTubePlayerFullScreenListener) var2.next();
                fullScreenListener.onYouTubePlayerExitFullScreen();
            }

        }
    }

    public void toggleFullScreen() {
        if (this.isFullScreen) {
            this.exitFullScreen();
        } else {
            this.enterFullScreen();
        }

    }

    public boolean addFullScreenListener(@NonNull YouTubePlayerFullScreenListener fullScreenListener) {
        return this.fullScreenListeners.add(fullScreenListener);
    }

    public boolean removeFullScreenListener(@NonNull YouTubePlayerFullScreenListener fullScreenListener) {
        return this.fullScreenListeners.remove(fullScreenListener);
    }

    public void initialize(@Nullable final Player.YouTubeListener youTubeListener, boolean handleNetworkEvents) {
        if (handleNetworkEvents) {
            this.getContext().registerReceiver(this.networkReceiver, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
        }

        if (!Utils.isOnline(this.getContext())) {
            Log.e("YouTubePlayerView", "Can\'t initialize because device is not connected to the internet.");
            this.onNetworkAvailableCallback = new Callable() {
                public void call() {
                    Log.d("YouTubePlayerView", "Network available. Initializing player.");
                    Temp.this.youTubePlayer.initialize(youTubeListener);
                    Temp.this.initialized = true;
                    Temp.this.onNetworkAvailableCallback = null;
                }
            };
        } else {
            this.youTubePlayer.initialize(youTubeListener);
            this.initialized = true;
        }
    }

    public void loadVideo(String videoId, float startSecond) {
        if (!this.initialized) {
            Log.e("YouTubePlayerView", "the player has not been initialized");
        } else {
            this.youTubePlayer.loadVideo(videoId, startSecond);
            this.wrapper.onNewVideo();
        }
    }

    public void cueVideo(String videoId, float startSeconds) {
        if (!this.initialized) {
            Log.e("YouTubePlayerView", "the player has not been initialized");
        } else {
            this.youTubePlayer.cueVideo(videoId, startSeconds);
            this.wrapper.onNewVideo();
        }
    }

    public void release() {
        if (!this.initialized) {
            Log.e("YouTubePlayerView", "the player has not been initialized");
        } else {
            this.youTubePlayer.destroy();

            try {
                this.getContext().unregisterReceiver(this.networkReceiver);
            } catch (Exception var2) {
            }

        }
    }

    public void seekTo(int time) {
        if (!this.initialized) {
            Log.e("YouTubePlayerView", "the player has not been initialized");
        } else {
            this.youTubePlayer.seekTo(time);
        }
    }

    public void playVideo() {
        if (!this.initialized) {
            Log.e("YouTubePlayerView", "the player has not been initialized");
        } else {
            this.youTubePlayer.play();
        }
    }

    public void pauseVideo() {
        if (!this.initialized) {
            Log.e("YouTubePlayerView", "the player has not been initialized");
        } else {
            this.youTubePlayer.pause();
        }
    }

    public void onNetworkAvailable() {
        Log.d("YouTubePlayerView", "Network available.");
        if (!this.initialized && this.onNetworkAvailableCallback != null) {
            this.onNetworkAvailableCallback.call();
        } else {
            this.playbackResumer.resume();
        }

    }

    public void onNetworkUnavailable() {
    }

    public void showTitle(boolean show) {
        this.wrapper.showTitle(show);
    }

    public void setCustomActionRight(Drawable icon, OnClickListener clickListener) {
        this.wrapper.setCustomActionRight(icon, clickListener);
    }

    public void setCustomActionLeft(Drawable icon, OnClickListener clickListener) {
        this.wrapper.setCustomActionLeft(icon, clickListener);
    }
}

