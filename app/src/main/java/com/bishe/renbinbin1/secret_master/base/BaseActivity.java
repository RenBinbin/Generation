package com.bishe.renbinbin1.secret_master.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setWindow();
        setContentView(getLayoutId());
        initData();
        getData();
    }


    protected abstract int getLayoutId();
    public abstract void initData();

    protected abstract void getData();
    protected  void setWindow(){
    }
}
