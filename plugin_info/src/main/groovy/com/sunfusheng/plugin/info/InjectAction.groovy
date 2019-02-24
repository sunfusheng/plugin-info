package com.sunfusheng.plugin.info

import com.android.build.api.transform.JarInput
import org.gradle.api.Project;

/**
 * @author sunfusheng on 2019/2/23.
 */
public class InjectAction {
    static final excludeJar = 'lib_info'

    Project project
    InfoCache infoCache

    InjectAction(Project project) {
        this.project = project
        this.infoCache = new InfoCache()
    }

    void inject(JarInput jarInput) {
        if (jarInput.name.endsWith('.jar')) {
            infoCache.addJar(jarInput.name)
        }
    }
}
