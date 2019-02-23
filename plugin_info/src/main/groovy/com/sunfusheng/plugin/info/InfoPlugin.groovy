package com.sunfusheng.plugin.info

import com.android.build.gradle.BaseExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * @author sunfusheng on 2019/2/22.
 */
class InfoPlugin implements Plugin<Project> {
    private static final String TAG = getClass().canonicalName + ' : '

    @Override
    void apply(Project project) {
        println '【' + project.name + '】apply plugin.'

        // 注册Transform
        project.getExtensions()
                .findByType(BaseExtension.class)
                .registerTransform(new InfoTransform(project))
    }
}
