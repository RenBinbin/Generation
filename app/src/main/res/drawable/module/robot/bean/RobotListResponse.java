package com.weiruanit.lifepro.module.robot.bean;

/**
 * Created by Administrator on 2016/11/14 0014.
 */

public class RobotListResponse {


    /**
     * reason : 成功的返回
     * result : {"code":100000,"text":"北京:11月14号 周一,-2-13° 11° 晴 无持续风微风;11月15号 周二,-1-10° 晴 无持续风微风;11月16号 周三,1-11° 霾 无持续风微风;11月17号 周四,5-10° 霾 无持续风微风;"}
     * error_code : 0
     */

    private String reason;
    /**
     * code : 100000
     * text : 北京:11月14号 周一,-2-13° 11° 晴 无持续风微风;11月15号 周二,-1-10° 晴 无持续风微风;11月16号 周三,1-11° 霾 无持续风微风;11月17号 周四,5-10° 霾 无持续风微风;
     */

    private ResultBean result;
    private int error_code;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public static class ResultBean {
        private int code;
        private String text;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }
}
