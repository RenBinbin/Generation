package com.weiruanit.lifepro.module.joke.collect;

import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.weiruanit.lifepro.R;
import com.weiruanit.lifepro.base.BaseBackActivity;
import com.weiruanit.lifepro.module.joke.bean.JokeBeanResponse;

import okhttp3.Call;
import okhttp3.Response;

public class ImageJokeCollectDetailsActivity extends BaseBackActivity {


    private SimpleDraweeView pic;
    private TextView tvTitle;
    ImageView ivBackCollectImageJoke;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_image_joke_collect_details;
    }

    @Override
    public void initData() {
        ivBackCollectImageJoke = (ImageView) findViewById(R.id.ivBack_collect_image_joke);
        pic = (SimpleDraweeView) findViewById(R.id.sdv_collect_image_joke_details);
        tvTitle = (TextView) findViewById(R.id.tvTitle_collect_image_joke_details);
        ImageView ivBackCollectImageJoke = (ImageView) findViewById(R.id.ivBack_collect_image_joke);
        ivBackCollectImageJoke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if (getIntent().getStringExtra("uri")==null){
            getdata();

        }else {
            String uri = getIntent().getStringExtra("uri");
            String title = getIntent().getStringExtra("title");

            pic.setImageURI(Uri.parse(uri));
            tvTitle.setText(title);
        }
    }

    @Override
    protected void getData() {

    }

    private void getdata() {

        int pageIndex = (int)(Math.random() * 20);
//        int pageIndex = 20;

        String url ="http://apis.baidu.com/showapi_open_bus/showapi_joke/joke_pic";
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
                            JokeBeanResponse jokeBeanResponse = gson.fromJson(s,JokeBeanResponse.class);
                            if (jokeBeanResponse.getShowapi_res_body().getContentlist()!=null){
                                int position = (int) (Math.random()*20);

                                String title =  jokeBeanResponse.getShowapi_res_body().getContentlist().get(position).getTitle();
                                String img =     jokeBeanResponse.getShowapi_res_body().getContentlist().get(position).getImg();

                                pic.setImageURI(Uri.parse(img));
                                tvTitle.setText(title);
                            }
                        }catch (Exception e){
                            e.printStackTrace();

                        }
                    }
                    @Override
                    public void onError(Call call, Response response, Exception e) {

                    }
                });


    }


}
