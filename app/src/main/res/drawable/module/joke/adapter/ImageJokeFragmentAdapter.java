package com.weiruanit.lifepro.module.joke.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.weiruanit.lifepro.R;
import com.weiruanit.lifepro.module.joke.bean.JokeBeanResponse;

import java.util.List;
import java.util.Random;

/**
 * Created by Administrator on 2016/11/14 0014.
 */

public class ImageJokeFragmentAdapter extends RecyclerView.Adapter<ImageJokeFragmentAdapter.MyViewHolder>  {
    private List<JokeBeanResponse.ShowapiResBodyBean.ContentlistBean> mJokeBeanResponseList;
    private Context context;
    public ImageJokeFragmentAdapter(List<JokeBeanResponse.ShowapiResBodyBean.ContentlistBean> jokeBeanResponseList,Context context) {
        this.mJokeBeanResponseList = jokeBeanResponseList;
        this.context = context;
    }

    public interface MyItemClickListener {
        public void onItemClick(View view,int postion);
    }
    private MyItemClickListener mListener;

    public void setmListener(MyItemClickListener mListener) {
        this.mListener = mListener;
    }

    //长按

    public interface OnItemLongClickListener{
        void onItemClick(View v,int position);
    }
    private OnItemLongClickListener onItemLongClickListener;
    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_joke_image,parent,false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        //随机数
        Random random =new Random();
        int x= random.nextInt(20);
        int y=170;
        if(x<6){
            y=170;
        }else if(x>=6&&x<=13)
        {
            y=220;
        }
        else {
            y=260;
        }
        //转换dp
        y= (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,y,context.getResources().getDisplayMetrics());
        //获取图片
        LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,y);

        holder.ivJoke.setLayoutParams(layoutParams);
        holder.ivJoke.setImageURI(Uri.parse(mJokeBeanResponseList.get(position).getImg()));
        holder.tvTitle.setText(mJokeBeanResponseList.get(position).getTitle());
        //2016-11-14 18:01:47.230
        String[] str = mJokeBeanResponseList.get(position).getCt().split(" ");
        String time = str[0];
        holder.tvData.setText(time);
//        holder.itemView.setAnimation(AnimationUtils.loadAnimation(context,R.anim.alpha_in));
        holder.itemView.setTag(mJokeBeanResponseList.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                holder.getLayoutPosition()  才是真是Position
                mListener.onItemClick(holder.itemView,holder.getLayoutPosition());
            }
        });
        holder.linearLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                onItemLongClickListener.onItemClick(v,holder.getLayoutPosition());
                return false;
            }
        });
    }
    @Override
    public int getItemCount() {
        return mJokeBeanResponseList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView ivJoke;
        TextView tvTitle,tvContent,tvData;
        LinearLayout linearLayout;
        public MyViewHolder(final View itemView) {
            super(itemView);
            ivJoke = (SimpleDraweeView) itemView.findViewById(R.id.iv_imageJoke);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title_imageJoke);
//            tvContent = (TextView) itemView.findViewById(R.id.tv_content_imageJoke);
            tvData = (TextView) itemView.findViewById(R.id.tv_data_imageJoke);
              linearLayout   = (LinearLayout) itemView.findViewById(R.id.ll_item_joke_image);

        }
}
}
