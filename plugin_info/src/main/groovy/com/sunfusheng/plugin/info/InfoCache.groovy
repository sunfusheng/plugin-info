package com.sunfusheng.plugin.info;

/**
 * @author sunfusheng on 2019/2/23.
 */
public class InfoCache {

    List<String> jars = new ArrayList<>();

    void addJar(String jarName) {
        if (!jars.contains(jarName)) {
            jars.add(jarName);
        }
    }
}
