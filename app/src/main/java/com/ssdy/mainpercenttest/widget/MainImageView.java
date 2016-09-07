package com.ssdy.mainpercenttest.widget;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import com.ssdy.mainpercenttest.LogUtil;

/**
 * description:自定义ImageView 实现动画
 * 动画图片点击按钮需要注意的问题
 * 1.连续点击连续触发问题
 * 2.多个图片按钮点击触发多个动作问题
 * User: zhaojiahao
 * Date: 2016/6/20
 * Time: 10:00
 */
public class MainImageView extends ImageView {

    private Animator anim1;
    private Animator anim2;
    private int mHeight;
    private int mWidth;
    private float mX, mY;
    private Handler mHandler = new Handler();
    private OnClickMessageListener messageListener; //对于同一个activity中的按钮能够同时接受消息

    private ScaleAnimation mScaleAnimation;
    private ScaleAnimation mScaleAnimation2;

//    private Matrix mMatrix = new Matrix();
    private RectF mRectF = new RectF();

    /**
     * 防止同类按钮多个点击多点触发问题
     */
    private static boolean isDown= false;
    private    boolean isInner= false;

    public MainImageView(Context context) {
        super(context);
        init();
    }

    public MainImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MainImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setOnClickMessageListener(OnClickMessageListener messageListener) {
        this.messageListener = messageListener;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mHeight = getHeight() - getPaddingTop() - getPaddingBottom();
        mWidth = getWidth() - getPaddingLeft() - getPaddingRight();
        mX = getX();
        mY = getY();
    }

    private void init() {

        PropertyValuesHolder valueHolder_1 = PropertyValuesHolder.ofFloat("scaleX", 1f, 0.9f);
        PropertyValuesHolder valuesHolder_2 = PropertyValuesHolder.ofFloat("scaleY", 1f, 0.9f);
        anim1 = ObjectAnimator.ofPropertyValuesHolder(this, valueHolder_1, valuesHolder_2);
        anim1.setDuration(150);
        anim1.setInterpolator(new LinearInterpolator());

        PropertyValuesHolder valueHolder_3 = PropertyValuesHolder.ofFloat("scaleX", 0.9f, 1f);
        PropertyValuesHolder valuesHolder_4 = PropertyValuesHolder.ofFloat("scaleY", 0.9f, 1f);
        anim2 = ObjectAnimator.ofPropertyValuesHolder(this, valueHolder_3,valuesHolder_4);
        anim2.setDuration(1500);
        anim2.setInterpolator(new LinearInterpolator());
    }

    private void start()
    {
        mScaleAnimation = new ScaleAnimation(1.0f,0.9f,1.0f,0.9f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        mScaleAnimation.setDuration(200);
        mScaleAnimation.setInterpolator(new AccelerateInterpolator());
        mScaleAnimation.setFillAfter(true);
        mScaleAnimation.setFillEnabled(true);
        this.startAnimation(mScaleAnimation);
        mScaleAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void end()
    {
        mScaleAnimation2 = new ScaleAnimation(0.9f,1.0f,0.9f,1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        mScaleAnimation2.setDuration(200);
        mScaleAnimation2.setInterpolator(new AccelerateInterpolator());
        mScaleAnimation2.setFillAfter(true);
        mScaleAnimation2.setFillEnabled(true);
        this.startAnimation(mScaleAnimation2);
        mScaleAnimation2.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }
            @Override
            public void onAnimationEnd(Animation animation) {
                if(isDown && isInner)
                {
                    isDown = false;
                    if (messageListener != null) {
                        messageListener.onClickMessage(MainImageView.this);
                    }
                }

            }
            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isDown = true;
                isInner = true;
                start();
                break;
            case MotionEvent.ACTION_MOVE:
            {
                if(isInner)
                {
                    if(!isInner(this,event.getRawX(),event.getRawY()))
                    {
                        LogUtil.e("isInner");
                        isInner = false;
                        end();
                        if(mScaleAnimation!= null)
                        {
                            mScaleAnimation.cancel();
                        }
                    }
                }
                break;
            }
            case MotionEvent.ACTION_UP:
            {
                if(isInner)
                {
                    end();
                    if(mScaleAnimation!= null)
                    {
                        mScaleAnimation.cancel();
                    }
                }
                break;
            }
        }
        return true;
    }

    public boolean isInner(View view,float x,float y)
    {
        Rect rect = new Rect();
        view.getGlobalVisibleRect(rect);
//        LogUtil.i(rect.left+"  "+rect.right+"  "+rect.top+"   "+ rect.bottom);

        if(rect.contains((int)x,(int)y))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    /**
     * description: 设置点击回调
     * User: zhaojiahao
     * Date: 2016/6/20
     * Time: 11:09
     */
    public interface OnClickMessageListener {
        void onClickMessage(View view);
    }
}