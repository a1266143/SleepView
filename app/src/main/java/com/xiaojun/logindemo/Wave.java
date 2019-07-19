package com.xiaojun.logindemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * Crated by xiaojun on 2019/7/19 11:35
 */
public class Wave extends View {

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Path mPathWave1 = new Path();
    private int viewWidth,viewHeight;

    public Wave(Context context) {
        this(context,null);
    }

    public Wave(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public Wave(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.viewWidth = w;
        this.viewHeight = h;
    }

    private void init() {
        mPaint.setColor(Color.BLUE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPathWave1.moveTo(0,viewHeight/2.f);//设置起点
        mPathWave1.quadTo(viewWidth/2.f-70,-50,viewWidth,viewHeight/2.f);//设置控制点和终点
        canvas.drawPath(mPathWave1,mPaint);
    }
}
