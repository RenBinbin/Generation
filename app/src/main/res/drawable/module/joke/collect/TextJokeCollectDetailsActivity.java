package com.weiruanit.lifepro.module.joke.collect;

import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.weiruanit.lifepro.R;
import com.weiruanit.lifepro.base.BaseBackActivity;
import com.weiruanit.lifepro.module.joke.bean.TextJokeBeanResponse;

import okhttp3.Call;
import okhttp3.Response;

public class TextJokeCollectDetailsActivity extends BaseBackActivity {

    private TextView tvTitle;
    private TextView tvContent;
    private ImageView ivBack;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_text_joke_collect_details;
    }

    @Override
    public void initData() {
        ivBack = (ImageView) findViewById(R.id.ivBack_collect_text_joke);
        tvTitle = (TextView) findViewById(R.id.tvTitle_collect_text_joke_details);
        tvContent = (TextView) findViewById(R.id.tvContent_collect_text_joke_details);
        if (getIntent().getStringExtra("title")==null){
                getYaoyiyaoData();
        }
        else {
            String title = getIntent().getStringExtra("title");
            String content = getIntent().getStringExtra("content");

            tvTitle.setText(title);
            tvContent.setText(Html.fromHtml(content));
        }
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void getData() {


    }

    private void getYaoyiyaoData() {

        int pageIndex = (int)(Math.random() * 10);

        String url ="http://apis.baidu.com/showapi_open_bus/showapi_joke/joke_text";
        String apikey = "abcfe469f2ede2b495055162e97d8b82";
        OkGo.get(url)
                .tag(this)
                .headers("apikey",apikey)
                .params("page", pageIndex)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Log.e("xxx",s.toString());
                        try{
                            Gson gson = new Gson();
                            TextJokeBeanResponse jokeBeanResponse = gson.fromJson(s,TextJokeBeanResponse.class);
                            if (jokeBeanResponse.getShowapi_res_body().getContentlist()!= null){
                                int position = (int) (Math.random()*20);


                                String title =  jokeBeanResponse.getShowapi_res_body().getContentlist().get(position).getTitle();
                                String text =     jokeBeanResponse.getShowapi_res_body().getContentlist().get(position).getText();

                                tvTitle.setText(title);
                                tvContent.setText(Html.fromHtml(text));
                            }else {
                                tvTitle.setText("好像摇到了什么奇怪的东西");
                                tvContent.setText(Html.fromHtml("再摇一次试试?"));
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                            tvTitle.setText("好像摇到了什么奇怪的东西");
                            tvContent.setText(Html.fromHtml("再摇一次试试?"));
                        }
                    }
                    @Override
                    public void onError(Call call, Response response, Exception e) {

                    }
                });
    }

}
