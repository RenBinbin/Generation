package com.bishe.renbinbin1.secret_master.module.memo.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bishe.renbinbin1.secret_master.R;
import com.bishe.renbinbin1.secret_master.module.memo.bean.MemoBean;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by renbinbin1 on 2017/3/16.
 */

public class MemoAdapter extends RecyclerView.Adapter<MemoAdapter.BaseViewHolder> {

    private List<MemoBean> mMemoBean=new ArrayList<>();
    public MemoAdapter(List<MemoBean> memoBeen){
        this.mMemoBean=memoBeen;
    }
    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_memo,null);
        MemoViewHolder memoViewHolder=new MemoViewHolder(view,mRecyclerViewItemClick);
        return memoViewHolder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        if(mMemoBean!=null){
            MemoBean memoBean=mMemoBean.get(position);
            MemoViewHolder memoViewHolder= (MemoViewHolder) holder;
            memoViewHolder.tv_text.setText(memoBean.getText());
            memoViewHolder.tv_time.setText(memoBean.getTime());
        }
    }

    @Override
    public int getItemCount() {
        return mMemoBean.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public long getItemId(int position) {

        return super.getItemId(position);
    }

    class MemoViewHolder  extends MemoAdapter.BaseViewHolder{
        TextView tv_text;
        TextView tv_time;
        public MemoViewHolder(View itemView, RecyclerViewItemClick mRecyclerViewItemClick) {
            super(itemView, mRecyclerViewItemClick);
            tv_text= (TextView) itemView.findViewById(R.id.tv_text);
            tv_time= (TextView) itemView.findViewById(R.id.tv_time);
        }
    }

    //recyclerView的item点击事件
    public interface RecyclerViewItemClick{
        void onClick(View view,int position);
        void onLongClick(View view,int position);
    }
    public RecyclerViewItemClick mRecyclerViewItemClick;

    //对外暴露点击事件的方法
    public void setRecyclerViewItemClick(RecyclerViewItemClick mRecyclerViewItemClick) {
        this.mRecyclerViewItemClick = mRecyclerViewItemClick;
    }

    public class BaseViewHolder extends RecyclerView.ViewHolder {
        public BaseViewHolder(final View itemView, final MemoAdapter.RecyclerViewItemClick
                mRecyclerViewItemClick) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mRecyclerViewItemClick.onClick(itemView,getPosition());
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mRecyclerViewItemClick.onLongClick(itemView,getPosition());
                    return true;
                }
            });
        }
    }
}
