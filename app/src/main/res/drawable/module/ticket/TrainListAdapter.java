package com.weiruanit.lifepro.module.ticket;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.weiruanit.lifepro.R;
import com.weiruanit.lifepro.module.ticket.bean.TrainListInfo;

import java.util.ArrayList;

/**
 * 列车列表adapter
 */

public class TrainListAdapter extends RecyclerView.Adapter<TrainListAdapter.TrainViewHolder> {

    private String data;
    private ArrayList<TrainListInfo.DataBean.TrainListBean> trainListInfos;

    public TrainListAdapter(ArrayList<TrainListInfo.DataBean.TrainListBean> trainListInfos,String data) {
        this.trainListInfos = trainListInfos;
        this.data = data;
    }

    @Override
    public TrainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trainlist_items,
                null);
        TrainViewHolder trainViewHolder = new TrainViewHolder(view,mTicketListener);
        return trainViewHolder;
    }

    @Override
    public void onBindViewHolder(TrainViewHolder holder, int position) {

        TrainListInfo.DataBean.TrainListBean trainListBean = trainListInfos.get(position);

        String time = data +" " +trainListBean.getStartTime();

//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//        String format = simpleDateFormat.format(time);
        holder.tvTrips.setText(trainListBean.getTrainNo());
        holder.tvFromTime.setText(trainListBean.getStartTime());
        holder.tvToTime.setText(trainListBean.getEndTime());
        holder.tvTotalTime.setText(trainListBean.getDuration());
        holder.tvFromStation.setText(trainListBean.getFrom());
        holder.tvToStation.setText(trainListBean.getTo());
        holder.tvMoney.setText(trainListBean.getSeatInfos().get(0).getSeatPrice());
        holder.tvSeat.setText(trainListBean.getSeatInfos().get(0).getSeat());

        int remainNum = trainListBean.getSeatInfos().get(0).getRemainNum();
        if (remainNum < 50 && remainNum > 0) {
            holder.tvTicks.setText("仅剩"+remainNum+"张");
        }else if (remainNum <= 0){
            holder.tvTicks.setText("无票");
        }
        else {
            holder.tvTicks.setText(trainListBean.getSeatInfos().get(0).getSeat()+remainNum+"张");
        }
    }

    @Override
    public int getItemCount() {
        if (trainListInfos.size() != 0) {
            return trainListInfos.size();
        }
        return 0;
    }

    class TrainViewHolder extends RecyclerView.ViewHolder {
        TextView tvTrips;//车次
        TextView tvFromTime;//出发时间
        TextView tvToTime;//到达时间
        TextView tvTotalTime;//历时
        TextView tvFromStation;//起始站
        TextView tvToStation;//终点站
        TextView tvMoney;//票价
        TextView tvSeat;//座位等级
        TextView tvTicks;//票数

        public TrainViewHolder(View itemView) {
            this(itemView,null);
        }
        public TrainViewHolder(final View itemView, final TicketListener mTicketListener) {
            super(itemView);
            tvTrips = (TextView) itemView.findViewById(R.id.tv_trips);
            tvFromTime = (TextView) itemView.findViewById(R.id.tv_from_time);
            tvToTime = (TextView) itemView.findViewById(R.id.tv_to_time);
            tvTotalTime = (TextView) itemView.findViewById(R.id.tv_total_time);
            tvFromStation = (TextView) itemView.findViewById(R.id.tv_from_station);
            tvToStation = (TextView) itemView.findViewById(R.id.tv_to_station);
            tvMoney = (TextView) itemView.findViewById(R.id.tv_money);
            tvSeat = (TextView) itemView.findViewById(R.id.tv_seat);
            tvTicks = (TextView) itemView.findViewById(R.id.tv_ticks);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mTicketListener.itemClick(itemView,getPosition());
                }
            });
        }
    }

    //供外部调用
    public interface TicketListener{
        void itemClick(View view,int position);
    }
    private TicketListener mTicketListener;
    public void setTicketListener(TicketListener ticketListener){
        this.mTicketListener = ticketListener;
    }
    //车次排序
    private void trainSort(){

    }




}
