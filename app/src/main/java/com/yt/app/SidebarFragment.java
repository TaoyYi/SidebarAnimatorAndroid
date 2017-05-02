package com.yt.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * author : YiTao
 * Created by yt on 2017/4/27.
 * describe :
 */

public class SidebarFragment extends Fragment {

    private View mView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(com.yt.sidebar_animator.R.layout.fragment_sidebar, container, false);
        return mView;


    }
}
