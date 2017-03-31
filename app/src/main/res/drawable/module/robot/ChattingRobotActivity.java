package com.weiruanit.lifepro.module.robot;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.ClipboardManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.SynthesizerListener;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;
import com.iflytek.sunflower.FlowerCollector;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.weiruanit.lifepro.R;
import com.weiruanit.lifepro.base.BaseActivity;
import com.weiruanit.lifepro.module.robot.adapter.MessageDeatilAdapter;
import com.weiruanit.lifepro.module.robot.bean.Message;
import com.weiruanit.lifepro.module.robot.bean.RobotListResponse;
import com.weiruanit.lifepro.module.robot.xunfei.ApkInstaller;
import com.weiruanit.lifepro.module.robot.xunfei.IatSettings;
import com.weiruanit.lifepro.module.robot.xunfei.JsonParser;
import com.weiruanit.lifepro.module.robot.xunfei.TtsSettings;
import com.weiruanit.lifepro.utils.UrlUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

public class ChattingRobotActivity extends BaseActivity {
    //==============语音合成=====================
    private static String TAG = ChattingRobotActivity.class.getSimpleName();
    // 语音合成对象
    private SpeechSynthesizer mTts;
    // 默认发音人
    private String voicer = "xiaoyan";

    private String[] mCloudVoicersEntries;
    private String[] mCloudVoicersValue;
    // 缓冲进度
    private int mPercentForBuffering = 0;
    // 播放进度
    private int mPercentForPlaying = 0;
    // 云端/本地单选按钮
    private RadioGroup mRadioGroup;
    // 引擎类型
    private String mEngineType = SpeechConstant.TYPE_CLOUD;
    // 语记安装助手类
    ApkInstaller mInstaller;

    private Toast mToast;
    private SharedPreferences mSharedPreferences;

    //=============语音听写=================================
//    private static String TAG1 = ChattingRobotActivity.class.getSimpleName();
    // 语音听写对象
    private SpeechRecognizer mIat;
    // 语音听写UI
    private RecognizerDialog mIatDialog;
    // 用HashMap存储听写结果
    private HashMap<String, String> mIatResults = new LinkedHashMap<String, String>();

    //--------------------其它按钮-------------------------------------
    @BindView(R.id.et_msg)
    EditText etMsg;

    @BindView(R.id.iv_send)
    Button ivSend;
    @BindView(R.id.rel_robot)
    RecyclerView relRobot;
    @BindView(R.id.iv_robot_back)
    ImageView ivRobotBack;
    RelativeLayout rlBottom;
    @BindView(R.id.iv_mac)
    ImageView ivMac;

    private String sendMsg;
    MessageDeatilAdapter mMessageDeatilAdapter;
    ArrayList<Message> messages = new ArrayList<>();//聊天信息列表
    private Message message;

    private InputMethodManager imm;
    private boolean isVoice;
    private boolean isrefuseBoolean;

    @Override
    protected int getLayoutId() {
//        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager
// .LayoutParams.FLAG_FULLSCREEN);       //设置全屏
//        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        return R.layout.activity_chatting_robot;
    }

    @Override
    public void initData() {
        ButterKnife.bind(this);
        // 初始化合成对象
        mTts = SpeechSynthesizer.createSynthesizer(ChattingRobotActivity.this, mTtsInitListener);
        // 云端发音人名称列表
        mCloudVoicersEntries = getResources().getStringArray(R.array.voicer_cloud_entries);
        mCloudVoicersValue = getResources().getStringArray(R.array.voicer_cloud_values);

        mSharedPreferences = getSharedPreferences(TtsSettings.PREFER_NAME, MODE_PRIVATE);
        mToast = Toast.makeText(this, "", Toast.LENGTH_SHORT);
        // 设置参数
        setParam();
        mInstaller = new ApkInstaller(ChattingRobotActivity.this);

        //--------听写-----------
        // 初始化识别无UI识别对象
        // 使用SpeechRecognizer对象，可根据回调消息自定义界面；
        mIat = SpeechRecognizer.createRecognizer(ChattingRobotActivity.this, mInitListener);

        // 初始化听写Dialog，如果只使用有UI听写功能，无需创建SpeechRecognizer
        // 使用UI听写功能，请根据sdk文件目录下的notice.txt,放置布局文件和图片资源
        mIatDialog = new RecognizerDialog(ChattingRobotActivity.this, mInitListener);

        mSharedPreferences = getSharedPreferences(IatSettings.PREFER_NAME,
                Activity.MODE_PRIVATE);
        mToast = Toast.makeText(this, "", Toast.LENGTH_SHORT);
        mInstaller = new ApkInstaller(ChattingRobotActivity.this);
    }

    @Override
    protected void getData() {
        //合成  本地
        mEngineType = SpeechConstant.TYPE_LOCAL;//默认本地
//        mEngineType = SpeechConstant.TYPE_CLOUD;

        SharedPreferences isrefuse = getSharedPreferences("isrefuse",
                Context.MODE_PRIVATE);
        isrefuseBoolean = isrefuse.getBoolean("isRefuse", false);
        /**
         * 选择本地合成
         * 判断是否安装语记,未安装则跳转到提示安装页面
         */
        if (!SpeechUtility.getUtility().checkServiceInstalled()) {

            if (!isrefuseBoolean) {
                mInstaller.install();
            }
        }

        relRobot.setLayoutManager(new LinearLayoutManager(this));
        mMessageDeatilAdapter = new MessageDeatilAdapter(messages);
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
            public void onclicks(final View view, int position, int type) {
//                Toast.makeText(ChattingRobotActivity.this, "点击了", Toast.LENGTH_SHORT).show();
                if(Message.TYPE_SEND==type){
                    TextView tvContent= (TextView) view.findViewById(R.id.tv_right_content);
                    UrlUtils.handleText(tvContent,sendMsg);
                }
            }

            @Override
            public void onLongClick(View view, int position, int type) {
//                Toast.makeText(ChattingRobotActivity.this, "长按了", Toast.LENGTH_SHORT).show();
                createDialog(view, position, type);
            }
        });
//        etMsg.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                rlBottom.setVisibility(View.GONE);
//                isBottomShow = false;
//            }
//        });

            etMsg.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (!TextUtils.isEmpty(s) && isVoice) {
                        sendMsg = etMsg.getText().toString().trim();
                        etMsg.setText("");
                        sendMessge(sendMsg);
                        isVoice = false;
                    }
                }
            });

    }

    public void createDialog(final View view, int position, final int type) {
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
//                        Toast.makeText(ChattingRobotActivity.this, txt, Toast.LENGTH_SHORT).show();
                    } else {
                        TextView rightContent = (TextView) view.findViewById(R.id.tv_right_content);
                        String txt = rightContent.getText().toString();
                        cbm.setText(txt);
                        cbm.getText();
//                        Toast.makeText(ChattingRobotActivity.this, txt, Toast.LENGTH_SHORT).show();
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

    private boolean isBottomShow = false;

    @OnClick({R.id.iv_send, R.id.iv_robot_back,  R.id.iv_mac})
    public void onClick(final View view) {
        switch (view.getId()) {
//            case R.id.iv_show://显示底部的布局
//                if (isBottomShow) {
//                    rlBottom.setVisibility(View.GONE);
//                    isBottomShow = false;
//                } else {
//                    hintKbTwo();
//                    rlBottom.setVisibility(View.VISIBLE);
//                    isBottomShow = true;
//                }
//                break;

            case R.id.iv_robot_back:
                finish();
                overridePendingTransition(0, R.anim.back_out);
                break;

            case R.id.iv_mac://语音听写
                // 开始听写
                // 如何判断一次听写结束：OnResult isLast=true 或者 onError
                // 移动数据分析，收集开始听写事件
                mEngineType = SpeechConstant.TYPE_CLOUD;
                FlowerCollector.onEvent(ChattingRobotActivity.this, "iat_recognize");

                etMsg.setText(null);// 清空显示内容
                mIatResults.clear();
                // 设置参数
                setParamListen();
                boolean isShowDialog = mSharedPreferences.getBoolean(
                        getString(R.string.pref_key_iat_show), true);
                if (isShowDialog) {
                    // 显示听写对话框
                    mIatDialog.setListener(mRecognizerDialogListener);
                    mIatDialog.show();
                    showTip(getString(R.string.text_begin));
                } else {
                    // 不显示听写对话框
                    ret = mIat.startListening(mRecognizerListener);
                    if (ret != ErrorCode.SUCCESS) {
                        showTip("听写失败,错误码：" + ret);
                    } else {
                        showTip(getString(R.string.text_begin));
                    }
                }
                isVoice = true;
                etMsg.setText(null);
                break;

            case R.id.iv_send:
                sendMsg = etMsg.getText().toString().trim();
                etMsg.setText("");
//                if (!sendMsg.isEmpty()) {
//                    message = new Message(sendMsg, Message.TYPE_SEND);
//                    messages.add(message);
//                    mMessageDeatilAdapter.notifyDataSetChanged();
//                etMsg.setText("");
                sendMessge(sendMsg);
//                }
        }
    }

    public void sendMessge(String sendMsg) {
        etMsg.setText(null);
        if (!sendMsg.isEmpty()) {
            message = new Message(sendMsg, Message.TYPE_SEND);
            messages.add(message);
            etMsg.setText(null);
            mMessageDeatilAdapter.notifyDataSetChanged();
            etMsg.setText(null);
        }
//        etMsg.setText("");
        OkGo.post(RobotApi.INDEX)
                .tag(this)
                .params("key", "39c68595eb6be3e5799e7b6e14b5febc")
                .params("info", sendMsg)
                .params("dataType", "json")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        etMsg.setText(null);
                        Gson gson = new Gson();
                        RobotListResponse robotListResponse = gson.fromJson(s,
                                RobotListResponse.class);
                        if (robotListResponse != null && robotListResponse.getResult() !=
                                null) {
                            String msg = robotListResponse.getResult().getText();
                            heCheng(msg);
                            Message message1 = new Message(msg, Message.TYPE_RECEIVE);
                            messages.add(message1);
                            mMessageDeatilAdapter.notifyDataSetChanged();
                            relRobot.scrollToPosition(mMessageDeatilAdapter.getItemCount
                                    () - 1);//自动划至底部
                        }
                    }
                });
    }
    //    =====================合成================================

    /**
     * 初始化监听。
     */
    private InitListener mTtsInitListener = new InitListener() {
        @Override
        public void onInit(int code) {
            Log.d(TAG, "InitListener init() code = " + code);
            if (code != ErrorCode.SUCCESS) {
                showTip("初始化失败,错误码：" + code);
            } else {
                // 初始化成功，之后可以调用startSpeaking方法
                // 注：有的开发者在onCreate方法中创建完合成对象之后马上就调用startSpeaking进行合成，
                // 正确的做法是将onCreate中的startSpeaking调用移至这里
            }
        }
    };
    /**
     * 合成回调监听。
     */
    private SynthesizerListener mTtsListener = new SynthesizerListener() {

        @Override
        public void onSpeakBegin() {
//            showTip("开始播放");
        }

        @Override
        public void onSpeakPaused() {
//            showTip("暂停播放");
        }

        @Override
        public void onSpeakResumed() {
//            showTip("继续播放");
        }

        @Override
        public void onBufferProgress(int percent, int beginPos, int endPos,
                                     String info) {
            // 合成进度
            mPercentForBuffering = percent;
//            showTip(String.format(getString(R.string.tts_toast_format),
//                    mPercentForBuffering, mPercentForPlaying));
        }

        @Override
        public void onSpeakProgress(int percent, int beginPos, int endPos) {
            // 播放进度
            mPercentForPlaying = percent;
            etMsg.setText(null);
//            showTip(String.format(getString(R.string.tts_toast_format),
//                    mPercentForBuffering, mPercentForPlaying));
        }

        @Override
        public void onCompleted(SpeechError error) {
            if (error == null) {
//                showTip("播放完成");
            } else if (error != null) {
                showTip(error.getPlainDescription(true));
            }
        }

        @Override
        public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
        }
    };

    private void showTip(final String str) {
        mToast.setText(str);
        mToast.show();
    }

    /**
     * 参数设置
     *
     * @param
     * @return
     */
    private void setParam() {
        // 清空参数
        mTts.setParameter(SpeechConstant.PARAMS, null);
        // 根据合成引擎设置相应参数
        if (mEngineType.equals(SpeechConstant.TYPE_CLOUD)) {
            mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
            // 设置在线合成发音人
            mTts.setParameter(SpeechConstant.VOICE_NAME, voicer);
            //设置合成语速
            mTts.setParameter(SpeechConstant.SPEED, mSharedPreferences.getString
                    ("speed_preference", "50"));
            //设置合成音调
            mTts.setParameter(SpeechConstant.PITCH, mSharedPreferences.getString
                    ("pitch_preference", "50"));
            //设置合成音量
            mTts.setParameter(SpeechConstant.VOLUME, mSharedPreferences.getString
                    ("volume_preference", "50"));
        } else {
            mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_LOCAL);
            // 设置本地合成发音人 voicer为空，默认通过语记界面指定发音人。
            mTts.setParameter(SpeechConstant.VOICE_NAME, "");
        }
        //设置播放器音频流类型
        mTts.setParameter(SpeechConstant.STREAM_TYPE, mSharedPreferences.getString
                ("stream_preference", "3"));
        // 设置播放合成音频打断音乐播放，默认为true
        mTts.setParameter(SpeechConstant.KEY_REQUEST_FOCUS, "true");
        // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
        // 注：AUDIO_FORMAT参数语记需要更新版本才能生效
        mTts.setParameter(SpeechConstant.PARAMS, "tts_audio_path=" + Environment
                .getExternalStorageDirectory() + "/test.pcm");
    }

    /**
     * 合成
     */
    public void heCheng(String text) {
        // 移动数据分析，收集开始合成事件
        FlowerCollector.onEvent(ChattingRobotActivity.this, "tts_play");
        // 设置参数
        setParam();
        int code = mTts.startSpeaking(text, mTtsListener);

        if (code != ErrorCode.SUCCESS) {
            if (code == ErrorCode.ERROR_COMPONENT_NOT_INSTALLED) {
                //未安装则跳转到提示安装页面
                if (!isrefuseBoolean) {
                    mInstaller.install();
                }
            } else {
                showTip("语音合成失败,错误码: " + code);
            }
        }
    }


    //====================听写=====================
    /**
     * 初始化监听器。
     */
    private InitListener mInitListener = new InitListener() {

        @Override
        public void onInit(int code) {
            Log.d(TAG, "SpeechRecognizer init() code = " + code);
            if (code != ErrorCode.SUCCESS) {
                showTipListen("初始化失败，错误码：" + code);
            }
        }
    };

    int ret = 0; // 函数调用返回值
    /**
     * 听写UI监听器
     */
    private RecognizerDialogListener mRecognizerDialogListener = new RecognizerDialogListener() {
        public void onResult(RecognizerResult results, boolean isLast) {
            printResult(results);
        }

        /**
         * 识别回调错误.
         */
        public void onError(SpeechError error) {
            showTipListen(error.getPlainDescription(true));
        }

    };

    private void showTipListen(final String str) {
        mToast.setText(str);
        mToast.show();
    }

    /**
     * 听写监听器。
     */
    private RecognizerListener mRecognizerListener = new RecognizerListener() {

        @Override
        public void onBeginOfSpeech() {
            // 此回调表示：sdk内部录音机已经准备好了，用户可以开始语音输入
            showTip("开始说话");
        }

        @Override
        public void onError(SpeechError error) {
            // Tips：
            // 错误码：10118(您没有说话)，可能是录音机权限被禁，需要提示用户打开应用的录音权限。
            // 如果使用本地功能（语记）需要提示用户开启语记的录音权限。
            showTip(error.getPlainDescription(true));
        }

        @Override
        public void onEndOfSpeech() {
            // 此回调表示：检测到了语音的尾端点，已经进入识别过程，不再接受语音输入
            showTip("结束说话");
        }

        @Override
        public void onResult(RecognizerResult results, boolean isLast) {
            Log.d(TAG, results.getResultString());
            printResult(results);
            etMsg.setText(null);
            if (isLast) {
                // TODO 最后的结果
            }
        }

        @Override
        public void onVolumeChanged(int volume, byte[] data) {
            showTip("当前正在说话，音量大小：" + volume);
            Log.d(TAG, "返回音频数据：" + data.length);
        }

        @Override
        public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
            // 以下代码用于获取与云端的会话id，当业务出错时将会话id提供给技术支持人员，可用于查询会话日志，定位出错原因
            // 若使用本地能力，会话id为null
            //	if (SpeechEvent.EVENT_SESSION_ID == eventType) {
            //		String sid = obj.getString(SpeechEvent.KEY_EVENT_SESSION_ID);
            //		Log.d(TAG, "session id =" + sid);
            //	}
        }
    };

    /**
     * 参数设置
     *
     * @param
     * @return
     */
    public void setParamListen() {
        // 清空参数
        mIat.setParameter(SpeechConstant.PARAMS, null);

        // 设置听写引擎
        mIat.setParameter(SpeechConstant.ENGINE_TYPE, mEngineType);
        // 设置返回结果格式
        mIat.setParameter(SpeechConstant.RESULT_TYPE, "json");

        String lag = mSharedPreferences.getString("iat_language_preference",
                "mandarin");
        if (lag.equals("en_us")) {
            // 设置语言
            mIat.setParameter(SpeechConstant.LANGUAGE, "en_us");
        } else {
            // 设置语言
            mIat.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
            // 设置语言区域
            mIat.setParameter(SpeechConstant.ACCENT, lag);
        }

        // 设置语音前端点:静音超时时间，即用户多长时间不说话则当做超时处理
        mIat.setParameter(SpeechConstant.VAD_BOS, mSharedPreferences.getString
                ("iat_vadbos_preference", "4000"));

        // 设置语音后端点:后端点静音检测时间，即用户停止说话多长时间内即认为不再输入， 自动停止录音
        mIat.setParameter(SpeechConstant.VAD_EOS, mSharedPreferences.getString
                ("iat_vadeos_preference", "1000"));

        // 设置标点符号,设置为"0"返回结果无标点,设置为"1"返回结果有标点
        mIat.setParameter(SpeechConstant.ASR_PTT, mSharedPreferences.getString
                ("iat_punc_preference", "1"));

        // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
        // 注：AUDIO_FORMAT参数语记需要更新版本才能生效
        mIat.setParameter(SpeechConstant.AUDIO_FORMAT, "wav");
        mIat.setParameter(SpeechConstant.ASR_AUDIO_PATH, Environment.getExternalStorageDirectory
                () + "/msc/iat.wav");
    }

    private void printResult(RecognizerResult results) {
        String text = JsonParser.parseIatResult(results.getResultString());

        String sn = null;
        // 读取json结果中的sn字段
        try {
            JSONObject resultJson = new JSONObject(results.getResultString());
            sn = resultJson.optString("sn");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        etMsg.setText(null);
        mIatResults.put(sn, text);

        StringBuffer resultBuffer = new StringBuffer();
        for (String key : mIatResults.keySet()) {
            resultBuffer.append(mIatResults.get(key));
        }

        etMsg.setText(resultBuffer.toString());
        etMsg.setSelection(etMsg.length());
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mTts.stopSpeaking();
        // 退出时释放连接
        mTts.destroy();

        // 退出时释放连接
        mIat.cancel();
        mIat.destroy();
    }

    @Override
    protected void onResume() {
        //移动数据统计分析
        FlowerCollector.onResume(ChattingRobotActivity.this);
        FlowerCollector.onPageStart(TAG);
        super.onResume();
    }

    @Override
    protected void onPause() {
        //移动数据统计分析
        FlowerCollector.onPageEnd(TAG);
        FlowerCollector.onPause(ChattingRobotActivity.this);

        super.onPause();
    }
}











