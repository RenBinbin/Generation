package com.weiruanit.lifepro.module.travel.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.liaoinstan.springview.widget.SpringView;
import com.weiruanit.lifepro.R;
import com.weiruanit.lifepro.base.BaseActivity;
import com.weiruanit.lifepro.module.travel.MyViewDragHelper;
import com.weiruanit.lifepro.module.travel.adapter.TravelCollectionRecAdapter;
import com.weiruanit.lifepro.module.travel.anim.MyItemAnimator;
import com.weiruanit.lifepro.sqlbean.Book;
import com.weiruanit.lifepro.utils.DBUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TravelCollectionActivity extends BaseActivity {

    @BindView(R.id.rv_travel)
    RecyclerView rvTravel;
    @BindView(R.id.sv_travle)
    SpringView svTravle;
    @BindView(R.id.im_show)
    RelativeLayout imShow;
    @BindView(R.id.myViewDragHelper)
    MyViewDragHelper myViewDragHelper;

    //游记收藏的Adapter
    private TravelCollectionRecAdapter mAdapter;

    //游记收藏的bean的列表
    private List<Book> list = new ArrayList<>();


    //判断收藏是否选中
    private boolean isChecked = true;


    //定义一个标记
    public static final String FORM_TRAVELCOLLECTION = "TravelCollectionActivity";

    public static final int REQUEST_CODE = 22;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_travel_collection;
    }


    //在这个方法中初始化布局
    @Override
    public void initData() {
        initView();
    }

    public void initView() {
        ButterKnife.bind(this);
        myViewDragHelper.setmBackFinishListener(new MyViewDragHelper.BackFinishListener() {
            @Override
            public void finishActivity() {
                finish();   //关闭当前的Activity
            }
        });
        mAdapter = new TravelCollectionRecAdapter(list, TravelCollectionActivity.this);


        //设置RecyclerView的点击事件
        mAdapter.setViewOnClickListener(new TravelCollectionRecAdapter.ViewOnClickListener() {
            @Override
            public void onItemClick(int position, View view) {
                Intent intent = new Intent(TravelCollectionActivity.this, TravelDetailActivity2.class);
                Bundle bundle = new Bundle();
                Book booksbean = list.get(position);
                Log.e("asd", booksbean.toString());
                bundle.putSerializable("bookeans", booksbean);
                intent.putExtras(bundle);
                intent.putExtra("fromWhere", FORM_TRAVELCOLLECTION);
                intent.putExtra("position", position);
                startActivityForResult(intent, REQUEST_CODE);
            }

            @Override
            public void onFollowStatusChange(ImageView view, Book booksBean,int position) {

                DBUtils.DeleteBook(booksBean);
                //删除本地游记收藏后，自动更新UI
                if (list != null && !list.isEmpty()) {
                    list.remove(position);
                    mAdapter.notifyItemRemoved(position);       //删除动画
                    Toast.makeText(TravelCollectionActivity.this, "取消收藏", Toast.LENGTH_SHORT).show();

                }
                if(list.isEmpty()){
                    imShow.setVisibility(View.VISIBLE);
                }
            }
        });


        rvTravel.setLayoutManager(new LinearLayoutManager(this));
        rvTravel.setAdapter(mAdapter);
        rvTravel.setItemAnimator(new MyItemAnimator());

    }


    @Override
    protected void getData() {
        readLocalData();
    }


    //读本地数据库,并刷新界面
    public void readLocalData() {

        list.addAll(DBUtils.findAllBooks());
        if (list != null && !list.isEmpty()) {
            mAdapter.notifyDataSetChanged();
        } else {
            mAdapter.notifyDataSetChanged();        //没数据的时候又更新一次界面是因为：在有多个数据的情况下全部取消收藏始终会保留一个(即使数据库没有数据了),所以就得再更新一次界面
            imShow.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //点击城市刷新游记界面
        if (requestCode == REQUEST_CODE && 33 == resultCode) {
            int state = data.getIntExtra("state", 0);
            int position = data.getIntExtra("position", 0);
            Bundle bundle = data.getExtras();
            Book book = (Book) bundle.getSerializable("bookeans");
            if ((state == 1) && book != null) {
                DBUtils.DeleteBook(book);
                //删除本地游记收藏后，自动更新UI
                if (list != null && !list.isEmpty()) {
                    list.clear();
                    readLocalData();
                }
            }

        }

    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, R.anim.back_out);          //设置Activity销毁时的动画,一定要在finsh()方法中写这行代码，在OnDestroy()方法中写是没有用的
    }


}
