package com.weiruanit.lifepro.module.weather.util;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.View;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Administrator on 2016/11/19 0019.
 */

public class ScreenshotTool {

    static ByteArrayOutputStream baos = null;
    static Bitmap bitmap = null;
    static Bitmap mBitmap = null;

    public static Bitmap captureScreen(final Activity activity) {
        Runnable action = new Runnable() {
            @Override
            public void run() {
                final View contentView = activity.findViewById(android.R.id.content);
                try {
                    bitmap = Bitmap.createBitmap(contentView.getWidth(),
                            contentView.getHeight(), Bitmap.Config.ARGB_4444);
                    contentView.draw(new Canvas(bitmap));
                    baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    String name = System.currentTimeMillis() + "short.png";
                    savePic(bitmap,"sdcard/" + name,activity);
                    mBitmap = bitmap;
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                finally {
                    try {
                        if (null != baos)
                            baos.close();
                        if (null != bitmap && !bitmap.isRecycled()) {
                            bitmap = null;
                            return;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        try {
            action.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mBitmap;
    }

    private static void savePic(Bitmap b, String strFileName,Activity activity) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(strFileName);
            if (null != fos) {
                boolean success= b.compress(Bitmap.CompressFormat.PNG, 90, fos);
                fos.flush();
                fos.close();
                if(success)
                    Toast.makeText(activity, "截屏成功", Toast.LENGTH_SHORT).show();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
