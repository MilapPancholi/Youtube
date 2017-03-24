package com.elitetechnocrats.dev.youtube;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import android.animation.Animator;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.pierfrancescosoffritti.youtubeplayer.Utils;
import com.pierfrancescosoffritti.youtubeplayer.YouTubePlayerFullScreenListener;

public class wrapper implements View.OnClickListener, YouTubePlayerFullScreenListener, Player.YouTubeListener, SeekBar.OnSeekBarChangeListener {
    @NonNull
    private final Temp youTubePlayerView;
    @NonNull
    private final View panel;
    @NonNull
    private final View controlsRoot;
    @NonNull
    private final TextView videoTitle;
    @NonNull
    private final TextView videoCurrentTime;
    @NonNull
    private final TextView videoDuration;
    @NonNull
    private final ProgressBar progressBar;
    @NonNull
    private final ImageView playButton;
    @NonNull
    private final ImageView youTubeButton;
    @NonNull
    private final ImageView fullScreenButton;
    @NonNull
    private final ImageView customActionLeft;
    @NonNull
    private final ImageView customActionRight;
    @NonNull
    private final SeekBar seekBar;
    private final Handler handler = new Handler(Looper.getMainLooper());
    private View.OnClickListener onFullScreenButtonListener;
    private boolean isPlaying = false;
    private boolean isVisible = true;
    private boolean canFadeControls = false;
    private final Runnable fadeOutRunnable = new Runnable() {
        public void run() {
            wrapper.this.fadeControls(0.0F);
        }
    };
    private boolean seekBarTouchStarted = false;
    private int newSeekBarProgress = -1;

    protected wrapper(@NonNull Temp youTubePlayerView, @NonNull View controlsView) {
        this.youTubePlayerView = youTubePlayerView;
        this.panel = controlsView.findViewById(com.pierfrancescosoffritti.youtubeplayer.R.id.panel);
        this.controlsRoot = controlsView.findViewById(com.pierfrancescosoffritti.youtubeplayer.R.id.controls_root);
        this.videoTitle = (TextView) controlsView.findViewById(com.pierfrancescosoffritti.youtubeplayer.R.id.video_title);
        this.videoCurrentTime = (TextView) controlsView.findViewById(com.pierfrancescosoffritti.youtubeplayer.R.id.video_current_time);
        this.videoDuration = (TextView) controlsView.findViewById(com.pierfrancescosoffritti.youtubeplayer.R.id.video_duration);
        this.progressBar = (ProgressBar) controlsView.findViewById(com.pierfrancescosoffritti.youtubeplayer.R.id.progress);
        this.playButton = (ImageView) controlsView.findViewById(com.pierfrancescosoffritti.youtubeplayer.R.id.play_button);
        this.youTubeButton = (ImageView) controlsView.findViewById(com.pierfrancescosoffritti.youtubeplayer.R.id.youtube_button);
        this.fullScreenButton = (ImageView) controlsView.findViewById(com.pierfrancescosoffritti.youtubeplayer.R.id.fullscreen_button);
        this.customActionLeft = (ImageView) controlsView.findViewById(com.pierfrancescosoffritti.youtubeplayer.R.id.custom_action_left_button);
        this.customActionRight = (ImageView) controlsView.findViewById(com.pierfrancescosoffritti.youtubeplayer.R.id.custom_action_right_button);
        this.seekBar = (SeekBar) controlsView.findViewById(com.pierfrancescosoffritti.youtubeplayer.R.id.seek_bar);
        this.youTubeButton.setVisibility(View.INVISIBLE);
        this.seekBar.setOnSeekBarChangeListener(this);
        this.panel.setOnClickListener(this);
        this.playButton.setOnClickListener(this);
        this.fullScreenButton.setOnClickListener(this);
    }

    public void setOnFullScreenButtonListener(View.OnClickListener onFullScreenButtonListener) {
        this.onFullScreenButtonListener = onFullScreenButtonListener;
    }

    public void setYoutubeButtonVisible(int visible) {
        this.youTubeButton.setVisibility(visible);
    }

    protected void setCustomActionRight(Drawable icon, View.OnClickListener clickListener) {
        this.customActionRight.setImageDrawable(icon);
        this.customActionRight.setOnClickListener(clickListener);
        if (clickListener != null) {
            this.customActionRight.setVisibility(View.VISIBLE);
        } else {
            this.customActionRight.setVisibility(View.GONE);
        }

    }

    protected void setCustomActionLeft(Drawable icon, View.OnClickListener clickListener) {
        this.customActionLeft.setImageDrawable(icon);
        this.customActionLeft.setOnClickListener(clickListener);
        if (clickListener != null) {
            this.customActionLeft.setVisibility(View.VISIBLE);
        } else {
            this.customActionRight.setVisibility(View.GONE);
        }

    }

    public void onClick(View view) {
        if (view == this.panel) {
            this.toggleControlsVisibility();
        } else if (view == this.playButton) {
            this.onPlayButtonPressed();
        } else if (view == this.fullScreenButton) {
            this.onFullScreenPressed();
        }

    }

    private void onFullScreenPressed() {
        if (this.onFullScreenButtonListener == null) {
            this.youTubePlayerView.toggleFullScreen();
        } else {
            this.onFullScreenButtonListener.onClick(this.fullScreenButton);
        }

    }

    private void onPlayButtonPressed() {
        this.updateViewPlaybackState(!this.isPlaying);
        if (this.isPlaying) {
            this.youTubePlayerView.playVideo();
        } else {
            this.youTubePlayerView.pauseVideo();
        }

    }

    private void updateViewPlaybackState(boolean playing) {
        this.isPlaying = playing;
        int img = playing ? com.pierfrancescosoffritti.youtubeplayer.R.drawable.ic_pause_36dp : com.pierfrancescosoffritti.youtubeplayer.R.drawable.ic_play_36dp;
        this.playButton.setImageResource(img);
    }

    private void toggleControlsVisibility() {
        float finalAlpha = this.isVisible ? 0.0F : 1.0F;
        this.fadeControls(finalAlpha);
    }

    private void fadeControls(final float finalAlpha) {
        if (this.canFadeControls) {
            this.isVisible = finalAlpha != 0.0F;
            if (finalAlpha == 1.0F && this.isPlaying) {
                this.startFadeOutViewTimer();
            } else {
                this.handler.removeCallbacks(this.fadeOutRunnable);
            }

            this.controlsRoot.animate().alpha(finalAlpha).setDuration(300L).setListener(new Animator.AnimatorListener() {
                public void onAnimationStart(Animator animator) {
                    if (finalAlpha == 1.0F) {
                        wrapper.this.controlsRoot.setVisibility(View.VISIBLE);
                    }

                }

                public void onAnimationEnd(Animator animator) {
                    if (finalAlpha == 0.0F) {
                        wrapper.this.controlsRoot.setVisibility(View.GONE);
                    }

                }

                public void onAnimationCancel(Animator animator) {
                }

                public void onAnimationRepeat(Animator animator) {
                }
            }).start();
        }
    }

    private void startFadeOutViewTimer() {
        this.handler.postDelayed(this.fadeOutRunnable, 2500L);
    }

    public void onYouTubePlayerEnterFullScreen() {
        this.fullScreenButton.setImageResource(com.pierfrancescosoffritti.youtubeplayer.R.drawable.ic_fullscreen_exit_24dp);
    }

    public void onYouTubePlayerExitFullScreen() {
        this.fullScreenButton.setImageResource(com.pierfrancescosoffritti.youtubeplayer.R.drawable.ic_fullscreen_24dp);
    }

    public void onStateChange(int state) {
        this.newSeekBarProgress = -1;
        if (state != 1 && state != 2 && state != 5) {
            this.updateViewPlaybackState(false);
            this.fadeControls(1.0F);
            if (state == 3) {
                this.playButton.setVisibility(View.INVISIBLE);
                this.customActionLeft.setVisibility(View.GONE);
                this.customActionRight.setVisibility(View.GONE);
                this.progressBar.setVisibility(View.VISIBLE);
                this.canFadeControls = false;
            }

            if (state == -1) {
                this.panel.setBackgroundColor(ContextCompat.getColor(this.youTubePlayerView.getContext(), android.R.color.black));
                this.canFadeControls = false;
                this.progressBar.setVisibility(View.GONE);
                this.playButton.setVisibility(View.VISIBLE);
            }
        } else {
            this.panel.setBackgroundColor(ContextCompat.getColor(this.youTubePlayerView.getContext(), android.R.color.transparent));
            this.progressBar.setVisibility(View.GONE);
            this.playButton.setVisibility(View.VISIBLE);
            if (this.customActionLeft.hasOnClickListeners()) {
                this.customActionLeft.setVisibility(View.VISIBLE);
            } else {
                this.customActionLeft.setVisibility(View.GONE);
            }

            if (this.customActionRight.hasOnClickListeners()) {
                this.customActionRight.setVisibility(View.VISIBLE);
            } else {
                this.customActionRight.setVisibility(View.GONE);
            }

            this.canFadeControls = true;
            boolean playing = state == 1;
            this.updateViewPlaybackState(playing);
            if (playing) {
                this.startFadeOutViewTimer();
            } else {
                this.handler.removeCallbacks(this.fadeOutRunnable);
            }
        }

    }

    public void onCurrentSecond(float second) {
        if (!this.seekBarTouchStarted) {
            if (this.newSeekBarProgress <= 0 || Utils.formatTime(second).equals(Utils.formatTime((float) this.newSeekBarProgress))) {
                this.newSeekBarProgress = -1;
                this.seekBar.setProgress((int) second);
            }
        }
    }

    public void onVideoDuration(float duration) {
        this.videoDuration.setText(Utils.formatTime(duration));
        this.seekBar.setMax((int) duration);
    }

    public void onVideoTitle(String title) {
        this.videoTitle.setText(title);
    }

    public void onVideoId(final String videoId) {
        this.youTubeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("http://www.youtube.com/watch?v=" + videoId));
                wrapper.this.controlsRoot.getContext().startActivity(intent);
            }
        });
    }

    public void onReady() {
    }

    public void onLog(String log) {
    }

    public void onPlaybackQualityChange(int playbackQuality) {
    }

    public void onPlaybackRateChange(double rate) {
    }

    public void onError(int error) {
    }

    public void onApiChange() {
    }

    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        this.videoCurrentTime.setText(Utils.formatTime((float) i));
    }

    public void onStartTrackingTouch(SeekBar seekBar) {
        this.seekBarTouchStarted = true;
    }

    public void onStopTrackingTouch(SeekBar seekBar) {
        if (this.isPlaying) {
            this.newSeekBarProgress = seekBar.getProgress();
        }

        this.youTubePlayerView.seekTo(seekBar.getProgress());
        this.seekBarTouchStarted = false;
    }

    public void onNewVideo() {
        this.seekBar.setProgress(0);
        this.seekBar.setMax(0);
        this.videoDuration.post(new Runnable() {
            public void run() {
                wrapper.this.videoDuration.setText("");
            }
        });
        this.videoTitle.post(new Runnable() {
            public void run() {
                wrapper.this.videoTitle.setText("");
            }
        });
        this.youTubeButton.setOnClickListener(null);
    }

    public void showTitle(boolean show) {

        this.videoTitle.setVisibility(show ? View.VISIBLE : View.GONE);
    }
}
