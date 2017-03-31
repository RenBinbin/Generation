package com.weiruanit.lifepro.module.joke.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.weiruanit.lifepro.R;
import com.weiruanit.lifepro.base.BaseLoadFragment;
import com.weiruanit.lifepro.module.joke.TextJokeDatailsActivity;
import com.weiruanit.lifepro.module.joke.adapter.TextJokeFragmentAdapter;
import com.weiruanit.lifepro.module.joke.bean.JokeControl;
import com.weiruanit.lifepro.module.joke.bean.TextJokeBeanResponse;
import com.weiruanit.lifepro.module.joke.bean.TextJokeHeaderBean;
import com.weiruanit.lifepro.module.ticket.bean.MyAnimotion;
import com.weiruanit.lifepro.module.travel.bean.TravelResponce;
import com.weiruanit.lifepro.module.travel.utils.HttpUtils;
import com.weiruanit.lifepro.sqlbean.JokeText;
import com.weiruanit.lifepro.utils.DBUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

import static com.lzy.okgo.interceptor.LoggerInterceptor.TAG;
import static com.weiruanit.lifepro.module.travel.TravelApi.TRAVELLIST;

/**
 * Created by Administrator on 2016/11/14 0014.
 */

public class TextJokeFragment extends BaseLoadFragment {


    private RecyclerView mRecyclerView;
    private ArrayList<TextJokeBeanResponse.ShowapiResBodyBean.ContentlistBean> mContentlistBeen;
    private ArrayList<TextJokeHeaderBean> headerBeanList;
    private TextJokeFragmentAdapter mAdapter;
    private int page = 1;
    private SpringView mSpringView;
    private boolean isFresh;
    private ArrayList<Integer> favourites =  new ArrayList<>();//0 代表没收藏    1  代表收藏
    private  ArrayList<TravelResponce.DataBean.BooksBean> list = new ArrayList<>();
    private Map<String, String> map = new HashMap<>();
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_joke_text;
    }

    @Override
    protected void initData() {
        getData();
    }

    @Override
    protected void initView(View view) {
        mSpringView = (SpringView) view.findViewById(R.id.sv_text_joke);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_text_joke);


        EventBus.getDefault().register(this);//注册eventbus

        mContentlistBeen = new ArrayList<>();
        headerBeanList = new ArrayList<>();
        mAdapter = new TextJokeFragmentAdapter(mContentlistBeen,list,getContext());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter.setFavourites(favourites);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

//        mRecyclerView.addItemDecoration(new DividerGridItemDecoration(getContext()));
        mSpringView.setHeader(new DefaultHeader(getActivity()));
        mSpringView.setFooter(new DefaultFooter(getActivity()));
        mSpringView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                isFresh = true;
                page = 1;
                if (isFresh){
                    mContentlistBeen.clear();
                    favourites.clear();
                    isFresh = false;
                }
                getData();

            }
            @Override
            public void onLoadmore() {
                //上拉加载应该对pageIndex 进行重置
                page++;
                getData();
            }
        });
        mAdapter.setmCollectListener(new TextJokeFragmentAdapter.MyCollectClickListener() {
            @Override
            public void onCollectClick(View view, int position) {
                Log.d(TAG, "onCollectClick: ");

                /**int direction:方向  x  y  z
                 * float fromDegrees: 开始的角度
                 * float toDegrees: 结束的角度
                 * float centerX:   view的x位置
                 * float centerY:      view的y位置
                 * float depthZ:
                 *
                 */
                MyAnimotion myAnimotion = new MyAnimotion(2, 0, 180, view.getMeasuredWidth() / 2f,
                        view.getMeasuredHeight() / 2f, 500f);
                myAnimotion.setDuration(600);//动画时间
                myAnimotion.setInterpolator(new LinearInterpolator());//加速度
                //动画结束后是否保持结束后的状态( false : 不保持  true:保持)
                myAnimotion.setFillAfter(false);
                view.startAnimation(myAnimotion);//开始动画

                ImageView fav = (ImageView) view;

                if (favourites!=null){
                    if (favourites.get(position).equals(0)){
                        fav.setImageResource(R.mipmap.collection_selected);
                        DBUtils.SaveTextJoke(mContentlistBeen.get(position));
                        favourites.set(position,1);
                    }else if (favourites.get(position).equals(1)){
                        fav.setImageResource(R.mipmap.joke_collection_normal);
                        DBUtils.DeleteTextJoke(mContentlistBeen.get(position));
                        favourites.set(position,0);
                    }
                }

            }});
        mAdapter.setOnItemLongClickListener(new TextJokeFragmentAdapter.OnItemLongClickListener() {
            @Override
            public void onItemClick(View v,int position) {
                Log.d(TAG, "onItemClick: "+position+","+mContentlistBeen.get(position).getTitle());
                mContentlistBeen.remove(position);
                favourites.remove(position);

                mAdapter.notifyItemRemoved(position);

            }

        });
        mAdapter.setmListener(new TextJokeFragmentAdapter.MyItemClickListener() {
            //跳详情
            @Override
            public void onItemClick(View view, int postion) {
//                Toast.makeText(getContext(),"点击了",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), TextJokeDatailsActivity.class);
                Bundle bundle = new Bundle();
//                bundle.putBoolean("isCollect",favourites.get(postion));
                bundle.putIntegerArrayList("isCollect",favourites);
                bundle.putParcelableArrayList("list",mContentlistBeen);
                bundle.putIntegerArrayList("listFav",favourites);
                bundle.putParcelableArrayList("headerList",list);
                intent.putExtras(bundle);
                intent.putExtra("postion",postion);
//                intent.putExtra("uri",list.get(postion).getUserHeadImg());
//                intent.putExtra("name",list.get(postion).getUserName());
                startActivity(intent);
            }
        });
        setState(StateCode.LODING);

    }



    @Subscribe
    public void addFavourite(JokeControl control) {

        favourites.set(control.getPosition(),control.getTag());
        mAdapter.notifyDataSetChanged();
    }

    public void setListFav(ArrayList<Integer> favourites){
    }
    //
    @Override
    protected void getData() {
        getTextJokeData();
        requestData();
    }

    private  boolean isJokeOk = false;
    private  boolean isTravelOk = false;
    public void getTextJokeData(){
        String url = "http://apis.baidu.com/showapi_open_bus/showapi_joke/joke_text";
        String apikey = "abcfe469f2ede2b495055162e97d8b82";
        OkGo.get(url)
                .tag(getContext())
                .headers("apikey", apikey)
                .params("page", page+"")
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        setState(StateCode.ERROR);
                        mSpringView.onFinishFreshAndLoad();
                    }

                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Log.e("xxx", s.toString());
                        try{
                            isJokeOk = true;
                            Gson gson = new Gson();
                            TextJokeBeanResponse contentlistBean = gson.fromJson(s,TextJokeBeanResponse.class);
                            if (contentlistBean.getShowapi_res_body().getContentlist()!= null){
//                                    for (int i = 0; i <= 100; i++) {
//                                        favourites.add(0);
//                                    }
                                for (int i = 0; i < contentlistBean.getShowapi_res_body().getContentlist().size(); i++) {
//                                    if (JokeText.find(JokeText.class,"TIME = ? and tile = ?",contentlistBean.getShowapi_res_body().getContentlist().get(i).getCt(),contentlistBean.getShowapi_res_body().getContentlist().get(i).getTitle()).size()==0)
                                    if (JokeText.find(JokeText.class," title = ? ",contentlistBean.getShowapi_res_body().getContentlist().get(i).getTitle()).size()==0)
                                    {
                                        favourites.add(0);

                                    }else {
                                        favourites.add(1);

                                    }
                                }

                                mContentlistBeen.addAll(contentlistBean.getShowapi_res_body().getContentlist());
                                setState(StateCode.CONTENT);

                                if(isTravelOk && isJokeOk){
                                    mAdapter.notifyDataSetChanged();
                                    mSpringView.onFinishFreshAndLoad();
                                }

                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                });
    }
    public void requestData() {
        String requestInfo = "上海";
        map.put("page", page + "");
        map.put("query", requestInfo);
        HttpUtils.requestData(getContext(), TRAVELLIST, map, new StringCallback() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                Log.e("tag", s);
                setState(StateCode.CONTENT);
                mSpringView.onFinishFreshAndLoad();
                isTravelOk = true;
                Gson gson = new Gson();
                TravelResponce travelResponce = gson.fromJson(s, TravelResponce.class);
                if (travelResponce != null && travelResponce.getData().getBooks() != null) {
                    list.addAll(travelResponce.getData().getBooks());
                    list.addAll(travelResponce.getData().getBooks());
                }
                //下拉刷新后，刷新按钮得消失

                //数据获取后更新UI

                if(isTravelOk && isJokeOk){
                    mAdapter.notifyDataSetChanged();
                }
                page++;
            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);

                setState(StateCode.LODING);
                mSpringView.onFinishFreshAndLoad();                //延迟一下再实现加载失败
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        setState(StateCode.ERROR);
                    }
                },3000);
                //下拉刷新后，刷新按钮得消失

            }
        });
    }


}
