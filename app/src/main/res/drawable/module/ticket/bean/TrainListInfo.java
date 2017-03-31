package com.weiruanit.lifepro.module.ticket.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/11/15 0015.
 */

public class TrainListInfo {

    private boolean ret;
    private DataBean data;

    public boolean isRet() {
        return ret;
    }

    public void setRet(boolean ret) {
        this.ret = ret;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * trainType : 动车组
         * trainNo : D5666
         * from : 安庆
         * to : 合肥南
         * startTime : 20:49
         * endTime : 22:28
         * duration : 1时39分
         * seatInfos : [{"seat":"无座","seatPrice":"86.5","remainNum":112},{"seat":"二等座",
         * "seatPrice":"86.5","remainNum":548},{"seat":"一等座","seatPrice":"138","remainNum":50}]
         */

        private List<TrainListBean> trainList;

        public List<TrainListBean> getTrainList() {
            return trainList;
        }

        public void setTrainList(List<TrainListBean> trainList) {
            this.trainList = trainList;
        }

        public static class TrainListBean implements Parcelable {
            private String trainType;
            private String trainNo;
            private String from;
            private String to;
            private String startTime;
            private String endTime;
            private String duration;
            /**
             * seat : 无座
             * seatPrice : 86.5
             * remainNum : 112
             */

            private List<SeatInfosBean> seatInfos;

            protected TrainListBean(Parcel in) {
                trainType = in.readString();
                trainNo = in.readString();
                from = in.readString();
                to = in.readString();
                startTime = in.readString();
                endTime = in.readString();
                duration = in.readString();
            }

            public static final Creator<TrainListBean> CREATOR = new Creator<TrainListBean>() {
                @Override
                public TrainListBean createFromParcel(Parcel in) {
                    return new TrainListBean(in);
                }

                @Override
                public TrainListBean[] newArray(int size) {
                    return new TrainListBean[size];
                }
            };

            public String getTrainType() {
                return trainType;
            }

            public void setTrainType(String trainType) {
                this.trainType = trainType;
            }

            public String getTrainNo() {
                return trainNo;
            }

            public void setTrainNo(String trainNo) {
                this.trainNo = trainNo;
            }

            public String getFrom() {
                return from;
            }

            public void setFrom(String from) {
                this.from = from;
            }

            public String getTo() {
                return to;
            }

            public void setTo(String to) {
                this.to = to;
            }

            public String getStartTime() {
                return startTime;
            }

            public void setStartTime(String startTime) {
                this.startTime = startTime;
            }

            public String getEndTime() {
                return endTime;
            }

            public void setEndTime(String endTime) {
                this.endTime = endTime;
            }

            public String getDuration() {
                return duration;
            }

            public void setDuration(String duration) {
                this.duration = duration;
            }

            public List<SeatInfosBean> getSeatInfos() {
                return seatInfos;
            }

            public void setSeatInfos(List<SeatInfosBean> seatInfos) {
                this.seatInfos = seatInfos;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(trainType);
                dest.writeString(trainNo);
                dest.writeString(from);
                dest.writeString(to);
                dest.writeString(startTime);
                dest.writeString(endTime);
                dest.writeString(duration);
            }

            public static class SeatInfosBean implements Serializable {
                private String seat;
                private String seatPrice;
                private int remainNum;

                public String getSeat() {
                    return seat;
                }

                public void setSeat(String seat) {
                    this.seat = seat;
                }

                public String getSeatPrice() {
                    return seatPrice;
                }

                public void setSeatPrice(String seatPrice) {
                    this.seatPrice = seatPrice;
                }

                public int getRemainNum() {
                    return remainNum;
                }

                public void setRemainNum(int remainNum) {
                    this.remainNum = remainNum;
                }


            }
        }
    }
}
