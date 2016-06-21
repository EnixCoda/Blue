package com.xxx.blue;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);

        listView = (ListView)findViewById(R.id.listView);
        listView.setAdapter(adapter);

        adapter.add("开发团队成员：");
        adapter.add(" @Yang Yixin@");
        adapter.add(" @Li Zhen@");
        adapter.add(" @Wu Yan@");
        adapter.add(" @Zhang Lijuan@");
        adapter.add(" @Sun Shiyun@");
        adapter.add("官方网站");

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = null;
                switch(position) {
                    case 6:
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.ecnu.edu.cn")));
                        break;
                }
            }
        });

        findViewById(R.id.imageButton).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                About.this.finish();
            }
        });

    }
}
