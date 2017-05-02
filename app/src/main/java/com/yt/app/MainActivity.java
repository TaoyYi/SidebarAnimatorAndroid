package com.yt.app;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.TextView;

import com.yt.sidebar_animator.SidebarLayout;

public class MainActivity extends AppCompatActivity {

    SidebarFragment mSidebarFragment;
    private SidebarLayout mSidebarLayout;
    private TextView mSidebarhold;
    private int width;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //        获取屏幕的宽高
        WindowManager wm = (MainActivity.this).getWindowManager();
        width = wm.getDefaultDisplay().getWidth();

//初始化fragment (sidebar-animator支持所有的view用来做侧边栏,并不局限与fragment,本dome就以fragment为例)
        FragmentManager fm = getSupportFragmentManager();
        mSidebarFragment = (SidebarFragment) fm.findFragmentById(R.id.sidebar);
        if (mSidebarFragment == null) {
            mSidebarFragment = new SidebarFragment();
            fm.beginTransaction().add(R.id.sidebar, mSidebarFragment).commit();
        }
//SidebarLayout
        mSidebarLayout = (SidebarLayout) findViewById(R.id.sidebar_layout);
//开关
        mSidebarhold = (TextView) findViewById(R.id.sidebar_hold);
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        //重点来了 : 绑定侧滑栏
        mSidebarLayout.bindSidebar(mSidebarFragment.getView(), mSidebarhold, width);  // 侧滑栏 , 侧滑栏开关 , 屏幕的宽度
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        //将touch事件传给SidebarLayout
        mSidebarLayout.LayoutTouchEvent(ev);
        return super.dispatchTouchEvent(ev);
    }
}
