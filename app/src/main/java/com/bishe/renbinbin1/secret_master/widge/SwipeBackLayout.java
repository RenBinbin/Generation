package com.bishe.renbinbin1.secret_master.widge;

import android.app.Activity;
import android.content.Context;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
/**
 * Created by Administrator on 2016/11/1 0001.
 */

public class SwipeBackLayout extends RelativeLayout {
    //简化View事件的处理
    ViewDragHelper mViewDragHelper;
//    private ImageView mImageView;

    public SwipeBackLayout(Context context) {
        this(context, null);
    }

    public SwipeBackLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mViewDragHelper = ViewDragHelper.create(this, 1.0f, new ViewDragHelper.Callback() {

            //ViewDragHelperDemo 布局里子View接收到【触摸事件】，都会回调的方法

            /**
             *
             * @param child  当前触摸、接受点击事件的View
             * @param pointerId
             * @return
             */
            @Override
            public boolean tryCaptureView(View child, int pointerId) {

                return false;
            }

            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {
                Log.e("child", "" + left);

                return Math.max(0, left);
            }

            @Override
            public void onViewReleased(View releasedChild, float xvel, float yvel) {
                super.onViewReleased(releasedChild, xvel, yvel);
//                LL.e("onViewReleased  抬起啦" + xvel + "   " + yvel);

                int left = releasedChild.getLeft();
                int width = getWidth();
                if (left > width / 2) {

//                    LL.e("关闭啦");
                    mActivity.finish();
                } else {
//                    LL.e("不关闭");
                    mViewDragHelper.settleCapturedViewAt(0, 0);
                }
                invalidate();
            }

            @Override
            public void onEdgeDragStarted(int edgeFlags, int pointerId) {

                if (edgeFlags == 1) {
                    if (isScollEnabled) {
                        mViewDragHelper.captureChildView(getChildAt(0), pointerId);
                    }
                }
            }
        });


        mViewDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT | ViewDragHelper
                .EDGE_RIGHT);


    }

    private boolean isScollEnabled = true;

    public void setScollEnabled(boolean scoll) {
        isScollEnabled = scoll;
    }


    @Override
    public void computeScroll() {
        //如果是false 那么表示view滑动结束
//        boolean isFin = mViewDragHelper.continueSettling(true);

        if (mViewDragHelper.continueSettling(true)) {
            invalidate();
        }
    }

    /**
     * View的事件拦截，true 拦截  false 不拦截
     * @param ev
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.e("onInterceptTouchEvent", " onInterceptTouchEvent");
        //让ViewDragHelper 判断是否拦截触摸事件
        return mViewDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //让ViewDragHelper处理事件
        mViewDragHelper.processTouchEvent(event);
        return true;
    }

    public SwipeBackLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private Activity mActivity;

    public void onAttach(Activity activity) {
        mActivity = activity;
        ViewGroup decor = (ViewGroup) activity.getWindow().getDecorView();
        View content = decor.getChildAt(0);
        decor.removeView(content);
        //将第一个View 添加到当前的View里面
        addView(content);
        decor.addView(this);
    }


}
