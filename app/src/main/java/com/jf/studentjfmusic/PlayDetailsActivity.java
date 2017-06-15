package com.jf.studentjfmusic;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.jf.studentjfmusic.adapter.PlayDetailsAdapter;
import com.jf.studentjfmusic.bean.NewPlayListResultsBean;
import com.jf.studentjfmusic.fragment.found.RoundFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.glide.transformations.BlurTransformation;

public class PlayDetailsActivity extends AppCompatActivity {
    public static final String DETAILS_KEY = "details";


    @BindView(R.id.iv_bg)
    ImageView iv_bg;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);



        setContentView(R.layout.activity_play_details);
        ButterKnife.bind(this);
        NewPlayListResultsBean bean = getIntent().getParcelableExtra(DETAILS_KEY);




        Glide.with(this)
                .load("http://ac-kCFRDdr9.clouddn.com/e3e80803c73a099d96a5.jpg")
                //模糊图片, this   10 模糊度   5 将图片缩放到5倍后进行模糊
                .bitmapTransform(new BlurTransformation(this,10,3) {
                })
                .into(iv_bg);

    }

    @OnClick(R.id.iv_back)
    void back() {
        finish();
    }

}
