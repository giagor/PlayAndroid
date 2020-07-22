package com.example.playandroid.data;

import com.example.playandroid.entity.Project;

import java.util.List;

/**
 * 获得一级项目的数据.
 */
public interface ProjectModel {
    void getProjects(OnListener onListener);

    interface OnListener {
        /**
         * 成功得到项目列表.
         */
        void onGetProjectSuccess(List<Project> projects);

        /**
         * 拿项目列表时出错.
         */
        void onGetProjectFailure(Exception e);
    }
}
