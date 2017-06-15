package com.jf.studentjfmusic;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;

import com.jf.studentjfmusic.fragment.FoundFragment;
import com.jf.studentjfmusic.fragment.FriendFragment;
import com.jf.studentjfmusic.fragment.MyFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {


    private static final String TAG = "MainActivity";
//    @BindView(R.id.toolbar)
//    Toolbar toolbar;

    @BindView(R.id.dl)
    DrawerLayout dl;

    @BindView(R.id.vp_main)
    ViewPager vp_main;

    @BindView(R.id.my)
    ImageView my;

    @BindView(R.id.found)
    ImageView found;


    @BindView(R.id.friend)
    ImageView friend;


    ArrayList<Fragment> fragments;

    ArrayList<ImageView> mImageViews;

    //侧边栏布局
    //内容布局
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        mImageViews = new ArrayList<>();
        mImageViews.add(my);
        mImageViews.add(found);
        mImageViews.add(friend);

        fragments = new ArrayList<>();
        MyFragment f1 = new MyFragment();
        FoundFragment f2 = new FoundFragment();
        FriendFragment f3 = new FriendFragment();
        fragments.add(f1);
        fragments.add(f2);
        fragments.add(f3);

        MainAdapter mainAdapter = new MainAdapter(getSupportFragmentManager());
        vp_main.setAdapter(mainAdapter);
        vp_main.setCurrentItem(1);
        found.setSelected(true);
        my.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vp_main.setCurrentItem(0);

            }
        });

        found.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vp_main.setCurrentItem(1);
            }
        });

        friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vp_main.setCurrentItem(2);

            }
        });

        vp_main.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switchTabs(position);
//                if(position==0){
//                    found.setSelected(false);
//                    friend.setSelected(false);
//                    my.setSelected(true);
//                }
//                if(position == 1){
//                    found.setSelected(true);
//                    friend.setSelected(false);
//                    my.setSelected(false);
//                }
//
//                if(position == 2){
//                    found.setSelected(false);
//                    friend.setSelected(true);
//                    my.setSelected(false);
//                }

            }
        });
    }
    private void switchTabs(int position){
        for (int i = 0; i <mImageViews.size() ; i++) {
            if(i == position){
                mImageViews.get(i).setSelected(true);
            }else{
                mImageViews.get(i).setSelected(false);
            }
        }
    }



    class MainAdapter extends FragmentPagerAdapter {
        public MainAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

//        if(requestCode == 111){
//            ArrayList<Home> results = data.getParcelableArrayListExtra("homes");
//            homes.clear();
//            homes.addAll(results);
//            homeAdapter.notifyDataSetChanged();
//            rl_home.scrollToPosition(0);
//        }
    }




    @OnClick(R.id.iv_menu)
    void showMenu(View view){
        dl.openDrawer(Gravity.LEFT);
    }
}
