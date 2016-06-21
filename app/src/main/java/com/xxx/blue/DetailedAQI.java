package com.xxx.blue;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

import getData.Day;
import getData.FetchData;
import getData.NearbySites;
import getData.SiteData;

public class DetailedAQI extends AppCompatActivity {

    private GridView gridView;
    private ArrayAdapter<String> adapter;
    private Day day;
    LineGraphicView lgv;
    ArrayList<Double> yList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_aqi);

        String localLocation = getIntent().getExtras().getString("location");
        // 监测点地图
        NearbySites nearbySites = new NearbySites(new NearbySites.AsyncResponse() {
            @Override
            public void processFinish(ArrayList<SiteData> siteDatas) {
                FragmentManager fm = getFragmentManager();
                FragmentTransaction transaction = fm.beginTransaction();
                MapFragment mapFragment = new MapFragment();
                mapFragment.setData(siteDatas);
                transaction.replace(R.id.map_fragment, mapFragment);
                transaction.commit();
            }
        });
        nearbySites.execute(localLocation);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        lgv = new LineGraphicView(this);

        //各类污染物实时数据
        FetchData fetchData = new FetchData(new FetchData.AsyncResponse() {
            @Override
            public void processFinish(Day _day_) {
                day = _day_;
                adapter.add("PM2.5");
                adapter.add(Integer.toString(day.stringIAQIHashtable.get("PM2.5").cur));
                adapter.add("PM10");
                adapter.add(Integer.toString(day.stringIAQIHashtable.get("PM10").cur));
                adapter.add("SO2");
                adapter.add(Integer.toString(day.stringIAQIHashtable.get("SO2").cur));
                adapter.add("O3");
                adapter.add(Integer.toString(day.stringIAQIHashtable.get("O3").cur));
                adapter.add("NO2");
                adapter.add(Integer.toString(day.stringIAQIHashtable.get("NO2").cur));
                adapter.add("CO");
                adapter.add(Integer.toString(day.stringIAQIHashtable.get("CO").cur));

                gridView = (GridView) findViewById(R.id.gridView);
                gridView.setAdapter(adapter);

                // 最近7天空气质量曲线
                yList = new ArrayList<>();
                for (int i = 0; i < 7; i++) {
                    yList.add(Double.valueOf(DetailedAQI.this.day.forecastAQI.get(i * 9).max));
                }
                final Calendar c = Calendar.getInstance();
                c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
                int mDay = c.get(Calendar.DAY_OF_MONTH);
                ArrayList<String> xRawData = new ArrayList<>();
                xRawData.add("今天");
                xRawData.add(String.valueOf(mDay + 1) + "日");
                xRawData.add(String.valueOf(mDay + 2) + "日");
                xRawData.add(String.valueOf(mDay + 3) + "日");
                xRawData.add(String.valueOf(mDay + 4) + "日");
                xRawData.add(String.valueOf(mDay + 5) + "日");
                xRawData.add(String.valueOf(mDay + 6) + "日");
                lgv.setData(yList, xRawData, 300, 150);
                View dummyView = findViewById(R.id.dummy_view);
                try {
                    ViewGroup parent = (ViewGroup) dummyView.getParent();
                    int index = parent.indexOfChild(dummyView);
                    ((ViewGroup) dummyView.getParent()).removeView(dummyView);
                    lgv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 650));
                    parent.addView(lgv, index);

                } catch (NullPointerException e) {

                }

                findViewById(R.id.imageButton).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
            }
        });
        fetchData.execute(localLocation);

    }

}
