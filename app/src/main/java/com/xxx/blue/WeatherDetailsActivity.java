package com.xxx.blue;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.ButterKnife;
import getData.Day;
import getData.FetchData;

import com.xxx.blue.adapter.WeatherEverydayItemAdapter;
import com.xxx.blue.adapter.WeatherEveryhourItemAdapter;
import com.xxx.blue.model.WeatherEverydayItem;
import com.xxx.blue.model.WeatherEveryhourItem;

import java.util.ArrayList;

/**
 * Created by 张丽娟 on 2016/6/14.
 */
public class WeatherDetailsActivity extends AppCompatActivity {

    Button returnBtn;
    WeatherEverydayItemAdapter mAdapter;
    WeatherEveryhourItemAdapter hourAdapter;
    GridView mGridWeatherEveryday;
    GridView mGridWeatherEveryhour;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_details_ui);
        ButterKnife.bind(this);
        String localLocation = getIntent().getExtras().getString("location");

        FetchData fetchData = new FetchData(new FetchData.AsyncResponse() {
            @Override
            public void processFinish(Day day) {
                TextView temperature_value = (TextView) findViewById(R.id.temperature_value);
                temperature_value.setText(Integer.toString(day.stringIAQIHashtable.get("温度").cur) + "℃");
                TextView wind_value = (TextView) findViewById(R.id.wind_value);
                wind_value.setText(Integer.toString(day.stringIAQIHashtable.get("风").cur) + "m/s");
                TextView pressure_value = (TextView) findViewById(R.id.pressure_value);
                pressure_value.setText(Integer.toString(day.stringIAQIHashtable.get("空气压力").cur) + "kPa");
                TextView humidity_value = (TextView) findViewById(R.id.humidity_value);
                humidity_value.setText(Integer.toString(day.stringIAQIHashtable.get("湿度").cur) + "%");
                TextView today_weather = (TextView) findViewById(R.id.today_weather);
                today_weather.setText(day.dailyForecasts.get(0).description);
                TextView today_weather_value = (TextView) findViewById(R.id.today_weather_value);
                today_weather_value.setText(Integer.toString(day.dailyForecasts.get(0).tempMin) + "~" + Integer.toString(day.dailyForecasts.get(0).tempMin) + "℃");
                TextView tomorrow_weather = (TextView) findViewById(R.id.tomorrow_weather);
                tomorrow_weather.setText(day.dailyForecasts.get(1).description);
                TextView tomorrow_weather_value = (TextView) findViewById(R.id.tomorrow_weather_value);
                tomorrow_weather_value.setText(Integer.toString(day.dailyForecasts.get(1).tempMin) + "~" + Integer.toString(day.dailyForecasts.get(1).tempMin) + "℃");
            }
        });
        fetchData.execute(localLocation);

        returnBtn = (Button) findViewById(R.id.header_left_btn);
        returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mGridWeatherEveryday = (GridView) findViewById(R.id.grid_everyDayWeather);
        mGridWeatherEveryhour = (GridView) findViewById(R.id.grid_everyHourWeather);
        //gridView 每日天气
        ArrayList<WeatherEverydayItem> models = new ArrayList<>();
        models.add(new WeatherEverydayItem());
        models.add(new WeatherEverydayItem());
        models.add(new WeatherEverydayItem());
        models.add(new WeatherEverydayItem());
        models.add(new WeatherEverydayItem());
        models.add(new WeatherEverydayItem());
        mAdapter = new WeatherEverydayItemAdapter(this, models);
        mAdapter.setGridView(mGridWeatherEveryday);
        mGridWeatherEveryday.setAdapter(mAdapter);

        //gridView 每小时天气
        ArrayList<WeatherEveryhourItem> hourModels = new ArrayList<>();
        hourModels.add(new WeatherEveryhourItem());
        hourModels.add(new WeatherEveryhourItem());
        hourModels.add(new WeatherEveryhourItem());
        hourModels.add(new WeatherEveryhourItem());
        hourModels.add(new WeatherEveryhourItem());
        hourModels.add(new WeatherEveryhourItem());
        hourModels.add(new WeatherEveryhourItem());
        hourModels.add(new WeatherEveryhourItem());
        hourModels.add(new WeatherEveryhourItem());
        hourModels.add(new WeatherEveryhourItem());
        hourModels.add(new WeatherEveryhourItem());
        hourModels.add(new WeatherEveryhourItem());

        int size = hourModels.size();
        int length = 100;
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        float density = dm.density;
        int gridViewWidth = (int) (size * (length + 4) * density);
        int itemWidth = (int) (length * density);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                gridViewWidth, LinearLayout.LayoutParams.FILL_PARENT);
        mGridWeatherEveryhour.setLayoutParams(params); // 设置GirdView布局参数,横向布局的关键
        mGridWeatherEveryhour.setColumnWidth(itemWidth); // 设置列表项宽
        mGridWeatherEveryhour.setHorizontalSpacing(5); // 设置列表项水平间距
        mGridWeatherEveryhour.setStretchMode(GridView.NO_STRETCH);
        mGridWeatherEveryhour.setNumColumns(size); // 设置列数量=列表集合数
        hourAdapter = new WeatherEveryhourItemAdapter(this, hourModels);
        hourAdapter.setGridView(mGridWeatherEveryhour);
        mGridWeatherEveryhour.setAdapter(hourAdapter);
    }

}