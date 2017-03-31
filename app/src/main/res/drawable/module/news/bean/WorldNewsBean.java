package com.weiruanit.lifepro.module.news.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/11/15 0015.
 */

public class WorldNewsBean {

    /**
     * code : 200
     * msg : success
     * newslist : [{"ctime":"2016-11-15 07:54","title":"普京与特朗普电话会谈 称应联合打击国际恐怖主义","description":"搜狐国际","picUrl":"","url":"http://news.sohu.com/20161115/n473187628.shtml"},{"ctime":"2016-11-15 08:16","title":"朴槿惠亲信\u201c干政门\u201d持续发酵：独立检察将启动","description":"搜狐国际","picUrl":"","url":"http://news.sohu.com/20161115/n473187500.shtml"},{"ctime":"2016-11-15 08:19","title":"韩国总统朴槿惠何去何从？就看这一周","description":"搜狐国际","picUrl":"","url":"http://news.sohu.com/20161115/n473187327.shtml"},{"ctime":"2016-11-15 08:22","title":"韩朝野三党提升调查力度 朴槿惠是否违法将见分晓","description":"搜狐国际","picUrl":"","url":"http://news.sohu.com/20161115/n473192441.shtml"},{"ctime":"2016-11-15 08:29","title":"特朗普接受专访：现在我们要清理这个体制","description":"搜狐国际","picUrl":"","url":"http://news.sohu.com/20161115/n473188070.shtml"},{"ctime":"2016-11-15 08:33","title":"特朗普前妻想当驻捷克大使 其女任美驻日大使呼声高","description":"搜狐国际","picUrl":"http://photocdn.sohu.com/20161115/Img473187629_ss.jpg","url":"http://news.sohu.com/20161115/n473192450.shtml"},{"ctime":"2016-11-15 08:40","title":"两张钞票让印度慌了：穷人挤银行 富人囤iPhone","description":"搜狐国际","picUrl":"http://photocdn.sohu.com/20161115/Img473187501_ss.jpg","url":"http://news.sohu.com/20161115/n473189277.shtml"},{"ctime":"2016-11-15 08:46","title":"冰释前嫌？美媒：奥巴马呼吁\u201c给特朗普一次机会\u201d","description":"搜狐国际","picUrl":"http://photocdn.sohu.com/20161115/Img473187328_ss.jpeg","url":"http://news.sohu.com/20161115/n473189620.shtml"},{"ctime":"2016-11-15 08:53","title":"当上总统的富豪特朗普该如何处理自己的生意？","description":"搜狐国际","picUrl":"http://photocdn.sohu.com/20161115/Img473188071_ss.jpeg","url":"http://news.sohu.com/20161115/n473190168.shtml"},{"ctime":"2016-11-15 09:01","title":"韩最大在野党党首迫于压力取消单独会见朴槿惠计划","description":"搜狐国际","picUrl":"http://photocdn.sohu.com/20161115/Img473189278_ss.jpeg","url":"http://news.sohu.com/20161115/n473191129.shtml"}]
     */

    private int code;
    private String msg;
    private List<NewslistBean> newslist;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<NewslistBean> getNewslist() {
        return newslist;
    }

    public void setNewslist(List<NewslistBean> newslist) {
        this.newslist = newslist;
    }

    public static class NewslistBean {
        /**
         * ctime : 2016-11-15 07:54
         * title : 普京与特朗普电话会谈 称应联合打击国际恐怖主义
         * description : 搜狐国际
         * picUrl :
         * url : http://news.sohu.com/20161115/n473187628.shtml
         */

        private String ctime;
        private String title;
        private String description;
        private String picUrl;
        private String url;

        public String getCtime() {
            return ctime;
        }

        public void setCtime(String ctime) {
            this.ctime = ctime;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getPicUrl() {
            return picUrl;
        }

        public void setPicUrl(String picUrl) {
            this.picUrl = picUrl;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
