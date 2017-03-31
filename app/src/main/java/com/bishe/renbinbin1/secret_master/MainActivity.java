package com.bishe.renbinbin1.secret_master;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bishe.renbinbin1.secret_master.adapter.MainViewPagerAdapter;
import com.bishe.renbinbin1.secret_master.base.BaseActivity;
import com.bishe.renbinbin1.secret_master.bean.TabBean;
import com.bishe.renbinbin1.secret_master.module.joke.JokeFragment;
import com.bishe.renbinbin1.secret_master.module.memo.MemoFragment;
import com.bishe.renbinbin1.secret_master.module.news.NewsFragment;
import com.bishe.renbinbin1.secret_master.module.robot.ChattingRobotActivity;
import com.bishe.renbinbin1.secret_master.module.weather.WeatherActivity;
import com.bishe.renbinbin1.secret_master.uilts.PreferencesUtils;
import com.bishe.renbinbin1.secret_master.widge.MainBottomTabLayout;
import com.blankj.utilcode.utils.FileUtils;
import com.bumptech.glide.Glide;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jaiky.imagespickers.ImageConfig;
import com.jaiky.imagespickers.ImageLoader;
import com.jaiky.imagespickers.ImageSelector;
import com.jaiky.imagespickers.ImageSelectorActivity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @BindView(R.id.vp_main)
    ViewPager vpMain;
    @BindView(R.id.tablayout_main)
    MainBottomTabLayout tablayoutMain;
    @BindView(R.id.navigation)
    NavigationView navigation;
    @BindView(R.id.et_side_head)
    EditText etSideHead;
    @BindView(R.id.nav_head)
    RelativeLayout navHead;
    @BindView(R.id.side_menu_setting)
    LinearLayout sideMenuSetting;
    @BindView(R.id.side_menu_robot)
    LinearLayout sideMenuRobot;
    @BindView(R.id.activity_main)
    DrawerLayout activityMain;
    @BindView(R.id.simple_side_head)
    SimpleDraweeView simpleSideHead;

    private boolean isExit;
    MainViewPagerAdapter mainViewPagerAdapter;
    private InputMethodManager imm;//软键盘管理
    private Bitmap bitmap;
    private MyHandler myHandler;
    private String mHeadImg;
    private String mUserName;
    private String mCity;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initData() {
        ButterKnife.bind(this);

        //侧边
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, activityMain, null, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        activityMain.setDrawerListener(toggle);
        toggle.syncState();
        //底部导航
        ArrayList<TabBean> tabBeen = new ArrayList<>();
        tabBeen.add(new TabBean("备忘录", 0, R.mipmap.bottom_joke, R.mipmap.bottom_joke_1));
        tabBeen.add(new TabBean("新闻", 0, R.mipmap.bottom_news, R.mipmap.bottom_news_1));
        tabBeen.add(new TabBean("笑话", 0, R.mipmap.bottom_joke, R.mipmap.bottom_joke_1));
//        tabBeen.add(new TabBean("天气", 0, R.mipmap.bottom_joke, R.mipmap.bottom_joke_1));
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new MemoFragment());
        fragments.add(new NewsFragment());
        fragments.add(new JokeFragment());
//        fragments.add(new WeatherFragment());
        mainViewPagerAdapter = new MainViewPagerAdapter(getSupportFragmentManager(), fragments);
        vpMain.setAdapter(mainViewPagerAdapter);
        vpMain.setOffscreenPageLimit(mainViewPagerAdapter.getCount());
        tablayoutMain.setViewPager(vpMain, tabBeen);

        initUserData();

        etSideHead.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    String name = etSideHead.getText().toString();
                    PreferencesUtils.putString(MainActivity.this, "username", name);
                    hideSoftInput();
                }
                return false;
            }
        });

    }

    private void initUserData() {
        //取本地数据
        mHeadImg = PreferencesUtils.getString(this, "headimg");
        mCity = PreferencesUtils.getString(this, "city");
        mUserName = PreferencesUtils.getString(this, "userName");
        if (TextUtils.isEmpty(mCity)) {
            mCity = "苏州";
        }
        if (!TextUtils.isEmpty(mHeadImg)) {
            simpleSideHead.setImageURI("file://" + Uri.parse(mHeadImg));
        }
        if (!TextUtils.isEmpty(mUserName)) {
            etSideHead.setText(mUserName);
        }
    }

    @Override
    protected void getData() {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (navigation.getVisibility() == View.VISIBLE) {
                return super.onKeyDown(keyCode, event);
            } else {
                exitBy2Click(); //调用双击退出函数
            }
        }
        return false;
    }

    private void exitBy2Click() {
        Timer tExit;
        if (isExit == false) {
            isExit = true; // 准备退出
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false; // 取消退出
                }
            }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务

        } else {
            finish();
            System.exit(0);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ImageSelector.IMAGE_REQUEST_CODE && resultCode == RESULT_OK && data !=
                null) {
            //换头像
            // Get Image Path List
            List<String> pathList = data.getStringArrayListExtra(ImageSelectorActivity
                    .EXTRA_RESULT);

            for (String path : pathList) {
                simpleSideHead.setImageURI("file://" + Uri.parse(path));
                PreferencesUtils.putString(this, "headimg", path + "");

            }
        }

        //从天气返回天气图标code
        if(requestCode == 0x02){
            try {
                String weatherCode =data.getStringExtra("code") ;
                final String url = "http://files.heweather.com/cond_icon/" +
                        weatherCode + ".png";
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        bitmap = byteBitmap(url);
                        myHandler.sendEmptyMessage(1);
                    }
                }).start();
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    //读天气网络图片
    private Bitmap byteBitmap(String httpUrl) {
        Bitmap bitmap = null;
        try {
            HttpURLConnection connection = (HttpURLConnection)(new URL(httpUrl)).openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream is = connection.getInputStream();
            try {
                byte[] data = readStream(is);
                if(data != null){
                    bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                is.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    private byte[] readStream(InputStream inStream) throws IOException {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while((len=inStream.read(buffer)) != -1){
            outStream.write(buffer, 0, len);
        }
        outStream.close();
        inStream.close();
        return outStream.toByteArray();
    }

    class MyHandler extends Handler {
        public MyHandler() {
        }

        public MyHandler(Looper L) {
            super(L);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        //触摸收起键盘
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    etSideHead.clearFocus();
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    private boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    //侧滑栏
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (activityMain.isDrawerOpen(GravityCompat.START)) {
            activityMain.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @OnClick({R.id.simple_side_head, R.id.et_side_head, R.id.side_menu_robot,
            R.id.side_menu_setting,R.id.side_menu_weather,R.id.side_menu_about})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.simple_side_head:
                selectHeadImg();
                break;
            case R.id.et_side_head:
                break;
            case R.id.side_menu_robot:
                //聊天机器人
                Intent intent = new Intent(this, ChattingRobotActivity.class);
                startActivity(intent);
                break;
            //设置
            case R.id.side_menu_setting:
                showDialogSetting();
                break;
            case R.id.side_menu_weather:
                Intent intent1=new Intent(this, WeatherActivity.class);
                startActivity(intent1);
                break;
            case R.id.side_menu_about:
                Intent intent2=new Intent(this,AboutActivity.class);
                startActivity(intent2);
                break;


        }
    }

    private void selectHeadImg() {
        //换头像
        ImageConfig imageConfig
                = new ImageConfig.Builder(new GlideLoader())
                .steepToolBarColor(getResources().getColor(R.color
                        .colorPrimaryDark))
                .titleBgColor(getResources().getColor(R.color.colorPrimaryDark))
                .titleSubmitTextColor(getResources().getColor(R.color
                        .white))
                .titleTextColor(getResources().getColor(R.color.white))
                // 开启单选   （默认为多选）
                .singleSelect()
                // 开启拍照功能 （默认关闭）
                .showCamera()
                // 拍照后存放的图片路径（默认 /temp/picture） （会自动创建）
                .filePath("/ImageSelector/Pictures")
                .build();

        ImageSelector.open(MainActivity.this, imageConfig);   // 开启图片选择器
    }

    public class GlideLoader implements ImageLoader {
        //选头像
        @Override
        public void displayImage(Context context, String path, ImageView imageView) {
            Glide.with(context)
                    .load(path)
                    .placeholder(R.mipmap.imageselector_photo)
                    .centerCrop()
                    .into(imageView);
        }
    }

    private void showDialogSetting() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("设置");
        builder.setIcon(R.mipmap.icon_setting2);

        //    指定下拉列表的显示数据
        final String[] items = {"更换头像", "清除缓存"};

        //    设置一个下拉的列表选择项
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        selectHeadImg();
                        break;
                    case 1:
                        String dirSize = FileUtils.getDirSize(getCacheDir());
                        new AlertDialog.Builder(MainActivity.this)
                                .setTitle("确定要清理缓存").setMessage("缓存大小：" + dirSize)
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        FileUtils.deleteDir(getCacheDir());
                                        Toast.makeText(MainActivity.this, "清理成功", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .setNegativeButton("取消", null).show();
                        break;

                }
            }
        });
        builder.show();
    }

    public void hideSoftInput() {
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null && imm.isActive()) {        //判断是否已弹出
            //隐藏软键盘
            imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
        }
        //搜索框失去焦点
        etSideHead.clearFocus();
    }

}
