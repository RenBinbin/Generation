package com.weiruanit.lifepro.module.favourite;

import android.util.Log;
import android.widget.Button;
import android.widget.LinearLayout;

import com.weiruanit.lifepro.R;
import com.weiruanit.lifepro.base.BaseBackActivity;
import com.weiruanit.lifepro.sqlbean.Book;
import com.weiruanit.lifepro.utils.DBUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FavTravelActivity extends BaseBackActivity {


    private static final String TAG ="FavTravelActivity" ;
    @BindView(R.id.btn_fav_travel)
    Button btnFavTravel;
    @BindView(R.id.activity_favourite)
    LinearLayout activityFavourite;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_favtravel;
    }

    @Override
    public void initData() {
        ButterKnife.bind(this);
    }

    @Override
    protected void getData() {

    }



    @OnClick(R.id.btn_fav_travel)
    public void onClick() {
        List<Book> books = DBUtils.findAllBooks();
        for (int i = 0; i < books.size(); i++) {
            Log.d(TAG, "onClick: "+books.get(i).toString());
        }
    }
}
