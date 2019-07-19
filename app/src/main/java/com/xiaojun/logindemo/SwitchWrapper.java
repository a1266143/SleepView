package com.xiaojun.logindemo;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Crated by xiaojun on 2019/7/19 10:00
 */
public class SwitchWrapper extends View {

    private boolean flag;
    private Context mContext;
    private List<Switch> list = new CopyOnWriteArrayList<>();
    private ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();


    public SwitchWrapper(Context context) {
        this(context,null);
    }

    public SwitchWrapper(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SwitchWrapper(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
    }

    public void toggle(boolean start){
        this.flag = start;
        if (flag){
            startAnim();
        }
    }

    private void startAnim(){
        singleThreadExecutor.execute(new Runnable() {
            @Override
            public void run() {
                while(flag){
                    Switch switch2 = new Switch(mContext);
                    switch2.setLayoutParams(getLayoutParams());
                    switch2.setWandH(w,h);
                    list.add(switch2);
                    switch2.startAnim(SwitchWrapper.this, new AnimationCallback() {
                        @Override
                        public void end(Switch switch2) {
                            list.remove(switch2);
                        }
                    });
                    try {
                        Thread.currentThread().sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private int w,h;

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.w = w;
        this.h = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (Switch switch2:list){
            switch2.draw(canvas);
        }
    }

    public interface AnimationCallback{
        void end(Switch switch2);
    }
}
