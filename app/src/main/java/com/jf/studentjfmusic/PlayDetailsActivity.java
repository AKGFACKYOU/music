package com.jf.studentjfmusic;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jf.studentjfmusic.bean.NewPlayListResultsBean;
import com.jf.studentjfmusic.widget.DiscView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.glide.transformations.BlurTransformation;


public class PlayDetailsActivity extends AppCompatActivity {
    public static final String DETAILS_KEY = "details";
    public static final String RESULTSBEEN_KEY = "mResultsBeen";
    public static final String INDEX_KEY = "position";

    private static final String TAG = "PlayDetailsActivity";

    @BindView(R.id.iv_bg)
    ImageView iv_bg;


    @BindView(R.id.tv_name)
    TextView tv_name;

    @BindView(R.id.tv_artist)
    TextView tv_artist;

    @BindView(R.id.dv)
    DiscView dv;

    ArrayList<NewPlayListResultsBean> mResultsBeen;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.ll_actionbar)
    LinearLayout llActionbar;

    @BindView(R.id.iv_last)
    ImageView ivLast;
    @BindView(R.id.iv_play)
    ImageView ivPlay;
    @BindView(R.id.iv_next)
    ImageView ivNext;

    @BindView(R.id.ll_bottom)
    LinearLayout llBottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_details);
        ButterKnife.bind(this);


        mResultsBeen = getIntent().getParcelableArrayListExtra(RESULTSBEEN_KEY);
        Log.e(TAG, "onCreate:接收到的 mResultsBeen数据：" + mResultsBeen);

        //获取下标
        int position = getIntent().getIntExtra(INDEX_KEY, 0);


        NewPlayListResultsBean bean = getIntent().getParcelableExtra(DETAILS_KEY);

        String url = "http://ac-kCFRDdr9.clouddn.com/e3e80803c73a099d96a5.jpg";
        if (bean.getAlbumPic() != null) {
            url = bean.getAlbumPic().getUrl();
        }
        tv_name.setText(bean.getTitle());
        tv_artist.setText(bean.getArtist());


        Glide.with(this)
                .load(url)
                //模糊图片, this   10 模糊度   5 将图片缩放到5倍后进行模糊
                .bitmapTransform(new BlurTransformation(this, 10, 5) {
                })
                .into(iv_bg);

        dv.setDiscChangListener(discChangListener);
        dv.setMusicData(mResultsBeen, position);
//        discChangListener.onActionbarChanged(null);

    }

    DiscView.DiscChangListener discChangListener = new DiscView.DiscChangListener() {
        @Override
        public void onActionbarChanged(NewPlayListResultsBean bean) {
            tv_name.setText(bean.getTitle());
            tv_artist.setText(bean.getArtist());
        }
    };

    @OnClick(R.id.iv_back)
    void back() {
        finish();
    }


    @OnClick({R.id.iv_last, R.id.iv_play, R.id.iv_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_last:
                dv.playLast();
                break;
            case R.id.iv_play:
                ivPlay.setSelected(!ivPlay.isSelected());
            if(ivPlay.isSelected()) {
                ivPlay.setImageResource(R.mipmap.play_rdi_btn_pause);
            }
            else
            {
                ivPlay.setImageResource(R.mipmap.play_rdi_btn_play);
            }

            dv.pause();
                break;
            case R.id.iv_next:
                dv.playNext();
                break;
        }
    }
}
