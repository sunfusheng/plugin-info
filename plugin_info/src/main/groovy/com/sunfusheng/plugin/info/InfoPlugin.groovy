package com.sunfusheng.plugin.info

import com.android.build.gradle.AppExtension
import com.android.build.gradle.AppPlugin
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * @author sunfusheng on 2019/2/22.
 */
class InfoPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        def isApp = project.plugins.hasPlugin(AppPlugin.class)
        if (isApp) {
            def android = project.extensions.findByType(AppExtension.class)
            InjectAction injectAction = new InjectAction(project)
            android.registerTransform(new InfoTransform(project, injectAction))
            project.gradle.addListener(new TaskBuildListener(injectAction))
        }
    }
}
