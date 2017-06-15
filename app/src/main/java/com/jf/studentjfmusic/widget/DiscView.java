package com.jf.studentjfmusic.widget;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.jf.studentjfmusic.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 位置：
 * 作用：
 * 时间：2017/6/15
 */

public class DiscView  extends RelativeLayout {
    private static final String TAG = "DiscView";

    ViewPager mVp;
    ImageView iv_needle;

    /**
    *内容：
    *有趣的初始数据
    *
    **/
    /**
     * 唱针抬起角度
     */
    private float NEEDLE_UP_ROTATION;

    /**
     * 唱盘距顶部大小
     */
    private float DISC_MARGIN_TOP;

    //比例
    private static float RATIO = 0;

    private float NEEDLE_WIDTH;
    private float NEEDLE_HEIGHT;

    //唱针距离左边大小
    private float NEEDLE_MARGIN_LEFT;
    //中心点
    private float NEEDLE_PIVOT_X;
    //中心点
    private float NEEDLE_PIVOT_Y;
    //唱针距离顶部大小
    private float NEEDLE_MARGIN_TOP;

    //唱盘大小
    private float DISC_SIZE;

    //唱盘白色背景大小
    private float DISC_BG_SIZE;

    //唱盘头像大小
    private float PIC_SIZE;






    public DiscView(Context context) {
        this(context,null);
    }

    public DiscView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public DiscView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        //加载布局
        initView(context);
    }

    private void initView(Context context) {
        initSize();

        View.inflate(context, R.layout.layout_discview,this);
        initDiscBg();
        initNeedle();
        initViewPager(context);
    }
    List<View> views;
    private void initViewPager(Context context) {
        mVp= (ViewPager) findViewById(R.id.vp);
        views=new ArrayList<>();
        for (int i = 0; i <5 ; i++) {

            View itemView= LayoutInflater.from(context).inflate(R.layout.item_vi_disc,mVp,false);
            ImageView iv= (ImageView) itemView.findViewById(R.id.iv);

            LinearLayout.LayoutParams params= (LinearLayout.LayoutParams) iv.getLayoutParams();
            params.height= (int) DISC_SIZE;
            params.weight=(int) DISC_SIZE;

            params.topMargin= (int) DISC_MARGIN_TOP;

            iv.setLayoutParams(params);
            iv.setImageResource(R.mipmap.play_disc);
            views.add(itemView);



        }
        VPAdapter vpAdater=new VPAdapter();
        mVp.setAdapter(vpAdater);
        mVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if(state==1)
                {
                    ObjectAnimator animator=ObjectAnimator.ofFloat(iv_needle,View.ROTATION,0,NEEDLE_UP_ROTATION);
                    animator.setDuration(500);
                    animator.setInterpolator(new LinearInterpolator());
                    animator.start();


                }
                else if(state==0)
                {
                    ObjectAnimator animator=ObjectAnimator.ofFloat(iv_needle,View.ROTATION,NEEDLE_UP_ROTATION,0);
                    animator.setInterpolator(new LinearInterpolator());
                    animator.setDuration(500);
                    animator.start();


                }





            }
        });








    }
    class VPAdapter extends PagerAdapter
    {

        @Override
        public int getCount() {
            return views.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(views.get(position));
            return views.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(views.get(position));
        }
    }

    private void initNeedle() {

        iv_needle= (ImageView) findViewById(R.id.iv_needle);
        RelativeLayout.LayoutParams params= (LayoutParams) iv_needle.getLayoutParams();
        params.height= (int) NEEDLE_HEIGHT;
        params.width= (int) NEEDLE_WIDTH;

        params.leftMargin= (int) NEEDLE_MARGIN_LEFT;
        params.topMargin= (int) NEEDLE_MARGIN_TOP;

        params.leftMargin = (int) NEEDLE_MARGIN_LEFT;
        params.topMargin = (int) NEEDLE_MARGIN_TOP * -1;


        iv_needle.setPivotX(NEEDLE_PIVOT_X);
        iv_needle.setPivotY(NEEDLE_PIVOT_Y);

        iv_needle.setLayoutParams(params);





    }




    private void initDiscBg() {

        ImageView iv= (ImageView) findViewById(R.id.iv_disc_bg);
        RelativeLayout.LayoutParams LayoutParams= (LayoutParams) iv.getLayoutParams();
        LayoutParams.width= (int) DISC_BG_SIZE;
        LayoutParams.height= (int) DISC_BG_SIZE;
        LayoutParams.topMargin= (int) DISC_MARGIN_TOP;
        iv.setLayoutParams(LayoutParams);
    }

    private void initSize() {
        RATIO = 1F * (getResources().getDisplayMetrics().heightPixels / 1920.0F);
        NEEDLE_UP_ROTATION = -30;

        /*唱针宽高、距离等比例*/
        NEEDLE_WIDTH = (float) (276.0 * RATIO);
        NEEDLE_HEIGHT = (float) (413.0 * RATIO);

        NEEDLE_MARGIN_LEFT = (float) (550.0 * RATIO);
        NEEDLE_PIVOT_X = (float) (43.0 * RATIO);
        NEEDLE_PIVOT_Y = (float) (43.0 * RATIO);
        NEEDLE_MARGIN_TOP = (float) (43.0 * RATIO);

        /*唱盘比例*/
        DISC_SIZE = (float) (804.0 * RATIO);
        DISC_BG_SIZE = (float) (810.0 * RATIO);

        DISC_MARGIN_TOP = (190 * RATIO);

        /*专辑图片比例*/
        PIC_SIZE = (float) (533.0 * RATIO);

    }
}
