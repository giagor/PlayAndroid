package com.example.playandroid.data;

import com.example.playandroid.entity.Project;

import java.util.List;

public interface ProjectModel {
    void getProjects(OnListener onListener);

    interface OnListener {
        /**
         * 成功得到项目列表.
         */
        void onSuccess(List<Project> projects);

        /**
         * 拿项目列表时出错.
         * */
        void onFail(Exception e);
    }
}
