package com.weiruanit.lifepro.module.robot.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.weiruanit.lifepro.R;
import com.weiruanit.lifepro.module.robot.bean.Message;

import java.util.ArrayList;

/**
 * 会话信息
 * Created by Administrator on 2016/11/14 0014.
 */

public class MessageDeatilAdapter extends RecyclerView.Adapter<MessageDeatilAdapter
        .BaseViewHolder> {

    ArrayList<Message> messages;

    OnItemVIewListener mOnItemVIewListener;

    public MessageDeatilAdapter(ArrayList<Message> messages) {

        this.messages = messages;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == Message.TYPE_SEND) {
            View view = View.inflate(parent.getContext(), R.layout.item_person_message, null);
            RightViewHolder rightViewHolder = new RightViewHolder(view);
            return rightViewHolder;
        } else {
            View view = View.inflate(parent.getContext(), R.layout.item_robot_message, null);
            LeftViewHolder leftViewHolder = new LeftViewHolder(view);
            return leftViewHolder;
        }
    }

    @Override
    public void onBindViewHolder(final BaseViewHolder holder, final int position) {
        Message message = messages.get(position);
        if (message.getType() == Message.TYPE_SEND) {
            final RightViewHolder rightViewHolder = (RightViewHolder) holder;
            rightViewHolder.rightMsg.setText(message.getContent());

            if (mOnItemVIewListener != null) {
                ((RightViewHolder) holder).rightMsg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnItemVIewListener.onclicks(((RightViewHolder) holder).itemView,
                                position, Message.TYPE_SEND);
                    }
                });

                ((RightViewHolder) holder).rightMsg.setOnLongClickListener(new View
                        .OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        mOnItemVIewListener.onLongClick(((RightViewHolder) holder).rightMsg,
                                position, Message.TYPE_SEND);
                        return false;
                    }
                });
            }
        } else {
            final LeftViewHolder leftViewHolder = (LeftViewHolder) holder;
            leftViewHolder.leftMsg.setText(message.getContent());

            if (mOnItemVIewListener != null) {
                ((LeftViewHolder) holder).leftMsg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnItemVIewListener.onclicks(((LeftViewHolder) holder).itemView,
                                position, Message.TYPE_RECEIVE);
                    }
                });

                ((LeftViewHolder) holder).leftMsg.setOnLongClickListener(new View
                        .OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {

                        mOnItemVIewListener.onLongClick(((LeftViewHolder) holder).leftMsg, holder
                                .getLayoutPosition(), Message.TYPE_RECEIVE);
                        return false;
                    }
                });
            }


        }
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        if (messages != null) {
            return messages.size();
        }
        return 0;
    }

    @Override
    public int getItemViewType(int position) {

        return messages.get(position).getType();
    }


    public class RightViewHolder extends BaseViewHolder {
        TextView rightMsg;

        public RightViewHolder(View itemView) {
            super(itemView);
            rightMsg = (TextView) itemView.findViewById(R.id.tv_right_content);
        }
    }

    public class LeftViewHolder extends BaseViewHolder {
        TextView leftMsg;

        public LeftViewHolder(View itemView) {
            super(itemView);
            leftMsg = (TextView) itemView.findViewById(R.id.tv_left_content);
        }
    }


    class BaseViewHolder extends RecyclerView.ViewHolder {

        public BaseViewHolder(View itemView) {
            super(itemView);
        }
    }


    public interface OnItemVIewListener {
        void onclicks(View view, int position, int type);

        void onLongClick(View view, int position, int type);
    }

    public void setmOnItemVIewListener(OnItemVIewListener onItemVIewListener) {
        this.mOnItemVIewListener = onItemVIewListener;
    }


}

