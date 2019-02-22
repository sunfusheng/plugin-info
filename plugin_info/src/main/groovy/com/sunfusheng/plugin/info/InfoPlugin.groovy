package com.sunfusheng.plugin.info

import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * @author sunfusheng on 2019/2/22.
 */
class InfoPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        println '[' + project.name + '] apply plugin.'
    }
}
