package com.bishe.renbinbin1.secret_master.widge;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import static android.content.ContentValues.TAG;

/**
 * Created by Administrator on 2016/11/6 0006.
 */

public class BackView extends RelativeLayout {

    public ViewDragHelper mViewDragHelper;

    public BackView(Context context) {
        this(context,null);
    }

    public BackView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mViewDragHelper = ViewDragHelper.create(this, 1.0f, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                //是否响应滑动事件
                return false;
            }

            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {
                Log.d(TAG, "clampViewPositionHorizontal: "+left);
                if (left>=0){
                return left;
                }else {
                    return 0;

                }

            }

            @Override
            public void onViewReleased(View releasedChild, float xvel, float yvel) {
                super.onViewReleased(releasedChild, xvel, yvel);

                Log.d(TAG, "onViewReleased: "+xvel);
                int left = releasedChild.getLeft();
                int width = getWidth();
                if (left>width/2){
                    Log.d(TAG, "onViewReleased: 返回");
                    mOnBackFinishListener.onBackFinish();
                }
                else {
                    Log.d(TAG, "onViewReleased: 重绘");
                    mViewDragHelper.settleCapturedViewAt(0,0);
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
        //设置侧边滑动监听
        mViewDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT);

    }

    private boolean isScollEnabled = true;

    public void setScollEnabled(boolean scollEnabled) {
        isScollEnabled = scollEnabled;
    }

    public interface OnBackFinishListener{
        void onBackFinish();
    }
    OnBackFinishListener mOnBackFinishListener;

    public void setmOnBackFinishListener(OnBackFinishListener mOnBackFinishListener) {
        this.mOnBackFinishListener = mOnBackFinishListener;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        computeScroll();

    }

    @Override
    public void computeScroll() {
            if (mViewDragHelper.continueSettling(true)){
                invalidate();
        }
    }

    /**
     * View的事件拦截，true 拦截  false 不拦截
     *
     * @param ev
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        //让helper判断是否拦截事件
            return mViewDragHelper.shouldInterceptTouchEvent(ev);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //让Helper处理事件

        mViewDragHelper.processTouchEvent(event);
        return true;
    }

    public BackView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void onAttach(final Activity activity){
        ViewGroup viewGroup = (ViewGroup) activity.getWindow().getDecorView();
        View content = viewGroup.getChildAt(0);
        viewGroup.removeView(content);

        //将第一个View 添加到当前的View里面
        this.addView(content);
        viewGroup.addView(this);

        this.setmOnBackFinishListener(new BackView.OnBackFinishListener() {
            @Override
            public void onBackFinish() {
                activity.finish();
            }
        });

    }
}
