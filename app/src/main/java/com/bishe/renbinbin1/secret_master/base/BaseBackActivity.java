package com.bishe.renbinbin1.secret_master.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.bishe.renbinbin1.secret_master.R;
import com.bishe.renbinbin1.secret_master.widge.BackView;


public abstract class BaseBackActivity extends AppCompatActivity {


    public BackView getmBackView() {
        return mBackView;
    }

    private BackView mBackView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
 
        setContentView(getLayoutId());
        initView();
        initData();
        getData();
    }

    //activity创建完毕
    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mBackView.onAttach(this);
    }

    private void initView() {
        mBackView = new BackView(this);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, R.anim.back_out);
    }

    protected abstract int getLayoutId();
    public abstract void initData();

    protected abstract void getData();



}
