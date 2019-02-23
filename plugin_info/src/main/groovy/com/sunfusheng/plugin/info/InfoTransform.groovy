package com.sunfusheng.plugin.info

import com.android.build.api.transform.*
import com.android.build.gradle.internal.pipeline.TransformManager
import org.gradle.api.Project

/**
 * @author sunfusheng on 2019/2/23.
 */
class InfoTransform extends Transform {
    private static final String TAG = '---> '

    Project project

    InfoTransform(Project project) {
        this.project = project
    }

    @Override
    String getName() {
        return 'InfoTransform'
    }

    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        println TAG + 'getInputTypes()'
        return TransformManager.CONTENT_CLASS
    }

    @Override
    Set<? super QualifiedContent.Scope> getScopes() {
        return TransformManager.PROJECT_ONLY
    }

    @Override
    boolean isIncremental() {
        return false
    }

    @Override
    void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {
        super.transform(transformInvocation)

        transformInvocation.inputs.each { TransformInput input ->
            input.jarInputs.each { JarInput jar ->
                println TAG + '1 jar.name:' + jar.name
            }

            input.directoryInputs.each { DirectoryInput dir ->
                println TAG + '2 [dir.name]:' + dir.name + ' [dir.path]:'+dir.getFile().path

                dir.file.eachFileRecurse { File file ->
                    println TAG + '3' +
                            ' [isFile]:' + file.isFile() +
                            ' [file.name]:' + file.name +
                            ' [file.path]:' + file.path
                }
            }
        }

    }

}
