package com.weiruanit.lifepro.module.travel.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.lzy.okgo.callback.StringCallback;
import com.weiruanit.lifepro.MainActivity;
import com.weiruanit.lifepro.R;
import com.weiruanit.lifepro.TravelMessage;
import com.weiruanit.lifepro.base.BaseLoadFragment;
import com.weiruanit.lifepro.module.travel.activity.TravelDetailActivity2;
import com.weiruanit.lifepro.module.travel.adapter.TravelRecAdapter;
import com.weiruanit.lifepro.module.travel.adapter.base.TravelBaseAdapter;
import com.weiruanit.lifepro.module.travel.bean.TravelControler;
import com.weiruanit.lifepro.module.travel.bean.TravelResponce;
import com.weiruanit.lifepro.module.travel.bean.UpadteBrowsingNmber;
import com.weiruanit.lifepro.module.travel.utils.HttpUtils;
import com.weiruanit.lifepro.sqlbean.Book;
import com.weiruanit.lifepro.utils.DBUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

import static com.weiruanit.lifepro.module.travel.TravelApi.TRAVELLIST;


public class TravelFragment extends BaseLoadFragment {
    @BindView(R.id.rv_travel)
    RecyclerView rvTravel;
    @BindView(R.id.sv_travle)
    SpringView svTravle;
    private TravelRecAdapter mAdapter;

    //游记页数
    private int pageIndex = 1;

    //游记请求的关键字
    private String requestInfo = "南京";


    //定义一个标记
    public static final String FORM_TRAVELFRAGMENT = "TravelFragment";



    //游记内容的bean
    List<TravelResponce.DataBean.BooksBean> list = new ArrayList<>();
    private Map<String, String> map = new HashMap<>();


    // 用来记录当前点击的bean
    private TravelResponce.DataBean.BooksBean mBooksBean;

    private List<Integer> favourites = new ArrayList<>();


    public static TravelFragment newInstance() {
        TravelFragment fragment = new TravelFragment();
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_travel2;
    }


    @Override
    protected void initView(View view) {
        ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        mAdapter = new TravelRecAdapter(list, getContext());

        //设置RecyclerView的点击事件
        mAdapter.setViewOnClickListener(new TravelBaseAdapter.ViewOnClickListener() {

            @Override
            public void onItemClick(int position, View view) {
                Intent intent = new Intent(getActivity(), TravelDetailActivity2.class);
                //TravelDetailActivity2  测试
                mBooksBean = list.get(position);
                Bundle bundle = new Bundle();
                bundle.putInt("position",position);
                bundle.putInt("tag",favourites.get(position));
                bundle.putParcelable("bookeans", mBooksBean);
                intent.putExtras(bundle);
                intent.putExtra("fromWhere", FORM_TRAVELFRAGMENT);
                //只要点击一次item，浏览数就加一
                list.get(position).setViewCount(list.get(position).getViewCount()+1);
                startActivity(intent);
//                getActivity().overridePendingTransition(R.anim.in_anim,R.anim.out_anim);        //跳转Activity时的动画效果

            }

            @Override
            public void onFollowStatusChange(ImageView view, TravelResponce.DataBean.BooksBean booksBean,int position) {
                Log.e("position", booksBean.toString());
                ImageView imageView = view;

//
//                MyAnimotion myAnimotion = new MyAnimotion(1, 0, 180, imageView.getMeasuredWidth() / 2f,
//                        imageView.getMeasuredHeight() / 2f, 310f);
//                myAnimotion.setDuration(600);
//                myAnimotion.setInterpolator(new LinearInterpolator());
//                myAnimotion.setFillAfter(false);
//                imageView.startAnimation(myAnimotion);
//

                if (favourites.get(position)==0){
                    imageView.setImageResource(R.mipmap.collection_selected);
                    favourites.set(position,1);
                    DBUtils.SaveBook(booksBean);
                    //更新收藏数
                    list.get(position).setLikeCount( list.get(position).getLikeCount()+1);
                    mAdapter.notifyDataSetChanged();
                    Toast.makeText(getActivity(), "收藏成功", Toast.LENGTH_SHORT).show();

                }else {
                    imageView.setImageResource(R.mipmap.collection_normal);
                    favourites.set(position,0);
                    DBUtils.DeleteBook(booksBean);
                    //更新收藏数
                    list.get(position).setLikeCount(list.get(position).getLikeCount()-1);
                    mAdapter.notifyDataSetChanged();
                    Toast.makeText(getActivity(), "取消收藏", Toast.LENGTH_SHORT).show();
//                    MyAnimotion myAnimotion = new MyAnimotion(1, 0, 180, view.getMeasuredWidth() / 2f,
//                            view.getMeasuredHeight() / 2f, 310f);
//                    myAnimotion.setDuration(600);
//                    myAnimotion.setInterpolator(new LinearInterpolator());
//                    myAnimotion.setFillAfter(false);
//                    view.startAnimation(myAnimotion);
                }
            }
        });
        mAdapter.setFavourites(favourites);
        rvTravel.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvTravel.setAdapter(mAdapter);


        //设置上拉加载
        svTravle.setFooter(new DefaultFooter(getActivity()));
        svTravle.setHeader(new DefaultHeader(getActivity()));
        svTravle.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                pageIndex = 1;
                if (!list.isEmpty() && !favourites.isEmpty()) {
                    list.clear();
                    favourites.clear();
                }
                map.put("page", pageIndex + "");
                map.put("query", requestInfo);
                requestData(map);
            }

            @Override
            public void onLoadmore() {
                map.put("page", pageIndex + "");
                map.put("query", requestInfo);
                requestData(map);
            }
        });

        rvTravel.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                //监听滑动  隐藏软键盘
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.hideSoftInput();

                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {
            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });

        //本来这短代码是放在getData()方法中的，但是第一次加载的圆圈不会消失，所以会写在这里
        map.put("page", pageIndex + "");
        map.put("query", requestInfo);
        requestData(map);
    }

    @Override
    protected void getData() {
    }


    public void requestData(Map<String, String> map) {
        HttpUtils.requestData(getContext(), TRAVELLIST, map, new StringCallback() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                Log.e("tag", s);
                setState(StateCode.CONTENT);
                Gson gson = new Gson();
                TravelResponce travelResponce = gson.fromJson(s, TravelResponce.class);
                if (travelResponce != null && travelResponce.getData().getBooks() != null) {
                    list.addAll(travelResponce.getData().getBooks());
                    for (int i = 0; i < travelResponce.getData().getBooks().size(); i++) {
                        //判断是否收藏过
                     if ( Book.find(Book.class,"BOOK_URL = ? ",travelResponce.getData().getBooks().get(i).getBookUrl()).size()==0) {
                         favourites.add(0);
                     }else {
                         favourites.add(1);
                     }
                    }
                }
                svTravle.onFinishFreshAndLoad();            //下拉刷新后，刷新按钮得消失

                //数据获取后更新UI
                mAdapter.notifyDataSetChanged();
                pageIndex++;
            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);
                setState(StateCode.LODING);

                //延迟一下再实现加载失败
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        setState(StateCode.ERROR);
                    }
                },3000);
                svTravle.onFinishFreshAndLoad();            //下拉刷新后，刷新按钮得消失

            }
        });
    }


    @Subscribe
    public void getRequest(TravelMessage message) {
        TravelMessage mss = message;
        String query = mss.getQuery();
        requestInfo = query;
        pageIndex = 1;
        if (!list.isEmpty()) {
            list.clear();
        }
        map.put("page", pageIndex + "");
        map.put("query", query);
        requestData(map);
    }

    @Subscribe
    public void getBrowsingNumber(UpadteBrowsingNmber browsingNmber){
        //更新浏览数数
        mAdapter.notifyDataSetChanged();
    }

    @Subscribe
    public void getTravelControler(TravelControler controler){
        favourites.set(controler.getPosition(),controler.getTag());
        //更新收藏数
        if(controler.getTag() == 1){
            list.get(controler.getPosition()).setLikeCount(list.get(controler.getPosition()).getLikeCount()+1);
        }else{
            list.get(controler.getPosition()).setLikeCount(list.get(controler.getPosition()).getLikeCount()-1);
        }

        mAdapter.notifyDataSetChanged();
    }

    public void UpdateUi(TravelMessage message) {
        if (message != null) {
            String query = message.getQuery();
            requestInfo = query;
            pageIndex = 1;
            if (!list.isEmpty()) {
                list.clear();
                favourites.clear();
            }
            map.put("page", pageIndex + "");
            map.put("query", query);
            requestData(map);
        }
    }

}