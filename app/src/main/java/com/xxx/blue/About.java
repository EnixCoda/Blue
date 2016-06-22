package com.xxx.blue;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class About extends AppCompatActivity {

    private ListView listView;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.title_activity_about);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);

        listView = (ListView)findViewById(R.id.listView);
        listView.setAdapter(adapter);

        adapter.add("开发团队成员：");
        adapter.add("Wu Yan");
        adapter.add("Li Zhen");
        adapter.add("Sun Shiyun");
        adapter.add("Yang Yixin");
        adapter.add("Zhang Lijuan");

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                switch(position) {
//                    case 6:
//                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.ecnu.edu.cn")));
//                        break;
//                }
            }
        });
    }
}
