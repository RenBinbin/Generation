package com.bishe.renbinbin1.secret_master;

import android.content.Intent;
import android.os.Handler;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bishe.renbinbin1.secret_master.base.BaseActivity;
import com.bishe.renbinbin1.secret_master.uilts.PreferencesUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends BaseActivity {

    @BindView(R.id.splash_image)
    ImageView splashImage;
    @BindView(R.id.splash_logoname)
    TextView splashLogoname;
    @BindView(R.id.splash_logodiscribe)
    TextView splashVersionName;
    @BindView(R.id.splash_copyright)
    TextView splashCopyright;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public void initData() {
        ButterKnife.bind(this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                String firstTag= PreferencesUtils.getString(SplashActivity.this,"first_tag");
                if(TextUtils.isEmpty(firstTag)){
                    Intent intent=new Intent(SplashActivity.this,MainActivity.class);
                    startActivity(intent);
                }
                finish();
            }
        },2000);

    }

    @Override
    protected void getData() {

    }
}
