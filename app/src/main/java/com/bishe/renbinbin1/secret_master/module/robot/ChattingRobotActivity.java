package com.bishe.renbinbin1.secret_master.module.robot;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.ClipboardManager;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bishe.renbinbin1.secret_master.R;
import com.bishe.renbinbin1.secret_master.base.BaseBackActivity;
import com.bishe.renbinbin1.secret_master.module.robot.adapter.MessageDeatilAdapter;
import com.bishe.renbinbin1.secret_master.module.robot.bean.Message;
import com.bishe.renbinbin1.secret_master.module.robot.bean.RobotListResponse;
import com.bishe.renbinbin1.secret_master.uilts.UrlUtils;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

public class ChattingRobotActivity extends BaseBackActivity {

    @BindView(R.id.iv_robot_back)
    ImageView ivRobotBack;
    @BindView(R.id.rel_robot)
    RecyclerView relRobot;
    @BindView(R.id.et_msg)
    EditText etMsg;
    @BindView(R.id.iv_send)
    Button ivSend;

    private String sendMsg;
    MessageDeatilAdapter mMessageDeatilAdapter;
    ArrayList<Message> messages = new ArrayList<>();//聊天信息列表
    private Message message;
    private InputMethodManager imm;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_chatting_robot;
    }

    @Override
    public void initData() {
        ButterKnife.bind(this);
        relRobot.setLayoutManager(new LinearLayoutManager(this));
        mMessageDeatilAdapter=new MessageDeatilAdapter(messages);
        relRobot.setAdapter(mMessageDeatilAdapter);
        relRobot.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                //监听滑动  隐藏软键盘
                hintKbTwo();
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });

        //recyclerview item的点击事件
        mMessageDeatilAdapter.setmOnItemVIewListener(new MessageDeatilAdapter.OnItemVIewListener() {
            @Override
            public void onclicks(View view, int position, int type) {
                if(Message.TYPE_SEND==type){
                    TextView tvContent= (TextView) view.findViewById(R.id.tv_right_content);
                    UrlUtils.handleText(tvContent,sendMsg);
                }
            }

            @Override
            public void onLongClick(View view, int position, int type) {
                createDialog(view, position, type);
            }
        });

    }

    private void sendMessge(String sendMsg) {
        etMsg.setText("");
        if (!sendMsg.isEmpty()) {
            message = new Message(sendMsg, Message.TYPE_SEND);
            messages.add(message);
            etMsg.setText("");
            mMessageDeatilAdapter.notifyDataSetChanged();
            etMsg.setText("");
            getData();
        }
    }

    private void createDialog(final View view, int position, final int type) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(ChattingRobotActivity.this);
        builder.setTitle("提示");
        builder.setIcon(R.mipmap.ic_launcher);
        String[] items = {"复制"};
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ClipboardManager cbm = null;
                //复制
                //拿到复制、粘贴管理器
                cbm = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                if (which == 0) {
                    if (Message.TYPE_RECEIVE == type) {
                        TextView tv_left_content = (TextView) view.findViewById(R.id
                                .tv_left_content);
                        String txt = tv_left_content.getText().toString();
                        cbm.setText(txt);
                        cbm.getText();
                    } else {
                        TextView rightContent = (TextView) view.findViewById(R.id.tv_right_content);
                        String txt = rightContent.getText().toString();
                        cbm.setText(txt);
                        cbm.getText();
                    }
                }
            }
        });
        builder.create();
        builder.show();
    }

    /**
     * 关闭软键盘
     */
    //此方法只是关闭软键盘
    private void hintKbTwo() {
        imm = (InputMethodManager) getSystemService(Context
                .INPUT_METHOD_SERVICE);
        if (imm.isActive() && getCurrentFocus() != null) {
            if (getCurrentFocus().getWindowToken() != null) {
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
        //搜索框失去焦点
        etMsg.clearFocus();
        etMsg.clearFocus();

    }

    @OnClick({R.id.iv_send, R.id.iv_robot_back})
    public void onClick(final View view) {
        switch (view.getId()) {

            case R.id.iv_robot_back:
                finish();
//                overridePendingTransition(0, R.anim.back_out);
                break;


            case R.id.iv_send:
                sendMsg = etMsg.getText().toString().trim();
                etMsg.setText("");
                sendMessge(sendMsg);
                break;
//                }
        }
    }

    @Override
    protected void getData() {
        OkGo.post("http://op.juhe.cn/robot/index")
                .tag(this)
                .params("key", "39c68595eb6be3e5799e7b6e14b5febc")
                .params("info", sendMsg)
                .params("dataType", "json")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        etMsg.setText("");
                        Gson gson = new Gson();
                        RobotListResponse robotListResponse = gson.fromJson(s,
                                RobotListResponse.class);
                        if (robotListResponse != null && robotListResponse.getResult() !=
                                null) {
                            String msg = robotListResponse.getResult().getText();
                            Message message1 = new Message(msg, Message.TYPE_RECEIVE);
                            messages.add(message1);
                            mMessageDeatilAdapter.notifyDataSetChanged();
                            relRobot.scrollToPosition(mMessageDeatilAdapter.getItemCount
                                    () - 1);//自动划至底部
                        }
                    }
                });
    }
}
