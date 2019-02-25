package com.sunfusheng.plugin.info

import com.android.build.api.transform.*
import com.android.build.gradle.internal.pipeline.TransformManager
import org.apache.commons.io.FileUtils
import org.gradle.api.Project

/**
 * @author sunfusheng on 2019/2/23.
 */
class InfoTransform extends Transform {
    Project project
    InjectAction injectAction

    InfoTransform(Project project, InjectAction injectAction) {
        this.project = project
        this.injectAction = injectAction
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
                println '--->【jarInput.name】:' + jarInput.name

                injectAction.addJar(jarInput)

                def destFile = outputProvider.getContentLocation(jarInput.name, jarInput.contentTypes, jarInput.scopes, Format.JAR)
                FileUtils.copyFile(jarInput.file, destFile)
            }

            input.directoryInputs.each { DirectoryInput directoryInput ->
                println '--->【directoryInput.name】:' + directoryInput.name

                injectAction.inject(directoryInput)

                def destDir = outputProvider.getContentLocation(directoryInput.name, directoryInput.contentTypes, directoryInput.scopes, Format.DIRECTORY)
                FileUtils.copyDirectory(directoryInput.file, destDir)
            }
        }
    }

}
