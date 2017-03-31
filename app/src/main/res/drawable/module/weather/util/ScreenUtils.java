package com.weiruanit.lifepro.module.weather.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import java.io.FileOutputStream;

/**
 * Created by Administrator on 2016/11/22 0022.
 */

    //获得屏幕相关的辅助类
    public class ScreenUtils
    {
        private ScreenUtils()
        {
        /* cannot be instantiated */
            throw new UnsupportedOperationException("cannot be instantiated");
        }

        /**
         * 获得屏幕宽度
         *
         * @param context
         * @return
         */
        public static int getScreenWidth(Context context)
        {
            WindowManager wm = (WindowManager) context
                    .getSystemService(Context.WINDOW_SERVICE);
            DisplayMetrics outMetrics = new DisplayMetrics();
            wm.getDefaultDisplay().getMetrics(outMetrics);
            return outMetrics.widthPixels;
        }

        /**
         * 获得屏幕高度
         *
         * @param context
         * @return
         */
        public static int getScreenHeight(Context context)
        {
            WindowManager wm = (WindowManager) context
                    .getSystemService(Context.WINDOW_SERVICE);
            DisplayMetrics outMetrics = new DisplayMetrics();
            wm.getDefaultDisplay().getMetrics(outMetrics);
            return outMetrics.heightPixels;
        }

        /**
         * 获得状态栏的高度
         *
         * @param context
         * @return
         */
        public static int getStatusHeight(Context context)
        {

            int statusHeight = -1;
            try
            {
                Class<?> clazz = Class.forName("com.android.internal.R$dimen.xml");
                Object object = clazz.newInstance();
                int height = Integer.parseInt(clazz.getField("status_bar_height")
                        .get(object).toString());
                statusHeight = context.getResources().getDimensionPixelSize(height);
            } catch (Exception e)
            {
                e.printStackTrace();
            }
            return statusHeight;
        }

        /**
         * 获取当前屏幕截图，包含状态栏
         *
         * @param activity
         * @return
         */
        public static Bitmap snapShotWithStatusBar(Activity activity)
        {
            View view = activity.getWindow().getDecorView();
            view.setDrawingCacheEnabled(true);
            view.buildDrawingCache();
            Bitmap bmp = view.getDrawingCache();
            int width = getScreenWidth(activity);
            int height = getScreenHeight(activity);
            Bitmap bp = null;
            bp = Bitmap.createBitmap(bmp, 0, 0, width, height);
            view.destroyDrawingCache();
            String name = System.currentTimeMillis() + "short.png";
            savePic(bp,"sdcard/" + name,activity);
            return bp;

        }

        /**
         * 获取当前屏幕截图，不包含状态栏
         *
         * @param activity
         * @return
         */
        public static Bitmap snapShotWithoutStatusBar(Activity activity)
        {
            View view = activity.getWindow().getDecorView();
            view.setDrawingCacheEnabled(true);
            view.buildDrawingCache();
            Bitmap bmp = view.getDrawingCache();
            Rect frame = new Rect();
            activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
            int statusBarHeight = frame.top;

            int width = getScreenWidth(activity);
            int height = getScreenHeight(activity);
            Bitmap bp = null;
            bp = Bitmap.createBitmap(bmp, 0, statusBarHeight, width, height
                    - statusBarHeight);
            view.destroyDrawingCache();
            String name = System.currentTimeMillis() + "short.png";
            savePic(bp,"sdcard/" + name,activity);
            return bp;

        }

        private static void savePic(final Bitmap b, final String strFileName, final Activity activity) {
          new Thread(new Runnable() {
              @Override
              public void run() {
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
                  } catch (Exception e) {
                      e.printStackTrace();
                  }
              }
          }).start();

        }









    ////////////////////////////////////////////////////////////////
//
//    /**
//
//     * 截屏
//
//     * @param activity
//
//     * @return
//
//     */
//
//    public static Bitmap captureScreen(Activity activity) {
//
//        // 获取屏幕大小：
//
//        DisplayMetrics metrics = new DisplayMetrics();
//
//        WindowManager WM = (WindowManager) activity
//
//                .getSystemService(Context.WINDOW_SERVICE);
//
//        Display display = WM.getDefaultDisplay();
//
//        display.getMetrics(metrics);
//
//        int height = metrics.heightPixels; // 屏幕高
//
//        int width = metrics.widthPixels; // 屏幕的宽
//
//        // 获取显示方式
//
//        int pixelformat = display.getPixelFormat();
//
//        PixelFormat localPixelFormat1 = new PixelFormat();
//
//        PixelFormat.getPixelFormatInfo(pixelformat, localPixelFormat1);
//
//        int deepth = localPixelFormat1.bytesPerPixel;// 位深
//
//        byte[] piex = new byte[height * width * deepth];
//
//        try {
//
//            Runtime.getRuntime().exec(
//
//                    new String[] { "/system/bin/su", "-c",
//
//                            "chmod 777 /dev/graphics/fb0" });
//
//        } catch (IOException e) {
//
//            e.printStackTrace();
//
//        }
//
//        try {
//
//            // 获取fb0数据输入流
//
//            InputStream stream = new FileInputStream(new File(
//
//                    "/dev/graphics/fb0"));
//
//            DataInputStream dStream = new DataInputStream(stream);
//
//            dStream.readFully(piex);
//
//        } catch (Exception e) {
//
//            e.printStackTrace();
//
//        }
//
//        // 保存图片
//
//        int[] colors = new int[height * width];
//
//        for (int m = 0; m < colors.length; m++) {
//
//            int r = (piex[m * 4] & 0xFF);
//
//            int g = (piex[m * 4 + 1] & 0xFF);
//
//            int b = (piex[m * 4 + 2] & 0xFF);
//
//            int a = (piex[m * 4 + 3] & 0xFF);
//
//            colors[m] = (a << 24) + (r << 16) + (g << 8) + b;
//
//        }
//
//        // piex生成Bitmap
//
//        Bitmap bitmap = Bitmap.createBitmap(colors, width, height,
//
//                Bitmap.Config.ARGB_8888);
//
//        return bitmap;
//
//    }
}
