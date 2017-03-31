package com.bishe.renbinbin1.secret_master;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bishe.renbinbin1.secret_master.base.BaseBackActivity;
import com.xiawei.webviewlib.WebViewActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AboutActivity extends BaseBackActivity {

    @BindView(R.id.fruit_image_view)
    ImageView fruitImageView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.appBar)
    AppBarLayout appBar;
    @BindView(R.id.tv_about_github)
    TextView tvAboutGithub;
    @BindView(R.id.tv_about_coding)
    TextView tvAboutCoding;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_about;
    }

    @Override
    public void initData() {
        ButterKnife.bind(this);
//        Glide.with(getApplicationContext())
//                .load("http://img.17gexing.com/uploadfile/2016/07/2/20160725115727230.jpg")
//                .centerCrop()
//                .into(fruitImageView);
        fruitImageView.setImageResource(R.drawable.about);

    }

    @OnClick({R.id.tv_about_coding, R.id.tv_about_github})
    public void OnClick(View view){
        String   text  = ((TextView) view).getText().toString();
        String[] split = text.split(":");
        String   url   = split[1].trim() + ":" + split[2].trim();
        WebViewActivity.startUrl(this, url);
    }
    @Override
    protected void getData() {

    }

}
