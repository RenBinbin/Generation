package com.weiruanit.lifepro.module.ticket;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.weiruanit.lifepro.DataAPI;
import com.weiruanit.lifepro.R;
import com.weiruanit.lifepro.base.BaseBackActivity;
import com.weiruanit.lifepro.module.news.widget.LoadingActionFigureView;
import com.weiruanit.lifepro.module.ticket.bean.CalendarUtils;
import com.weiruanit.lifepro.module.ticket.bean.TrainListInfo;

import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 列车列表的显示
 */

public class TicketActivity extends BaseBackActivity {

    RelativeLayout rl;
    @BindView(R.id.tv_train_count)
    TextView trainCount;
    @BindView(R.id.recyclerView_ticket)
    RecyclerView recyclerViewTicket;
    @BindView(R.id.freshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.last)
    ImageView last;
    @BindView(R.id.next)
    ImageView next;
    @BindView(R.id.laf)
    LoadingActionFigureView laf;
    private ImageView ticketBack;
    private TextView tvStartStation;
    private TextView tvEndStation;
    private TextView tvDate;
    private TextView tvWeek;

    private ArrayList<TrainListInfo.DataBean.TrainListBean> trainListInfos = new ArrayList<>();
    private TrainListAdapter trainListAdapter;
    private String tvFrom;
    private String tvTo;
    private String date;
    private String dateWeek;

    private boolean isOtherDay;

    @Override
    protected int getLayoutId() {
        //隐藏标题 全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager
                .LayoutParams.FLAG_FULLSCREEN);
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        return R.layout.activity_ticket;
    }

    @Override
    public void initData() {
        ButterKnife.bind(this);
//        getmBackView().setScollEnabled(false);//设置无法滑动返回
        //获取站点
        Intent intent = getIntent();
        tvFrom = intent.getStringExtra("tvFrom");
        tvTo = intent.getStringExtra("tvTo");
        date = intent.getStringExtra("date");
        dateWeek = intent.getStringExtra("dateWeek");
        rl = (RelativeLayout) findViewById(R.id.rl);

        ticketBack = (ImageView) findViewById(R.id.in_ticket_title).findViewById(R.id
                .iv_ticket_back);
        //设置站点
        tvStartStation = (TextView) findViewById(R.id.in_ticket_title).findViewById(R.id
                .tv_start);
        tvEndStation = (TextView) findViewById(R.id.in_ticket_title).findViewById(R.id
                .tv_end);
        tvDate = (TextView) findViewById(R.id.in_ticket_title).findViewById(R.id
                .tv_date_details);
        tvWeek = (TextView) findViewById(R.id.in_ticket_title).findViewById(R.id
                .tv_week);
        tvStartStation.setText(tvFrom);
        tvEndStation.setText(tvTo);
        tvDate.setText(date);
        tvWeek.setText(dateWeek);

        //下拉刷新
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getTrainInfo(date);
            }
        });


        //返回
        ticketBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        recyclerViewTicket.setLayoutManager(new LinearLayoutManager(TicketActivity.this));
        trainListAdapter = new TrainListAdapter(trainListInfos, date);

        recyclerViewTicket.setAdapter(trainListAdapter);

        trainListAdapter.setTicketListener(new TrainListAdapter.TicketListener() {
            @Override
            public void itemClick(View view, int position) {
                Intent intent = new Intent(TicketActivity.this, TicketDetailsActivity.class);

                TrainListInfo.DataBean.TrainListBean trainListBean = trainListInfos.get(position);
                List<TrainListInfo.DataBean.TrainListBean.SeatInfosBean> seatInfos =
                        trainListBean.getSeatInfos();
                Bundle bundle = new Bundle();
                bundle.putParcelable("train", trainListBean);
                bundle.putSerializable("seatInfos", (Serializable) seatInfos);
                intent.putExtras(bundle);
                startActivity(intent);
//                Toast.makeText(TicketActivity.this, "点击了" + position, Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    protected void getData() {
        getTrainInfo(date);
    }

    /**
     * 获取站站信息
     */
    private void getTrainInfo(String date) {
        OkGo.get(DataAPI.QUNAR_TRAIN_SERVICE)
                .headers("apikey", "abcfe469f2ede2b495055162e97d8b82")
                .params("version", "1.0")
                .params("from", tvFrom)
                .params("to", tvTo)
                .params("date", date)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Log.d("aaaaaa", s);

                        Gson gson = new Gson();
                        TrainListInfo trainListInfo = gson.fromJson(s, TrainListInfo.class);
                        swipeRefreshLayout.setRefreshing(false);
                        //{"ret":false,"errcode":"1200","errmsg":"参数错误date"}
                        if (s != null && trainListInfo.isRet() && trainListInfo.getData()
                                .getTrainList() != null) {
                            if (trainListInfo.getData().getTrainList() != null) {
                                if (isOtherDay) {
                                    isOtherDay = false;
                                    trainListInfos.clear();
                                }
                                trainListInfos.addAll(trainListInfo.getData().getTrainList());
                                trainCount.setText(trainListInfo.getData().getTrainList().size()
                                        + "趟列车");
                                trainListAdapter.notifyDataSetChanged();
                                laf.setVisibility(View.GONE);
                            }
                        } else {
                            Toast.makeText(TicketActivity.this, "没有获取到相关的数据", Toast.LENGTH_SHORT)
                                    .show();
                            ErrorDialog errorDialog = new ErrorDialog();
                            errorDialog.show(getSupportFragmentManager(), "ErrorDialog");
                            errorDialog.setCancelable(true);
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        Toast.makeText(TicketActivity.this, "没有获取到相关的数据", Toast.LENGTH_SHORT)
                                .show();
                        swipeRefreshLayout.setRefreshing(false);
                        ErrorDialog errorDialog = new ErrorDialog();
                        errorDialog.show(getSupportFragmentManager(), "ErrorDialog");
                        errorDialog.setCancelable(true);

                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        overridePendingTransition(0, R.anim.ticket_out);
    }

    @OnClick({R.id.last, R.id.next})

    public void onClick(View view) {

        long systemTime1 = System.currentTimeMillis();
        long dayNum = 0;
        long currentTime = 0;
        try {
            String s = CalendarUtils.forDateToString(systemTime1);
            long systemTime = CalendarUtils.formatTime(s);//系统时间
            Log.d("aaaa", systemTime + "    systemTime");
            currentTime = CalendarUtils.formatTime(date);//
            Log.d("aaaa", currentTime + "    currentTime");
            dayNum = (currentTime - systemTime) / CalendarUtils.oneDay;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        switch (view.getId()) {
            case R.id.last://前一天
                if (dayNum >= 1) {//今天及今天之后
                    String s1 = CalendarUtils.forDateToString(currentTime - CalendarUtils.oneDay);
                    Log.d("aaaa", dayNum + "");
                    isOtherDay = true;
                    getTrainInfo(s1);
                    date = s1;
                    tvDate.setText(s1);
                    tvWeek.setText(CalendarUtils.getWeek(s1));
                } else {
                    Toast.makeText(this, "选择的日期有误!", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.next://后一天
                if (dayNum <= 19) {
                    String s1 = CalendarUtils.forDateToString(currentTime + CalendarUtils.oneDay);
                    Log.d("aaaa", dayNum + "");
                    isOtherDay = true;
                    getTrainInfo(s1);
                    date = s1;
                    tvDate.setText(s1);
                    tvWeek.setText(CalendarUtils.getWeek(s1));
                } else {
                    Toast.makeText(this, "没有数据了", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
