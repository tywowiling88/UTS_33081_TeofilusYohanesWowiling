package umn.ac.id.a33081_teofilusyohaneswowiling;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

import static umn.ac.id.a33081_teofilusyohaneswowiling.ListLaguActivity.songFiles;

public class PlayerActivity extends AppCompatActivity {

    TextView tvSongName, tvArtistName, tvDuration0, tvDuration1;
    ImageView ivBack, ivMenu, ivSongBackground, ivShuffle, ivSkipPrevious, ivSkipNext, ivRepeat;
    FloatingActionButton fabPlayPause;
    SeekBar sbSongDuration;

    int position = -1;
    ArrayList<SongFiles> listSongs = new ArrayList<>();
    static Uri uri;
    static MediaPlayer mediaPlayer;

    private Handler handler = new Handler();
    private Thread playThread, prevThread, nextThread, randomThread, repeatThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        initView();

        getIntentMethod();

        tvSongName.setText(listSongs.get(position).getTitle());
        tvArtistName.setText(listSongs.get(position).getArtist());
        sbSongDuration.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (mediaPlayer != null && fromUser){
                    mediaPlayer.seekTo(progress * 1000);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        PlayerActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mediaPlayer != null){
                    int mCurrentPosition = mediaPlayer.getCurrentPosition() / 1000;
                    sbSongDuration.setProgress(mCurrentPosition);
                    tvDuration0.setText(formattedTime(mCurrentPosition));
                }
                handler.postDelayed(this, 1000);
            }
        });

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back = new Intent(PlayerActivity.this, ListLaguActivity.class);
                startActivity(back);
            }
        });
    }

    @Override
    protected void onResume() {
        playThreadBtn();
        nextThreadBtn();
        prevThreadBtn();
        randomThreadBtn();
        repeatThreadBtn();

        super.onResume();
    }

    private void repeatThreadBtn() {
        repeatThread = new Thread(){
            @Override
            public void run() {
                super.run();
                ivRepeat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        repeatBtnClicked();
                    }
                });
            }
        };
        repeatThread.start();
    }

    private void repeatBtnClicked() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
            position = (position);
            uri = Uri.parse(listSongs.get(position).getPath());
            mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
            metaData(uri);
            tvSongName.setText(listSongs.get(position).getTitle());
            tvArtistName.setText(listSongs.get(position).getArtist());

            sbSongDuration.setMax(mediaPlayer.getDuration() / 1000);

            PlayerActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mediaPlayer != null){
                        int mCurrentPosition = mediaPlayer.getCurrentPosition() / 1000;
                        sbSongDuration.setProgress(mCurrentPosition);
                    }
                    handler.postDelayed(this, 1000);
                }
            });
            ivRepeat.setImageResource(R.drawable.ic_repeat_on);
            mediaPlayer.pause();
        } else{
            mediaPlayer.stop();
            mediaPlayer.release();
            position = (position);
            uri = Uri.parse(listSongs.get(position).getPath());
            mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
            metaData(uri);
            tvSongName.setText(listSongs.get(position).getTitle());
            tvArtistName.setText(listSongs.get(position).getArtist());

            sbSongDuration.setMax(mediaPlayer.getDuration() / 1000);

            PlayerActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mediaPlayer != null){
                        int mCurrentPosition = mediaPlayer.getCurrentPosition() / 1000;
                        sbSongDuration.setProgress(mCurrentPosition);
                    }
                    handler.postDelayed(this, 1000);
                }
            });
            ivRepeat.setImageResource(R.drawable.ic_repeat_on);
            mediaPlayer.pause();
        }
    }

    private void randomThreadBtn() {
        randomThread = new Thread(){
            @Override
            public void run() {
                super.run();
                ivShuffle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        randomBtnClicked();
                    }
                });
            }
        };
        randomThread.start();
    }

    private void randomBtnClicked() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();

            final int random = new Random().nextInt(listSongs.size());
            if (random > listSongs.size()){
                position = ((position + 1) % listSongs.size());
            } else if (random < listSongs.size()){
                position = ((position - 1) < 0 ? (listSongs.size() - 1) : (position - 1));
            } else {
                position = random;
            }

            uri = Uri.parse(listSongs.get(position).getPath());
            mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
            metaData(uri);
            tvSongName.setText(listSongs.get(position).getTitle());
            tvArtistName.setText(listSongs.get(position).getArtist());
            ivShuffle.setImageResource(R.drawable.ic_shuffle_on);
            sbSongDuration.setMax(mediaPlayer.getDuration() / 1000);

            PlayerActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mediaPlayer != null){
                        int mCurrentPosition = mediaPlayer.getCurrentPosition() / 1000;
                        sbSongDuration.setProgress(mCurrentPosition);
                    }
                    handler.postDelayed(this, 1000);
                }
            });
            fabPlayPause.setImageResource(R.drawable.ic_pause);
            mediaPlayer.start();
        } else{
            ivShuffle.setImageResource(R.drawable.ic_shuffle_on);
            mediaPlayer.start();
        }
    }

    private void prevThreadBtn() {
        prevThread = new Thread(){
            @Override
            public void run() {
                super.run();
                ivSkipPrevious.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        prevBtnClicked();
                    }
                });
            }
        };
        prevThread.start();
    }

    private void prevBtnClicked() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
            position = ((position - 1) < 0 ? (listSongs.size() - 1) : (position - 1));
            uri = Uri.parse(listSongs.get(position).getPath());
            mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
            metaData(uri);
            tvSongName.setText(listSongs.get(position).getTitle());
            tvArtistName.setText(listSongs.get(position).getArtist());

            sbSongDuration.setMax(mediaPlayer.getDuration() / 1000);

            PlayerActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mediaPlayer != null){
                        int mCurrentPosition = mediaPlayer.getCurrentPosition() / 1000;
                        sbSongDuration.setProgress(mCurrentPosition);
                    }
                    handler.postDelayed(this, 1000);
                }
            });
            fabPlayPause.setImageResource(R.drawable.ic_pause);
            mediaPlayer.start();;
        } else{
            mediaPlayer.stop();
            mediaPlayer.release();
            position = ((position - 1) < 0 ? (listSongs.size() - 1) : (position - 1));
            uri = Uri.parse(listSongs.get(position).getPath());
            mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
            metaData(uri);
            tvSongName.setText(listSongs.get(position).getTitle());
            tvArtistName.setText(listSongs.get(position).getArtist());

            sbSongDuration.setMax(mediaPlayer.getDuration() / 1000);

            PlayerActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mediaPlayer != null){
                        int mCurrentPosition = mediaPlayer.getCurrentPosition() / 1000;
                        sbSongDuration.setProgress(mCurrentPosition);
                    }
                    handler.postDelayed(this, 1000);
                }
            });
            fabPlayPause.setImageResource(R.drawable.ic_play);
        }
    }

    private void nextThreadBtn() {
        nextThread = new Thread(){
            @Override
            public void run() {
                super.run();
                ivSkipNext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        nextBtnClicked();
                    }
                });
            }
        };
        nextThread.start();
    }

    private void nextBtnClicked() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
            position = ((position + 1) % listSongs.size());
            uri = Uri.parse(listSongs.get(position).getPath());
            mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
            metaData(uri);
            tvSongName.setText(listSongs.get(position).getTitle());
            tvArtistName.setText(listSongs.get(position).getArtist());

            sbSongDuration.setMax(mediaPlayer.getDuration() / 1000);

            PlayerActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mediaPlayer != null){
                        int mCurrentPosition = mediaPlayer.getCurrentPosition() / 1000;
                        sbSongDuration.setProgress(mCurrentPosition);
                    }
                    handler.postDelayed(this, 1000);
                }
            });
            fabPlayPause.setImageResource(R.drawable.ic_pause);
            mediaPlayer.start();;
        } else{
            mediaPlayer.stop();
            mediaPlayer.release();
            position = ((position + 1) % listSongs.size());
            uri = Uri.parse(listSongs.get(position).getPath());
            mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
            metaData(uri);
            tvSongName.setText(listSongs.get(position).getTitle());
            tvArtistName.setText(listSongs.get(position).getArtist());

            sbSongDuration.setMax(mediaPlayer.getDuration() / 1000);

            PlayerActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mediaPlayer != null){
                        int mCurrentPosition = mediaPlayer.getCurrentPosition() / 1000;
                        sbSongDuration.setProgress(mCurrentPosition);
                    }
                    handler.postDelayed(this, 1000);
                }
            });
            fabPlayPause.setImageResource(R.drawable.ic_play);
        }
    }

    private void playThreadBtn() {
        playThread = new Thread(){
            @Override
            public void run() {
                super.run();
                fabPlayPause.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        playPauseBtnClicked();
                    }
                });
            }
        };
        playThread.start();
    }

    private void playPauseBtnClicked() {
        if (mediaPlayer.isPlaying()){
            fabPlayPause.setImageResource(R.drawable.ic_pause);
            mediaPlayer.pause();
            sbSongDuration.setMax(mediaPlayer.getDuration() / 1000);

            PlayerActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mediaPlayer != null){
                        int mCurrentPosition = mediaPlayer.getCurrentPosition() / 1000;
                        sbSongDuration.setProgress(mCurrentPosition);
                    }
                    handler.postDelayed(this, 1000);
                }
            });
        } else{
            fabPlayPause.setImageResource(R.drawable.ic_play);
            mediaPlayer.start();
            sbSongDuration.setMax(mediaPlayer.getDuration() / 1000);
        }
    }

    private String formattedTime(int mCurrentPosition) {
        String totalout = "";
        String totalNew = "";
        String seconds = String.valueOf(mCurrentPosition % 60);
        String minutes = String.valueOf(mCurrentPosition / 60);
        totalout = minutes + ":" + seconds;
        totalNew = minutes + ":" + "0" + seconds;

        if (seconds.length() == 1){
            return totalNew;
        }
        else {
            return totalout;
        }
    }


    private void getIntentMethod() {
        position = getIntent().getIntExtra("position", -1);
        listSongs = songFiles;

        if (listSongs != null){
            fabPlayPause.setImageResource(R.drawable.ic_pause);
            uri = Uri.parse(listSongs.get(position).getPath());
        }

        if (mediaPlayer != null){
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
            mediaPlayer.start();
        } else{
            mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
            mediaPlayer.start();
        }

        sbSongDuration.setMax(mediaPlayer.getDuration() / 1000);
        metaData(uri);

    }

    private void initView() {
        tvSongName = findViewById(R.id.tvSongName);
        tvArtistName = findViewById(R.id.tvArtistName);
        tvDuration0 = findViewById(R.id.tvDuration0);
        tvDuration1 = findViewById(R.id.tvDuration1);
        ivBack = findViewById(R.id.ivBack);
        ivMenu = findViewById(R.id.ivMenu);
        ivSongBackground = findViewById(R.id.ivSongBackground);
        ivShuffle = findViewById(R.id.ivShuffle);
        ivSkipPrevious = findViewById(R.id.ivSkipPrevious);
        ivSkipNext = findViewById(R.id.ivSkipNext);
        ivRepeat = findViewById(R.id.ivRepeat);
        fabPlayPause = findViewById(R.id.fabPlay);
        sbSongDuration = findViewById(R.id.sbSongDuration);
    }

    private void metaData(Uri uri){
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(uri.toString());

        int durationTotal = Integer.parseInt(listSongs.get(position).getDuration()) / 1000;
        tvDuration1.setText(formattedTime(durationTotal));

        byte[] art = retriever.getEmbeddedPicture();
        if (art != null)
        {
            Glide.with(this)
                    .asBitmap()
                    .load(art)
                    .into(ivSongBackground);
        } else{
            Glide.with(this)
                    .asBitmap()
                    .load(R.drawable.ic_launcher_background)
                    .into(ivSongBackground);
        }
    }
}