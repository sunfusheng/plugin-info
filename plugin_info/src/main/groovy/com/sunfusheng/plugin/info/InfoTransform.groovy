package com.sunfusheng.plugin.info

import com.android.build.api.transform.*
import com.android.build.gradle.internal.pipeline.TransformManager
import org.apache.commons.io.FileUtils
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
                println TAG + '1 jarInput.name:' + jarInput.name

                def destFile = outputProvider.getContentLocation(jarInput.name, jarInput.contentTypes, jarInput.scopes, Format.JAR)
                FileUtils.copyFile(jarInput.file, destFile)
            }

            input.directoryInputs.each { DirectoryInput directoryInput ->
                println TAG + '2 [directoryInput.name]:' + directoryInput.name + ' [directoryInput.path]:' + directoryInput.getFile().path

                directoryInput.file.eachFileRecurse { File file ->
                    println TAG + '3' +
                            ' [isFile]:' + file.isFile() +
                            ' [file.name]:' + file.name +
                            ' [file.path]:' + file.path
                }

                def destDir = outputProvider.getContentLocation(directoryInput.name, directoryInput.contentTypes, directoryInput.scopes, Format.DIRECTORY)
                FileUtils.copyDirectory(directoryInput.file, destDir)
            }
        }
    }

}