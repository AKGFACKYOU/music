package com.jf.studentjfmusic.service;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.IOException;

/**
 * Created by weidong on 2017/6/12.
 */

public class MusicService extends Service {
    private static final String TAG = "MusicService";

    private MediaPlayer mMediaPlayer;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MusicBinder();
    }

    public class MusicBinder extends Binder {

        public void play(){
            mMediaPlayer.start();
        }

        /**
         * 播放
         */
        public void play(String uri) {
            Log.e(TAG, "play: 开始播放啦");

            if (mMediaPlayer == null) {
                mMediaPlayer = new MediaPlayer();
                mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mMediaPlayer.setLooping(false);
            }
            mMediaPlayer.reset();
            try {
                mMediaPlayer.setDataSource(uri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                mMediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mMediaPlayer.start();
        }

        /**
         * 暂停
         */
        public void pause() {
            mMediaPlayer.pause();
        }

        /**
         * 获取播放状态
         * @return
         */
        public boolean isPlaying() {
            if (mMediaPlayer != null) {
                return mMediaPlayer.isPlaying();
            } else {
                return false;
            }
        }
    }

}
