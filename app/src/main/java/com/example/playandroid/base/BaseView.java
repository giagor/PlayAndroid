package com.example.playandroid.base;

public interface BaseView<P extends BasePresenter> {
    void setPresenter(P presenter);
}
