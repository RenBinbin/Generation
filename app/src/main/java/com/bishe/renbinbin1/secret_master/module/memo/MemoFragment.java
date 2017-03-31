package com.bishe.renbinbin1.secret_master.module.memo;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bishe.renbinbin1.secret_master.R;
import com.bishe.renbinbin1.secret_master.base.BaseFragment;
import com.bishe.renbinbin1.secret_master.module.memo.adapter.MemoAdapter;
import com.bishe.renbinbin1.secret_master.module.memo.bean.MemoBean;
import com.bishe.renbinbin1.secret_master.uilts.EventUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MemoFragment extends BaseFragment {

    @BindView(R.id.iv_add)
    ImageView ivAdd;
    @BindView(R.id.memo_rel)
    RecyclerView memoRel;

    List<MemoBean> mMemoBean=new ArrayList<>();
    private DataBaseMemo dataBaseMemo;
    private MemoAdapter memoAdapter;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_memo;
    }

    @Override
    protected void initView(View view) {
        ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        dataBaseMemo = new DataBaseMemo(getContext());
        ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), MemoActivity.class);
                startActivity(intent);
            }
        });
        memoRel.setLayoutManager(new LinearLayoutManager(getContext()));
        memoAdapter = new MemoAdapter(mMemoBean);
        memoAdapter.setRecyclerViewItemClick(new MemoAdapter.RecyclerViewItemClick() {
            @Override
            public void onClick(View view, int position) {
                Intent intent=new Intent(getActivity(),MemoDetailActivity.class);
                intent.putExtra("memo",mMemoBean.get(position));
                startActivity(intent);
            }
            @Override
            public void onLongClick(View view, final int position) {
                AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
                builder.setTitle("选项");
                builder.setItems(new String[]{"删除"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int id=mMemoBean.get(position).getId();
                        long l= dataBaseMemo.delete(id);
                        if(l>0){
                            EventBus.getDefault().post(new EventUtils());
//                            Toast.makeText(getContext(), "删除成功", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.create();
                builder.show();
            }
        });
        memoRel.setAdapter(memoAdapter);

        getDataBase();
        memoAdapter.notifyDataSetChanged();
    }

    private void getDataBase() {
        /**
         * 从数据库读取数据
         */
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<MemoBean> memoBeanList=dataBaseMemo.query();
                if(memoBeanList!=null){
                    for (MemoBean memoBean : memoBeanList){
                        mMemoBean.add(memoBean);
                    }
                    memoAdapter.notifyDataSetChanged();
                }
            }
        }).start();
    }

    /**
     * 刷新NoteBookFragment页面
     * @param event
     */
    @Subscribe
    public void onEventMainThread(EventUtils event) {
        ArrayList<MemoBean> list = dataBaseMemo.query();
        if (list != null){
            mMemoBean.clear();
            for (MemoBean memoBean : list){
                mMemoBean.add(memoBean);
            }
            memoAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void getData() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }
}
