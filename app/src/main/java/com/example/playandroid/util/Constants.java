package com.example.playandroid.util;

/**
 * 存放常量的类.
 */
public class Constants {

    /**
     * 主活动的常量.
     */
    public static class MainConstant {
        //碎片所对应的常量值.
        public static final int ARTICLE = 0;
        public static final int FRAME = 1;
        public static final int PROJECT = 2;
    }

    /**
     * 文章模块对应的常量.
     */
    public static class ArticleConstant {
        //网络请求对应的状态值
        public static final int SUCCESS = 0;
    }

    /**
     * 加载文章的网页对应的常量值.
     */
    public static class ArticleDetailConstant {
        //启动活动时，传入的数据所对应的键值
        public static final String TITLE = "title";
        public static final String URL = "url";
    }

    /**
     * 项目模块对应的常量值.
     */
    public static class ProjectConstant {
        //网络请求对应的状态值
        public static final int SUCCESS = 0;
    }

    /**
     * 子项目对应的常量值.
     */
    public static class ProjectChildConstant {
        //网络请求对应的状态值
        public static final int SUCCESS = 0;
    }

    /**
     * 搜索界面所对应的常量值.
     */
    public static class SearchConstant {
        //网络请求搜索热词的状态值
        public static final int HOT_WORD_SUCCESS = 0;

        //搜索成功
        public static final int SEARCH_SUCCESS = 10;
    }

    /**
     * 知识体系模块对应的常量值.
     * */
    public static class FrameConstant{
        //成功拿到知识体系(一级)的内容
        public static final int FRAME_SUCCESS = 0;
    }
}
