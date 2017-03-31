package com.weiruanit.lifepro.module.weather.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/11/16 0016.
 */

public class WeatherDataBean {

            /**
             * aqi : {"city":{"aqi":"108","co":"2","no2":"58","o3":"4","pm10":"84","pm25":"108","qlty":"轻度污染","so2":"20"}}
             * basic : {"city":"北京","cnty":"中国","id":"CN101010100","lat":"39.904000","lon":"116.391000","update":{"loc":"2016-11-17 09:53","utc":"2016-11-17 01:53"}}
             * daily_forecast : [{"astro":{"sr":"07:01","ss":"16:56"},"cond":{"code_d":"502","code_n":"502","txt_d":"霾","txt_n":"霾"},"date":"2016-11-17","hum":"70","pcpn":"0.0","pop":"0","pres":"1023","tmp":{"max":"7","min":"4"},"uv":"1","vis":"10","wind":{"deg":"134","dir":"无持续风向","sc":"微风","spd":"5"}},{"astro":{"sr":"07:03","ss":"16:56"},"cond":{"code_d":"502","code_n":"502","txt_d":"霾","txt_n":"霾"},"date":"2016-11-18","hum":"82","pcpn":"0.0","pop":"2","pres":"1015","tmp":{"max":"10","min":"6"},"uv":"2","vis":"10","wind":{"deg":"132","dir":"无持续风向","sc":"微风","spd":"5"}},{"astro":{"sr":"07:04","ss":"16:55"},"cond":{"code_d":"502","code_n":"104","txt_d":"霾","txt_n":"阴"},"date":"2016-11-19","hum":"46","pcpn":"0.0","pop":"1","pres":"1018","tmp":{"max":"14","min":"3"},"uv":"2","vis":"10","wind":{"deg":"323","dir":"北风","sc":"3-4","spd":"16"}},{"astro":{"sr":"07:05","ss":"16:54"},"cond":{"code_d":"305","code_n":"404","txt_d":"小雨","txt_n":"雨夹雪"},"date":"2016-11-20","hum":"46","pcpn":"3.4","pop":"85","pres":"1028","tmp":{"max":"6","min":"0"},"uv":"1","vis":"2","wind":{"deg":"146","dir":"无持续风向","sc":"微风","spd":"8"}},{"astro":{"sr":"07:06","ss":"16:54"},"cond":{"code_d":"400","code_n":"104","txt_d":"小雪","txt_n":"阴"},"date":"2016-11-21","hum":"93","pcpn":"16.5","pop":"92","pres":"1031","tmp":{"max":"1","min":"-4"},"uv":"0","vis":"2","wind":{"deg":"356","dir":"北风","sc":"3-4","spd":"13"}},{"astro":{"sr":"07:07","ss":"16:53"},"cond":{"code_d":"101","code_n":"100","txt_d":"多云","txt_n":"晴"},"date":"2016-11-22","hum":"81","pcpn":"0.0","pop":"0","pres":"1043","tmp":{"max":"-1","min":"-8"},"uv":"-999","vis":"10","wind":{"deg":"360","dir":"北风","sc":"3-4","spd":"11"}},{"astro":{"sr":"07:08","ss":"16:53"},"cond":{"code_d":"100","code_n":"100","txt_d":"晴","txt_n":"晴"},"date":"2016-11-23","hum":"80","pcpn":"0.0","pop":"0","pres":"1042","tmp":{"max":"-2","min":"-9"},"uv":"-999","vis":"10","wind":{"deg":"351","dir":"无持续风向","sc":"微风","spd":"8"}}]
             * hourly_forecast : [{"date":"2016-11-17 10:00","hum":"61","pop":"0","pres":"1025","tmp":"5","wind":{"deg":"99","dir":"无持续风向","sc":"微风","spd":"5"}},{"date":"2016-11-17 13:00","hum":"58","pop":"0","pres":"1023","tmp":"6","wind":{"deg":"123","dir":"无持续风向","sc":"微风","spd":"7"}},{"date":"2016-11-17 16:00","hum":"69","pop":"0","pres":"1022","tmp":"6","wind":{"deg":"98","dir":"无持续风向","sc":"微风","spd":"5"}},{"date":"2016-11-17 19:00","hum":"82","pop":"0","pres":"1022","tmp":"6","wind":{"deg":"53","dir":"无持续风向","sc":"微风","spd":"4"}},{"date":"2016-11-17 22:00","hum":"88","pop":"0","pres":"1021","tmp":"5","wind":{"deg":"50","dir":"无持续风向","sc":"微风","spd":"4"}}]
             * now : {"cond":{"code":"101","txt":"多云"},"fl":"6","hum":"62","pcpn":"0","pres":"1026","tmp":"5","vis":"7","wind":{"deg":"80","dir":"东风","sc":"4-5","spd":"22"}}
             * status : ok
             * suggestion : {"air":{"brf":"较差","txt":"气象条件较不利于空气污染物扩散。"},"comf":{"brf":"较舒适","txt":"白天天气阴沉，会感到有点儿凉，但大部分人完全可以接受。"},"cw":{"brf":"不宜","txt":"不宜洗车，未来24小时内有霾，如果在此期间洗车，会弄脏您的爱车。"},"drsg":{"brf":"较冷","txt":"建议着厚外套加毛衣等服装。年老体弱者宜着大衣、呢外套加羊毛衫。"},"flu":{"brf":"极易发","txt":"将有一次强降温过程，天气寒冷，且空气湿度较大，极易发生感冒，请特别注意增加衣服保暖防寒。"},"sport":{"brf":"较不宜","txt":"有扬沙或浮尘，建议适当停止户外运动，选择在室内进行运动，以避免吸入更多沙尘，有损健康。"},"trav":{"brf":"较不宜","txt":"空气质量差，不适宜旅游"},"uv":{"brf":"最弱","txt":"属弱紫外线辐射天气，无需特别防护。若长期在户外，建议涂擦SPF在8-12之间的防晒护肤品。"}}
             */

            private AqiBean aqi;
            private BasicBean basic;
            private NowBean now;
            private String status;
            private SuggestionBean suggestion;
            private List<DailyForecastBean> daily_forecast;
            private List<HourlyForecastBean> hourly_forecast;

            public AqiBean getAqi() {
                return aqi;
            }

            public void setAqi(AqiBean aqi) {
                this.aqi = aqi;
            }

            public BasicBean getBasic() {
                return basic;
            }

            public void setBasic(BasicBean basic) {
                this.basic = basic;
            }

            public NowBean getNow() {
                return now;
            }

            public void setNow(NowBean now) {
                this.now = now;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public SuggestionBean getSuggestion() {
                return suggestion;
            }

            public void setSuggestion(SuggestionBean suggestion) {
                this.suggestion = suggestion;
            }

            public List<DailyForecastBean> getDaily_forecast() {
                return daily_forecast;
            }

            public void setDaily_forecast(List<DailyForecastBean> daily_forecast) {
                this.daily_forecast = daily_forecast;
            }

            public List<HourlyForecastBean> getHourly_forecast() {
                return hourly_forecast;
            }

            public void setHourly_forecast(List<HourlyForecastBean> hourly_forecast) {
                this.hourly_forecast = hourly_forecast;
            }

            public static class AqiBean {
                /**
                 * city : {"aqi":"108","co":"2","no2":"58","o3":"4","pm10":"84","pm25":"108","qlty":"轻度污染","so2":"20"}
                 */

                private CityBean city;

                public CityBean getCity() {
                    return city;
                }

                public void setCity(CityBean city) {
                    this.city = city;
                }

                public static class CityBean {
                    /**
                     * aqi : 108
                     * co : 2
                     * no2 : 58
                     * o3 : 4
                     * pm10 : 84
                     * pm25 : 108
                     * qlty : 轻度污染
                     * so2 : 20
                     */

                    private String aqi;
                    private String co;
                    private String no2;
                    private String o3;
                    private String pm10;
                    private String pm25;
                    private String qlty;
                    private String so2;

                    public String getAqi() {
                        return aqi;
                    }

                    public void setAqi(String aqi) {
                        this.aqi = aqi;
                    }

                    public String getCo() {
                        return co;
                    }

                    public void setCo(String co) {
                        this.co = co;
                    }

                    public String getNo2() {
                        return no2;
                    }

                    public void setNo2(String no2) {
                        this.no2 = no2;
                    }

                    public String getO3() {
                        return o3;
                    }

                    public void setO3(String o3) {
                        this.o3 = o3;
                    }

                    public String getPm10() {
                        return pm10;
                    }

                    public void setPm10(String pm10) {
                        this.pm10 = pm10;
                    }

                    public String getPm25() {
                        return pm25;
                    }

                    public void setPm25(String pm25) {
                        this.pm25 = pm25;
                    }

                    public String getQlty() {
                        return qlty;
                    }

                    public void setQlty(String qlty) {
                        this.qlty = qlty;
                    }

                    public String getSo2() {
                        return so2;
                    }

                    public void setSo2(String so2) {
                        this.so2 = so2;
                    }
                }
            }

            public static class BasicBean {
                /**
                 * city : 北京
                 * cnty : 中国
                 * id : CN101010100
                 * lat : 39.904000
                 * lon : 116.391000
                 * update : {"loc":"2016-11-17 09:53","utc":"2016-11-17 01:53"}
                 */

                private String city;
                private String cnty;
                private String id;
                private String lat;
                private String lon;
                private UpdateBean update;

                public String getCity() {
                    return city;
                }

                public void setCity(String city) {
                    this.city = city;
                }

                public String getCnty() {
                    return cnty;
                }

                public void setCnty(String cnty) {
                    this.cnty = cnty;
                }

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getLat() {
                    return lat;
                }

                public void setLat(String lat) {
                    this.lat = lat;
                }

                public String getLon() {
                    return lon;
                }

                public void setLon(String lon) {
                    this.lon = lon;
                }

                public UpdateBean getUpdate() {
                    return update;
                }

                public void setUpdate(UpdateBean update) {
                    this.update = update;
                }

                public static class UpdateBean {
                    /**
                     * loc : 2016-11-17 09:53
                     * utc : 2016-11-17 01:53
                     */

                    private String loc;
                    private String utc;

                    public String getLoc() {
                        return loc;
                    }

                    public void setLoc(String loc) {
                        this.loc = loc;
                    }

                    public String getUtc() {
                        return utc;
                    }

                    public void setUtc(String utc) {
                        this.utc = utc;
                    }
                }
            }

            public static class NowBean {
                /**
                 * cond : {"code":"101","txt":"多云"}
                 * fl : 6
                 * hum : 62
                 * pcpn : 0
                 * pres : 1026
                 * tmp : 5
                 * vis : 7
                 * wind : {"deg":"80","dir":"东风","sc":"4-5","spd":"22"}
                 */

                private CondBean cond;
                private String fl;
                private String hum;
                private String pcpn;
                private String pres;
                private String tmp;
                private String vis;
                private WindBean wind;

                public CondBean getCond() {
                    return cond;
                }

                public void setCond(CondBean cond) {
                    this.cond = cond;
                }

                public String getFl() {
                    return fl;
                }

                public void setFl(String fl) {
                    this.fl = fl;
                }

                public String getHum() {
                    return hum;
                }

                public void setHum(String hum) {
                    this.hum = hum;
                }

                public String getPcpn() {
                    return pcpn;
                }

                public void setPcpn(String pcpn) {
                    this.pcpn = pcpn;
                }

                public String getPres() {
                    return pres;
                }

                public void setPres(String pres) {
                    this.pres = pres;
                }

                public String getTmp() {
                    return tmp;
                }

                public void setTmp(String tmp) {
                    this.tmp = tmp;
                }

                public String getVis() {
                    return vis;
                }

                public void setVis(String vis) {
                    this.vis = vis;
                }

                public WindBean getWind() {
                    return wind;
                }

                public void setWind(WindBean wind) {
                    this.wind = wind;
                }

                public static class CondBean {
                    /**
                     * code : 101
                     * txt : 多云
                     */

                    private String code;
                    private String txt;

                    public String getCode() {
                        return code;
                    }

                    public void setCode(String code) {
                        this.code = code;
                    }

                    public String getTxt() {
                        return txt;
                    }

                    public void setTxt(String txt) {
                        this.txt = txt;
                    }
                }

                public static class WindBean {
                    /**
                     * deg : 80
                     * dir : 东风
                     * sc : 4-5
                     * spd : 22
                     */

                    private String deg;
                    private String dir;
                    private String sc;
                    private String spd;

                    public String getDeg() {
                        return deg;
                    }

                    public void setDeg(String deg) {
                        this.deg = deg;
                    }

                    public String getDir() {
                        return dir;
                    }

                    public void setDir(String dir) {
                        this.dir = dir;
                    }

                    public String getSc() {
                        return sc;
                    }

                    public void setSc(String sc) {
                        this.sc = sc;
                    }

                    public String getSpd() {
                        return spd;
                    }

                    public void setSpd(String spd) {
                        this.spd = spd;
                    }
                }
            }

            public static class SuggestionBean {
                /**
                 * air : {"brf":"较差","txt":"气象条件较不利于空气污染物扩散。"}
                 * comf : {"brf":"较舒适","txt":"白天天气阴沉，会感到有点儿凉，但大部分人完全可以接受。"}
                 * cw : {"brf":"不宜","txt":"不宜洗车，未来24小时内有霾，如果在此期间洗车，会弄脏您的爱车。"}
                 * drsg : {"brf":"较冷","txt":"建议着厚外套加毛衣等服装。年老体弱者宜着大衣、呢外套加羊毛衫。"}
                 * flu : {"brf":"极易发","txt":"将有一次强降温过程，天气寒冷，且空气湿度较大，极易发生感冒，请特别注意增加衣服保暖防寒。"}
                 * sport : {"brf":"较不宜","txt":"有扬沙或浮尘，建议适当停止户外运动，选择在室内进行运动，以避免吸入更多沙尘，有损健康。"}
                 * trav : {"brf":"较不宜","txt":"空气质量差，不适宜旅游"}
                 * uv : {"brf":"最弱","txt":"属弱紫外线辐射天气，无需特别防护。若长期在户外，建议涂擦SPF在8-12之间的防晒护肤品。"}
                 */

                private AirBean air;
                private ComfBean comf;
                private CwBean cw;
                private DrsgBean drsg;
                private FluBean flu;
                private SportBean sport;
                private TravBean trav;
                private UvBean uv;

                public AirBean getAir() {
                    return air;
                }

                public void setAir(AirBean air) {
                    this.air = air;
                }

                public ComfBean getComf() {
                    return comf;
                }

                public void setComf(ComfBean comf) {
                    this.comf = comf;
                }

                public CwBean getCw() {
                    return cw;
                }

                public void setCw(CwBean cw) {
                    this.cw = cw;
                }

                public DrsgBean getDrsg() {
                    return drsg;
                }

                public void setDrsg(DrsgBean drsg) {
                    this.drsg = drsg;
                }

                public FluBean getFlu() {
                    return flu;
                }

                public void setFlu(FluBean flu) {
                    this.flu = flu;
                }

                public SportBean getSport() {
                    return sport;
                }

                public void setSport(SportBean sport) {
                    this.sport = sport;
                }

                public TravBean getTrav() {
                    return trav;
                }

                public void setTrav(TravBean trav) {
                    this.trav = trav;
                }

                public UvBean getUv() {
                    return uv;
                }

                public void setUv(UvBean uv) {
                    this.uv = uv;
                }

                public static class AirBean {
                    /**
                     * brf : 较差
                     * txt : 气象条件较不利于空气污染物扩散。
                     */

                    private String brf;
                    private String txt;

                    public String getBrf() {
                        return brf;
                    }

                    public void setBrf(String brf) {
                        this.brf = brf;
                    }

                    public String getTxt() {
                        return txt;
                    }

                    public void setTxt(String txt) {
                        this.txt = txt;
                    }
                }

                public static class ComfBean {
                    /**
                     * brf : 较舒适
                     * txt : 白天天气阴沉，会感到有点儿凉，但大部分人完全可以接受。
                     */

                    private String brf;
                    private String txt;

                    public String getBrf() {
                        return brf;
                    }

                    public void setBrf(String brf) {
                        this.brf = brf;
                    }

                    public String getTxt() {
                        return txt;
                    }

                    public void setTxt(String txt) {
                        this.txt = txt;
                    }
                }

                public static class CwBean {
                    /**
                     * brf : 不宜
                     * txt : 不宜洗车，未来24小时内有霾，如果在此期间洗车，会弄脏您的爱车。
                     */

                    private String brf;
                    private String txt;

                    public String getBrf() {
                        return brf;
                    }

                    public void setBrf(String brf) {
                        this.brf = brf;
                    }

                    public String getTxt() {
                        return txt;
                    }

                    public void setTxt(String txt) {
                        this.txt = txt;
                    }
                }

                public static class DrsgBean {
                    /**
                     * brf : 较冷
                     * txt : 建议着厚外套加毛衣等服装。年老体弱者宜着大衣、呢外套加羊毛衫。
                     */

                    private String brf;
                    private String txt;

                    public String getBrf() {
                        return brf;
                    }

                    public void setBrf(String brf) {
                        this.brf = brf;
                    }

                    public String getTxt() {
                        return txt;
                    }

                    public void setTxt(String txt) {
                        this.txt = txt;
                    }
                }

                public static class FluBean {
                    /**
                     * brf : 极易发
                     * txt : 将有一次强降温过程，天气寒冷，且空气湿度较大，极易发生感冒，请特别注意增加衣服保暖防寒。
                     */

                    private String brf;
                    private String txt;

                    public String getBrf() {
                        return brf;
                    }

                    public void setBrf(String brf) {
                        this.brf = brf;
                    }

                    public String getTxt() {
                        return txt;
                    }

                    public void setTxt(String txt) {
                        this.txt = txt;
                    }
                }

                public static class SportBean {
                    /**
                     * brf : 较不宜
                     * txt : 有扬沙或浮尘，建议适当停止户外运动，选择在室内进行运动，以避免吸入更多沙尘，有损健康。
                     */

                    private String brf;
                    private String txt;

                    public String getBrf() {
                        return brf;
                    }

                    public void setBrf(String brf) {
                        this.brf = brf;
                    }

                    public String getTxt() {
                        return txt;
                    }

                    public void setTxt(String txt) {
                        this.txt = txt;
                    }
                }

                public static class TravBean {
                    /**
                     * brf : 较不宜
                     * txt : 空气质量差，不适宜旅游
                     */

                    private String brf;
                    private String txt;

                    public String getBrf() {
                        return brf;
                    }

                    public void setBrf(String brf) {
                        this.brf = brf;
                    }

                    public String getTxt() {
                        return txt;
                    }

                    public void setTxt(String txt) {
                        this.txt = txt;
                    }
                }

                public static class UvBean {
                    /**
                     * brf : 最弱
                     * txt : 属弱紫外线辐射天气，无需特别防护。若长期在户外，建议涂擦SPF在8-12之间的防晒护肤品。
                     */

                    private String brf;
                    private String txt;

                    public String getBrf() {
                        return brf;
                    }

                    public void setBrf(String brf) {
                        this.brf = brf;
                    }

                    public String getTxt() {
                        return txt;
                    }

                    public void setTxt(String txt) {
                        this.txt = txt;
                    }
                }
            }

            public static class DailyForecastBean {
                /**
                 * astro : {"sr":"07:01","ss":"16:56"}
                 * cond : {"code_d":"502","code_n":"502","txt_d":"霾","txt_n":"霾"}
                 * date : 2016-11-17
                 * hum : 70
                 * pcpn : 0.0
                 * pop : 0
                 * pres : 1023
                 * tmp : {"max":"7","min":"4"}
                 * uv : 1
                 * vis : 10
                 * wind : {"deg":"134","dir":"无持续风向","sc":"微风","spd":"5"}
                 */

                private AstroBean astro;
                private CondBeanX cond;
                private String date;
                private String hum;
                private String pcpn;
                private String pop;
                private String pres;
                private TmpBean tmp;
                private String uv;
                private String vis;
                private WindBeanX wind;

                public AstroBean getAstro() {
                    return astro;
                }

                public void setAstro(AstroBean astro) {
                    this.astro = astro;
                }

                public CondBeanX getCond() {
                    return cond;
                }

                public void setCond(CondBeanX cond) {
                    this.cond = cond;
                }

                public String getDate() {
                    return date;
                }

                public void setDate(String date) {
                    this.date = date;
                }

                public String getHum() {
                    return hum;
                }

                public void setHum(String hum) {
                    this.hum = hum;
                }

                public String getPcpn() {
                    return pcpn;
                }

                public void setPcpn(String pcpn) {
                    this.pcpn = pcpn;
                }

                public String getPop() {
                    return pop;
                }

                public void setPop(String pop) {
                    this.pop = pop;
                }

                public String getPres() {
                    return pres;
                }

                public void setPres(String pres) {
                    this.pres = pres;
                }

                public TmpBean getTmp() {
                    return tmp;
                }

                public void setTmp(TmpBean tmp) {
                    this.tmp = tmp;
                }

                public String getUv() {
                    return uv;
                }

                public void setUv(String uv) {
                    this.uv = uv;
                }

                public String getVis() {
                    return vis;
                }

                public void setVis(String vis) {
                    this.vis = vis;
                }

                public WindBeanX getWind() {
                    return wind;
                }

                public void setWind(WindBeanX wind) {
                    this.wind = wind;
                }

                public static class AstroBean {
                    /**
                     * sr : 07:01
                     * ss : 16:56
                     */

                    private String sr;
                    private String ss;

                    public String getSr() {
                        return sr;
                    }

                    public void setSr(String sr) {
                        this.sr = sr;
                    }

                    public String getSs() {
                        return ss;
                    }

                    public void setSs(String ss) {
                        this.ss = ss;
                    }
                }

                public static class CondBeanX {
                    /**
                     * code_d : 502
                     * code_n : 502
                     * txt_d : 霾
                     * txt_n : 霾
                     */

                    private String code_d;
                    private String code_n;
                    private String txt_d;
                    private String txt_n;

                    public String getCode_d() {
                        return code_d;
                    }

                    public void setCode_d(String code_d) {
                        this.code_d = code_d;
                    }

                    public String getCode_n() {
                        return code_n;
                    }

                    public void setCode_n(String code_n) {
                        this.code_n = code_n;
                    }

                    public String getTxt_d() {
                        return txt_d;
                    }

                    public void setTxt_d(String txt_d) {
                        this.txt_d = txt_d;
                    }

                    public String getTxt_n() {
                        return txt_n;
                    }

                    public void setTxt_n(String txt_n) {
                        this.txt_n = txt_n;
                    }
                }

                public static class TmpBean {
                    /**
                     * max : 7
                     * min : 4
                     */

                    private String max;
                    private String min;

                    public String getMax() {
                        return max;
                    }

                    public void setMax(String max) {
                        this.max = max;
                    }

                    public String getMin() {
                        return min;
                    }

                    public void setMin(String min) {
                        this.min = min;
                    }
                }

                public static class WindBeanX {
                    /**
                     * deg : 134
                     * dir : 无持续风向
                     * sc : 微风
                     * spd : 5
                     */

                    private String deg;
                    private String dir;
                    private String sc;
                    private String spd;

                    public String getDeg() {
                        return deg;
                    }

                    public void setDeg(String deg) {
                        this.deg = deg;
                    }

                    public String getDir() {
                        return dir;
                    }

                    public void setDir(String dir) {
                        this.dir = dir;
                    }

                    public String getSc() {
                        return sc;
                    }

                    public void setSc(String sc) {
                        this.sc = sc;
                    }

                    public String getSpd() {
                        return spd;
                    }

                    public void setSpd(String spd) {
                        this.spd = spd;
                    }
                }
            }

            public static class HourlyForecastBean {
                /**
                 * date : 2016-11-17 10:00
                 * hum : 61
                 * pop : 0
                 * pres : 1025
                 * tmp : 5
                 * wind : {"deg":"99","dir":"无持续风向","sc":"微风","spd":"5"}
                 */

                private String date;
                private String hum;
                private String pop;
                private String pres;
                private String tmp;
                private WindBeanXX wind;

                public String getDate() {
                    return date;
                }

                public void setDate(String date) {
                    this.date = date;
                }

                public String getHum() {
                    return hum;
                }

                public void setHum(String hum) {
                    this.hum = hum;
                }

                public String getPop() {
                    return pop;
                }

                public void setPop(String pop) {
                    this.pop = pop;
                }

                public String getPres() {
                    return pres;
                }

                public void setPres(String pres) {
                    this.pres = pres;
                }

                public String getTmp() {
                    return tmp;
                }

                public void setTmp(String tmp) {
                    this.tmp = tmp;
                }

                public WindBeanXX getWind() {
                    return wind;
                }

                public void setWind(WindBeanXX wind) {
                    this.wind = wind;
                }

                public static class WindBeanXX {
                    /**
                     * deg : 99
                     * dir : 无持续风向
                     * sc : 微风
                     * spd : 5
                     */

                    private String deg;
                    private String dir;
                    private String sc;
                    private String spd;

                    public String getDeg() {
                        return deg;
                    }

                    public void setDeg(String deg) {
                        this.deg = deg;
                    }

                    public String getDir() {
                        return dir;
                    }

                    public void setDir(String dir) {
                        this.dir = dir;
                    }

                    public String getSc() {
                        return sc;
                    }

                    public void setSc(String sc) {
                        this.sc = sc;
                    }

                    public String getSpd() {
                        return spd;
                    }

                    public void setSpd(String spd) {
                        this.spd = spd;
                    }
                }
            }
}
