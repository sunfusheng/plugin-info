package com.sunfusheng.plugin.info

import com.android.build.api.transform.DirectoryInput
import com.android.build.api.transform.JarInput
import com.android.build.api.transform.QualifiedContent
import com.android.build.gradle.AppExtension
import javassist.ClassPool
import javassist.CtClass
import javassist.CtConstructor
import org.gradle.api.Project
/**
 * @author sunfusheng on 2019/2/23.
 */
class InjectAction {
    Project project
    Map<String, List<String>> infoMap = new HashMap<>()
    ClassPool pool = ClassPool.getDefault();

    InjectAction(Project project) {
        this.project = project
    }

    void addBuildTime(String timeConsumedDesc) {
        def key = "编译时间"
        def list = infoMap.get(key)
        if (list == null) {
            list = new ArrayList<String>()
            infoMap.put(key, list)
        }
        if (!list.contains(timeConsumedDesc)) {
            list.add(timeConsumedDesc)
        }
    }

    void addJar(JarInput jarInput) {
        if (jarInput.scopes.first() == QualifiedContent.Scope.EXTERNAL_LIBRARIES) {
            def key = "第三方库"
            def list = infoMap.get(key)
            if (list == null) {
                list = new ArrayList<String>()
                infoMap.put(key, list)
            }
            if (!list.contains(jarInput.name)) {
                list.add(jarInput.name)
            }
        }
    }

    void inject(DirectoryInput directoryInput) {
        def path = directoryInput.file.path
        pool.appendClassPath(path)
        pool.appendClassPath(project.extensions.findByType(AppExtension).bootClasspath[0].toString())

        try {
            directoryInput.file.eachFileRecurse { File file ->
                if (file.name == 'InfoStore.class') {
                    CtClass ctClass = pool.getCtClass('com.sunfusheng.plugin.demo.InfoStore')
                    if (ctClass.isFrozen()) {
                        ctClass.defrost()
                    }
                    CtConstructor ctConstructor = ctClass.getConstructor('()V')
                    infoMap.each { Map.Entry<String, List<String>> entry ->
                        entry.value.each {
                            String insertCode = """add("$entry.key", "$it");"""
                            ctConstructor.insertAfter(insertCode)
                        }
                    }
                    ctClass.writeFile(path)
                    ctClass.detach()
                }
            }
        } catch (Exception e) {
            e.printStackTrace()
        }
    }

}
