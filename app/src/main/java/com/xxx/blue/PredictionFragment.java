package com.xxx.blue;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import utility.Constant;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Burgess on 2016/6/11.
 */
public class PredictionFragment extends Fragment {
    @Bind(R.id.tv_today_whether)
    TextView mTodayWhether;
    @Bind(R.id.tv_today_temperature_range)
    TextView mTodayTemperatureRange;
    @Bind(R.id.forecast_whether)
    TextView mForecastWhether;
    @Bind(R.id.forecast_temperature)
    TextView mForecastTemperature;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View viewGroup = inflater.inflate(R.layout.viewstub_predictions, container, false);
        ButterKnife.bind(this, viewGroup);

        setData(getArguments());
        return viewGroup;
    }

    private void setData(Bundle data) {
        mTodayWhether.setText(data.getString(Constant.EXTRA_WHETHER, "小雨转阴"));
        mTodayTemperatureRange.setText(data.getString(Constant.EXTRA_TEMPERATURE_RANGE, "18~23℃"));
        mForecastWhether.setText(data.getString(Constant.EXTRA_FORECAST_WHETHER, "晴"));
        mForecastTemperature.setText(data.getString(Constant.EXTRA_FORECAST_TEMPERATURE_RANGE, "20~25℃"));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
