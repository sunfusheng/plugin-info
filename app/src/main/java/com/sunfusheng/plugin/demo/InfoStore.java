package com.sunfusheng.plugin.demo;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author sunfusheng on 2019/2/23.
 */
public class InfoStore {

    private static final Map<String, List<String>> infoMap = new HashMap<>();

    public InfoStore() {
    }

    private void add(String key, String value) {
        if (!TextUtils.isEmpty(key) && !TextUtils.isEmpty(value)) {
            List<String> list = infoMap.get(key);
            if (list == null) {
                list = new ArrayList<>();
                infoMap.put(key, list);
            }
            if (!list.contains(value)) {
                list.add(value);
            }
        }
    }

    public List<List<String>> getLists() {
        List<List<String>> lists = new ArrayList<>();
        for (Map.Entry<String, List<String>> entry : infoMap.entrySet()) {
            List<String> list = entry.getValue();
            if (list != null && list.size() > 0) {
                list.add(0, entry.getKey());
                lists.add(list);
            }
        }
        return lists;
    }
}
