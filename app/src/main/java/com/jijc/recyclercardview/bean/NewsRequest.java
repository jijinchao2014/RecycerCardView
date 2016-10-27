package com.jijc.recyclercardview.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Description:
 * Created by jijc on 2016/8/29.
 * PackageName: com.jijc.recyclercardview.bean
 */
public class NewsRequest implements Serializable{
    public int errcode=0;
    public String message;
    public String server_time;
    public ReData data;

    public class ReData {
        public int count;
        public ArrayList<Recomment> list;

        public class Recomment implements Serializable{
            public int cat_id;    //宝贝看看 宝贝听听 ID
            public int id; //帖子id
            public String title;//	标题
            public String[] picUrl;    //	图片数组
            public String linkUrl;        //跳转地址
            public String recommendType;        //1 育儿头条/育儿锦囊 /广告/ 置顶    2 宝贝听听 3 宝贝看看
            public String sourceFrom;  // 来源
            public String goodNum;//  数量
            public int picType;    // 1  一张  2 三张 3 广告   4 无图

            public String stick;//热门
            public int isTop;//是否置顶 1：置顶
            public int postType; //是否是育儿锦囊 1：是 0：不是
        }
    }
}
