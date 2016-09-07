package com.ssdy.mainpercenttest.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

import com.ssdy.mainpercenttest.R;


/**
 * description:自定义进度条
 * User: zhaojiahao
 * Date: 2016/6/12
 * Time: 18:34
 */
public class ProgressView extends View {

    private static final int UPDATE_PROGRESS = 1;
    /**
     * 进度条最大值
     */
    private float mMaxCount;
    /**
     * 进度条当前值
     */
    private float mCurrentCount;
//    //设定的进度条数值
//    private float progressCount;
    /**
     * 画笔
     */
    private Paint mPaint;
    private int mWidth, mHeight;

    private Handler mHandler = new Handler();


    public ProgressView(Context context, AttributeSet attrs,
                        int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    public ProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public ProgressView(Context context) {
        super(context);
        initView(context);
    }

    private void initView(Context context) {
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        int round = mHeight / 2;
        System.out.println("max=" + mMaxCount + "  current=" + mCurrentCount);
        mPaint.setColor(getResources().getColor(R.color.snow));
        RectF rectBg = new RectF(0, 0, mWidth, mHeight);
        canvas.drawRoundRect(rectBg, round, round, mPaint);
        mPaint.setColor(getResources().getColor(R.color.snow));
        RectF rectBlackBg = new RectF(2, 2, mWidth - 2, mHeight - 2);
        canvas.drawRoundRect(rectBlackBg, round, round, mPaint);

        float section = mCurrentCount / mMaxCount;
        RectF rectProgressBg = new RectF(3, 3, (mWidth-3)*section, mHeight-3);
        if(section <= 3.0f/3.0f){
            if(section != 0.0f){
                mPaint.setColor(getResources().getColor(R.color.sky_blue));
            }else{
                mPaint.setColor(Color.TRANSPARENT);
            }
        }
        canvas.drawRoundRect(rectProgressBg, round, round, mPaint);
    }

    private int dipToPx(int dip) {
        float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f * (dip >= 0 ? 1 : -1));
    }

    /**
     * 设置进度条的进度值，使得进度条由当前的数值跳转到设定的数值
     * @param progressCount  进度值
     */
    public void setProgress(final float progressCount)
    {
        final float mProgressCount = progressCount > mMaxCount ? mMaxCount : progressCount;
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(mProgressCount> mCurrentCount)
                {
                    setmCurrentCount(mCurrentCount +1);
                    mHandler.postDelayed(this, 50);
                }
                else
                {
                    mHandler.removeCallbacksAndMessages(null);
                }
            }
        },50);
    }

    public void removeHandler(){
        if(null != mHandler){
            mHandler.removeCallbacksAndMessages(null);
        }
    }

    /***
     * 设置最大的进度值
     */
    public void setmMaxCount(float mMaxCount) {
        this.mMaxCount = mMaxCount;
    }

    /***
     * 设置当前的进度值
     */
    private void setmCurrentCount(float mCurrentCount) {
        this.mCurrentCount = mCurrentCount > mMaxCount ? mMaxCount : mCurrentCount;
        invalidate();
    }

    public float getmMaxCount() {
        return mMaxCount;
    }

    public float getmCurrentCount() {
        return mCurrentCount;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        if (widthSpecMode == MeasureSpec.EXACTLY || widthSpecMode == MeasureSpec.AT_MOST) {
            mWidth = widthSpecSize;
        } else {
            mWidth = 0;
        }
        if (heightSpecMode == MeasureSpec.AT_MOST || heightSpecMode == MeasureSpec.UNSPECIFIED) {
            mHeight = dipToPx(15);
        } else {
            mHeight = heightSpecSize;
        }
        setMeasuredDimension(mWidth, mHeight);
    }




}
