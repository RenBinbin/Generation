package com.weiruanit.lifepro.module.news.adapter;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.weiruanit.lifepro.R;
import com.weiruanit.lifepro.module.news.bean.SportsNewsBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/11/14 0014.
 */

public class SportsNewsListItemAdapter extends RecyclerView.Adapter<SportsNewsListItemAdapter.BaseViewHolder> {

    private List<SportsNewsBean.NewslistBean> mNewslistBean = new ArrayList<>();
    private OnItemClickLitener mOnItemClickLitener;

    public SportsNewsListItemAdapter(List<SportsNewsBean.NewslistBean> mNewslistBean) {
        this.mNewslistBean = mNewslistBean;
    }

    @Override
    public NewsItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news,parent,false);
        NewsItemViewHolder newsItemViewHolder = new NewsItemViewHolder(view);
        return newsItemViewHolder;
    }

    @Override
    public void onBindViewHolder(final BaseViewHolder holder, int position) {
        NewsItemViewHolder newsItemViewHolder = (NewsItemViewHolder)holder;
        SportsNewsBean.NewslistBean newslistBean = mNewslistBean.get(position);
        newsItemViewHolder.simpleDraweeView.setImageURI(Uri.parse(newslistBean.getPicUrl()));
        newsItemViewHolder.textView.setText(newslistBean.getTitle());
        newsItemViewHolder.newDescription.setText(newslistBean.getDescription());

        if(mOnItemClickLitener != null){
            ((NewsItemViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemClick(((NewsItemViewHolder) holder).itemView,pos);
                }
            });

            ((NewsItemViewHolder) holder).itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemLongClick(((NewsItemViewHolder) holder).itemView,pos);
                    return false;
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return mNewslistBean.size();
    }

    /**
     * 普通ViewHolder
     */
    class NewsItemViewHolder extends BaseViewHolder {
        @BindView(R.id.news_simpledraweeview)
        SimpleDraweeView simpleDraweeView;//绑定图片控件
        @BindView(R.id.news_title)
        TextView textView;
        @BindView(R.id.new_description)
        TextView newDescription;
        public NewsItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    /**
     * BaseViewHolder
     */
    class BaseViewHolder extends RecyclerView.ViewHolder {
        public BaseViewHolder(View itemView) {
            super(itemView);
        }
    }

    /**
     * 点击监听接口
     */
    public interface OnItemClickLitener{

        /**
         * 点击事件
         * @param view
         * @param position
         */
        void onItemClick(View view, int position);

        /**
         * 长按事件
         * @param view
         * @param position
         */
        void onItemLongClick(View view, int position);
    }

    /**
     * 设置点击监听
     * @param mOnItemClickLitener
     */
    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener)
    {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }
}
