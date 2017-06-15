package com.jf.studentjfmusic;


/**
 * 存储app中的常量
 * 比如：url链接
 * Created by weidong on 2017/6/9.
 */

public class Constant {

    /**
     * 存储app所有的接口地址
     */
    public static class URL {
        //首页数据接口
        public final static String HOME = "https://leancloud.cn:443/1.1/classes/Home";
        //大小写转换  Ctrl + shift + u
        public final static String NEWPLAYLIST = "https://leancloud.cn:443/1.1/classes/NewPlayList";
    }

    public static class Action{
        public final static String PLAY = "com.jf.studentjfmusic.action_play";


    }


}
