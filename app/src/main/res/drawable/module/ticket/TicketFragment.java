package com.weiruanit.lifepro.module.ticket;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.weiruanit.lifepro.R;
import com.weiruanit.lifepro.base.BaseFragment;
import com.weiruanit.lifepro.module.ticket.bean.CalendarUtils;
import com.weiruanit.lifepro.module.ticket.bean.MyAnimotion;
import com.weiruanit.lifepro.module.ticket.city.CityActivity;

import java.text.ParseException;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 车票查询界面
 */

public class TicketFragment extends BaseFragment {


    private static final int FROM_STATTON = 123;
    private static final int TO_STATION = 111;
    private static final int CALENDAR_DATE = 124;
    @BindView(R.id.tv_from)
    TextView tvFrom;
    @BindView(R.id.iv_exchange)
    ImageView ivExchange;
    @BindView(R.id.tv_to)
    TextView tvTo;
    @BindView(R.id.ll_choose_date)
    LinearLayout llChooseDate;
    @BindView(R.id.btn_search)
    Button btnSearch;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.rl)
    RelativeLayout rl;
    private Calendar calendar;
    private int state = 1;//0 起始站; 1 终点站

    private String dateWeek;

    public TicketFragment() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_ticket;
    }

    @Override
    protected void initView(View view) {
        ButterKnife.bind(this, view);
        initCalendar();//初始化日历
    }

    @Override
    protected void getData() {

    }

    /**
     * 日历的初始化
     */
    private void initCalendar() {

        String str = CalendarUtils.initCalendar();
        dateWeek = CalendarUtils.getWeek(str);
        tvDate.setText(str);
//        Toast.makeText(getActivity(), dateWeek, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (requestCode == FROM_STATTON && state == 1) {
                String fromCityname = data.getStringExtra("fromcityname");
                tvFrom.setText(fromCityname);
            } else if (requestCode == TO_STATION && state == 2) {
                String toCityname = data.getStringExtra("fromcityname");
                tvTo.setText(toCityname);
            }
        }
    }
    //获取日历
    class TrainDatePicker extends DatePickerDialog{

        public TrainDatePicker(Context context, OnDateSetListener listener, int year, int month,
                               int dayOfMonth) {
            super(context, listener, year, month, dayOfMonth);
        }

        @Override
        protected void onStop() {
//            super.onStop();
        }
    }
    //获取日期
    private DatePickerDialog.OnDateSetListener datePickerDialog = new DatePickerDialog
            .OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

            int dayMonth = month + 1;
            String str = year + "-" + dayMonth + "-" + dayOfMonth;
            dateWeek = CalendarUtils.getWeek(str);
            tvDate.setText(year + "-" + dayMonth + "-" + dayOfMonth);
//            Toast.makeText(getActivity(), "onDateSet " + year + "-" + dayMonth + "-" + dayOfMonth
//                            + " " + dateWeek,
//                    Toast.LENGTH_SHORT).show();
        }

    };

    @OnClick({R.id.tv_from, R.id.iv_exchange, R.id.tv_to, R.id.ll_choose_date, R.id.btn_search})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_from://起始站

//                Toast.makeText(getActivity(), tvFrom.getText().toString(), Toast.LENGTH_SHORT)
//                        .show();
                state = 1;
                Intent intent1 = new Intent(getActivity(), CityActivity.class);
                intent1.putExtra("state", state);
                startActivityForResult(intent1, FROM_STATTON);
                break;
            case R.id.iv_exchange://交换
                final String s1 = tvFrom.getText().toString();
                final String s2 = tvTo.getText().toString();
                MyAnimotion myAnimotion = new MyAnimotion(1, 0, 180, rl.getMeasuredWidth() / 2f,
                        rl.getMeasuredHeight() / 2f, 310f);
                myAnimotion.setDuration(600);
                myAnimotion.setInterpolator(new LinearInterpolator());
                myAnimotion.setFillAfter(false);
                rl.startAnimation(myAnimotion);

                tvFrom.setText(s2);
                tvTo.setText(s1);
                break;
            case R.id.tv_to://终点站
//                Toast.makeText(getActivity(), tvTo.getText().toString(), Toast.LENGTH_SHORT).show();
                state = 2;
                Intent intent2 = new Intent(getActivity(), CityActivity.class);
                intent2.putExtra("state", state);
                startActivityForResult(intent2, TO_STATION);
                break;
            case R.id.ll_choose_date://获取日历
                calendar = Calendar.getInstance();

                TrainDatePicker pickerDialog = new TrainDatePicker(getActivity(), this
                        .datePickerDialog
                        , calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get
                        (Calendar.DAY_OF_MONTH));
                //设置时间限制
                pickerDialog.getDatePicker().setMinDate(System.currentTimeMillis()-1000);

                pickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis() +
                        CalendarUtils.oneDay * 20);
                pickerDialog.show();
                break;
            case R.id.btn_search://开始搜索

                String selectDate = tvDate.getText().toString();
                String s = CalendarUtils.forDateToString(System.currentTimeMillis());
                long l = 0;
                try {
                    l = CalendarUtils.formatTime(selectDate);
                    if (l - CalendarUtils.formatTime(s) >= 0) {
                        Intent intent3 = new Intent(getActivity(), TicketActivity.class);
                        intent3.putExtra("tvFrom", tvFrom.getText().toString());
                        intent3.putExtra("tvTo", tvTo.getText().toString());
                        intent3.putExtra("date", tvDate.getText().toString());
                        intent3.putExtra("dateWeek", dateWeek);
                        startActivity(intent3);
                        getActivity().overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);
                    } else {
                        Toast.makeText(getActivity(), "无效的日期，请重新选择", Toast.LENGTH_SHORT).show();
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                break;
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }


}
