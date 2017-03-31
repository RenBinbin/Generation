package com.weiruanit.lifepro.module.ticket.bean;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * 3D 动画
 * Created by Administrator on 2016/11/17 0017.
 */

public class MyAnimotion extends Animation {

    private final float mFromDegrees;
    private final float mToDegrees;
    private final float mCenterX;
    private final float mCenterY;
    private final float mDepthZ;
    private Camera mCamera;
    private int mDirection;
    private final static int ROTATE_X = 0;//沿着x轴旋转
    private final static int ROTATE_Y = 1;//沿着y轴旋转
    public static final int ROLL_BY_Z = 2;//沿z轴旋转


    public MyAnimotion(int direction, float fromDegrees, float toDegrees,
                       float centerX, float centerY, float depthZ) {
        mDirection = direction;
        mFromDegrees = fromDegrees;
        mToDegrees = toDegrees;
        mCenterX = centerX;
        mCenterY = centerY;
        mDepthZ = depthZ;
    }

    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);

        mCamera = new Camera();
    }
    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        final float fromDegrees = mFromDegrees;
        float degrees = fromDegrees
                + ((mToDegrees - fromDegrees) * interpolatedTime);

        final float centerX = mCenterX;
        final float centerY = mCenterY;
        final Camera camera = mCamera;

        final Matrix matrix = t.getMatrix();

        camera.save();

        if (centerX!=0){
            if (interpolatedTime < 0.5) {
                camera.translate(0.0f, 0.0f, mDepthZ * interpolatedTime);
            } else {
                camera.translate(0.0f, 0.0f, mDepthZ * (1.0f - interpolatedTime));
            }
        }

        switch (mDirection) {
            case ROTATE_X:
                camera.rotateX(degrees);
                break;
            case ROTATE_Y:
                camera.rotateY(degrees);
                break;
            case ROLL_BY_Z:
                camera.rotateY(degrees);
                break;
        }

        camera.getMatrix(matrix);
        camera.restore();
        matrix.preTranslate(-centerX, -centerY);
        matrix.postTranslate(centerX, centerY);
    }
}
