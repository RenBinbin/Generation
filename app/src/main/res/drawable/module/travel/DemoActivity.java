package com.weiruanit.lifepro.module.travel;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.weiruanit.lifepro.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DemoActivity extends AppCompatActivity {
//
//    @BindView(R.id.im_heade)
//    SimpleDraweeView imHeade;
    @BindView(R.id.webView)
    WebView webView;
//    private int pageIndex = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        ButterKnife.bind(this);


//        Map<String, String> map = new HashMap<>();
//        map.put("page", pageIndex + "");
//        HttpUtils.requestData(getContext(), TRAVELLIST, map, new StringCallback() {
//            @Override
//            public void onSuccess(String s, Call call, Response response) {
//                Log.e("tag", s);
//                Gson gson = new Gson();
//                TravelResponce travelResponce = gson.fromJson(s, TravelResponce.class);
//                imHeade.setImageURI(Uri.parse(travelResponce.getData().getBooks().get(0).getHeadImage()));
//
//            }
//        });

//        WebSettings settings = webView.getSettings();
//        settings.setJavaScriptEnabled(true);    //启用JS脚本
//        webView.setWebViewClient(new WebViewClient() {
//            //当点击链接时,希望覆盖而不是打开新窗口
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                view.loadUrl(url);  //加载新的url
//                return true;    //返回true,代表事件已处理,事件流到此终止
//            }
//
//
//            @Override
//            public void onPageFinished(WebView view, String url) {
//                super.onPageFinished(view, url);
//                if(url!=null && url.contains("/p/resource/weapon/iProductID/39")){
//
//
//                    String fun="javascript:function getClass(parent,sClass) { var aEle=parent.getElementsByTagName('div'); var aResult=[]; var i=0; for(i<0;i<aEle.length;i++) { if(aEle[i].className==sClass) { aResult.push(aEle[i]); } }; return aResult; } ";
//
//                    view.loadUrl(fun);
//
//                    String fun2="javascript:function hideOther() {getClass(document,'nav-sides')[0].style.display='none'; getClass(document,'side-bar')[0].style.display='none'; getClass(document,'area-main')[0].style.display='none'; getClass(document,'home-foot')[0].style.display='none'; getClass(document,'enter')[0].style.display='none'; getClass(document,'crumb')[0].style.display='none';getClass(document,'date-tab clearfix')[0].style.display='none'; document.getElementById('id_sidebar').style.display='none'; document.getElementById('top_nav').style.display='none'; document.getElementById('fix-personal').style.display='none'; document.getElementById('waterlogo').style.display='none';getClass(document,'wrap')[0].style.minWidth=0;getClass(document,'game')[0].style.paddingTop=0;}";
//
//                    view.loadUrl(fun2);
//
//                    view.loadUrl("javascript:hideOther();");
//
//
//                }
//
//                super.onPageFinished(view, url);
//            }
//        });
//        webView.loadUrl("http://travel.qunar.com/youji/6630121?from=baidu_apistore");
////        http://travel.qunar.com/youji/6630121?from=baidu_apistore


//        WebSettings settings = webView.getSettings();
//        settings.setJavaScriptEnabled(true);    //启用JS脚本
//        webView.setWebViewClient(new WebViewClient() {
//            @Override
//            public void onPageFinished(WebView view, String url) {
//
//
//                if (url != null/* && url.contains("/p/resource/weapon/iProductID/39")*/) {
//
//
//                    String fun = "javascript:function getClass(parent,sClass) { var aEle=parent.getElementsByTagName('div'); var aResult=[]; var i=0; for(i<0;i<aEle.length;i++) { if(aEle[i].className==sClass) { aResult.push(aEle[i]); } }; return aResult; } ";
//
//                    view.loadUrl(fun);
//
//                    String fun2 = "javascript:function hideOther() {getClass(document,'q_header q_header_travel q_header_travel_travelbook')[0].style.display='none'; getClass(document,'side-bar')[0].style.display='none'; getClass(document,'area-main')[0].style.display='none'; getClass(document,'home-foot')[0].style.display='none'; getClass(document,'enter')[0].style.display='none'; getClass(document,'crumb')[0].style.display='none';getClass(document,'date-tab clearfix')[0].style.display='none'; document.getElementById('id_sidebar').style.display='none'; document.getElementById('top_nav').style.display='none'; document.getElementById('fix-personal').style.display='none'; document.getElementById('waterlogo').style.display='none';getClass(document,'wrap')[0].style.minWidth=0;getClass(document,'game')[0].style.paddingTop=0;}";
//
//                    view.loadUrl(fun2);
//
//                    view.loadUrl("javascript:hideOther();");
//
//
//                }
//
//                super.onPageFinished(view, url);
//            }
//        });
//        webView.loadUrl("http://travel.qunar.com/youji/6630121?from=baidu_apistore");


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

                Log.e("qwe","执行了吗"+url);
                if(url!=null&&url.contains("/books/")) {
                    String fun="javascript:function getClass(parent,sClass) { var aEle=parent.getElementsByTagName('div'); var aResult=[]; var i=0; for(i<0;i<aEle.length;i++) { if(aEle[i].className==sClass) { aResult.push(aEle[i]); } }; return aResult; } ";

                    view.loadUrl(fun);

                    String fun2="javascript:function hideOther() {getClass(document,'info-bar')[0].style.display='none'; getClass(document,'title-bd')[0].style.display='none'; getClass(document,'e_crumb')[0].style.display='none'; getClass(document,'title')[0].style.display='none';}";


                    view.loadUrl(fun2);

                    view.loadUrl("javascript:hideOther();");
                }

                super.onPageFinished(view, url);
            }

//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//
//                view.loadUrl(url);  //加载新的url
//                return true;    //返回true,代表事件已处理,事件流到此终止
//            }
        });

        webView.loadUrl("http://travel.qunar.com/youji/6630121?from=baidu_apistore");
    }
}
