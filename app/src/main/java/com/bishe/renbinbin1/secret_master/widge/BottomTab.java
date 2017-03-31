package com.bishe.renbinbin1.secret_master.widge;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bishe.renbinbin1.secret_master.R;
import com.bishe.renbinbin1.secret_master.bean.TabBean;

/**
 * 底部导航【图标+文字】
 */
public class BottomTab extends LinearLayout{
    //文字
    TextView tvTitle;
    //图标
    ImageView ivIcon;
    TabBean tabBean;

    public BottomTab(Context context,TabBean tabBean) {
        super(context);
        this.tabBean=tabBean;
        setOrientation(HORIZONTAL);
        init();
    }
    //初始化控件，这里会创建两个控件【图标+文字】，并且用一个LinearLayout包裹起来
    private void init() {
        LinearLayout linearLayout = new LinearLayout(getContext());
        LayoutParams lllayoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        linearLayout.setLayoutParams(lllayoutParams);
        linearLayout.setOrientation(VERTICAL);
        linearLayout.setGravity(Gravity.CENTER);

        ivIcon = new ImageView(getContext());
        int size = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, getContext().getResources().getDisplayMetrics());
        if(tabBean.getTabType()==0||tabBean.getTabType()==1){
            //图标的布局参数
            if(tabBean.getTabType()==1){
                int imageSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, getContext().getResources().getDisplayMetrics());
                LayoutParams iconLayoutParams = new LayoutParams(imageSize, imageSize);
                ivIcon.setLayoutParams(iconLayoutParams);
                ivIcon.setScaleType(ImageView.ScaleType.CENTER);
                Selector selector = new Selector();
                Drawable normal = ContextCompat.getDrawable(getContext(),tabBean.getIconNormalResId());
                Drawable choose = ContextCompat.getDrawable(getContext(),tabBean.getIconChooseResId());
                ivIcon.setBackgroundDrawable(selector.newSelector(normal,choose));
            }
            else {
                LayoutParams iconLayoutParams = new LayoutParams(size, size);
                ivIcon.setLayoutParams(iconLayoutParams);
                ivIcon.setScaleType(ImageView.ScaleType.FIT_CENTER);
                ivIcon.setImageResource(tabBean.getIconNormalResId());
            }
            linearLayout.addView(ivIcon);
        }
        if(tabBean.getTabType()==0||tabBean.getTabType()==2){
            tvTitle = new TextView(getContext());
            tvTitle.setGravity(Gravity.CENTER);
            LayoutParams titlelayoutParams = new LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            tvTitle.setLayoutParams(titlelayoutParams);
            tvTitle.setText(tabBean.getTitle() == null ? "默认" : tabBean.getTitle());
            tvTitle.setTextColor(ContextCompat.getColor(getContext(), R.color.main_gray));

            //添加到最外层LinearLayout
            linearLayout.addView(tvTitle);
        }
        //将最外层LinearLayout 添加到当前的View里面
        addView(linearLayout);
    }
    public class Selector {
        public StateListDrawable newSelector(Drawable normal, Drawable choose) {
            StateListDrawable listDrawable = new StateListDrawable();
            listDrawable.addState(new int[]{android.R.attr.state_pressed, android.R.attr.state_enabled}, choose);
            listDrawable.addState(new int[]{android.R.attr.state_enabled}, normal);
            listDrawable.addState(new int[]{}, normal);
            return listDrawable;
        }
    }

    /**
     * 改变图片颜色，改变文字颜色
     *
     * @param selected true 选择  false 未选中
     */
    @Override
    public void setSelected(boolean selected) {
        if (selected) {
            if (null != tvTitle) {
                tvTitle.setTextColor(ContextCompat.getColor(getContext(), R.color.main_blue));
            }
            if (null != ivIcon) {
                ivIcon.setImageResource(tabBean.getIconChooseResId());
            }

        } else {
            if (null != tvTitle) {
                tvTitle.setTextColor(ContextCompat.getColor(getContext(), R.color.main_gray));
            }
            //必须配合setImageResource 才有效果
            //一般改变图标的方式是直接替换掉选择的图片
            // imageView.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.unselect));
            if (null != ivIcon) {
//                ivIcon.setColorFilter(ContextCompat.getColor(getContext(), R.color.unselect));
                ivIcon.setImageResource(tabBean.getIconNormalResId());

            }
        }
    }
    public BottomTab(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BottomTab(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
