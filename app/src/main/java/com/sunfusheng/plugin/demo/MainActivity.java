package com.sunfusheng.plugin.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.sunfusheng.StickyHeaderDecoration;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new StickyHeaderDecoration());
        List<List<String>> lists = new InfoStore().getLists();
        StickyGroupAdapter stickyAdapter = new StickyGroupAdapter(this, lists);
        recyclerView.setAdapter(stickyAdapter);
    }
}
