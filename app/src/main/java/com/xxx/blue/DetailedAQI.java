package com.xxx.blue;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.xxx.blue.adapter.AQIPollutionsItemAdapter;
import com.xxx.blue.model.AQIPollutionItem;

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
    ArrayList<AQIPollutionItem> pollutionModels = new ArrayList<>();
    AQIPollutionsItemAdapter mAQIPollutionItemAdapter;
    GridView mGridPollutionView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_aqi);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.title_activity_detail_aqi);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        String localLocation = getIntent().getExtras().getString("location");
        mGridPollutionView = (GridView) findViewById(R.id.pollutions_grid_view);
        lgv = new LineGraphicView(this);

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

        //各类污染物实时数据
        FetchData fetchData = new FetchData(new FetchData.AsyncResponse() {
            @Override
            public void processFinish(Day _day_) {
                day = _day_;
                pollutionModels.add(new AQIPollutionItem("PM2.5", day.stringIAQIHashtable.get("PM2.5").cur));
                pollutionModels.add(new AQIPollutionItem("PM10", day.stringIAQIHashtable.get("PM10").cur));
                pollutionModels.add(new AQIPollutionItem("SO2", day.stringIAQIHashtable.get("SO2").cur));
                pollutionModels.add(new AQIPollutionItem("O3", day.stringIAQIHashtable.get("O3").cur));
                pollutionModels.add(new AQIPollutionItem("NO2", day.stringIAQIHashtable.get("NO2").cur));
                pollutionModels.add(new AQIPollutionItem("CO", day.stringIAQIHashtable.get("CO").cur));

                mAQIPollutionItemAdapter = new AQIPollutionsItemAdapter(DetailedAQI.this, pollutionModels);
                mGridPollutionView.setAdapter(mAQIPollutionItemAdapter);
                mAQIPollutionItemAdapter.setGridView(mGridPollutionView);

                // 最近7天空气质量曲线
                yList = new ArrayList<>();
                int min = 100, max = 100, aqi;
                for (int i = 0; i < 7; i++) {
                    aqi = DetailedAQI.this.day.forecastAQI.get(i * 9).max;
                    min = min < aqi ? min : aqi;
                    max = max > aqi ? max : aqi;
                    yList.add((double) aqi);
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
                lgv.setData(yList, xRawData, max, min);
                View dummyView = findViewById(R.id.dummy_view);
                ViewGroup parent = (ViewGroup) dummyView.getParent();
                int index = parent.indexOfChild(dummyView);
                ((ViewGroup) dummyView.getParent()).removeView(dummyView);
                lgv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 650));
                parent.addView(lgv, index);
            }
        });
        fetchData.execute(localLocation);
    }
}
