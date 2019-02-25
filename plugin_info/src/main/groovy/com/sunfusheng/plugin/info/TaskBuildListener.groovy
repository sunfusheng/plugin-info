package com.sunfusheng.plugin.info


import org.gradle.api.Task
import org.gradle.api.execution.TaskExecutionListener
import org.gradle.api.tasks.TaskState

/**
 * @author by sunfusheng on 2019/2/25
 */
class TaskBuildListener implements TaskExecutionListener {
    InjectAction injectAction
    long startTime

    TaskBuildListener(InjectAction injectAction) {
        this.injectAction = injectAction
    }

    @Override
    void beforeExecute(Task task) {
        startTime = System.currentTimeMillis()
    }

    @Override
    void afterExecute(Task task, TaskState taskState) {
        long time = System.currentTimeMillis() - startTime
        injectAction.addBuildTime(time + 'ms ' + task.path)
    }
}
