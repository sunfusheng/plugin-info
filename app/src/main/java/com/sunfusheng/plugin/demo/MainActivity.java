package com.sunfusheng.plugin.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import com.sunfusheng.lib.info.JarsInspector;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView vInfo = findViewById(R.id.vInfo);

        StringBuilder sb = new StringBuilder();
        List<String> jars = new JarsInspector().getJars();
        for (String jarName : jars) {
            sb.append(jarName).append('\n');
        }
        vInfo.setText(sb);
    }
}
