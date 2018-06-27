package com.sample;

import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import com.activeandroid.query.Select;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.sample.databinding.ActivityDetailBinding;

import io.objectbox.Box;

public class DetailActivity extends AppCompatActivity {

    private ActivityDetailBinding binding;
    private String videoId = "", videoUrl = "";
    private int likes, dislikes;
    private Box<VideoData> videoDataBox;
    private VideoData vd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail);

        videoId = getIntent().hasExtra("VideoId") ? getIntent().getStringExtra("VideoId") : "";
        videoUrl = getIntent().hasExtra("VideoUrl") ? getIntent().getStringExtra("VideoUrl") : "";

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

     //   videoDataBox = ((MyApplication) getApplication()).getBoxStore().boxFor(VideoData.class);

        vd =VideoData.getLikeDislikeCounts(videoId);
        likes = vd.getLikeCounts();
        dislikes = vd.getDislikeCounts();

        binding.tvLikeCount.setText(likes + " " + getString(R.string.likes));
        binding.tvDislikeCount.setText(dislikes + " " + getString(R.string.dislikes));
        initVideo();
    }

    public void handleClicks(View v)
    {
        switch (v.getId())
        {
            case R.id.ivLike:
                likes++;
                binding.tvLikeCount.setText(likes + " " + getString(R.string.likes));
                vd.setLikeCounts(likes);
                vd.save();
                break;

            case R.id.ivDislike:
                dislikes++;
                binding.tvDislikeCount.setText(dislikes + " " + getString(R.string.dislikes));
                vd.setDislikeCounts(dislikes);
                vd.save();
                break;
        }
    }

    private void initVideo() {
        DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory =
                new AdaptiveTrackSelection.Factory(bandwidthMeter);

        DefaultTrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);

        SimpleExoPlayer player = ExoPlayerFactory.newSimpleInstance(this, trackSelector);

        binding.videoPlayer.setPlayer(player);

        player.setPlayWhenReady(true);
        DefaultExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();

        MediaSource mediaSource = new ExtractorMediaSource(Uri.parse(videoUrl),
                ExoUtils.getCacheData(this), extractorsFactory, null, null);
        player.prepare(mediaSource);
        player.setPlayWhenReady(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(menuItem);
    }
}