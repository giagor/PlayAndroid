package com.example.playandroid.data;

import com.example.playandroid.entity.ProjectChild;

import java.util.List;

/**
 * 获得子项目的数据.
 */
public interface ProjectChildModel {
    void getProjectChildren(OnListener onListener, long id);

    interface OnListener {
        void onGetProjectChildrenSuccess(List<ProjectChild> projectChildren);

        void onGetProjectChildrenFailure(Exception e);
    }
}
