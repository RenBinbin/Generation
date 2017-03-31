package com.weiruanit.lifepro.module.travel.activity;

import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.lzy.okgo.callback.StringCallback;
import com.weiruanit.lifepro.R;
import com.weiruanit.lifepro.base.BaseActivity;
import com.weiruanit.lifepro.module.ticket.bean.MyAnimotion;
import com.weiruanit.lifepro.module.travel.bean.TravelResponce;
import com.weiruanit.lifepro.module.travel.utils.HttpUtils;
import com.weiruanit.lifepro.utils.DBUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

import static com.weiruanit.lifepro.module.travel.TravelApi.TRAVELLIST;


public class TravelDetailActivity3 extends BaseActivity {


    @BindView(R.id.im_back)
    ImageView imBack;
    @BindView(R.id.im_sdv_detatil)
    SimpleDraweeView imSdvDetatil;
    @BindView(R.id.activity_trabel_detail)
    RelativeLayout activityTrabelDetail;
    @BindView(R.id.tv_detail_title)
    TextView tvDetailTitle;
    @BindView(R.id.line)
    View line;
    @BindView(R.id.im_heade)
    SimpleDraweeView imHeade;
    @BindView(R.id.tv_userName)
    TextView tvUserName;
    @BindView(R.id.tv_viewCount)
    TextView tvViewCount;
    @BindView(R.id.im_viewCount)
    ImageView imViewCount;
    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.ll_progress)
    LinearLayout llProgress;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    private TravelResponce.DataBean.BooksBean mBookBean;

    private int mTag = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_travel_deail2;
    }

    @Override
    public void initData() {

    }

    @Override
    protected void getData() {
        ButterKnife.bind(this);
        getYaoyiyaoData();

    }

    private void getYaoyiyaoData() {
        int pageIndex = (int) (Math.random() * 100 + 1);           //1到100的随机数
        Map<String, String> map = new HashMap<>();
        HttpUtils.requestData(this, TRAVELLIST, map, new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Log.e("tag", s);
                        Gson gson = new Gson();
                        TravelResponce travelResponce = gson.fromJson(s, TravelResponce.class);
                        if (travelResponce != null && travelResponce.getData().getBooks() != null) {
                            int position = (int) (Math.random() * 10);           //0到9的随机数
                            mBookBean = travelResponce.getData().getBooks().get(position);
                            initTop();
                            initWebView();
                        }else{
                            Toast.makeText(TravelDetailActivity3.this,"好像摇到了什么奇怪的东西,再摇一次试试?",Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        Toast.makeText(TravelDetailActivity3.this,"无网络，请连接网络！",Toast.LENGTH_LONG).show();
                        llProgress.setVisibility(View.GONE);

                    }
                }

        );
    }

    //详情界面上半部分的初始化

    public void initTop() {
        if (mBookBean != null) {
            imSdvDetatil.setImageURI(mBookBean.getHeadImage());
            imHeade.setImageURI(Uri.parse(mBookBean.getUserHeadImg()));
            tvDetailTitle.setText(mBookBean.getTitle() + "");
            tvUserName.setText(mBookBean.getUserName() + "");
            tvViewCount.setText(mBookBean.getViewCount() + "");     //注意这里不需要加一了，因为是地址引用传过来的浏览数就是已经改好的
                fab.setImageResource(R.mipmap.collection_normal);
        }
    }

    //webView的加载
    public void initWebView() {
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);    //启用JS脚本
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                Log.e("qwe", "执行了吗" + url);
                if (url != null && url.contains("/books/")) {
                    String fun = "javascript:function getClass(parent,sClass) { var aEle=parent.getElementsByTagName('div'); var aResult=[]; var i=0; for(i<0;i<aEle.length;i++) { if(aEle[i].className==sClass) { aResult.push(aEle[i]); } }; return aResult; } ";
                    view.loadUrl(fun);
                    String fun2 = "javascript:function hideOther() {getClass(document,'info-bar')[0].style.display='none'; getClass(document,'title-bd')[0].style.display='none'; getClass(document,'e_crumb')[0].style.display='none'; getClass(document,'title')[0].style.display='none';}";
                    view.loadUrl(fun2);
                    view.loadUrl("javascript:hideOther();");
                    //webView显示，提示框消失
                    llProgress.setVisibility(View.GONE);
                    webView.setVisibility(View.VISIBLE);
                }
                super.onPageFinished(view, url);
            }
        });
        webView.loadUrl(mBookBean != null ? mBookBean.getBookUrl() : "");
    }

    //设置沉浸式
    @Override
    protected void setWindow() {
        super.setWindow();

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
    }

    @OnClick({R.id.im_back, R.id.fab})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.im_back:
                finish();           //关闭activity
                break;
            case R.id.fab:
                MyAnimotion myAnimotion = new MyAnimotion(1, 0, 180, fab.getMeasuredWidth() / 2f,
                        fab.getMeasuredHeight() / 2f, 310f);
                myAnimotion.setDuration(600);
                myAnimotion.setInterpolator(new LinearInterpolator());
                myAnimotion.setFillAfter(false);
                fab.startAnimation(myAnimotion);
                if (mTag == 0) {
                    fab.setImageResource(R.mipmap.collection_selected);
                    DBUtils.SaveBook(mBookBean);
                    mTag = 1;
                    Toast.makeText(TravelDetailActivity3.this, "收藏成功", Toast.LENGTH_SHORT).show();
                } else {
                    fab.setImageResource(R.mipmap.collection_normal);
                    try {
                        DBUtils.DeleteBook(mBookBean);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    mTag = 0;
                    Toast.makeText(TravelDetailActivity3.this, "取消收藏", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
