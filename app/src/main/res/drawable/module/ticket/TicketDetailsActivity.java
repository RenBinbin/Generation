package com.weiruanit.lifepro.module.ticket;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.weiruanit.lifepro.R;
import com.weiruanit.lifepro.module.ticket.bean.TrainListInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TicketDetailsActivity extends AppCompatActivity {

    @BindView(R.id.iv_ticket_back)
    ImageView ivTicketBack;
    @BindView(R.id.tv_trainnum)
    TextView tvTrainnum;
    @BindView(R.id.tv_from1)
    TextView tvFrom1;
    @BindView(R.id.tv_to1)
    TextView tvTo1;
    @BindView(R.id.tv_seat)
    TextView tvSeat;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.tv_ticks)
    TextView tvTicks;
    @BindView(R.id.btn_buy)
    Button btnBuy;
    @BindView(R.id.tv_seat1)
    TextView tvSeat1;
    @BindView(R.id.tv_price1)
    TextView tvPrice1;
    @BindView(R.id.tv_ticks1)
    TextView tvTicks1;
    @BindView(R.id.btn_buy1)
    Button btnBuy1;
    @BindView(R.id.tv_seat2)
    TextView tvSeat2;
    @BindView(R.id.tv_price2)
    TextView tvPrice2;
    @BindView(R.id.tv_ticks2)
    TextView tvTicks2;
    @BindView(R.id.btn_buy2)
    Button btnBuy2;
    @BindView(R.id.btn_buying)
    Button btnBuying;
    @BindView(R.id.activity_ticket_details)
    LinearLayout activityTicketDetails;
    private TrainListInfo.DataBean.TrainListBean train;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_details);
        ButterKnife.bind(this);
        initView();
    }
    private void initView() {
        Bundle bundle = getIntent().getExtras();
        train = bundle.getParcelable("train");
        List<TrainListInfo.DataBean.TrainListBean.SeatInfosBean> seatInfos = (List<TrainListInfo
                .DataBean.TrainListBean.SeatInfosBean>) bundle.getSerializable("seatInfos");

        tvTrainnum.setText(train.getTrainNo());
        tvFrom1.setText(train.getFrom());
        tvTo1.setText(train.getTo());

        if (seatInfos.size() != 0) {
            tvSeat.setText(seatInfos.get(0).getSeat());
            tvPrice.setText(seatInfos.get(0).getSeatPrice());
            tvPrice.setTextColor(Color.RED);
            if (seatInfos.get(0).getRemainNum()>0){
                tvTicks.setText(seatInfos.get(0).getRemainNum()+"张");
            }else {
                tvTicks.setText("无票");
            }

            tvSeat1.setText(seatInfos.get(1).getSeat());
            tvPrice1.setText(seatInfos.get(1).getSeatPrice());
            tvPrice1.setTextColor(Color.RED);

            if (seatInfos.get(0).getRemainNum()>0){
                tvTicks1.setText(seatInfos.get(1).getRemainNum()+"张");
            }else {
                tvTicks1.setText("无票");
            }

            tvSeat2.setText(seatInfos.get(2).getSeat());
            tvPrice2.setText(seatInfos.get(2).getSeatPrice());
            tvPrice2.setTextColor(Color.RED);

            if (seatInfos.get(0).getRemainNum()>0){
                tvTicks2.setText(seatInfos.get(2).getRemainNum()+"张");
            }else {
                tvTicks2.setText("无票");
            }
        } else {
            ErrorDialog dialog = new ErrorDialog();
            dialog.show(getSupportFragmentManager(),"ErrorDialog");
        }
    }

    @OnClick({R.id.iv_ticket_back, R.id.btn_buy, R.id.btn_buy1, R.id.btn_buy2, R.id.btn_buying})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_ticket_back:
                finish();
                overridePendingTransition(0, R.anim.back_out);
                break;
            case R.id.btn_buy:
                Toast.makeText(this, "只能查看，不能购买", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_buy1:
                Toast.makeText(this, "只能查看，不能购买", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_buy2:
                Toast.makeText(this, "只能查看，不能购买", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_buying:
                Intent intent = new Intent(this, WebActivity.class);
                startActivity(intent);
                break;
        }
    }
}
