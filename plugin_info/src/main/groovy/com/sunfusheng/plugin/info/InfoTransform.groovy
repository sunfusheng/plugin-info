package com.sunfusheng.plugin.info

import com.android.build.api.transform.*
import com.android.build.gradle.internal.pipeline.TransformManager
import org.apache.commons.io.FileUtils
import org.gradle.api.Project

/**
 * @author sunfusheng on 2019/2/23.
 */
public class InfoTransform extends Transform {
    private static final String TAG = '---> '

    Project project
    InjectAction injectTask

    InfoTransform(Project project) {
        this.project = project
        this.injectTask = new InjectAction(project)
    }

    @Override
    String getName() {
        return 'InfoTransform'
    }

    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS
    }

    @Override
    Set<? super QualifiedContent.Scope> getScopes() {
        return TransformManager.SCOPE_FULL_PROJECT
    }

    @Override
    boolean isIncremental() {
        return false
    }

    @Override
    void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {
        super.transform(transformInvocation)

        TransformOutputProvider outputProvider = transformInvocation.outputProvider

        transformInvocation.inputs.each { TransformInput input ->
            input.jarInputs.each { JarInput jarInput ->
//                println TAG + '【jarInput.name】:' + jarInput.name

                injectTask.addJar(jarInput)

                def destFile = outputProvider.getContentLocation(jarInput.name, jarInput.contentTypes, jarInput.scopes, Format.JAR)
                FileUtils.copyFile(jarInput.file, destFile)
            }

            input.directoryInputs.each { DirectoryInput directoryInput ->
                println TAG + '【directoryInput.name】:' + directoryInput.name

                injectTask.inject(directoryInput)

//                directoryInput.file.eachFileRecurse { File file ->
//                    if (file.isFile()) {
//                        println TAG + '【file.name】:' + file.name + '【file.path】:'+file.path
//                    }
//                }

                def destDir = outputProvider.getContentLocation(directoryInput.name, directoryInput.contentTypes, directoryInput.scopes, Format.DIRECTORY)
                FileUtils.copyDirectory(directoryInput.file, destDir)
            }
        }
    }

}
