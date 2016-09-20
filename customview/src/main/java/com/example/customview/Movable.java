package com.example.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Describe:
 * Created by ys on 2016/9/20 14:09.
 */
public class Movable extends View {

    public float bitmapX;
    public float bitmapY;
    private Context context;

    public Movable(Context context) {
        super(context);
        this.context = context;
        bitmapX = 50;
        bitmapY = 50;
    }

    public Movable(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Movable(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setAntiAlias(true);//抗锯齿功能
        paint.setColor(Color.BLUE);  //设置画笔颜色
        paint.setStyle(Paint.Style.STROKE);//设置填充样式
        paint.setStrokeWidth(30);//设置画笔宽度
        paint.setShadowLayer(10, 15, 15, Color.GREEN);//设置阴影
        /*Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(),R.drawable.yellow);//根据图片生成图对象
        canvas.drawBitmap(bitmap,bitmapX,bitmapY,paint);//绘制
        if(bitmap.isRecycled()){ //判断图片是否回收
            bitmap.recycle();//强制回收图片
        }*/
        canvas.drawCircle(bitmapX, bitmapY, 50, paint);

        Paint paint2 = new Paint();
        paint2.setAntiAlias(true);//抗锯齿功能
        paint2.setColor(Color.BLUE);  //设置画笔颜色
        paint2.setStyle(Paint.Style.STROKE);//设置填充样式
        paint2.setStrokeWidth(3);//设置画笔宽度
        //先创建两个大小一样的路径
        // 第一个逆向生成
        Path CCWRectpath = new Path();
        RectF rect1 = new RectF(50, 150, 350, 250);
        CCWRectpath.addRect(rect1, Path.Direction.CCW);

        //第二个顺向生成
        Path CWRectpath = new Path();
        RectF rect2 = new RectF(400, 150, 600, 250);
        CWRectpath.addRect(rect2, Path.Direction.CW);

        //先画出这两个路径
        canvas.drawPath(CCWRectpath, paint2);
        canvas.drawPath(CWRectpath, paint2);

        //依据路径写出文字
        String text = "abcdefghijklmnopqrstuvwxyz";
        paint2.setColor(Color.GRAY);
        paint2.setTextSize(35);
        canvas.drawTextOnPath(text, CCWRectpath, 0, 18, paint2);//逆时针生成
        canvas.drawTextOnPath(text, CWRectpath, 0, 18, paint2);//顺时针生成
    }
}
