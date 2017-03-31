package com.weiruanit.lifepro.module.news.widget;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.weiruanit.lifepro.R;

/**
 * Created by Administrator on 2016/11/18 0018.
 */

public class LoadingActionFigureView extends LinearLayout {

    private AnimationDrawable mAnimation;
    private ImageView mLoadImageView;
    private TextView mLoadTextView;
    private int mLoadAnimation;

    public LoadingActionFigureView(Context context) {
        super(context);
    }

    public LoadingActionFigureView(Context context, AttributeSet attrs) {
        super(context, attrs);
//        init();
    }

    public LoadingActionFigureView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init(){
        LinearLayout linearLayout = new LinearLayout(getContext());
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        linearLayout.setLayoutParams(layoutParams);
        linearLayout.setOrientation(VERTICAL);
        linearLayout.setGravity(Gravity.CENTER);

        LayoutParams imageViewLayoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        mLoadImageView = new ImageView(getContext());
        mLoadImageView.setLayoutParams(imageViewLayoutParams);
        linearLayout.addView(mLoadImageView);

        mLoadTextView = new TextView(getContext());
        mLoadTextView.setLayoutParams(imageViewLayoutParams);
        mLoadTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.blue));
        linearLayout.addView(mLoadTextView);

        mLoadImageView.setBackgroundResource(R.drawable.loading_s);
        // 通过ImageView对象拿到背景显示的AnimationDrawable
        mAnimation = (AnimationDrawable) mLoadImageView.getBackground();
        // 只显示第一帧的解决方案之一
        mLoadImageView.post(new Runnable() {
            @Override
            public void run() {
                mAnimation.start();
            }
        });
        mLoadTextView.setText("正在加载");
        addView(linearLayout);
    }

    public void setAdapter(int loadAnimation){
        mLoadAnimation = loadAnimation;
        init();
    }
}
