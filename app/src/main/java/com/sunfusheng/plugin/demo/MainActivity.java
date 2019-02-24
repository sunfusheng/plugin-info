package com.sunfusheng.plugin.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView vInfo = findViewById(R.id.vInfo);

        List<String> jars = new InfoStore().getLists().get(0);

        StringBuilder sb = new StringBuilder();
        for (String jarName : jars) {
            sb.append(jarName).append('\n');
        }
        vInfo.setText(sb);
    }
}
