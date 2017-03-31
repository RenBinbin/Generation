package com.weiruanit.lifepro.module.travel.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Administrator on 2016/11/14 0014.
 */

public class TravelResponce  implements Parcelable {

    /**
     * ret : true
     * errcode : 0
     * errmsg : success
     * ver : 1
     * data : {"books":[{"bookUrl":"http://travel.qunar.com/youji/6627820?from=baidu_apistore",
     * "title":"夏至，厦门。","headImage":"https://img1.qunarzz.com/travel/d2/1611/55/c12655371036f4b5
     * .jpg","userName":"da_dan_doge","userHeadImg":"http://headshot.user.qunar
     * .com/headshotsById/182389367.png?s","startTime":"2015-06-20","routeDays":3,
     * "bookImgNum":119,"viewCount":4,"likeCount":0,"commentCount":0,
     * "text":"环岛路>五老峰>鼓浪屿>南普陀寺>芙蓉隧道>厦门大学>胡里山炮台>曾厝垵>中山路步行街>黄则和花生汤店(中山路总店)
     * >日光岩>龙头路>菽庄花园>钢琴博物馆>十二洞天>鹭江>郑成功纪念馆","elite":false},{"bookUrl":"http://travel.qunar
     * .com/youji/6627789?from=baidu_apistore","title":"江城上的咖啡香~2015暖冬武汉游",
     * "headImage":"http://img1.qunarzz.com/travel/d8/1611/70/29a980305d96e0b5.jpg",
     * "userName":"唉我去我鞋呢","userHeadImg":"http://headshot.user.qunar.com/headshotsById/176998537
     * .png?s","startTime":"2015-02-12","routeDays":6,"bookImgNum":161,"viewCount":5,
     * "likeCount":0,"commentCount":0,
     * "text":"武汉大学>古德寺>武汉长江大桥>户部巷>昙华林>晴川阁>光谷步行街>湖北美术学院>武汉辛亥革命博物馆>黄鹤楼>参差货柜咖啡>汉阳造文化创意产业园>湖北省博物馆>徐刀刀和她的鲜花饼们>乔巴寿司(昙华林店)>大水的店>东湖梅园>东湖>楚河汉街>蔡林记热干面馆(汉口旗舰店)>江汉关大楼>靓靓蒸虾(雪松路总店)>昙华林微书局>Merci Cafe>Corner>小蒋糖糍粑>老王超级豆腐脑>老何记豆皮大王>金包银糍粑面窝>陈记烧梅>东北秘制烤猪手(户部巷店)>厨府贵妃玫瑰虾>周黑鸭","elite":false},{"bookUrl":"http://travel.qunar.com/youji/6627629?from=baidu_apistore","title":"云霞满袖，翠挹三清\u2014\u2014山与海的爱情故事","headImage":"http://img1.qunarzz.com/travel/d3/1611/98/8fa92cf2e41031b5.jpg","userName":"da_dan_doge","userHeadImg":"http://headshot.user.qunar.com/headshotsById/182389367.png?s","startTime":"2015-10-02","routeDays":2,"bookImgNum":247,"viewCount":57,"likeCount":0,"commentCount":0,"text":"三清山>三清宫>阳光海岸>南清园>玉山火车站>玉山南站>玉京峰>猴王献宝>玉山国贸大酒店>西海岸景区>司春女神>巨蟒出山>一线天>三龙出海","elite":false},{"bookUrl":"http://travel.qunar.com/youji/6627616?from=baidu_apistore","title":"昆明-大理6天5夜自由行","headImage":"http://img1.qunarzz.com/travel/d6/1611/6e/1c4c4f996d0de8b5.jpg","userName":"蓝精灵爱吃鱼","userHeadImg":"http://headshot.user.qunar.com/headshotsById/242351864.png?s","startTime":"2015-01-09","routeDays":6,"bookImgNum":371,"viewCount":27,"likeCount":0,"commentCount":0,"text":"喜洲>大理喜洲严家民居>云南大学>苍山洗马潭索道>洱海>翠湖公园>苍山>大理站>双廊>喜洲镇>昆明长水国际机场>昆明站>德克士>大理古城印象大理花园客栈>大理古城>洋人街>大理古城梧桐里客栈>天龙八部影视城","elite":false},{"bookUrl":"http://travel.qunar.com/youji/6627509?from=baidu_apistore","title":"橙撞绿 缤纷小岛8日记","headImage":"http://img1.qunarzz.com/travel/d6/1611/e1/0d95f8ea23f572b5.jpg","userName":"唉我去我鞋呢","userHeadImg":"http://headshot.user.qunar.com/headshotsById/176998537.png?s","startTime":"2014-01-22","routeDays":8,"bookImgNum":241,"viewCount":59,"likeCount":0,"commentCount":0,"text":"澳门澳莱大三元酒店(Ole Tai Sam Un Hotel)>义顺牛奶公司(新马路三店)>澳门邮政总局>民政总署大楼>新葡京娱乐场>大三巴牌坊>陈光记烧味饭店>议事亭前地>仁慈堂>玫瑰圣母堂>板樟堂区>黄枝记面家(议事亭前地店)>澳门手信街>岗顶前地>何东图书馆>圣若瑟修院及圣堂>圣奥斯定教堂>岗顶剧院>圣老楞佐教堂>郑家大屋>亚婆井前地>海事博物馆>妈祖阁>主教山小堂>澳门旅游观光塔>金玉满堂(南湾大马路店)>新八佰伴百货>番茄屋美食>亚美打利庇卢大马路>福隆新街>二龙喉公园>东望洋炮台>松山>东望洋灯塔>塔石广场 >国父纪念馆>卢廉若公园>望德圣母堂>礼记雪糕饮冰专家>疯堂斜巷>疯堂十号创意园>澳门博物馆>大炮台>白鸽巢前地>白鸽巢公园>九如坊>葡京娱乐场>合诚小食店>路环市区>尚喜>安德鲁饼店（撻沙街店）>圣方济各教堂>谭公庙>天后古庙>安德鲁咖啡店>新濠天地>威尼斯人娱乐场>澳门大学>嘉模圣母堂>龙环葡韵住宅式博物馆>官也街>熊曲奇>大利来记咖啡室(氹仔总店)>路氹历史馆>莫义记（官也街店）>柠檬车露(地堡街店)>沙度娜木糠布甸专门店(氹仔店)>猫屎咖啡>凼仔市集>盛记白粥(氹仔店)>成昌超级市场","elite":false},{"bookUrl":"http://travel.qunar.com/youji/6627504?from=baidu_apistore","title":"新西兰，上帝留给自己养老的地方。","headImage":"http://img1.qunarzz.com/travel/d5/1611/70/123dbd0cedd00bb5.jpg","userName":"段段summer","userHeadImg":"http://headshot.user.qunar.com/headshotsById/243518890.png?s","startTime":"2016-02-02","routeDays":15,"bookImgNum":193,"viewCount":95,"likeCount":0,"commentCount":0,"text":"奥克兰国际机场>奥克兰海港>怀欧塔普地热公园>爱歌顿牧场>基督城国际机场>薄饼岩>霍基蒂卡>天空缆车>蒂阿瑙萤火虫岩洞>蒂阿瑙湖>拉纳克城堡>皇家信天翁中心>但尼丁火车站>黄眼企鹅保护区","elite":false},{"bookUrl":"http://travel.qunar.com/youji/6627489?from=baidu_apistore","title":"游走津门，天津实用攻略","headImage":"http://img1.qunarzz.com/travel/d8/1611/48/42590aa0ccfd8db5.jpg","userName":"卖山楂的大叔","userHeadImg":"http://headshot.user.qunar.com/headshotsById/182385900.png?s","startTime":"2015-04-23","routeDays":4,"bookImgNum":200,"viewCount":172,"likeCount":0,"commentCount":0,"text":"小洋楼>塘沽>天后宫>周恩来邓颖超纪念馆>瓷房子>西开教堂>五大道>曹禺故居>天津博物馆>天津自然博物馆>津湾广场>天津文化中心>天津之眼>天津站>名流茶馆(鼓楼店)>洋货市场>意式风情街>解放桥>世纪钟>马可波罗广场>梁启超故居>望海楼天主堂>张学良故居>庆王府>狮子林桥>滨海航母主题公园>海河>天津古文化街","elite":false},{"bookUrl":"http://travel.qunar.com/youji/6627388?from=baidu_apistore","title":"初秋的首尔，一切都刚刚好","headImage":"http://img1.qunarzz.com/travel/d4/1611/59/ae84692881fe8db5.jpg","userName":"蓝精灵爱吃鱼","userHeadImg":"http://headshot.user.qunar.com/headshotsById/242351864.png?s","startTime":"2016-10-03","routeDays":5,"bookImgNum":335,"viewCount":297,"likeCount":0,"commentCount":0,"text":"首尔小房子大门酒店(Small House Big Door Hotel)>明洞>伊蒂之屋（乐天世界店）>乐天免税店>易买得(中区店)>仁川国际机场>明洞小吃摊>N首尔塔>北村韩屋村>三清洞文化街>国立民俗博物馆>景福宫>土俗村参鸡汤>东大门>斗塔购物中心>东大门设计广场>梨花女子大学>小法兰西>南怡岛>铁路自行车(金裕贞站)>韩牛料理>LINE friends store","elite":false},{"bookUrl":"http://travel.qunar.com/youji/6627278?from=baidu_apistore","title":"感动黔行，享自由极之旅","headImage":"http://img1.qunarzz.com/travel/d2/1611/95/f1897684a163d2b5.jpg","userName":"莎莎爱旅行","userHeadImg":"http://headshot.user.qunar.com/headshotsById/146547790.png?s","startTime":"2016-10-30","routeDays":5,"bookImgNum":65,"viewCount":190,"likeCount":0,"commentCount":0,"text":"榕江>侗族大歌>加榜梯田>黎平>下司酸汤>凯里>肇兴侗寨","elite":false},{"bookUrl":"http://travel.qunar.com/youji/6626993?from=baidu_apistore","title":"山河论剑@骑行淳安千岛湖","headImage":"http://img1.qunarzz.com/travel/d7/1611/e9/8f6f9be2abc1abb5.jpg","userName":"山河论剑","userHeadImg":"http://headshot.user.qunar.com/headshotsById/185979315.png?s","startTime":"2016-09-24","routeDays":1,"bookImgNum":115,"viewCount":92,"likeCount":1,"commentCount":0,"text":"千岛湖>龙川湾","elite":false}],"count":2000}
     */

    private boolean ret;
    private int errcode;
    private String errmsg;
    private int ver;
    /**
     * books : [{"bookUrl":"http://travel.qunar.com/youji/6627820?from=baidu_apistore",
     * "title":"夏至，厦门。","headImage":"https://img1.qunarzz.com/travel/d2/1611/55/c12655371036f4b5
     * .jpg","userName":"da_dan_doge","userHeadImg":"http://headshot.user.qunar
     * .com/headshotsById/182389367.png?s","startTime":"2015-06-20","routeDays":3,
     * "bookImgNum":119,"viewCount":4,"likeCount":0,"commentCount":0,
     * "text":"环岛路>五老峰>鼓浪屿>南普陀寺>芙蓉隧道>厦门大学>胡里山炮台>曾厝垵>中山路步行街>黄则和花生汤店(中山路总店)
     * >日光岩>龙头路>菽庄花园>钢琴博物馆>十二洞天>鹭江>郑成功纪念馆","elite":false},{"bookUrl":"http://travel.qunar
     * .com/youji/6627789?from=baidu_apistore","title":"江城上的咖啡香~2015暖冬武汉游",
     * "headImage":"http://img1.qunarzz.com/travel/d8/1611/70/29a980305d96e0b5.jpg",
     * "userName":"唉我去我鞋呢","userHeadImg":"http://headshot.user.qunar.com/headshotsById/176998537
     * .png?s","startTime":"2015-02-12","routeDays":6,"bookImgNum":161,"viewCount":5,
     * "likeCount":0,"commentCount":0,
     * "text":"武汉大学>古德寺>武汉长江大桥>户部巷>昙华林>晴川阁>光谷步行街>湖北美术学院>武汉辛亥革命博物馆>黄鹤楼>参差货柜咖啡>汉阳造文化创意产业园>湖北省博物馆>徐刀刀和她的鲜花饼们>乔巴寿司(昙华林店)>大水的店>东湖梅园>东湖>楚河汉街>蔡林记热干面馆(汉口旗舰店)>江汉关大楼>靓靓蒸虾(雪松路总店)>昙华林微书局>Merci Cafe>Corner>小蒋糖糍粑>老王超级豆腐脑>老何记豆皮大王>金包银糍粑面窝>陈记烧梅>东北秘制烤猪手(户部巷店)>厨府贵妃玫瑰虾>周黑鸭","elite":false},{"bookUrl":"http://travel.qunar.com/youji/6627629?from=baidu_apistore","title":"云霞满袖，翠挹三清\u2014\u2014山与海的爱情故事","headImage":"http://img1.qunarzz.com/travel/d3/1611/98/8fa92cf2e41031b5.jpg","userName":"da_dan_doge","userHeadImg":"http://headshot.user.qunar.com/headshotsById/182389367.png?s","startTime":"2015-10-02","routeDays":2,"bookImgNum":247,"viewCount":57,"likeCount":0,"commentCount":0,"text":"三清山>三清宫>阳光海岸>南清园>玉山火车站>玉山南站>玉京峰>猴王献宝>玉山国贸大酒店>西海岸景区>司春女神>巨蟒出山>一线天>三龙出海","elite":false},{"bookUrl":"http://travel.qunar.com/youji/6627616?from=baidu_apistore","title":"昆明-大理6天5夜自由行","headImage":"http://img1.qunarzz.com/travel/d6/1611/6e/1c4c4f996d0de8b5.jpg","userName":"蓝精灵爱吃鱼","userHeadImg":"http://headshot.user.qunar.com/headshotsById/242351864.png?s","startTime":"2015-01-09","routeDays":6,"bookImgNum":371,"viewCount":27,"likeCount":0,"commentCount":0,"text":"喜洲>大理喜洲严家民居>云南大学>苍山洗马潭索道>洱海>翠湖公园>苍山>大理站>双廊>喜洲镇>昆明长水国际机场>昆明站>德克士>大理古城印象大理花园客栈>大理古城>洋人街>大理古城梧桐里客栈>天龙八部影视城","elite":false},{"bookUrl":"http://travel.qunar.com/youji/6627509?from=baidu_apistore","title":"橙撞绿 缤纷小岛8日记","headImage":"http://img1.qunarzz.com/travel/d6/1611/e1/0d95f8ea23f572b5.jpg","userName":"唉我去我鞋呢","userHeadImg":"http://headshot.user.qunar.com/headshotsById/176998537.png?s","startTime":"2014-01-22","routeDays":8,"bookImgNum":241,"viewCount":59,"likeCount":0,"commentCount":0,"text":"澳门澳莱大三元酒店(Ole Tai Sam Un Hotel)>义顺牛奶公司(新马路三店)>澳门邮政总局>民政总署大楼>新葡京娱乐场>大三巴牌坊>陈光记烧味饭店>议事亭前地>仁慈堂>玫瑰圣母堂>板樟堂区>黄枝记面家(议事亭前地店)>澳门手信街>岗顶前地>何东图书馆>圣若瑟修院及圣堂>圣奥斯定教堂>岗顶剧院>圣老楞佐教堂>郑家大屋>亚婆井前地>海事博物馆>妈祖阁>主教山小堂>澳门旅游观光塔>金玉满堂(南湾大马路店)>新八佰伴百货>番茄屋美食>亚美打利庇卢大马路>福隆新街>二龙喉公园>东望洋炮台>松山>东望洋灯塔>塔石广场 >国父纪念馆>卢廉若公园>望德圣母堂>礼记雪糕饮冰专家>疯堂斜巷>疯堂十号创意园>澳门博物馆>大炮台>白鸽巢前地>白鸽巢公园>九如坊>葡京娱乐场>合诚小食店>路环市区>尚喜>安德鲁饼店（撻沙街店）>圣方济各教堂>谭公庙>天后古庙>安德鲁咖啡店>新濠天地>威尼斯人娱乐场>澳门大学>嘉模圣母堂>龙环葡韵住宅式博物馆>官也街>熊曲奇>大利来记咖啡室(氹仔总店)>路氹历史馆>莫义记（官也街店）>柠檬车露(地堡街店)>沙度娜木糠布甸专门店(氹仔店)>猫屎咖啡>凼仔市集>盛记白粥(氹仔店)>成昌超级市场","elite":false},{"bookUrl":"http://travel.qunar.com/youji/6627504?from=baidu_apistore","title":"新西兰，上帝留给自己养老的地方。","headImage":"http://img1.qunarzz.com/travel/d5/1611/70/123dbd0cedd00bb5.jpg","userName":"段段summer","userHeadImg":"http://headshot.user.qunar.com/headshotsById/243518890.png?s","startTime":"2016-02-02","routeDays":15,"bookImgNum":193,"viewCount":95,"likeCount":0,"commentCount":0,"text":"奥克兰国际机场>奥克兰海港>怀欧塔普地热公园>爱歌顿牧场>基督城国际机场>薄饼岩>霍基蒂卡>天空缆车>蒂阿瑙萤火虫岩洞>蒂阿瑙湖>拉纳克城堡>皇家信天翁中心>但尼丁火车站>黄眼企鹅保护区","elite":false},{"bookUrl":"http://travel.qunar.com/youji/6627489?from=baidu_apistore","title":"游走津门，天津实用攻略","headImage":"http://img1.qunarzz.com/travel/d8/1611/48/42590aa0ccfd8db5.jpg","userName":"卖山楂的大叔","userHeadImg":"http://headshot.user.qunar.com/headshotsById/182385900.png?s","startTime":"2015-04-23","routeDays":4,"bookImgNum":200,"viewCount":172,"likeCount":0,"commentCount":0,"text":"小洋楼>塘沽>天后宫>周恩来邓颖超纪念馆>瓷房子>西开教堂>五大道>曹禺故居>天津博物馆>天津自然博物馆>津湾广场>天津文化中心>天津之眼>天津站>名流茶馆(鼓楼店)>洋货市场>意式风情街>解放桥>世纪钟>马可波罗广场>梁启超故居>望海楼天主堂>张学良故居>庆王府>狮子林桥>滨海航母主题公园>海河>天津古文化街","elite":false},{"bookUrl":"http://travel.qunar.com/youji/6627388?from=baidu_apistore","title":"初秋的首尔，一切都刚刚好","headImage":"http://img1.qunarzz.com/travel/d4/1611/59/ae84692881fe8db5.jpg","userName":"蓝精灵爱吃鱼","userHeadImg":"http://headshot.user.qunar.com/headshotsById/242351864.png?s","startTime":"2016-10-03","routeDays":5,"bookImgNum":335,"viewCount":297,"likeCount":0,"commentCount":0,"text":"首尔小房子大门酒店(Small House Big Door Hotel)>明洞>伊蒂之屋（乐天世界店）>乐天免税店>易买得(中区店)>仁川国际机场>明洞小吃摊>N首尔塔>北村韩屋村>三清洞文化街>国立民俗博物馆>景福宫>土俗村参鸡汤>东大门>斗塔购物中心>东大门设计广场>梨花女子大学>小法兰西>南怡岛>铁路自行车(金裕贞站)>韩牛料理>LINE friends store","elite":false},{"bookUrl":"http://travel.qunar.com/youji/6627278?from=baidu_apistore","title":"感动黔行，享自由极之旅","headImage":"http://img1.qunarzz.com/travel/d2/1611/95/f1897684a163d2b5.jpg","userName":"莎莎爱旅行","userHeadImg":"http://headshot.user.qunar.com/headshotsById/146547790.png?s","startTime":"2016-10-30","routeDays":5,"bookImgNum":65,"viewCount":190,"likeCount":0,"commentCount":0,"text":"榕江>侗族大歌>加榜梯田>黎平>下司酸汤>凯里>肇兴侗寨","elite":false},{"bookUrl":"http://travel.qunar.com/youji/6626993?from=baidu_apistore","title":"山河论剑@骑行淳安千岛湖","headImage":"http://img1.qunarzz.com/travel/d7/1611/e9/8f6f9be2abc1abb5.jpg","userName":"山河论剑","userHeadImg":"http://headshot.user.qunar.com/headshotsById/185979315.png?s","startTime":"2016-09-24","routeDays":1,"bookImgNum":115,"viewCount":92,"likeCount":1,"commentCount":0,"text":"千岛湖>龙川湾","elite":false}]
     * count : 2000
     */

    private DataBean data;

    protected TravelResponce(Parcel in) {
        ret = in.readByte() != 0;
        errcode = in.readInt();
        errmsg = in.readString();
        ver = in.readInt();
    }

    public static final Creator<TravelResponce> CREATOR = new Creator<TravelResponce>() {
        @Override
        public TravelResponce createFromParcel(Parcel in) {
            return new TravelResponce(in);
        }

        @Override
        public TravelResponce[] newArray(int size) {
            return new TravelResponce[size];
        }
    };

    public boolean isRet() {
        return ret;
    }

    public void setRet(boolean ret) {
        this.ret = ret;
    }

    public int getErrcode() {
        return errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public int getVer() {
        return ver;
    }

    public void setVer(int ver) {
        this.ver = ver;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (ret ? 1 : 0));
        dest.writeInt(errcode);
        dest.writeString(errmsg);
        dest.writeInt(ver);
    }

    public static class DataBean {
        private int count;
        /**
         * bookUrl : http://travel.qunar.com/youji/6627820?from=baidu_apistore
         * title : 夏至，厦门。
         * headImage : https://img1.qunarzz.com/travel/d2/1611/55/c12655371036f4b5.jpg
         * userName : da_dan_doge
         * userHeadImg : http://headshot.user.qunar.com/headshotsById/182389367.png?s
         * startTime : 2015-06-20
         * routeDays : 3
         * bookImgNum : 119
         * viewCount : 4
         * likeCount : 0
         * commentCount : 0
         * text : 环岛路>五老峰>鼓浪屿>南普陀寺>芙蓉隧道>厦门大学>胡里山炮台>曾厝垵>中山路步行街>黄则和花生汤店(中山路总店)
         * >日光岩>龙头路>菽庄花园>钢琴博物馆>十二洞天>鹭江>郑成功纪念馆
         * elite : false
         * isFavourite =1
         */

        private List<BooksBean> books;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public List<BooksBean> getBooks() {
            return books;
        }

        public void setBooks(List<BooksBean> books) {
            this.books = books;
        }

        public static class BooksBean implements Parcelable {
            private String bookUrl;
            private String title;
            private String headImage;
            private String userName;
            private String userHeadImg;
            private String startTime;
            private int routeDays;
            private int bookImgNum;
            private int viewCount;
            private int likeCount;
            private int commentCount;
            private String text;
            private boolean elite;

            protected BooksBean(Parcel in) {
                bookUrl = in.readString();
                title = in.readString();
                headImage = in.readString();
                userName = in.readString();
                userHeadImg = in.readString();
                startTime = in.readString();
                routeDays = in.readInt();
                bookImgNum = in.readInt();
                viewCount = in.readInt();
                likeCount = in.readInt();
                commentCount = in.readInt();
                text = in.readString();
                elite = in.readByte() != 0;
            }

            public static final Creator<BooksBean> CREATOR = new Creator<BooksBean>() {
                @Override
                public BooksBean createFromParcel(Parcel in) {
                    return new BooksBean(in);
                }

                @Override
                public BooksBean[] newArray(int size) {
                    return new BooksBean[size];
                }
            };

            public String getBookUrl() {
                return bookUrl;
            }

            public void setBookUrl(String bookUrl) {
                this.bookUrl = bookUrl;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getHeadImage() {
                return headImage;
            }

            public void setHeadImage(String headImage) {
                this.headImage = headImage;
            }

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }

            public String getUserHeadImg() {
                return userHeadImg;
            }

            public void setUserHeadImg(String userHeadImg) {
                this.userHeadImg = userHeadImg;
            }

            public String getStartTime() {
                return startTime;
            }

            public void setStartTime(String startTime) {
                this.startTime = startTime;
            }

            public int getRouteDays() {
                return routeDays;
            }

            public void setRouteDays(int routeDays) {
                this.routeDays = routeDays;
            }

            public int getBookImgNum() {
                return bookImgNum;
            }

            public void setBookImgNum(int bookImgNum) {
                this.bookImgNum = bookImgNum;
            }

            public int getViewCount() {
                return viewCount;
            }

            public void setViewCount(int viewCount) {
                this.viewCount = viewCount;
            }

            public int getLikeCount() {
                return likeCount;
            }

            public void setLikeCount(int likeCount) {
                this.likeCount = likeCount;
            }

            public int getCommentCount() {
                return commentCount;
            }

            public void setCommentCount(int commentCount) {
                this.commentCount = commentCount;
            }

            public String getText() {
                return text;
            }

            public void setText(String text) {
                this.text = text;
            }

            public boolean isElite() {
                return elite;
            }

            public void setElite(boolean elite) {
                this.elite = elite;
            }

            @Override
            public String toString() {
                return "BooksBean{" +
                        "bookUrl='" + bookUrl + '\'' +
                        ", title='" + title + '\'' +
                        ", headImage='" + headImage + '\'' +
                        ", userName='" + userName + '\'' +
                        ", userHeadImg='" + userHeadImg + '\'' +
                        ", startTime='" + startTime + '\'' +
                        ", routeDays=" + routeDays +
                        ", bookImgNum=" + bookImgNum +
                        ", viewCount=" + viewCount +
                        ", likeCount=" + likeCount +
                        ", commentCount=" + commentCount +
                        ", text='" + text + '\'' +
                        ", elite=" + elite +
                        '}';
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(bookUrl);
                dest.writeString(title);
                dest.writeString(headImage);
                dest.writeString(userName);
                dest.writeString(userHeadImg);
                dest.writeString(startTime);
                dest.writeInt(routeDays);
                dest.writeInt(bookImgNum);
                dest.writeInt(viewCount);
                dest.writeInt(likeCount);
                dest.writeInt(commentCount);
                dest.writeString(text);
                dest.writeByte((byte) (elite ? 1 : 0));
            }
        }
    }
}
