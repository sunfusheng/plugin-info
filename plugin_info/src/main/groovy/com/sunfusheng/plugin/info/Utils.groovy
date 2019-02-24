package com.sunfusheng.plugin.info

import java.util.jar.JarFile
import java.util.jar.JarOutputStream
import java.util.zip.Deflater
import java.util.zip.ZipEntry;

/**
 * @author sunfusheng on 2019/2/23.
 */
public class Utils {

    /**
     * 将该jar包解压到指定目录
     * @param jarPath jar包路径
     * @param destDirPath jar包解压后路径
     * @return isExclude
     */
    static boolean unzipJar(String jarPath, String destDirPath) {
        if (jarPath.endsWith('.jar')) {
            JarFile jarFile = new JarFile(jarPath)
            jarFile.entries().each { jarEntry ->
                if (!jarEntry.directory) {
                    def outFile = new File(destDirPath, jarEntry.name)
                    outFile.getParentFile().mkdirs()
                    def inputStream = jarFile.getInputStream(jarEntry)
                    def outputStream = new FileOutputStream(outFile)
                    outputStream << inputStream//这是groovy特有写法把右边输入到左右，比java简洁
                    outputStream.close()
                    inputStream.close()
                }
            }
            jarFile.close()
        }
        return false
    }

    /**
     * 重新打包jar
     * @param packagePath 目录文件
     * @param destPath jar包路径
     */
    static void zipJar(String packagePath, String destPath) {
        File dir = new File(packagePath)
        JarOutputStream outputStream = new JarOutputStream(new FileOutputStream(destPath))
        outputStream.setLevel(Deflater.BEST_SPEED)
        dir.eachFileRecurse { file ->
            if (!file.directory) {//此处需要把windows系统下的\\替换成/,否则在后续打包遍历jar会找不到文件
                def entryName = file.getAbsolutePath().substring(packagePath.length() + 1).replace("\\", "/")
                outputStream.putNextEntry(new ZipEntry(entryName))
                InputStream inputStream = new FileInputStream(file)
                outputStream << inputStream
                inputStream.close()
            }
        }
        outputStream.close()
    }
}
