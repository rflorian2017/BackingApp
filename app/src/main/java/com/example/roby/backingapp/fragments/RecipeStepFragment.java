package com.example.roby.backingapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.media.session.MediaSessionCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.roby.backingapp.R;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.RenderersFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeStepFragment extends Fragment implements Player.EventListener {
    private SimpleExoPlayer mExoPlayer;
    private MediaSessionCompat mMediaSession;
    private boolean mTwoPane;

    //Define an interface that triggers a callback in the host activity for loading next or previous steps
    OnNextStepClickListener mOnNextStepCallback;
    OnPreviousStepClickListener mOnPreviousStepCallback;

    @BindView(R.id.step_description_tv)
    TextView stepDescriptionTv;

    @BindView(R.id.step_video_player)
    PlayerView mPlayerView;

    public RecipeStepFragment() {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        //make sure that the host has implemented the callback
        try {
            mOnPreviousStepCallback = (RecipeStepFragment.OnPreviousStepClickListener) context;
            mOnNextStepCallback = (RecipeStepFragment.OnNextStepClickListener) context;
        }
        catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnPreviousStepClickListener and OnNextStepClickListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //ButterKnife.bind(this, )
        return super.onCreateView(inflater, container, savedInstanceState);

    }

    public interface OnNextStepClickListener {
        void clickNextStep(int step);
    }

    public interface OnPreviousStepClickListener {
        void clickPreviousStep(int step);
    }

    /**
     * Initialize ExoPlayer.
     */
    private void initializePlayer() {
        if (mExoPlayer == null) {
            // Create an instance of the ExoPlayer.
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();

            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), new DefaultRenderersFactory(getContext()) , trackSelector, loadControl);
            mPlayerView.setPlayer(mExoPlayer);

            // Set the ExoPlayer.EventListener to this activity.
            mExoPlayer.addListener(this);

            // Prepare the MediaSource.

        }
    }
}
