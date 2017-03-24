package com.elitetechnocrats.dev.youtube;

/**
 * Created by dev on 3/22/2017.
 */

public abstract class AbstractYouTubeListner implements Player.YouTubeListener {
    public AbstractYouTubeListner() {
    }

    public void onReady() {
    }

    public void onStateChange(int state) {
    }

    public void onPlaybackQualityChange(int playbackQuality) {
    }

    public void onPlaybackRateChange(double rate) {
    }

    public void onError(int error) {
    }

    public void onApiChange() {
    }

    public void onCurrentSecond(float second) {
    }

    public void onVideoDuration(float duration) {
    }

    public void onLog(String log) {
    }

    public void onVideoTitle(String videoTitle) {
    }

    public void onVideoId(String videoId) {
    }
}
