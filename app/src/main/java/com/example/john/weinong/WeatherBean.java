package com.example.john.weinong;

import java.util.List;

/**
 * Created by john on 2018/9/7.
 */

public class WeatherBean {
    /**
     * daily_forecast : [{"cond_code_d":"300","cond_code_n":"300","cond_txt_d":"阵雨","cond_txt_n":"阵雨","date":"2018-09-07","hum":"79","mr":"02:30","ms":"16:31","pcpn":"0.0","pop":"25","pres":"1009","sr":"05:39","ss":"18:12","tmp_max":"25","tmp_min":"20","uv_index":"4","vis":"18","wind_deg":"-1","wind_dir":"无持续风向","wind_sc":"1-2","wind_spd":"11"},{"cond_code_d":"300","cond_code_n":"104","cond_txt_d":"阵雨","cond_txt_n":"阴","date":"2018-09-08","hum":"92","mr":"03:38","ms":"17:21","pcpn":"1.0","pop":"55","pres":"1014","sr":"05:40","ss":"18:11","tmp_max":"25","tmp_min":"20","uv_index":"3","vis":"14","wind_deg":"-1","wind_dir":"无持续风向","wind_sc":"1-2","wind_spd":"4"},{"cond_code_d":"101","cond_code_n":"101","cond_txt_d":"多云","cond_txt_n":"多云","date":"2018-09-09","hum":"86","mr":"04:46","ms":"18:06","pcpn":"0.0","pop":"1","pres":"1017","sr":"05:41","ss":"18:10","tmp_max":"27","tmp_min":"21","uv_index":"3","vis":"18","wind_deg":"-1","wind_dir":"无持续风向","wind_sc":"1-2","wind_spd":"11"}]
     * lifestyle : [{"brf":"舒适","txt":"白天不太热也不冷，风力不大，相信您在这样的天气条件下，应会感到比较清爽和舒适。","type":"comf"},{"brf":"舒适","txt":"建议着长袖T恤、衬衫加单裤等服装。年老体弱者宜着针织长袖衬衫、马甲和长裤。","type":"drsg"},{"brf":"少发","txt":"各项气象条件适宜，无明显降温过程，发生感冒机率较低。","type":"flu"},{"brf":"较不宜","txt":"有降水，推荐您在室内进行健身休闲运动；若坚持户外运动，须注意携带雨具并注意避雨防滑。","type":"sport"},{"brf":"适宜","txt":"有降水，温度适宜，在细雨中游玩别有一番情调，可不要错过机会呦！但记得出门要携带雨具。","type":"trav"},{"brf":"弱","txt":"紫外线强度较弱，建议出门前涂擦SPF在12-15之间、PA+的防晒护肤品。","type":"uv"},{"brf":"不宜","txt":"不宜洗车，未来24小时内有雨，如果在此期间洗车，雨水和路上的泥水可能会再次弄脏您的爱车。","type":"cw"},{"brf":"良","txt":"气象条件有利于空气污染物稀释、扩散和清除，可在室外正常活动。","type":"air"}]
     * now : {"cloud":"50","cond_code":"104","cond_txt":"阴","fl":"24","hum":"80","pcpn":"0.0","pres":"1010","tmp":"22","vis":"20","wind_deg":"10","wind_dir":"北风","wind_sc":"1","wind_spd":"2"}
     * basic : {"admin_area":"浙江","cid":"CN101210502","cnty":"中国","lat":"29.71366119","location":"诸暨","lon":"120.24432373","parent_city":"绍兴","tz":"+8.00"}
     * status : ok
     * update : {"loc":"2018-09-07 14:46","utc":"2018-09-07 06:46"}
     */

    private now now;
    private basic basic;
    private String status;
    private update update;
    private List<daily_forecast> daily_forecast;
    private List<lifestyle> lifestyle;

    public now getNow() {
        return now;
    }

    public void setNow(now now) {
        this.now = now;
    }

    public basic getBasic() {
        return basic;
    }

    public void setBasic(basic basic) {
        this.basic = basic;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public update getUpdate() {
        return update;
    }

    public void setUpdate(update update) {
        this.update = update;
    }

    public List<daily_forecast> getDaily_forecast() {
        return daily_forecast;
    }

    public void setDaily_forecast(List<daily_forecast> daily_forecast) {
        this.daily_forecast = daily_forecast;
    }

    public List<lifestyle> getLifestyle() {
        return lifestyle;
    }

    public void setLifestyle(List<lifestyle> lifestyle) {
        this.lifestyle = lifestyle;
    }

    public static class now {
        /**
         * cloud : 50
         * cond_code : 104
         * cond_txt : 阴
         * fl : 24
         * hum : 80
         * pcpn : 0.0
         * pres : 1010
         * tmp : 22
         * vis : 20
         * wind_deg : 10
         * wind_dir : 北风
         * wind_sc : 1
         * wind_spd : 2
         */

        private String cloud;
        private String cond_code;
        private String cond_txt;
        private String fl;
        private String hum;
        private String pcpn;
        private String pres;
        private String tmp;
        private String vis;
        private String wind_deg;
        private String wind_dir;
        private String wind_sc;
        private String wind_spd;

        public String getCloud() {
            return cloud;
        }

        public void setCloud(String cloud) {
            this.cloud = cloud;
        }

        public String getCond_code() {
            return cond_code;
        }

        public void setCond_code(String cond_code) {
            this.cond_code = cond_code;
        }

        public String getCond_txt() {
            return cond_txt;
        }

        public void setCond_txt(String cond_txt) {
            this.cond_txt = cond_txt;
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

        public String getWind_deg() {
            return wind_deg;
        }

        public void setWind_deg(String wind_deg) {
            this.wind_deg = wind_deg;
        }

        public String getWind_dir() {
            return wind_dir;
        }

        public void setWind_dir(String wind_dir) {
            this.wind_dir = wind_dir;
        }

        public String getWind_sc() {
            return wind_sc;
        }

        public void setWind_sc(String wind_sc) {
            this.wind_sc = wind_sc;
        }

        public String getWind_spd() {
            return wind_spd;
        }

        public void setWind_spd(String wind_spd) {
            this.wind_spd = wind_spd;
        }
    }

    public static class basic {
        /**
         * admin_area : 浙江
         * cid : CN101210502
         * cnty : 中国
         * lat : 29.71366119
         * location : 诸暨
         * lon : 120.24432373
         * parent_city : 绍兴
         * tz : +8.00
         */

        private String admin_area;
        private String cid;
        private String cnty;
        private String lat;
        private String location;
        private String lon;
        private String parent_city;
        private String tz;

        public String getAdmin_area() {
            return admin_area;
        }

        public void setAdmin_area(String admin_area) {
            this.admin_area = admin_area;
        }

        public String getCid() {
            return cid;
        }

        public void setCid(String cid) {
            this.cid = cid;
        }

        public String getCnty() {
            return cnty;
        }

        public void setCnty(String cnty) {
            this.cnty = cnty;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getLon() {
            return lon;
        }

        public void setLon(String lon) {
            this.lon = lon;
        }

        public String getParent_city() {
            return parent_city;
        }

        public void setParent_city(String parent_city) {
            this.parent_city = parent_city;
        }

        public String getTz() {
            return tz;
        }

        public void setTz(String tz) {
            this.tz = tz;
        }
    }

    public static class update {
        /**
         * loc : 2018-09-07 14:46
         * utc : 2018-09-07 06:46
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

    public static class daily_forecast {
        /**
         * cond_code_d : 300
         * cond_code_n : 300
         * cond_txt_d : 阵雨
         * cond_txt_n : 阵雨
         * date : 2018-09-07
         * hum : 79
         * mr : 02:30
         * ms : 16:31
         * pcpn : 0.0
         * pop : 25
         * pres : 1009
         * sr : 05:39
         * ss : 18:12
         * tmp_max : 25
         * tmp_min : 20
         * uv_index : 4
         * vis : 18
         * wind_deg : -1
         * wind_dir : 无持续风向
         * wind_sc : 1-2
         * wind_spd : 11
         */

        private String cond_code_d;
        private String cond_code_n;
        private String cond_txt_d;
        private String cond_txt_n;
        private String date;
        private String hum;
        private String mr;
        private String ms;
        private String pcpn;
        private String pop;
        private String pres;
        private String sr;
        private String ss;
        private String tmp_max;
        private String tmp_min;
        private String uv_index;
        private String vis;
        private String wind_deg;
        private String wind_dir;
        private String wind_sc;
        private String wind_spd;

        public String getCond_code_d() {
            return cond_code_d;
        }

        public void setCond_code_d(String cond_code_d) {
            this.cond_code_d = cond_code_d;
        }

        public String getCond_code_n() {
            return cond_code_n;
        }

        public void setCond_code_n(String cond_code_n) {
            this.cond_code_n = cond_code_n;
        }

        public String getCond_txt_d() {
            return cond_txt_d;
        }

        public void setCond_txt_d(String cond_txt_d) {
            this.cond_txt_d = cond_txt_d;
        }

        public String getCond_txt_n() {
            return cond_txt_n;
        }

        public void setCond_txt_n(String cond_txt_n) {
            this.cond_txt_n = cond_txt_n;
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

        public String getMr() {
            return mr;
        }

        public void setMr(String mr) {
            this.mr = mr;
        }

        public String getMs() {
            return ms;
        }

        public void setMs(String ms) {
            this.ms = ms;
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

        public String getTmp_max() {
            return tmp_max;
        }

        public void setTmp_max(String tmp_max) {
            this.tmp_max = tmp_max;
        }

        public String getTmp_min() {
            return tmp_min;
        }

        public void setTmp_min(String tmp_min) {
            this.tmp_min = tmp_min;
        }

        public String getUv_index() {
            return uv_index;
        }

        public void setUv_index(String uv_index) {
            this.uv_index = uv_index;
        }

        public String getVis() {
            return vis;
        }

        public void setVis(String vis) {
            this.vis = vis;
        }

        public String getWind_deg() {
            return wind_deg;
        }

        public void setWind_deg(String wind_deg) {
            this.wind_deg = wind_deg;
        }

        public String getWind_dir() {
            return wind_dir;
        }

        public void setWind_dir(String wind_dir) {
            this.wind_dir = wind_dir;
        }

        public String getWind_sc() {
            return wind_sc;
        }

        public void setWind_sc(String wind_sc) {
            this.wind_sc = wind_sc;
        }

        public String getWind_spd() {
            return wind_spd;
        }

        public void setWind_spd(String wind_spd) {
            this.wind_spd = wind_spd;
        }
    }

    public static class lifestyle {
        /**
         * brf : 舒适
         * txt : 白天不太热也不冷，风力不大，相信您在这样的天气条件下，应会感到比较清爽和舒适。
         * type : comf
         */

        private String brf;
        private String txt;
        private String type;

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

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
