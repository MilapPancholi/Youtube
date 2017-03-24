package com.elitetechnocrats.dev.youtube;

/**
 * Created by dev on 3/22/2017.
 */

public class PlaybackResumer implements Player.YouTubeListener {
    private boolean isPlaying = false;
    private int error = -1;
    private String videoId;
    private float currentSecond;
    private Temp youTubePlayerView;

    public PlaybackResumer(Temp youTubePlayerView) {
        this.youTubePlayerView = youTubePlayerView;
    }

    public void resume() {
        if (this.isPlaying && this.error == 1) {
            this.youTubePlayerView.loadVideo(this.videoId, this.currentSecond);
        } else if (!this.isPlaying && this.error == 1) {
            this.youTubePlayerView.cueVideo(this.videoId, this.currentSecond);
        }

        this.error = -1;
    }

    public void onReady() {
    }

    public void onStateChange(int state) {
        switch (state) {
            case 0:
                this.isPlaying = false;
                break;
            case 1:
                this.isPlaying = true;
                break;
            case 2:
                this.isPlaying = false;
        }

    }

    public void onPlaybackQualityChange(int playbackQuality) {
    }

    public void onPlaybackRateChange(double rate) {
    }

    public void onError(int error) {
        if (error == 1) {
            this.error = error;
        }

    }

    public void onApiChange() {
    }

    public void onCurrentSecond(float second) {
        this.currentSecond = second;
    }

    public void onVideoDuration(float duration) {
    }

    public void onLog(String log) {
    }

    public void onVideoTitle(String videoTitle) {
    }

    public void onVideoId(String videoId) {
        this.videoId = videoId;
    }
}