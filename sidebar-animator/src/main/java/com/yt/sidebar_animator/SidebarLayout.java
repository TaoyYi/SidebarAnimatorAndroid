package com.yt.sidebar_animator;

import android.animation.Animator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * author : YiTao
 * Created by yt on 2017/4/27.
 * describe :
 */

public class SidebarLayout extends RelativeLayout {

    private View mView;
    private SidebarAnimator mSidebarAnimator;
    private Context mContext;
    private View mHold;
    private boolean isHold = false;
    private Animator.AnimatorListener mAnimatorListener;


    public SidebarLayout(Context context) {
        this(context, null);
    }


    public SidebarLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        mSidebarAnimator = SidebarAnimator.getInstance();
    }

    float width;                        //屏幕的宽度

    private final float mMovingLayout = 0.8f; //layout最小的缩小距离
    private final float mMovingView = 0.5f; //view最小的缩小距离

    /**
     * 绑定侧滑栏
     *
     * @param view   侧滑栏
     * @param hold   侧滑栏开关
     * @param widths 屏幕的宽度
     */
    public void bindSidebar(View view, View hold, int widths) {
        if (mView == null) {

            this.width = widths;
            mView = view == null ? new View(mContext) : view;
            mHold = hold == null ? new View(mContext) : hold;
//        mMovingLayout = movingLayout;
//        mMovingView = movingView;

            mSidebarAnimator.initHide(view, mHold, width, mMovingView, mAnimatorListener);//初始化隐藏侧边栏
            viewEndTranslation = mMovingView * -width;
            mHold.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (isHold) {


                        layoutEndTranslation = 0;
                        viewEndTranslation = (int) (-width * mMovingView);

                        layoutStartScale = mMovingLayout;
                        viewStartScale = 1f;

                        layoutEndScale = 1f;
                        viewEndScale = mMovingView;

                        mSidebarAnimator.start(SidebarLayout.this, mView, SidebarLayout.this.getX(), mView.getX(), layoutEndTranslation, viewEndTranslation, layoutStartScale, viewStartScale, layoutEndScale, viewEndScale, 250);
                    } else {

                        layoutEndTranslation = mMovingView * width;
                        viewEndTranslation = 0;

                        layoutStartScale = 1f;
                        viewStartScale = mMovingView;

                        layoutEndScale = mMovingLayout;
                        viewEndScale = 1f;

                        mSidebarAnimator.start(SidebarLayout.this, mView, SidebarLayout.this.getX(), mView.getX(), layoutEndTranslation, viewEndTranslation, layoutStartScale, viewStartScale, layoutEndScale, viewEndScale, 250);

                    }
                    isHold = !isHold;

                }
            });
        }

    }


    //    float layoutStartTranslation = 1f;   //移动的开始位置
//    float viewStartTranslation = mMovingView * -width;
    float layoutEndTranslation = 0;      //移动的结束位置
    float viewEndTranslation;
    float layoutStartScale = 1f;            //现在的比例
    float viewStartScale = mMovingView;
    float layoutEndScale = layoutStartScale;               //结束的比例
    float viewEndScale = mMovingView;


    float downY;
    float downX;
    float startX;
    float startY;
    float upX;
    float upY;


    public void LayoutTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:// 按下
                // 获取按下的时候的XY轴的位置
                downY = event.getRawY();
                downX = event.getRawX();
                // 获取手指按下的坐标
                startX = downX;
                startY = downY;


                break;
            case MotionEvent.ACTION_MOVE:// 移动
                // 获取手指移动到了哪个点的坐标
                float movingX = event.getRawX();
                float movingY = event.getRawY();
                // 相对于上一个点，手指在X移动的距离
                float dx = movingX - startX;
                float dy = movingY - startY;

                //view放大缩小后的大小
                float moving = viewEndScale + (dx / width);
                float movinglayout = layoutEndScale - (dx / width * mMovingView * mMovingLayout);
                if (moving <= 1f && moving >= mMovingView) {
                    viewEndScale = moving;
                    layoutEndScale = movinglayout;

                    layoutEndTranslation = layoutEndTranslation + dx;
                    viewEndTranslation = viewEndTranslation + dx;
                    mSidebarAnimator.start(this, mView, layoutEndTranslation, viewEndTranslation, layoutEndTranslation, viewEndTranslation, layoutEndScale, viewEndScale, layoutEndScale, viewEndScale, 1);
                }

                // 本次移动的结尾作为下一次移动的开始
                startX = (int) event.getRawX();
                startY = (int) event.getRawY();

                break;
            case MotionEvent.ACTION_UP:// 抬起

                float y2 = event.getRawY();
                float x2 = event.getRawX();

                upX = x2 - downX;
                upY = y2 - downY;

                if (upX > 5) {
                    layoutStartScale = layoutEndScale;
                    viewStartScale = viewEndScale;
                    layoutEndTranslation = width * mMovingView;
                    viewEndTranslation = 0;
                    layoutEndScale = mMovingLayout;
                    viewEndScale = 1f;
                    isHold = true;
                    mSidebarAnimator.start(this, mView, this.getX(), mView.getX(), layoutEndTranslation, viewEndTranslation, layoutStartScale, viewStartScale, layoutEndScale, viewEndScale, (int) (250 * (1 - this.getX() / width)));


                } else if (upX < -5) {
                    layoutStartScale = layoutEndScale;
                    viewStartScale = viewEndScale;
                    layoutEndTranslation = 0;
                    viewEndTranslation = mMovingView * -width;
                    layoutEndScale = 1f;
                    viewEndScale = mMovingView;
                    isHold = false;
                    mSidebarAnimator.start(this, mView, this.getX(), mView.getX(), layoutEndTranslation, viewEndTranslation, layoutStartScale, viewStartScale, layoutEndScale, viewEndScale, (int) (500 * (this.getX() / width)));


                }

                break;

        }
    }

    /**
     * 添加动画监听
     *
     * @param animatorListener
     */
    public void addAnimatorListener(Animator.AnimatorListener animatorListener) {
        mAnimatorListener = animatorListener;

    }


}
