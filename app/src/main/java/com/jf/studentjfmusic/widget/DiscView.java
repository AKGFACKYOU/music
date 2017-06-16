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

import com.bumptech.glide.Glide;
import com.jf.studentjfmusic.R;
import com.jf.studentjfmusic.bean.NewPlayListResultsBean;

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

    List<View> mViews;


    //歌曲列表

    ArrayList<ObjectAnimator> mObjectAnimators = new ArrayList<>();
    ArrayList<NewPlayListResultsBean> mResultsBeen;
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

    private VPAdapter mVpAdapter;






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

    private void initViewPager(Context context) {
        mVp= (ViewPager) findViewById(R.id.vp);
        mResultsBeen=new ArrayList<>();
        mViews=new ArrayList<>();


        mVpAdapter=new VPAdapter();
        mVp.setAdapter(mVpAdapter);

        mVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            int lastpositionOffsetPixels;
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if(lastpositionOffsetPixels>positionOffsetPixels)
                {
                    if(positionOffset<0.5)
                    {
                        if(mDiscChangListener!=null)
                        {
                            mDiscChangListener.onActionbarChanged(mResultsBeen.get(position));
                        }
                    }else
                    {
                        mDiscChangListener.onActionbarChanged(mResultsBeen.get(mVp.getCurrentItem()));
                    }

                }
                else if(lastpositionOffsetPixels<positionOffsetPixels)
                {
                    if(positionOffset>0.5)
                    {
                        if(mDiscChangListener!=null)
                        {
                            mDiscChangListener.onActionbarChanged(mResultsBeen.get(position+1));
                        }
                    }
                    else
                    {
                        mDiscChangListener.onActionbarChanged(mResultsBeen.get(position));
                    }
                }
                lastpositionOffsetPixels=positionOffsetPixels;
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                pageScrollOPeration(state);
            }
        });


    }

    private void pageScrollOPeration(int state) {
        switch (state) {
            case ViewPager.SCROLL_STATE_IDLE:
                break;
            case ViewPager.SCROLL_STATE_SETTLING:
                ObjectAnimator animator1 = ObjectAnimator.ofFloat(iv_needle, View.ROTATION, NEEDLE_UP_ROTATION, 0);
                animator1.setInterpolator(new LinearInterpolator());
                animator1.setDuration(500);
                animator1.start();
                break;
            case ViewPager.SCROLL_STATE_DRAGGING:

                ObjectAnimator animator2 = ObjectAnimator.ofFloat(iv_needle, View.ROTATION, 0, NEEDLE_UP_ROTATION);
                animator2.setDuration(500);
                animator2.setInterpolator(new LinearInterpolator());
                animator2.start();
                break;
        }

    }

 class VPAdapter extends PagerAdapter
 {
     @Override
     public int getCount() {
         return mViews.size();
     }

     @Override
     public boolean isViewFromObject(View view, Object object) {
         return view==object;
     }

     @Override
     public Object instantiateItem(ViewGroup container, int position) {
         container.addView(mViews.get(position));
         return mViews.get(position);
     }

     @Override
     public void destroyItem(ViewGroup container, int position, Object object) {
         container.removeView(mViews.get(position));
     }
 }

    public void setMusicData(ArrayList<NewPlayListResultsBean> resultsBeen,int position)
    {
        mResultsBeen.clear();
        mResultsBeen.addAll(resultsBeen);
        for (NewPlayListResultsBean bean:mResultsBeen) {

            View itemView=LayoutInflater.from(getContext()).inflate(R.layout.item_vi_disc,mVp,false);

            ImageView iv= (ImageView) itemView.findViewById(R.id.iv);
            RelativeLayout.LayoutParams params= (RelativeLayout.LayoutParams) iv.getLayoutParams();
            params.height= (int) DISC_SIZE;
            params.width= (int) DISC_BG_SIZE;
            params.topMargin= (int) DISC_MARGIN_TOP;
            iv.setLayoutParams(params);
            iv.setImageResource(R.mipmap.play_disc);


            ImageView iv_pic= (ImageView) itemView.findViewById(R.id.iv_pic);
            RelativeLayout.LayoutParams picParams= (LayoutParams) iv_pic.getLayoutParams();
            picParams.width= (int) PIC_SIZE;
            picParams.height= (int) PIC_SIZE;

            picParams.topMargin=(int) (DISC_MARGIN_TOP + ((DISC_SIZE - PIC_SIZE) / 2));
            iv_pic.setLayoutParams(picParams);

            if(bean.getAlbumPic()!=null)
            {
                String url=bean.getAlbumPic().getUrl();
                Glide.with(getContext()).load(url).into(iv_pic);
            }

            mViews.add(itemView);

        }

        mVpAdapter.notifyDataSetChanged();
        mVp.setCurrentItem(position);
    }



    public DiscChangListener mDiscChangListener;

    public interface DiscChangListener
    {

        public void onActionbarChanged(NewPlayListResultsBean bean);
    }
    //构造方法
    public void setDiscChangListener(DiscChangListener discChangListener)
    {
        this.mDiscChangListener=discChangListener;
    }

    public void playLast()
    {
        mVp.setCurrentItem(mVp.getCurrentItem()-1,true);

    }

    public void pause()
    {
        if(mMusicPlayStatus==MusicPlayStatus.PLAY)
        {

            needleUp();
            mObjectAnimators.get(mVp.getCurrentItem()).pause();

            mMusicPlayStatus=MusicPlayStatus.PAUSE;


        }
        else if(mMusicPlayStatus==MusicPlayStatus.PAUSE)

        {

            needleDown();
            mMusicPlayStatus=MusicPlayStatus.PLAY;
            mObjectAnimators.get(mVp.getCurrentItem()).resume();
        }









    }
    public void playNext()
    {
        mVp.setCurrentItem(mVp.getCurrentItem()+1,true);
    }
    int mMusicPlayStatus=MusicPlayStatus.PLAY;
   interface MusicPlayStatus
   {
       int PLAY=0;
       int PAUSE=1;
   }

    public void needleUp(){
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(iv_needle, View.ROTATION, 0, NEEDLE_UP_ROTATION);
        animator2.setDuration(500);
        animator2.setInterpolator(new LinearInterpolator());
        animator2.start();
    }


    public void needleDown(){
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(iv_needle, View.ROTATION,NEEDLE_UP_ROTATION, 0);
        animator2.setDuration(500);
        animator2.setInterpolator(new LinearInterpolator());
        animator2.start();
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
