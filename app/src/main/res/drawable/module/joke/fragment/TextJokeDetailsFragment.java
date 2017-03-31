package com.weiruanit.lifepro.module.joke.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;
import com.weiruanit.lifepro.R;
import com.weiruanit.lifepro.base.BaseFragment;
import com.weiruanit.lifepro.module.joke.CommentListAdapter;
import com.weiruanit.lifepro.module.joke.bean.JokeControl;
import com.weiruanit.lifepro.module.joke.bean.TextJokeBeanResponse;
import com.weiruanit.lifepro.module.joke.periscope.FavorLayout;
import com.weiruanit.lifepro.module.ticket.bean.MyAnimotion;
import com.weiruanit.lifepro.module.travel.bean.TravelResponce;
import com.weiruanit.lifepro.utils.DBUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/11/15 0015.
 */

public class TextJokeDetailsFragment extends BaseFragment {
    @BindView(R.id.tvTitle_text_joke_details)
    TextView tvTitleTextJokeDetails;
    @BindView(R.id.tvContent_text_joke_details)
    TextView tvContentTextJokeDetails;
    //    @BindView(R.id.like_joke_text_details)
//    ImageView likeJokeTextDetails;
//    @BindView(R.id.unlike_joke_text_details)
//    ImageView unlikeJokeTextDetails;
    @BindView(R.id.share_joke_text_details)
    ImageView shareJokeTextDetails;
    @BindView(R.id.collect_joke_text_details)
    ImageView collectJokeTextDetails;
    @BindView(R.id.joke_girl)
    ImageView jokeGirl;
    @BindView(R.id.joke_boy)
    ImageView jokeBoy;
    @BindView(R.id.favor_text)
    FavorLayout favorText;
    @BindView(R.id.svHeader_text_joke_details)
    SimpleDraweeView svHeaderTextJokeDetails;
    @BindView(R.id.tvName_text_joke_details)
    TextView tvNameTextJokeDetails;
    @BindView(R.id.lvComment)
    RecyclerView lvComment;
    @BindView(R.id.iv_nocomment)
    ImageView ivNocomment;
    @BindView(R.id.btn_send)
    Button btnSend;
    @BindView(R.id.comment_text_joke)
    EditText commentTextJoke;
    private String title;
    private int position;
    private ArrayList<TextJokeBeanResponse.ShowapiResBodyBean.ContentlistBean> contentlistBeanArrayList = new ArrayList<>();
    private ArrayList<Integer> isCollect = new ArrayList<>();
    private ArrayList<TravelResponce.DataBean.BooksBean> list = new ArrayList<>();
     ArrayList<String> commentList = new ArrayList<>();
    private CommentListAdapter commentListAdapter;
    private InputMethodManager imm;
    private String text;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_text_joke_datails;
    }

    @Override
    protected void initView(View view) {
        ButterKnife.bind(this, view);
        Bundle bundle = getArguments();
        position = bundle.getInt("id");
        contentlistBeanArrayList = bundle.getParcelableArrayList("contentlist");
        isCollect = bundle.getIntegerArrayList("iscollect");
        title = bundle.getString("title");
        text = bundle.getString("text");
        list = bundle.getParcelableArrayList("headerImageList");
        svHeaderTextJokeDetails.setImageURI(Uri.parse(list.get(position).getUserHeadImg()));
        tvNameTextJokeDetails.setText(list.get(position).getUserName());
        tvTitleTextJokeDetails.setText(title);
        tvContentTextJokeDetails.setText(Html.fromHtml(bundle.getString("text")));
        if (isCollect.get(position) == 1) {
            collectJokeTextDetails.setImageResource(R.mipmap.collection_selected);
        }
        if (position % 3 == 0) {
            jokeGirl.setVisibility(View.GONE);
        } else {
            jokeBoy.setVisibility(View.GONE);
        }

        commentListAdapter = new CommentListAdapter(commentList);
        lvComment.setLayoutManager(new LinearLayoutManager(getContext()));
        lvComment.setAdapter(commentListAdapter);
        lvComment.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
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
    }

    @Override
    protected void getData() {
    }



    @OnClick({R.id.collect_joke_text_details, R.id.share_joke_text_details,R.id.btn_send})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.collect_joke_text_details:
                MyAnimotion myAnimotion = new MyAnimotion(2, 0, 180, collectJokeTextDetails.getMeasuredWidth() / 2f,
                        collectJokeTextDetails.getMeasuredHeight() / 2f, 500f);
                myAnimotion.setDuration(600);//动画时间
                myAnimotion.setInterpolator(new LinearInterpolator());//加速度
                //动画结束后是否保持结束后的状态( false : 不保持  true:保持)
                myAnimotion.setFillAfter(false);
                collectJokeTextDetails.startAnimation(myAnimotion);//开始动画
                if (isCollect.get(position) == 0) {
                    for (int i = 0; i < 5; i++) {
                        favorText.addFavor();
                    }
                    Toast.makeText(getActivity(), "收藏成功", Toast.LENGTH_SHORT).show();
                    collectJokeTextDetails.setImageResource(R.mipmap.collection_selected);
                    DBUtils.SaveTextJoke(contentlistBeanArrayList.get(position));
//                    isCollect.remove(position);
                    isCollect.set(position, 1);
                    //EventBus传值
                    JokeControl control = new JokeControl(position, 1);
                    EventBus.getDefault().post(control);
                } else if (isCollect.get(position) == 1) {
                    Toast.makeText(getActivity(), "取消收藏", Toast.LENGTH_SHORT).show();
                    collectJokeTextDetails.setImageResource(R.mipmap.joke_collection_normal);
                    DBUtils.DeleteTextJoke(contentlistBeanArrayList.get(position));
//                    isCollect.remove(position);
                    isCollect.set(position, 0);

                    JokeControl control = new JokeControl(position, 0);
                    EventBus.getDefault().post(control);
                }
                break;
            case R.id.share_joke_text_details:
                new ShareAction(getActivity()).setDisplayList(SHARE_MEDIA.SINA, SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)
                        .addButton("umeng_sharebutton_custom", "umeng_sharebutton_custom", "info_icon_1", "info_icon_1")
                        .setShareboardclickCallback(new ShareBoardlistener() {
                            @Override
                            public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {
                                if (snsPlatform.mShowWord.equals("umeng_sharebutton_custom")) {
                                    Toast.makeText(getActivity(), "自定义按钮", Toast.LENGTH_LONG).show();
                                } else {
                                    new ShareAction(getActivity()).withText(text + "(每日博君一笑)。  --来自LifePro分享")
                                            .setPlatform(share_media)
                                            .setCallback(new UMShareListener() {
                                                @Override
                                                public void onResult(SHARE_MEDIA share_media) {
                                                    Toast.makeText(getActivity(), "分享成功", Toast.LENGTH_SHORT).show();
                                                }

                                                @Override
                                                public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                                                    Toast.makeText(getActivity(), "分享失败", Toast.LENGTH_SHORT).show();
                                                }

                                                @Override
                                                public void onCancel(SHARE_MEDIA share_media) {
                                                    Toast.makeText(getActivity(), "取消分享", Toast.LENGTH_SHORT).show();
                                                }
                                            })
                                            .share();

                                }
                            }
                        }).open();
                break;
            case R.id.btn_send:
            {
                String content=commentTextJoke.getText().toString().trim();
                if(content!=null&&!content.equals("")){
                    commentList.add(content);
                    commentListAdapter.notifyDataSetChanged();
                    ivNocomment.setVisibility(View.GONE);
                    commentTextJoke.setText("");
                    hintKbTwo();
                }else{
                    Toast.makeText(getContext(),"你好像没评论什么吧~~",Toast.LENGTH_SHORT).show();
                }
            }
                break;

        }
    }
    private void hintKbTwo() {
        imm = (InputMethodManager) getActivity().getSystemService(Context
                .INPUT_METHOD_SERVICE);
        if (imm.isActive() && getActivity().getCurrentFocus() != null) {
            if (getActivity().getCurrentFocus().getWindowToken() != null) {
                imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
        //搜索框失去焦点
        commentTextJoke.clearFocus();
        commentTextJoke.clearFocus();
    }
}
