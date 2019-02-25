# plugin-info

自定义gradle插件，应用在运行时显示编译时的信息：第三方库、编译时间

### 注意一：编译找不到插件

由于Android Studio不能直接创建.groovy后缀的文件，所以需要先创建一个.java文件，然后修改其后缀。

### 注意二：编译后apk里无classes.dex

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

### 注意三

由于 Javassist 的机制，在处理的过程中将会产生额外的没有实际作用的局部变量用以指向参数变量和保存返回值，知道就好不需要care。

### 注意四

编译时间仅仅是通过javac编译的时间，不包括后续的打包、签名的时间（这些信息可以在开发环境上看到，已经无法注入到class中去了）。