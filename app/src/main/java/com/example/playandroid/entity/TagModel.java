package com.example.playandroid.entity;

/**
 * 储存流式布局的子控件的"位置信息MarginModel"和"自身信息实体类"
 * */
public class TagModel<T> {
    private T t;
    private MarginModel mMarginModel;

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }

    public MarginModel getMarginModel() {
        return mMarginModel;
    }

    public void setMarginModel(MarginModel marginModel) {
        this.mMarginModel = marginModel;
    }
}
