package com.bishe.renbinbin1.secret_master.module.joke.adapter;

import com.bishe.renbinbin1.secret_master.R;
import com.bishe.renbinbin1.secret_master.module.joke.bean.JokeBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;



public class JokeAdapter extends BaseQuickAdapter<JokeBean.ResultBean.DataBean,
        BaseViewHolder> {

    public JokeAdapter() {
        super(R.layout.item_joke);
    }

    @Override
    protected void convert(BaseViewHolder helper, JokeBean.ResultBean.DataBean item) {
        helper.setText(R.id.tv_joke_content, item.getContent());
        helper.setText(R.id.tv_joke_date, item.getUpdatetime());
        helper.getConvertView().setOnClickListener(null);
    }

}
