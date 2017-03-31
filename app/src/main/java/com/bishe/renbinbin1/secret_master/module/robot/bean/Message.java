package com.bishe.renbinbin1.secret_master.module.robot.bean;

/**
 * Created by Administrator on 2016/11/14 0014.
 */

public class Message {
    public static final int TYPE_RECEIVE=0;
    public static final int TYPE_SEND=1;
    private int type;
    private String content;

    public Message(String content, int type) {
        this.content = content;
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
