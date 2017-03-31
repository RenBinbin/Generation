package com.bishe.renbinbin1.secret_master.module.memo;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bishe.renbinbin1.secret_master.uilts.CalendarUtils;
import com.bishe.renbinbin1.secret_master.uilts.EventUtils;
import com.bishe.renbinbin1.secret_master.R;
import com.bishe.renbinbin1.secret_master.base.BaseActivity;
import com.bishe.renbinbin1.secret_master.module.memo.bean.MemoBean;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MemoActivity extends BaseActivity {
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.tv_finsh)
    TextView tvFinsh;
    @BindView(R.id.ll)
    RelativeLayout ll;
    @BindView(R.id.et_msg)
    EditText etMsg;
    @BindView(R.id.iv_back)
    ImageView ivBack;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_memo;
    }

    @Override
    public void initData() {
        ButterKnife.bind(this);
        String forDateToString = CalendarUtils.forDateToString(System.currentTimeMillis());
        tvDate.setText(forDateToString);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //完成
        tvFinsh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataBaseMemo dataBaseMemo=new DataBaseMemo(MemoActivity.this);
                MemoBean memoBean=new MemoBean();
                String message=etMsg.getText().toString().trim();
                if(message.isEmpty()){
                    Toast.makeText(MemoActivity.this, "内容不为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    memoBean.setText(message);
                    memoBean.setTime(tvDate.getText().toString());
                    memoBean.setId(memoBean.getId());
                }

                long l=dataBaseMemo.insert(memoBean);
                if (l > 0) {
//                    Toast.makeText(MemoActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(MemoActivity.this, "添加失败", Toast.LENGTH_SHORT).show();
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
