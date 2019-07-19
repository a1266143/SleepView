package com.xiaojun.logindemo;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateInterpolator;

import androidx.annotation.Nullable;

/**
 * Crated by xiaojun on 2019/7/19 9:45
 */
public class Switch extends View {

    private Paint mPaint;
    private final String PN_RADIUS = "pn_radius";
    private final String PN_ALPHA = "pn_alpha";
    private ValueAnimator mValueAnimator = new ValueAnimator();
    private float radius,alpha;
    private Handler mHandler;

    private int w,h;

    public void setWandH(int w,int h){
        this.w = w;
        this.h = h;
    }

    public Switch(Context context) {
        this(context,null);
    }

    public Switch(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public Switch(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mHandler = new Handler(context.getMainLooper());
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.rgb(21,67,96));
        mPaint.setColor(Color.WHITE);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    public void startAnim(final SwitchWrapper wrapper, final SwitchWrapper.AnimationCallback callback){
        PropertyValuesHolder radiusHolder = PropertyValuesHolder.ofFloat(PN_RADIUS,w/2.f,0f);//半径
        PropertyValuesHolder alphaHolder = PropertyValuesHolder.ofFloat(PN_ALPHA,0f,0.5f);//透明度
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                radius = (float) animation.getAnimatedValue(PN_RADIUS);
                alpha = (float) animation.getAnimatedValue(PN_ALPHA);
                mPaint.setAlpha((int) (255*alpha));
                wrapper.postInvalidateOnAnimation();
                postInvalidateOnAnimation();
            }
        });
        mValueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation, boolean isReverse) {
                callback.end(Switch.this);
            }
        });
        mValueAnimator.setValues(radiusHolder,alphaHolder);
        mValueAnimator.setInterpolator(new AccelerateInterpolator());
        mValueAnimator.setDuration(2000);
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mValueAnimator.start();
            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        canvas.translate(w/2.f,h/2.f);
        canvas.drawCircle(0,0,radius-70,mPaint);
        canvas.restore();
    }
}
