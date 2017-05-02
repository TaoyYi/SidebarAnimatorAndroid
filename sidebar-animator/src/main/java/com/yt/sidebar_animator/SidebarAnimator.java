package com.yt.sidebar_animator;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;

/**
 * author : YiTao
 * Created by yt on 2017/4/25.
 * describe :
 */

public class SidebarAnimator {


    private View mHold;
    private Animator.AnimatorListener mListener;

    private SidebarAnimator() {
    }

    public static SidebarAnimator getInstance() {
        return SidebarInstanceHolder.sSidebarAnimator;
    }

    static class SidebarInstanceHolder {
        private static final SidebarAnimator sSidebarAnimator = new SidebarAnimator();

    }


    /**
     * 初始化隐藏控件位置
     *
     * @param view   需要执行该动画的控件
     * @param width  屏幕的宽度
     * @param minify view的缩小倍数,与左移倍数
     */
    public void initHide(View view, View hold, float width, float minify, Animator.AnimatorListener listener) {
        mHold = hold;
        mListener = listener;
        /**
         * 初始化view的位置
         */
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationX", 0, (int) (-width * minify));
        ObjectAnimator rotate = ObjectAnimator.ofFloat(view, "scaleY", 1f, minify);
        ObjectAnimator rotate2 = ObjectAnimator.ofFloat(view, "scaleX", 1f, minify);
        AnimatorSet animSet = new AnimatorSet();
        animSet.playTogether(rotate, rotate2, animator);//, animator , rotate2
        animSet.setDuration(1);
        animSet.start();
    }

    boolean isAnimator = true; //是否没有动画正在进行

    public void start(View act, View view, float actStartTranslation, float viewStartTranslation, float actEndTranslation, float viewEndTranslation, float actStartScale, float viewStartScale, float actEndScale, float viewEndScale, int animDuration) {
        if (isAnimator) {
            if (animDuration != 1) {
                isAnimator = false;
            }
            ObjectAnimator animator = ObjectAnimator.ofFloat(act, "translationX", actStartTranslation, actEndTranslation);
            ObjectAnimator animatorView = ObjectAnimator.ofFloat(view, "translationX", viewStartTranslation, viewEndTranslation);


            ObjectAnimator rotate = ObjectAnimator.ofFloat(act, "scaleY", actStartScale, actEndScale);
            ObjectAnimator rotate2 = ObjectAnimator.ofFloat(act, "scaleX", actStartScale, actEndScale);

            ObjectAnimator rotateView = ObjectAnimator.ofFloat(view, "scaleY", viewStartScale, viewEndScale);
            ObjectAnimator rotateView2 = ObjectAnimator.ofFloat(view, "scaleX", viewStartScale, viewEndScale);

            AnimatorSet animSet = new AnimatorSet();
            animSet.playTogether(rotate, rotate2, animator, animatorView, rotateView, rotateView2);//, animator , rotate2


            animSet.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {
                    if (mListener != null) {
                        mListener.onAnimationStart(animator);

                    }
                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    isAnimator = true;
                    if (mListener != null) {
                        mListener.onAnimationEnd(animator);
                    }
                }

                @Override
                public void onAnimationCancel(Animator animator) {
                    if (mListener != null) {
                        mListener.onAnimationCancel(animator);
                    }
                }

                @Override
                public void onAnimationRepeat(Animator animator) {
                    if (mListener != null) {
                        mListener.onAnimationRepeat(animator);
                    }
                }
            });

            //根据距离终点的距离 调节动画的速度
            animSet.setDuration(animDuration);
            animSet.start();

        }

    }


}
