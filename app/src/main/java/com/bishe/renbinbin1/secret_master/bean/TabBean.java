package com.bishe.renbinbin1.secret_master.bean;

/**
 * 底部导航Tab实体Bean
 * Created by Administrator on 2016/10/12 0012.
 */
public class TabBean {
    private String title;
    private int icon;
    //0 所有都显示  1 显示图片  2 文字
    private int tabType;


    private int iconChooseResId;
    private int iconNormalResId;

    public int getIconChooseResId() {
        return iconChooseResId;
    }

    public void setIconChooseResId(int iconChooseResId) {
        this.iconChooseResId = iconChooseResId;
    }

    public int getIconNormalResId() {
        return iconNormalResId;
    }

    public void setIconNormalResId(int iconNormalResId) {
        this.iconNormalResId = iconNormalResId;
    }

    //正常的tab 下标
    private int index;


    public TabBean(String title, int tabType, int iconChooseResId,
                   int iconNormalResId) {
        this.title = title;
        this.tabType = tabType;
        this.iconChooseResId = iconChooseResId;
        this.iconNormalResId = iconNormalResId;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public TabBean(String title, int icon) {
        this.title = title;
        this.icon = icon;
    }

    public int getTabType() {
        return tabType;
    }

    public void setTabType(int tabType) {
        this.tabType = tabType;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
