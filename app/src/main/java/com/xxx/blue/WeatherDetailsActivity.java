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
import getData.Forecast;

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
    ArrayList<WeatherEverydayItem> models = new ArrayList<>();
    ArrayList<WeatherEveryhourItem> hourModels = new ArrayList<>();
    WeatherEverydayItemAdapter mAdapter;
    WeatherEveryhourItemAdapter mHourAdapter;
    GridView mGridWeatherEveryday;
    GridView mGridWeatherEveryhour;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_details_ui);
        ButterKnife.bind(this);
        String localLocation = getIntent().getExtras().getString("location");

        mGridWeatherEveryday = (GridView) findViewById(R.id.grid_everyDayWeather);
        mGridWeatherEveryhour = (GridView) findViewById(R.id.grid_everyHourWeather);

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
                today_weather_value.setText(Integer.toString(day.dailyForecasts.get(0).tempMin) + "~" + Integer.toString(day.dailyForecasts.get(0).tempMax) + "℃");
                TextView tomorrow_weather = (TextView) findViewById(R.id.tomorrow_weather);
                tomorrow_weather.setText(day.dailyForecasts.get(1).description);
                TextView tomorrow_weather_value = (TextView) findViewById(R.id.tomorrow_weather_value);
                tomorrow_weather_value.setText(Integer.toString(day.dailyForecasts.get(1).tempMin) + "~" + Integer.toString(day.dailyForecasts.get(1).tempMax) + "℃");

                //gridView 每日天气
                for (Forecast forecast : day.dailyForecasts) {
                    models.add(new WeatherEverydayItem(forecast.date, forecast.code, forecast.tempMax, forecast.tempMin));
                }
                mAdapter = new WeatherEverydayItemAdapter(WeatherDetailsActivity.this, models);
                mGridWeatherEveryday.setAdapter(mAdapter);
                mAdapter.setGridView(mGridWeatherEveryday);

                //gridView 每3小时天气

                for (Forecast forecast : day.hourlyForecasts) {
                    hourModels.add(new WeatherEveryhourItem(forecast.date, forecast.description, forecast.temp, forecast.rainPoss));
                }

                int size = hourModels.size();
                int length = 240;
                DisplayMetrics dm = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(dm);
                float density = dm.density;
                int gridViewWidth = (int) (size * length * density);
                int itemWidth = (int) (length * density);

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        gridViewWidth, LinearLayout.LayoutParams.MATCH_PARENT);
                mGridWeatherEveryhour.setLayoutParams(params); // 设置GirdView布局参数,横向布局的关键
                mGridWeatherEveryhour.setColumnWidth(itemWidth); // 设置列表项宽
                mGridWeatherEveryhour.setHorizontalSpacing(5); // 设置列表项水平间距
                mGridWeatherEveryhour.setStretchMode(GridView.NO_STRETCH);
                mGridWeatherEveryhour.setNumColumns(size); // 设置列数量=列表集合数

                mHourAdapter = new WeatherEveryhourItemAdapter(WeatherDetailsActivity.this, hourModels);
                mGridWeatherEveryhour.setAdapter(mHourAdapter);
                mHourAdapter.setGridView(mGridWeatherEveryhour);
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
    }
}