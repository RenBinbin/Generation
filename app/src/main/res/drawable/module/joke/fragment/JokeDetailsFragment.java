package com.weiruanit.lifepro.module.joke.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.weiruanit.lifepro.module.joke.bean.ImageJokeControl;
import com.weiruanit.lifepro.module.joke.bean.JokeBeanResponse;
import com.weiruanit.lifepro.module.joke.periscope.FavorLayout;
import com.weiruanit.lifepro.module.ticket.bean.MyAnimotion;
import com.weiruanit.lifepro.utils.DBUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/11/15 0015.
 */

public class JokeDetailsFragment extends BaseFragment implements View.OnClickListener {
    @BindView(R.id.sdv_joke_details)
    SimpleDraweeView sdvJokeDetails;
    @BindView(R.id.tvTitle_joke_details)
    TextView tvTitleJokeDetails;
    @BindView(R.id.activity_joke_datails)
    LinearLayout activityJokeDatails;
    @BindView(R.id.favor_image)
    FavorLayout favorImage;
    private ImageView  ivCollect;
    private ImageView ivShare;
    private Uri uri;
    private int position;
    private ArrayList<JokeBeanResponse.ShowapiResBodyBean.ContentlistBean> mJokeBeanResponseList = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_joke_datails;
    }
    @Override
    protected void initView(View view) {
        ButterKnife.bind(this, view);

        Bundle bundle = getArguments();
            mJokeBeanResponseList = bundle.getParcelableArrayList("contentlist");
        position = bundle.getInt("id");
//            uri =Uri.parse(bundle.getString("uri")) ;
            uri =Uri.parse(mJokeBeanResponseList.get(position).getImg()) ;
         sdvJokeDetails.setImageURI(uri);
         tvTitleJokeDetails.setText(mJokeBeanResponseList.get(position).getTitle());
           ivShare= (ImageView) view.findViewById(R.id.share_joke_image);
         ivCollect= (ImageView) view.findViewById(R.id.collect_joke_image);
        ivShare.setOnClickListener(this);

        if (favourites.get(position)==0){
            ivCollect.setImageResource(R.mipmap.joke_collection_normal);

        }
        else {
            ivCollect.setImageResource(R.mipmap.collection_selected);
        }

         ivCollect.setOnClickListener(this);
    }
    @Override
    protected void getData() {

    }

    List<Integer> favourites ;
    public  void setFavourites(List<Integer> favourites){
        this.favourites = favourites;
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.collect_joke_image:
                MyAnimotion myAnimotion = new MyAnimotion(2, 0, 180, ivCollect.getMeasuredWidth() / 2f,
                        ivCollect.getMeasuredHeight() / 2f, 500f);
                myAnimotion.setDuration(600);//动画时间
                myAnimotion.setInterpolator(new LinearInterpolator());//加速度
                //动画结束后是否保持结束后的状态( false : 不保持  true:保持)
                myAnimotion.setFillAfter(false);
                ivCollect.startAnimation(myAnimotion);//开始动画



                    if (favourites.get(position) == 0) {
                        for (int i = 0; i < 5; i++) {
                            favorImage.addFavor();
                        }
                        Toast.makeText(getActivity(), "收藏成功", Toast.LENGTH_SHORT).show();
                        ivCollect.setImageResource(R.mipmap.collection_selected);
                        DBUtils.SaveImgJoke(mJokeBeanResponseList.get(position));
                        favourites.set(position, 1);
                        EventBus.getDefault().post(new ImageJokeControl(position, 1));
                    }else{


//
                         Toast.makeText(getActivity(), "取消收藏", Toast.LENGTH_SHORT).show();
                          ivCollect.setImageResource(R.mipmap.joke_collection_normal);
                          DBUtils.DeleteImgJoke(mJokeBeanResponseList.get(position));
                          favourites.set(position, 0);
                          EventBus.getDefault().post(new ImageJokeControl(position, 0));
                        }
                          break;
                    case R.id.share_joke_image:
//                        图片笑话分享
                        new ShareAction(getActivity()).setDisplayList(SHARE_MEDIA.SINA, SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)
                                .addButton("umeng_sharebutton_custom", "umeng_sharebutton_custom", "info_icon_1", "info_icon_1")
                                .setShareboardclickCallback(new ShareBoardlistener() {
                                    @Override
                                    public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {
                                        if (snsPlatform.mShowWord.equals("umeng_sharebutton_custom")) {
                                            Toast.makeText(getActivity(), "自定义按钮", Toast.LENGTH_LONG).show();
                                        } else {
                                            new ShareAction(getActivity()).withText(uri + "(每日博君一笑)  --来自LifePro分享")
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


                }
        }
}

