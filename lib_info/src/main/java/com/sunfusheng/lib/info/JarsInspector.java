package com.sunfusheng.lib.info;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sunfusheng on 2019/2/23.
 */
public class JarsInspector {

    private final List<String> list = new ArrayList<>();

    public JarsInspector() {
    }

    private void add(String jarName) {
        if (!TextUtils.isEmpty(jarName) && !list.contains(jarName)) {
            list.add(jarName);
        }
    }

    public List<String> getJars() {
        return list;
    }
}
