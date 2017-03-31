package com.weiruanit.lifepro.module.travel;

import android.content.Context;
import android.graphics.Canvas;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * Created by Administrator on 2016/11/6 0006.
 */

public class MyViewDragHelper extends RelativeLayout {

    //ViewDragHelper这个帮助类的出现就是为了简化在没有出现ViewDragHelper这个帮助类之前onTounchEvent中对View事件的处理步骤，其实就是将步骤封装成方法，从面向过程到面向对象

    private ViewDragHelper viewDragHelper;

    public MyViewDragHelper(Context context) {
        this(context,null);
    }

    public MyViewDragHelper(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyViewDragHelper(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViewDragHelper();
    }


    /**
     * 初始化ViewDragHelper
     */
    public void initViewDragHelper(){

        /**
         * 每当MyViewDragHelper这个自定义的容器里的子View接收到接收到触摸事件的时候，就会调用ViewDragHelper.Callback()这个抽象类中的方法，
         * 这些方法就可以实现view跟随手指移动的效果
         *
         */
        viewDragHelper = ViewDragHelper.create(this, 1.0f, new ViewDragHelper.Callback() {


            /**
             *
             * @param child 表示在MyViewDragHelper这个自定义容器中当前接收触摸、点击等事件的子View
             * @param pointerId
             * @return 返回false表示当前的子view不可以响应事件,换句话说当前的子View不可以移动(因为在这里我们是实现view跟随手指移动的效果)，返回true，则表示子View可以响应事件
             *          可以在这个方法中控制MyViewDragHelper这个自定义容器中哪些子View可以移动，哪些不可以移动
             *
             *
             *          说白了，tryCaptureView这个方法，就是用来决定哪些View可以滑动的(并且还是那种只要触摸子View的任何地方都会滑动的)，返回true，表示所有的子view都可以滑动
             */
            @Override
            public boolean tryCaptureView(View child, int pointerId) {
//                return true;

                //为了可以实现边缘滑动的效果，这里我们返回了false
                return  false;
            }


            /**
             *
             * 只要重写了这个方法，你的View就可以横向滑动了
             * @param child
             * @param left  表示当前的子View在MyViewDragHelper这个自定义容器中，在移动的过程中距离容器左边的移动距离，当left大于0表示ziView是从左往右滑的，小于0表示是从右往左滑的，可以通过这个参数指定子View的滑动方向
             * @param dx
             * @return
             */
            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {
                if(left<=0){
                   return 0;
                }
                return left;
            }


//            /**
//             *
//             * 只要重写了这个方法，你的View就可以纵向滑动了
//             * @param child
//             * @param top  表示当前的子View在MyViewDragHelper这个自定义容器中，在移动的过程中距离容器上边的移动距离，当top大于0表示子View是从上往下滑的，小于0表示是从下往上滑的，可以通过这个参数指定子View的滑动方向
//             * @param dy
//             * @return
//             */
//
//           @Override
//            public int clampViewPositionVertical(View child, int top, int dy) {
//                return top;
//            }


            /**
             * 该方法是当手指松开View的时候就会调用的方法
             * @param releasedChild         表示手指松开前所触摸的那个View
             * @param xvel
             * @param yvel
             */
            @Override
            public void onViewReleased(View releasedChild, float xvel, float yvel) {
                super.onViewReleased(releasedChild, xvel, yvel);

                int left = releasedChild.getLeft();    //得到view最终距离容器最左边的距离
                int width = getWidth();         //得到屏幕的高度
                if(left>=width/2){
                    if(mBackFinishListener!=null){
                        mBackFinishListener.finishActivity();
                    }
//                    mActivity.finish();

                }else {
                    /**
                     * settleCapturedViewAt()这个方法的意思其实就是将捕获到的view定居到位置(finalLeft,finalTop)处，回去的过程是个滑动的过程，需要一定的时间，所以Ui是不断变化的，所以需要调用invalidate()进行重绘，因为Ui是不断变化的
                     *
                     * 注意:在使用settleCapturedViewAt()这个方法的时候，必须得重写computeScroll方法，而computeScroll()这个方法其实是在onDraw()方法中调用的，而在调用invalidate()方法进行重绘的时候，就会调用onDraw()方法
                     *
                     */
                    viewDragHelper.settleCapturedViewAt(0,0);
                }
                invalidate();
            }


            /**
             *
             * 该方法是触发边缘时的回调，可以在这个方法中设置哪些View可以滑动了，而且还是边缘滑动
             *
             * @param edgeFlags     //表示是触发边缘滑动的类型是哪一个，左边缘，还是右边缘，还是上边缘，还是下边缘
             * @param pointerId
             */
            @Override
            public void onEdgeDragStarted(int edgeFlags, int pointerId) {
                super.onEdgeDragStarted(edgeFlags, pointerId);
                if(edgeFlags == 1){         //1就表示左边缘
//                    if(isScroll){
                        viewDragHelper.captureChildView(getChildAt(0),pointerId);       //重新设置viewDragHelper这个容器中的第一个子VIew可以滑动了!!

//                    }
                }
            }
        });
        viewDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT);            //表示是否开启边缘滑动
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        computeScroll();//这里手动调用这个方法是为了说明computeScroll()这个方法是在onDraw()方法中自动调用的，当然如果你手动调用，默认的自动调用就失效了呗
    }

    @Override
    public void computeScroll() {
        //使用settleCapturedViewAt方法时，必须重写computeScroll方法
        //持续滚动期间，Ui是不断变化的，所以需要不断刷新ViewGroup，那么就得需要不断的调用invalidate()方法，
        //continueSettling()这个方法会返回一个返回值，在整个滑动的过程中,这个方法会返回true.直至返回false,表示滑动过程的结束，参数传true是官方规定的，官方规定在computeScroll()方法中调用continueSettling()这个方法的时候就要传true
        if(viewDragHelper.continueSettling(true)){
            invalidate();
        }
    }

    /**
     * 提供一个回调，用来给外界提供方法以便销毁Activity
     *
     */
    private BackFinishListener mBackFinishListener;

    public void setmBackFinishListener(BackFinishListener mBackFinishListener) {
        this.mBackFinishListener = mBackFinishListener;
    }

    public interface BackFinishListener{
        public void finishActivity();
    }



    /**
     *
     * @param ev
     * @return 返回true，表示拦截这次事件，返回false则表示不拦截
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        //让ViewDragHelper 判断是否拦截触摸事件
        return viewDragHelper.shouldInterceptTouchEvent(ev);
    }

    /**
     *
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //让ViewDragHelper处理事件
        viewDragHelper.processTouchEvent(event);
        return true;
    }


//    //自定义一个方法，让外界调用，以此来通知mViewDragHelper这个自定义容器自己添加子View
//    private Activity mActivity;
//    public void onAttach(Activity activity) {
//        mActivity = activity;
//        ViewGroup group = (ViewGroup) activity.getWindow().getDecorView();
//        View firstChild = group.getChildAt(0);
//        group.removeView(firstChild);
//        this.addView(firstChild);       //this就代表了MyViewDragHelper这个自定义容器
//        group.addView(this);
//
//        //既然这里已经持有了MyViewDragHelper这个自定义容器所绑定的Activity，所以就不用在提供回调，让外界来finish掉Activity了，直接在这个类中就可以finish掉，所以这里的代码优化就把之前的回调给去掉了
//    }

    //自定义一个方法，让外界决定Activity到底要不要可以滑动，如果是一级Activity那滑动个毛线

//    private boolean isScroll =true;
//
//    public void setScrollEnable(boolean scroll) {
//        isScroll = scroll;
//    }
}
