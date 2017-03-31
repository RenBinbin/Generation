package com.bishe.renbinbin1.secret_master.module.memo;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bishe.renbinbin1.secret_master.R;
import com.bishe.renbinbin1.secret_master.base.BaseActivity;
import com.bishe.renbinbin1.secret_master.module.memo.bean.MemoBean;
import com.bishe.renbinbin1.secret_master.uilts.EventUtils;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MemoDetailActivity extends BaseActivity {

    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.tv_finsh)
    TextView tvFinsh;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.et_msg)
    EditText etMsg;

    private MemoBean mMemoBean;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_memo_detail;
    }

    @Override
    public void initData() {
        ButterKnife.bind(this);
        mMemoBean=getIntent().getParcelableExtra("memo");
        tvDate.setText(mMemoBean.getTime());
        etMsg.setText(mMemoBean.getText());

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvFinsh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataBaseMemo dataBaseMemo=new DataBaseMemo(MemoDetailActivity.this);
                MemoBean memoBean=new MemoBean();
                memoBean.setText(etMsg.getText().toString());
                memoBean.setTime(tvDate.getText().toString());
                memoBean.setId(mMemoBean.getId());
                long l=dataBaseMemo.update(memoBean);
                if (l > 0) {
//                    Toast.makeText(MemoDetailActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
//                    Toast.makeText(MemoDetailActivity.this, "添加失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void getData() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().post(new EventUtils());
    }

}
