package com.weiruanit.lifepro.module.travel.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
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
import com.weiruanit.lifepro.R;
import com.weiruanit.lifepro.base.BaseActivity;
import com.weiruanit.lifepro.module.ticket.bean.MyAnimotion;
import com.weiruanit.lifepro.module.travel.bean.TravelControler;
import com.weiruanit.lifepro.module.travel.bean.TravelResponce;
import com.weiruanit.lifepro.module.travel.bean.UpadteBrowsingNmber;
import com.weiruanit.lifepro.sqlbean.Book;
import com.weiruanit.lifepro.utils.DBUtils;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.weiruanit.lifepro.module.travel.activity.TravelCollectionActivity.FORM_TRAVELCOLLECTION;


public class TravelDetailActivity2 extends BaseActivity {


    @BindView(R.id.im_back)
    ImageView imBack;
    @BindView(R.id.im_sdv_detatil)
    SimpleDraweeView imSdvDetatil;
    @BindView(R.id.activity_trabel_detail)
    RelativeLayout activityTrabelDetail;
    @BindView(R.id.tv_detail_title)
    TextView tvDetailTitle;
    //    @BindView(R.id.im_detail_likeCount)
////    ImageView imDetailLikeCount;
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


    //用来接收上个界面传递过来的book的bean
    private TravelResponce.DataBean.BooksBean mBookBean;

    //    用来接收上个界面传递过来的本地book的bean
    private Book book;


    private String mWhere;


    //定义收藏的状态 0 选中  1 未选中
    private int state = 0;


    private int mTag;
    private int mPosition;

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

        Intent intent = getIntent();
        mWhere = intent.getStringExtra("fromWhere");

        if (mWhere.equals(FORM_TRAVELCOLLECTION) && mWhere != null) {
            mPosition =  intent.getIntExtra("intent",0);
            Bundle bundle = intent.getExtras();
            book = (Book) bundle.getSerializable("bookeans");
        } else {
            Bundle bundle = intent.getExtras();
            mBookBean = bundle.getParcelable("bookeans");             //得到传递过来的数据
            mPosition = bundle.getInt("position");
            mTag = bundle.getInt("tag");
        }
        initTop();
        initWebView();

    }

    //详情界面上半部分的初始化
    public void initTop() {
        if (mWhere.equals(FORM_TRAVELCOLLECTION) && mWhere != null && book != null) {
            imSdvDetatil.setImageURI(book.getHeadImage());
            imHeade.setImageURI(Uri.parse(book.getUserHeadImg()));
            tvDetailTitle.setText(book.getTitle() + "");
            tvUserName.setText(book.getUserName() + "");
            tvViewCount.setText(book.getViewCount() + "");
//            imDetailLikeCount.setImageResource(R.mipmap.collection_selected);
            fab.setImageResource(R.mipmap.collection_selected);


        } else {
            if (mBookBean != null) {
                imSdvDetatil.setImageURI(mBookBean.getHeadImage());
                imHeade.setImageURI(Uri.parse(mBookBean.getUserHeadImg()));
                tvDetailTitle.setText(mBookBean.getTitle() + "");
                tvUserName.setText(mBookBean.getUserName() + "");
                tvViewCount.setText(mBookBean.getViewCount() + "");     //注意这里不需要加一了，因为是地址引用传过来的浏览数就是已经改好的
                if (mTag == 0) {
//                    imDetailLikeCount.setImageResource(R.mipmap.collection_normal);
                    fab.setImageResource(R.mipmap.collection_normal);

                } else {
//                    imDetailLikeCount.setImageResource(R.mipmap.collection_selected);
                    fab.setImageResource(R.mipmap.collection_selected);

                }
            }
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
//

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

        if (mWhere.equals(FORM_TRAVELCOLLECTION) && mWhere != null) {
            webView.loadUrl(book != null ? book.getBookUrl() : "");
        } else {
            webView.loadUrl(mBookBean != null ? mBookBean.getBookUrl() : "");
//            webView.loadUrl("https://www.baidu.com");
        }


    }


    //设置沉浸式
    @Override
    protected void setWindow() {
        super.setWindow();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);       //设置全屏
//        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);                    //设置没有标题,即没有标题栏
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

    }


//    @OnClick({R.id.im_back, R.id.im_detail_likeCount})
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.im_back:
//                if (mWhere.equals(FORM_TRAVELCOLLECTION)) {              //来自收藏列表的收藏点击事件
//                    //按返回建的时候通知列表更新ui
//                    Intent intent = new Intent();
//                    Bundle bundle = new Bundle();
//                    bundle.putSerializable("bookeans", book);
//                    intent.putExtras(bundle);
//                    intent.putExtra("state", state);
//                    setResult(33, intent);
//                    finish();           //关闭activity
//                } else {
//                    finish();           //关闭activity
//                    EventBus.getDefault().post(new UpadteBrowsingNmber());       //通知更新浏览数
//                }
//                break;
//            case R.id.im_detail_likeCount:
//                if (mWhere.equals(FORM_TRAVELCOLLECTION)) {              //来自收藏列表的收藏点击事件
//                    if (state == 0) {
//                        fab.setImageResource(R.mipmap.collection_normal);
//                        state = 1;
//                    } else {
//                        fab.setImageResource(R.mipmap.collection_selected);
//                        state = 0;
//                    }
//                } else {                                              //来自游记列表的收藏点击事件
//                    if (mTag == 0) {
//                        fab.setImageResource(R.mipmap.collection_selected);
//                        DBUtils.SaveBook(mBookBean);
//                        mTag = 1;
//                        EventBus.getDefault().post(new TravelControler(mPosition, 1));
//                    } else {
//                        fab.setImageResource(R.mipmap.collection_normal);
//                        try {
//                            DBUtils.DeleteBook(mBookBean);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                        EventBus.getDefault().post(new TravelControler(mPosition, 0));
//                        mTag = 0;
//                    }
//                }
//                break;
//        }

    @OnClick({R.id.im_back, R.id.fab})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.im_back:
                if (mWhere.equals(FORM_TRAVELCOLLECTION)) {              //来自收藏列表的收藏点击事件
                    //按返回建的时候通知列表更新ui
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("bookeans", book);
                    intent.putExtras(bundle);
                    intent.putExtra("state", state);
                    intent.putExtra("position", mPosition);
                    setResult(33, intent);
                    finish();           //关闭activity
                } else {
                    finish();           //关闭activity
//                    overridePendingTransition(R.anim.in_anim2,R.anim.out_anim2);        //跳转Activity时的动画效果
                    EventBus.getDefault().post(new UpadteBrowsingNmber());       //通知更新浏览数
                }
                break;
            case R.id.fab:

                MyAnimotion myAnimotion = new MyAnimotion(1, 0, 180, fab.getMeasuredWidth() / 2f,
                        fab.getMeasuredHeight() / 2f, 310f);
                myAnimotion.setDuration(600);
                myAnimotion.setInterpolator(new LinearInterpolator());
                myAnimotion.setFillAfter(false);
                fab.startAnimation(myAnimotion);

                if (mWhere.equals(FORM_TRAVELCOLLECTION)) {              //来自收藏列表的收藏点击事件
                    if (state == 0) {
                        fab.setImageResource(R.mipmap.collection_normal);
                        Toast.makeText(TravelDetailActivity2.this, "取消收藏", Toast.LENGTH_SHORT).show();
                        state = 1;
                    } else {
                        fab.setImageResource(R.mipmap.collection_selected);
                        Toast.makeText(TravelDetailActivity2.this, "收藏成功", Toast.LENGTH_SHORT).show();

                        state = 0;
                    }
                } else {                                              //来自游记列表的收藏点击事件
                    if (mTag == 0) {
                        fab.setImageResource(R.mipmap.collection_selected);
                        DBUtils.SaveBook(mBookBean);
                        mTag = 1;
                        EventBus.getDefault().post(new TravelControler(mPosition, 1));
                        Toast.makeText(TravelDetailActivity2.this, "收藏成功", Toast.LENGTH_SHORT).show();
                    } else {
                        fab.setImageResource(R.mipmap.collection_normal);
                        try {
                            DBUtils.DeleteBook(mBookBean);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        EventBus.getDefault().post(new TravelControler(mPosition, 0));
                        mTag = 0;
                        Toast.makeText(TravelDetailActivity2.this, "取消收藏", Toast.LENGTH_SHORT).show();

                    }
                }
                break;
        }


    }

    //按物理返回建的时候也得通知列表更新ui
    @Override
    public void onBackPressed() {
        if (mWhere.equals(FORM_TRAVELCOLLECTION)) {              //来自收藏列表的收藏点击事件
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putSerializable("bookeans", book);
            intent.putExtras(bundle);
            intent.putExtra("position", mPosition);
            intent.putExtra("state", state);
            setResult(33, intent);
            finish();           //关闭activity
        } else {
            //按返回建的时候通知列表更新ui
            finish();           //关闭activity
//            overridePendingTransition(R.anim.in_anim2,R.anim.out_anim2);        //跳转Activity时的动画效果
            EventBus.getDefault().post(new UpadteBrowsingNmber());       //通知更新浏览数
        }
    }

}
