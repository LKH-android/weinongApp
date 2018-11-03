package com.example.john.weinong;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by john on 2018/9/10.
 */

public class NewsBean {
    /**
     * ret_code : 0
     * pagebean : {"allPages":536,"contentlist":[{"id":"9c5fc3342cd8fd0c357bdc9874979dce","pubDate":"2018-09-10 18:35:55","havePic":true,"channelName":"财经最新","title":"农业农村部副部长屈冬玉会见澳大利亚塔斯马尼亚州州长","desc":"屈冬玉表示，中澳农业交流与合作关系良好，中国是澳大利亚第一大农产品出口目的国。屈冬玉建议双方进一步加强技术、教育和科技领域合作，积极开展食品安全检测技术和生物安全技术合作，推动渔业、园艺、畜牧业合作，拓展一二三产业融合发展，从农、牧、渔、林、园艺和旅游结合提升价值链等方面助力双方合作，实现农业可持续发展。","imageurls":[{"height":0,"width":0,"url":"http://getimg.jrj.com.cn/images/2018/09/weixin/one_20180909210149577.jpg"},{"height":0,"width":0,"url":"http://chart.jrjimg.cn/pngdata/kpic/pic430/601958.png"},{"height":0,"width":0,"url":"http://chart.jrjimg.cn/pngdata/kpic/pic430/600248.png"},{"height":0,"width":0,"url":"http://chart.jrjimg.cn/pngdata/kpic/pic430/603505.png"},{"height":0,"width":0,"url":"http://chart.jrjimg.cn/pngdata/kpic/pic430/002167.png"},{"height":0,"width":0,"url":"http://chart.jrjimg.cn/pngdata/kpic/pic430/603903.png"},{"height":0,"width":0,"url":"http://chart.jrjimg.cn/pngdata/kpic/pic430/600239.png"},{"height":0,"width":0,"url":"http://chart.jrjimg.cn/pngdata/kpic/pic430/300431.png"}],"source":"金融界","channelId":"5572a109b3cdc86cf39001e0","nid":"1083484967841411087","link":"http://stock.jrj.com.cn/2018/09/10175025070740.shtml"},{"id":"556f28ed65971a50bab02eb90249db04","pubDate":"2018-09-10 17:57:01","havePic":true,"channelName":"财经最新","title":"中俄农业合作与农产品贸易活跃 俄对中国出口农产品创历史新高","desc":"2017年，中俄农产品贸易额突破40亿美元。","imageurls":[{"height":0,"width":0,"url":"http://t11.baidu.com/it/u=4061511265,4157713838&fm=173&app=25&f=JPEG?w=543&h=341&s=E0541AD4B4CA574D1498CB7403004074"},{"height":0,"width":0,"url":"http://t12.baidu.com/it/u=668355689,1324816&fm=173&app=25&f=JPEG?w=545&h=345&s=AF943CC9004A437452B93C150300D0CA"},{"height":0,"width":0,"url":"http://t11.baidu.com/it/u=3156148714,856167920&fm=173&app=25&f=JPEG?w=548&h=351&s=8F6AE714C7EA6509185830D5030050F0"},{"height":0,"width":0,"url":"http://t11.baidu.com/it/u=3279254395,2983075253&fm=173&app=25&f=JPEG?w=545&h=391&s=6EF872D94AB2DDDE1888643C0300E054"},{"height":0,"width":0,"url":"http://t10.baidu.com/it/u=3723620718,2850887027&fm=173&app=25&f=JPEG?w=561&h=353&s=85D01CC44523313409D9B4A203007090"},{"height":0,"width":0,"url":"http://t12.baidu.com/it/u=2020227794,2128873269&fm=173&app=25&f=JPEG?w=556&h=358&s=4D50ED1209DF6DC81ADC4DD40300C0B0"}],"source":"国际在线","channelId":"5572a109b3cdc86cf39001e0","nid":"8684816024239350794","link":"http://baijiahao.baidu.com/s?id=1611204105608035798&wfr=newsapp"},{"id":"4eeec7ec17c17555cbdc10a47ceb2d41","pubDate":"2018-09-10 15:46:07","havePic":true,"channelName":"财经最新","title":"农产品期货多数收涨，沥青、豆一尾盘涨停","desc":"沥青收涨6%，豆一涨5%，焦煤涨逾3%，沪铅、郑醇涨逾2%，豆粕、郑煤、苹果、螺纹、菜粕、原油...","imageurls":[{"height":0,"width":0,"url":"https://timg01.bdimg.com/timg?pacompress&imgtype=3&sec=1439619614&autorotate=1&di=9da7deef765381841b787b53c5f00ffe&quality=90&size=b200_200&src=http%3A%2F%2Fbos.nj.bpc.baidu.com%2Fv1%2Fmediaspot%2F939aad93d90b0811993b3ff04be3ceca.png"},{"height":0,"width":0,"url":"http://t12.baidu.com/it/u=2159711891,157004736&fm=173&app=25&f=JPEG?w=614&h=385&s=455433C2D3F2B3CA4C714C0E020050C2"}],"source":"证券之星","channelId":"5572a109b3cdc86cf39001e0","nid":"8858105147101940584","link":"http://baijiahao.baidu.com/s?id=1611203579517355393&wfr=newsapp"}],"currentPage":1,"allNum":1607,"maxResult":3}
     */

    private int ret_code;
    private pagebean pagebean;

    public static NewsBean objectFromData(String str) {

        return new Gson().fromJson(str, NewsBean.class);
    }

    public static NewsBean objectFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getString(str), NewsBean.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<NewsBean> arrayNewsBeanFromData(String str) {

        Type listType = new TypeToken<ArrayList<NewsBean>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public static List<NewsBean> arrayNewsBeanFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);
            Type listType = new TypeToken<ArrayList<NewsBean>>() {
            }.getType();

            return new Gson().fromJson(jsonObject.getString(str), listType);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new ArrayList();


    }

    public int getRet_code() {
        return ret_code;
    }

    public void setRet_code(int ret_code) {
        this.ret_code = ret_code;
    }

    public pagebean getPagebean() {
        return pagebean;
    }

    public void setPagebean(pagebean pagebean) {
        this.pagebean = pagebean;
    }

    public static class pagebean {
        /**
         * allPages : 536
         * contentlist : [{"id":"9c5fc3342cd8fd0c357bdc9874979dce","pubDate":"2018-09-10 18:35:55","havePic":true,"channelName":"财经最新","title":"农业农村部副部长屈冬玉会见澳大利亚塔斯马尼亚州州长","desc":"屈冬玉表示，中澳农业交流与合作关系良好，中国是澳大利亚第一大农产品出口目的国。屈冬玉建议双方进一步加强技术、教育和科技领域合作，积极开展食品安全检测技术和生物安全技术合作，推动渔业、园艺、畜牧业合作，拓展一二三产业融合发展，从农、牧、渔、林、园艺和旅游结合提升价值链等方面助力双方合作，实现农业可持续发展。","imageurls":[{"height":0,"width":0,"url":"http://getimg.jrj.com.cn/images/2018/09/weixin/one_20180909210149577.jpg"},{"height":0,"width":0,"url":"http://chart.jrjimg.cn/pngdata/kpic/pic430/601958.png"},{"height":0,"width":0,"url":"http://chart.jrjimg.cn/pngdata/kpic/pic430/600248.png"},{"height":0,"width":0,"url":"http://chart.jrjimg.cn/pngdata/kpic/pic430/603505.png"},{"height":0,"width":0,"url":"http://chart.jrjimg.cn/pngdata/kpic/pic430/002167.png"},{"height":0,"width":0,"url":"http://chart.jrjimg.cn/pngdata/kpic/pic430/603903.png"},{"height":0,"width":0,"url":"http://chart.jrjimg.cn/pngdata/kpic/pic430/600239.png"},{"height":0,"width":0,"url":"http://chart.jrjimg.cn/pngdata/kpic/pic430/300431.png"}],"source":"金融界","channelId":"5572a109b3cdc86cf39001e0","nid":"1083484967841411087","link":"http://stock.jrj.com.cn/2018/09/10175025070740.shtml"},{"id":"556f28ed65971a50bab02eb90249db04","pubDate":"2018-09-10 17:57:01","havePic":true,"channelName":"财经最新","title":"中俄农业合作与农产品贸易活跃 俄对中国出口农产品创历史新高","desc":"2017年，中俄农产品贸易额突破40亿美元。","imageurls":[{"height":0,"width":0,"url":"http://t11.baidu.com/it/u=4061511265,4157713838&fm=173&app=25&f=JPEG?w=543&h=341&s=E0541AD4B4CA574D1498CB7403004074"},{"height":0,"width":0,"url":"http://t12.baidu.com/it/u=668355689,1324816&fm=173&app=25&f=JPEG?w=545&h=345&s=AF943CC9004A437452B93C150300D0CA"},{"height":0,"width":0,"url":"http://t11.baidu.com/it/u=3156148714,856167920&fm=173&app=25&f=JPEG?w=548&h=351&s=8F6AE714C7EA6509185830D5030050F0"},{"height":0,"width":0,"url":"http://t11.baidu.com/it/u=3279254395,2983075253&fm=173&app=25&f=JPEG?w=545&h=391&s=6EF872D94AB2DDDE1888643C0300E054"},{"height":0,"width":0,"url":"http://t10.baidu.com/it/u=3723620718,2850887027&fm=173&app=25&f=JPEG?w=561&h=353&s=85D01CC44523313409D9B4A203007090"},{"height":0,"width":0,"url":"http://t12.baidu.com/it/u=2020227794,2128873269&fm=173&app=25&f=JPEG?w=556&h=358&s=4D50ED1209DF6DC81ADC4DD40300C0B0"}],"source":"国际在线","channelId":"5572a109b3cdc86cf39001e0","nid":"8684816024239350794","link":"http://baijiahao.baidu.com/s?id=1611204105608035798&wfr=newsapp"},{"id":"4eeec7ec17c17555cbdc10a47ceb2d41","pubDate":"2018-09-10 15:46:07","havePic":true,"channelName":"财经最新","title":"农产品期货多数收涨，沥青、豆一尾盘涨停","desc":"沥青收涨6%，豆一涨5%，焦煤涨逾3%，沪铅、郑醇涨逾2%，豆粕、郑煤、苹果、螺纹、菜粕、原油...","imageurls":[{"height":0,"width":0,"url":"https://timg01.bdimg.com/timg?pacompress&imgtype=3&sec=1439619614&autorotate=1&di=9da7deef765381841b787b53c5f00ffe&quality=90&size=b200_200&src=http%3A%2F%2Fbos.nj.bpc.baidu.com%2Fv1%2Fmediaspot%2F939aad93d90b0811993b3ff04be3ceca.png"},{"height":0,"width":0,"url":"http://t12.baidu.com/it/u=2159711891,157004736&fm=173&app=25&f=JPEG?w=614&h=385&s=455433C2D3F2B3CA4C714C0E020050C2"}],"source":"证券之星","channelId":"5572a109b3cdc86cf39001e0","nid":"8858105147101940584","link":"http://baijiahao.baidu.com/s?id=1611203579517355393&wfr=newsapp"}]
         * currentPage : 1
         * allNum : 1607
         * maxResult : 3
         */

        private int allPages;
        private int currentPage;
        private int allNum;
        private int maxResult;
        private List<contentlist> contentlist;

        public static pagebean objectFromData(String str) {

            return new Gson().fromJson(str, pagebean.class);
        }

        public static pagebean objectFromData(String str, String key) {

            try {
                JSONObject jsonObject = new JSONObject(str);

                return new Gson().fromJson(jsonObject.getString(str), pagebean.class);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        public static List<pagebean> arraypagebeanFromData(String str) {

            Type listType = new TypeToken<ArrayList<pagebean>>() {
            }.getType();

            return new Gson().fromJson(str, listType);
        }

        public static List<pagebean> arraypagebeanFromData(String str, String key) {

            try {
                JSONObject jsonObject = new JSONObject(str);
                Type listType = new TypeToken<ArrayList<pagebean>>() {
                }.getType();

                return new Gson().fromJson(jsonObject.getString(str), listType);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return new ArrayList();


        }

        public int getAllPages() {
            return allPages;
        }

        public void setAllPages(int allPages) {
            this.allPages = allPages;
        }

        public int getCurrentPage() {
            return currentPage;
        }

        public void setCurrentPage(int currentPage) {
            this.currentPage = currentPage;
        }

        public int getAllNum() {
            return allNum;
        }

        public void setAllNum(int allNum) {
            this.allNum = allNum;
        }

        public int getMaxResult() {
            return maxResult;
        }

        public void setMaxResult(int maxResult) {
            this.maxResult = maxResult;
        }

        public List<contentlist> getContentlist() {
            return contentlist;
        }

        public void setContentlist(List<contentlist> contentlist) {
            this.contentlist = contentlist;
        }

        public static class contentlist {
            /**
             * id : 9c5fc3342cd8fd0c357bdc9874979dce
             * pubDate : 2018-09-10 18:35:55
             * havePic : true
             * channelName : 财经最新
             * title : 农业农村部副部长屈冬玉会见澳大利亚塔斯马尼亚州州长
             * desc : 屈冬玉表示，中澳农业交流与合作关系良好，中国是澳大利亚第一大农产品出口目的国。屈冬玉建议双方进一步加强技术、教育和科技领域合作，积极开展食品安全检测技术和生物安全技术合作，推动渔业、园艺、畜牧业合作，拓展一二三产业融合发展，从农、牧、渔、林、园艺和旅游结合提升价值链等方面助力双方合作，实现农业可持续发展。
             * imageurls : [{"height":0,"width":0,"url":"http://getimg.jrj.com.cn/images/2018/09/weixin/one_20180909210149577.jpg"},{"height":0,"width":0,"url":"http://chart.jrjimg.cn/pngdata/kpic/pic430/601958.png"},{"height":0,"width":0,"url":"http://chart.jrjimg.cn/pngdata/kpic/pic430/600248.png"},{"height":0,"width":0,"url":"http://chart.jrjimg.cn/pngdata/kpic/pic430/603505.png"},{"height":0,"width":0,"url":"http://chart.jrjimg.cn/pngdata/kpic/pic430/002167.png"},{"height":0,"width":0,"url":"http://chart.jrjimg.cn/pngdata/kpic/pic430/603903.png"},{"height":0,"width":0,"url":"http://chart.jrjimg.cn/pngdata/kpic/pic430/600239.png"},{"height":0,"width":0,"url":"http://chart.jrjimg.cn/pngdata/kpic/pic430/300431.png"}]
             * source : 金融界
             * channelId : 5572a109b3cdc86cf39001e0
             * nid : 1083484967841411087
             * link : http://stock.jrj.com.cn/2018/09/10175025070740.shtml
             */

            private String id;
            private String pubDate;
            private boolean havePic;
            private String channelName;
            private String title;
            private String desc;
            private String source;
            private String channelId;
            private String nid;
            private String link;
            private List<imageurls> imageurls;

            public static contentlist objectFromData(String str) {

                return new Gson().fromJson(str, contentlist.class);
            }

            public static contentlist objectFromData(String str, String key) {

                try {
                    JSONObject jsonObject = new JSONObject(str);

                    return new Gson().fromJson(jsonObject.getString(str), contentlist.class);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return null;
            }

            public static List<contentlist> arraycontentlistFromData(String str) {

                Type listType = new TypeToken<ArrayList<contentlist>>() {
                }.getType();

                return new Gson().fromJson(str, listType);
            }

            public static List<contentlist> arraycontentlistFromData(String str, String key) {

                try {
                    JSONObject jsonObject = new JSONObject(str);
                    Type listType = new TypeToken<ArrayList<contentlist>>() {
                    }.getType();

                    return new Gson().fromJson(jsonObject.getString(str), listType);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return new ArrayList();


            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getPubDate() {
                return pubDate;
            }

            public void setPubDate(String pubDate) {
                this.pubDate = pubDate;
            }

            public boolean isHavePic() {
                return havePic;
            }

            public void setHavePic(boolean havePic) {
                this.havePic = havePic;
            }

            public String getChannelName() {
                return channelName;
            }

            public void setChannelName(String channelName) {
                this.channelName = channelName;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }

            public String getSource() {
                return source;
            }

            public void setSource(String source) {
                this.source = source;
            }

            public String getChannelId() {
                return channelId;
            }

            public void setChannelId(String channelId) {
                this.channelId = channelId;
            }

            public String getNid() {
                return nid;
            }

            public void setNid(String nid) {
                this.nid = nid;
            }

            public String getLink() {
                return link;
            }

            public void setLink(String link) {
                this.link = link;
            }

            public List<imageurls> getImageurls() {
                return imageurls;
            }

            public void setImageurls(List<imageurls> imageurls) {
                this.imageurls = imageurls;
            }

            public static class imageurls {
                /**
                 * height : 0
                 * width : 0
                 * url : http://getimg.jrj.com.cn/images/2018/09/weixin/one_20180909210149577.jpg
                 */

                private int height;
                private int width;
                private String url;

                public static imageurls objectFromData(String str) {

                    return new Gson().fromJson(str, imageurls.class);
                }

                public static imageurls objectFromData(String str, String key) {

                    try {
                        JSONObject jsonObject = new JSONObject(str);

                        return new Gson().fromJson(jsonObject.getString(str), imageurls.class);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    return null;
                }

                public static List<imageurls> arrayimageurlsFromData(String str) {

                    Type listType = new TypeToken<ArrayList<imageurls>>() {
                    }.getType();

                    return new Gson().fromJson(str, listType);
                }

                public static List<imageurls> arrayimageurlsFromData(String str, String key) {

                    try {
                        JSONObject jsonObject = new JSONObject(str);
                        Type listType = new TypeToken<ArrayList<imageurls>>() {
                        }.getType();

                        return new Gson().fromJson(jsonObject.getString(str), listType);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    return new ArrayList();


                }

                public int getHeight() {
                    return height;
                }

                public void setHeight(int height) {
                    this.height = height;
                }

                public int getWidth() {
                    return width;
                }

                public void setWidth(int width) {
                    this.width = width;
                }

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }
            }
        }
    }
}
