package com.sunfusheng.plugin.info

import com.android.build.gradle.AppExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * @author sunfusheng on 2019/2/22.
 */
class InfoPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        println "【$project.name】applys plugin"

        // AppExtension.class就是build.gradle中android{...}配置
        def android = project.extensions.findByType(AppExtension.class)

        // 注册自己的Transform
        android.registerTransform(new InfoTransform(project))
    }
}
