package com.jf.studentjfmusic.fragment.found;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jf.studentjfmusic.R;

/**
 * 位置：
 * 作用：
 * 时间：2017/6/14
 */

public class RoundFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_round,null);
        return view;
    }
}
