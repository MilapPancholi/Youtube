package com.elitetechnocrats.dev.youtube;

/**
 * Created by dev on 3/22/2017.
 */

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.webkit.JavascriptInterface;

import com.elitetechnocrats.dev.youtube.Player.YouTubeListener;

import java.util.Iterator;

public class Bridge {
    @NonNull
    private final Player youTubePlayer;
    @NonNull
    private final Handler mainThreadHandler;

    public Bridge(@NonNull Player youTubePlayer) {
        this.youTubePlayer = youTubePlayer;
        this.mainThreadHandler = new Handler(Looper.getMainLooper());
    }

    @JavascriptInterface
    public void onReady() {
        Iterator var1 = this.youTubePlayer.getListeners().iterator();

        while (var1.hasNext()) {
            YouTubeListener listener = (YouTubeListener) var1.next();
            listener.onReady();
        }

    }

    @JavascriptInterface
    public void onStateChange(final String state) {
        this.mainThreadHandler.post(new Runnable() {
            public void run() {
                Iterator var1 = Bridge.this.youTubePlayer.getListeners().iterator();

                while (var1.hasNext()) {
                    YouTubeListener listener = (YouTubeListener) var1.next();
                    if ("UNSTARTED".equalsIgnoreCase(state)) {
                        listener.onStateChange(-1);
                    } else if ("ENDED".equalsIgnoreCase(state)) {
                        listener.onStateChange(0);
                    } else if ("PLAYING".equalsIgnoreCase(state)) {
                        listener.onStateChange(1);
                    } else if ("PAUSED".equalsIgnoreCase(state)) {
                        listener.onStateChange(2);
                    } else if ("BUFFERING".equalsIgnoreCase(state)) {
                        listener.onStateChange(3);
                    } else if ("CUED".equalsIgnoreCase(state)) {
                        listener.onStateChange(5);
                    }
                }

            }
        });
    }

    @JavascriptInterface
    public void onPlaybackQualityChange(final String quality) {
        this.mainThreadHandler.post(new Runnable() {
            public void run() {
                Iterator var1 = Bridge.this.youTubePlayer.getListeners().iterator();

                while (var1.hasNext()) {
                    YouTubeListener listener = (YouTubeListener) var1.next();
                    if ("small".equalsIgnoreCase(quality)) {
                        listener.onPlaybackQualityChange(0);
                    } else if ("medium".equalsIgnoreCase(quality)) {
                        listener.onPlaybackQualityChange(1);
                    } else if ("large".equalsIgnoreCase(quality)) {
                        listener.onPlaybackQualityChange(2);
                    } else if ("hd720".equalsIgnoreCase(quality)) {
                        listener.onPlaybackQualityChange(3);
                    } else if ("hd1080".equalsIgnoreCase(quality)) {
                        listener.onPlaybackQualityChange(4);
                    } else if ("highres".equalsIgnoreCase(quality)) {
                        listener.onPlaybackQualityChange(5);
                    } else if ("default".equalsIgnoreCase(quality)) {
                        listener.onPlaybackQualityChange(-1);
                    }
                }

            }
        });
    }

    @JavascriptInterface
    public void onPlaybackRateChange(final String rate) {
        this.mainThreadHandler.post(new Runnable() {
            public void run() {
                double dRate = Double.parseDouble(rate);
                Iterator var3 = Bridge.this.youTubePlayer.getListeners().iterator();

                while (var3.hasNext()) {
                    YouTubeListener listener = (YouTubeListener) var3.next();
                    listener.onPlaybackRateChange(dRate);
                }

            }
        });
    }

    @JavascriptInterface
    public void onError(final String error) {
        this.mainThreadHandler.post(new Runnable() {
            public void run() {
                Iterator var1 = Bridge.this.youTubePlayer.getListeners().iterator();

                while (var1.hasNext()) {
                    YouTubeListener listener = (YouTubeListener) var1.next();
                    if ("2".equalsIgnoreCase(error)) {
                        listener.onError(0);
                    } else if ("5".equalsIgnoreCase(error)) {
                        listener.onError(1);
                    } else if ("100".equalsIgnoreCase(error)) {
                        listener.onError(2);
                    } else if ("101".equalsIgnoreCase(error)) {
                        listener.onError(3);
                    } else if ("150".equalsIgnoreCase(error)) {
                        listener.onError(3);
                    }
                }

            }
        });
    }

    @JavascriptInterface
    public void onApiChange() {
        this.mainThreadHandler.post(new Runnable() {
            public void run() {
                Iterator var1 = Bridge.this.youTubePlayer.getListeners().iterator();

                while (var1.hasNext()) {
                    YouTubeListener listener = (YouTubeListener) var1.next();
                    listener.onApiChange();
                }

            }
        });
    }

    @JavascriptInterface
    public void currentSeconds(String seconds) {
        final float fSeconds;
        try {
            fSeconds = Float.parseFloat(seconds);
        } catch (NumberFormatException var4) {
            var4.printStackTrace();
            return;
        }

        this.mainThreadHandler.post(new Runnable() {
            public void run() {
                Iterator var1 = Bridge.this.youTubePlayer.getListeners().iterator();

                while (var1.hasNext()) {
                    YouTubeListener listener = (YouTubeListener) var1.next();
                    listener.onCurrentSecond(fSeconds);
                }

            }
        });
    }

    @JavascriptInterface
    public void onVideoTitle(final String videoTitle) {
        this.mainThreadHandler.post(new Runnable() {
            public void run() {
                Iterator var1 = Bridge.this.youTubePlayer.getListeners().iterator();

                while (var1.hasNext()) {
                    YouTubeListener listener = (YouTubeListener) var1.next();
                    listener.onVideoTitle(videoTitle);
                }

            }
        });
    }

    @JavascriptInterface
    public void onVideoId(final String videoId) {
        this.mainThreadHandler.post(new Runnable() {
            public void run() {
                Iterator var1 = Bridge.this.youTubePlayer.getListeners().iterator();

                while (var1.hasNext()) {
                    YouTubeListener listener = (YouTubeListener) var1.next();
                    listener.onVideoId(videoId);
                }

            }
        });
    }

    @JavascriptInterface
    public void onVideoDuration(final String seconds) {
        this.mainThreadHandler.post(new Runnable() {
            public void run() {
                float videoDuration = Float.parseFloat(seconds);
                Iterator var2 = Bridge.this.youTubePlayer.getListeners().iterator();

                while (var2.hasNext()) {
                    YouTubeListener listener = (YouTubeListener) var2.next();
                    listener.onVideoDuration(videoDuration);
                }

            }
        });
    }

    @JavascriptInterface
    public void onLog(final String message) {
        this.mainThreadHandler.post(new Runnable() {
            public void run() {
                Iterator var1 = Bridge.this.youTubePlayer.getListeners().iterator();

                while (var1.hasNext()) {
                    YouTubeListener listener = (YouTubeListener) var1.next();
                    listener.onLog(message);
                }

            }
        });
    }
}