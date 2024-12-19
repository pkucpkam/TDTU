package com.example.bai2;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bai2.adapter.Music;
import com.example.bai2.adapter.MusicAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements MusicAdapter.OnMusicClickListener {
    TextView tvCurrentTime;
    TextView tvFinishTime;
    TextView tvSongName;
    Button btnBack;
    Button btnPause;
    Button btnNext;
    RecyclerView rvMusic;
    SeekBar seekBar;
    Handler handler = new Handler();
    MediaPlayer mediaPlayer;
    MusicAdapter musicAdapter;
    List<Music> dataSource;
    int currentMusicIndex;
    View musicController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        musicController.setVisibility(View.GONE);

        dataSource = new ArrayList<>();
        dataSource.add(new Music("Thiên Lý Ơi - J97", R.raw.thienlyoi));
        dataSource.add(new Music("Pickleball - Đỗ Phú Quí", R.raw.pickleball));
        dataSource.add(new Music("Hành khúc Đại học Tôn Đức Thắng", R.raw.tdtu));
        musicAdapter = new MusicAdapter(this, dataSource, this);

        rvMusic.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvMusic.setAdapter(musicAdapter);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int position, boolean fromUser) {
                if (fromUser) {
                    mediaPlayer.seekTo(position);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                mediaPlayer.pause();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.start();
            }
        });

        btnBack.setOnClickListener(v -> {
            backToPreviousSong();
        });

        btnNext.setOnClickListener(v -> {
            changeToNextSong();
        });

        btnPause.setOnClickListener(v -> {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
                btnPause.setText("Play");
            } else {
                mediaPlayer.start();
                updateSeekBarProgress();
                btnPause.setText("Pause");
            }
        });
    }

    private String convertMillisecond2Str(int milliseconds) {
        return String.format("%d:%d", TimeUnit.MILLISECONDS.toMinutes(milliseconds), TimeUnit.MILLISECONDS.toSeconds(milliseconds) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(milliseconds)));
    }

    public void updateSeekBarProgress() {
        seekBar.setProgress(mediaPlayer.getCurrentPosition());
        tvCurrentTime.setText(convertMillisecond2Str(mediaPlayer.getCurrentPosition()));
        if (mediaPlayer.isPlaying()) {
            Runnable notification = new Runnable() {
                public void run() {
                    updateSeekBarProgress();
                }
            };
            handler.postDelayed(notification, 1000);
        }
    }

    private void initViews() {
        btnBack = findViewById(R.id.btn_back);
        btnPause = findViewById(R.id.btn_pause);
        btnNext = findViewById(R.id.btn_next);
        rvMusic = findViewById(R.id.rv_music);
        tvCurrentTime = findViewById(R.id.tv_current_time);
        tvFinishTime = findViewById(R.id.tv_finish_time);
        seekBar = findViewById(R.id.seek_bar);
        tvSongName = findViewById(R.id.tv_song_name);
        musicController = findViewById(R.id.music_controller);
    }

    @Override
    public void onMusicClick(Music music) {
        musicController.setVisibility(View.VISIBLE);

        currentMusicIndex = dataSource.indexOf(music);
        setupMusicControllerAndPlayMusic(music);
    }

    private void setupMusicControllerAndPlayMusic(Music music) {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }

        mediaPlayer = MediaPlayer.create(this, music.getResourceId());
        tvSongName.setText(music.getName());
        mediaPlayer.start();
        tvFinishTime.setText(convertMillisecond2Str(mediaPlayer.getDuration()));
        updateSeekBarProgress();
        seekBar.setMax(mediaPlayer.getDuration());

        // cause new media player is created, so we need to set on completion listener again
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                changeToNextSong();
            }
        });
    }

    private void changeToNextSong() {
        currentMusicIndex++;
        if (currentMusicIndex >= dataSource.size()) {
            currentMusicIndex = 0;
        }
        Music music = dataSource.get(currentMusicIndex);
        setupMusicControllerAndPlayMusic(music);
    }

    private void backToPreviousSong() {
        currentMusicIndex--;
        if (currentMusicIndex < 0) {
            currentMusicIndex = dataSource.size() - 1;
        }
        Music music = dataSource.get(currentMusicIndex);
        setupMusicControllerAndPlayMusic(music);
    }
}