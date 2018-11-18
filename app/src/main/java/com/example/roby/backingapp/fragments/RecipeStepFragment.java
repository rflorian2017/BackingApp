package com.example.roby.backingapp.fragments;

import android.content.Context;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.roby.backingapp.R;
import com.example.roby.backingapp.model.Recipe;
import com.example.roby.backingapp.model.RecipeStep;
import com.example.roby.backingapp.ui.RecipeDetailActivity;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.Player;
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

import static android.support.constraint.Constraints.TAG;

public class RecipeStepFragment extends Fragment implements Player.EventListener {
    private SimpleExoPlayer mExoPlayer;
    private MediaSessionCompat mMediaSession;
    private PlaybackStateCompat.Builder mStateBuilder;
    private boolean mTwoPane;

    private RecipeStep currentStep;
    private Recipe selectedRecipe;

    //Define an interface that triggers a callback in the host activity for loading next or previous steps
    OnNextStepClickListener mOnNextStepCallback;
    OnPreviousStepClickListener mOnPreviousStepCallback;

    @BindView(R.id.step_description_tv)
    TextView stepDescriptionTv;

    @BindView(R.id.step_video_player)
    PlayerView mPlayerView;

    @BindView(R.id.previous_recipe_step_btn)
    Button mPrevStepBtn;

    @BindView(R.id.next_recipe_step_btn)
    Button mNextStepBtn;

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
        // get transferred information from bundle
        Bundle bundle = this.getArguments();

        if(bundle != null) {
            selectedRecipe = bundle.getParcelable(RecipeDetailActivity.RECIPE_PARCEL);
            currentStep = bundle.getParcelable(RecipeDetailActivity.RECIPE_STEP_PARCEL);
            mTwoPane = bundle.getBoolean(RecipeDetailActivity.TWO_PANE_BUNDLE);
        }

        View view = inflater.inflate(R.layout.fragment_recipe_step, container, false);

        ButterKnife.bind(this, view);
        buttonActions();

        stepDescriptionTv.setText(currentStep.getDescription());

        // fail safe, to hide video layout if no video is available
        if(currentStep.getVideoURL() == null || currentStep.getVideoURL().isEmpty() ) {
            mPlayerView.setVisibility(View.INVISIBLE);
        }
        else {
            initializeMediaSession();
            initializePlayer(Uri.parse(currentStep.getVideoURL()));
        }

        return view;

    }

    private void buttonActions() {
        //set buttons to inactive if first or last step
        if(0 == Integer.parseInt(currentStep.getId())) {
            mPrevStepBtn.setVisibility(View.INVISIBLE);
        }
        else if(Integer.parseInt(currentStep.getId()) == selectedRecipe.getmRecipeSteps().size() - 1) {
            mNextStepBtn.setVisibility(View.INVISIBLE);
        }

        mNextStepBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mOnNextStepCallback.clickNextStep(Integer.parseInt(currentStep.getId()) + 1);
            }
        });

        mPrevStepBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mOnPreviousStepCallback.clickPreviousStep(Integer.parseInt(currentStep.getId()) - 1);
            }
        });
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
    private void initializePlayer(Uri videoUri) {
        if (mExoPlayer == null) {
            // Create an instance of the ExoPlayer.
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();

            //mExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), new DefaultRenderersFactory(getContext()) , trackSelector, loadControl);
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(this.getContext(), trackSelector, loadControl);
            mPlayerView.setPlayer(mExoPlayer);

            // Set the ExoPlayer.EventListener to this activity.
            mExoPlayer.addListener(this);

            // Prepare the MediaSource.
            String userAgent = Util.getUserAgent(this.getContext(), "BackingApp");
            MediaSource mediaSource = new ExtractorMediaSource(videoUri, new DefaultDataSourceFactory(
                    this.getContext(), userAgent), new DefaultExtractorsFactory(), null, null);
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(true);


            //https://developer.android.com/training/system-ui/immersive
            //check if the orientation is landscape - if it is, load in fullscreen
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE & !mTwoPane) {
                openFullScreen();
            }
        }
    }

    private void openFullScreen() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        View decorView = this.getActivity().getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        // Set the content to appear under the system bars so that the
                        // content doesn't resize when the system bars hide and show.
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and status bar
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    /**
     * Initializes the Media Session to be enabled with media buttons, transport controls, callbacks
     * and media controller.
     */
    private void initializeMediaSession() {

        // Create a MediaSessionCompat.
        mMediaSession = new MediaSessionCompat(this.getContext(), TAG);

        // Enable callbacks from MediaButtons and TransportControls.
        mMediaSession.setFlags(
                MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                        MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);

        // Do not let MediaButtons restart the player when the app is not visible.
        mMediaSession.setMediaButtonReceiver(null);

        // Set an initial PlaybackState with ACTION_PLAY, so media buttons can start the player.
        mStateBuilder = new PlaybackStateCompat.Builder()
                .setActions(
                        PlaybackStateCompat.ACTION_PLAY |
                                PlaybackStateCompat.ACTION_PAUSE |
                                PlaybackStateCompat.ACTION_PLAY_PAUSE);

        mMediaSession.setPlaybackState(mStateBuilder.build());


        // MySessionCallback has methods that handle callbacks from a media controller.
        mMediaSession.setCallback(new MySessionCallback());

        // Start the Media Session since the activity is active.
        mMediaSession.setActive(true);

    }

    /**
     * Release ExoPlayer.
     */
    private void releasePlayer() {
        if(mExoPlayer !=null ) {
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }

        //release media session
        if (mMediaSession != null) {
            mMediaSession.release();
        }
    }

    /**
     * Method that is called when the ExoPlayer state changes. Used to update the MediaSession
     * PlayBackState to keep in sync, and post the media notification.
     * @param playWhenReady true if ExoPlayer is playing, false if it's paused.
     * @param playbackState int describing the state of ExoPlayer. Can be STATE_READY, STATE_IDLE,
     *                      STATE_BUFFERING, or STATE_ENDED.
     */
    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        if((playbackState == Player.STATE_READY) && playWhenReady){
            mStateBuilder.setState(PlaybackStateCompat.STATE_PLAYING,
                    mExoPlayer.getCurrentPosition(), 1f);
        } else if((playbackState == Player.STATE_READY)){
            mStateBuilder.setState(PlaybackStateCompat.STATE_PAUSED,
                    mExoPlayer.getCurrentPosition(), 1f);
        }
        mMediaSession.setPlaybackState(mStateBuilder.build());
    }

    @Override
    public void onStop() {
        super.onStop();
        releasePlayer();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        releasePlayer();
    }

    @Override
    public void onPause() {
        super.onPause();
        releasePlayer();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        releasePlayer();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }

    @Override
    public void onResume() {
        super.onResume();
        //play video on resume to ensure the player is initialize on resume to activity
        if (currentStep.getVideoURL() != null && !currentStep.getVideoURL().isEmpty()) {
            initializePlayer(Uri.parse(currentStep.getVideoURL()));
        }
    }

    private class MySessionCallback extends MediaSessionCompat.Callback {
        @Override
        public void onPlay() {
            mExoPlayer.setPlayWhenReady(true);
        }

        @Override
        public void onPause() {
            mExoPlayer.setPlayWhenReady(false);
        }

        @Override
        public void onSkipToPrevious() {
            mExoPlayer.seekTo(0);
        }
    }
}
