# PluginBuildInfo

### 第一个坑：编译找不到插件

由于Android Studio不能直接创建.groovy后缀的文件，所以需要先创建一个.java文件，然后修改其后缀。

### 第二个坑：编译后apk里无classes.dex

在从class到dex的构建过程中，不应该因为我们添加的Transform任务而中断中间转换文件的传递，否则会导致编译后apk里无classes.dex，我们必须将input的文件复制到output的目录中去！

    transformInvocation.inputs.each { TransformInput input ->
        input.jarInputs.each { JarInput jarInput ->
            def destFile = outputProvider.getContentLocation(jarInput.name, jarInput.contentTypes, jarInput.scopes, Format.JAR)
            FileUtils.copyFile(jarInput.file, destFile)
        }

        input.directoryInputs.each { DirectoryInput directoryInput ->
            def destDir = outputProvider.getContentLocation(directoryInput.name, directoryInput.contentTypes, directoryInput.scopes, Format.DIRECTORY)
            FileUtils.copyDirectory(directoryInput.file, destDir)
        }
    }