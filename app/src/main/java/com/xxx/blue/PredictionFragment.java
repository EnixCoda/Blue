package com.xxx.blue;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.OnClick;
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
    @Bind(R.id.tv_pm2_5)
    TextView mPM2_5;
    @Bind(R.id.tv_air)
    TextView mAir;

    @OnClick(R.id.vg_air)
    void onAirClick(){
//        //TODO：跳转至天气详情
    }

    @OnClick(R.id.vg_temperature)
    void onTemperatureClick(){
//        //TODO：跳转至温度详情
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View viewGroup = inflater.inflate(R.layout.viewstub_predictions, container, false);
        ButterKnife.bind(this, viewGroup);

        Bundle bundle = getArguments();
        if (bundle != null)
            setData(bundle);
        return viewGroup;
    }

    public void setData(Bundle data) {
        mTodayWhether.setText(data.getString(Constant.EXTRA_WHETHER, "小雨转阴"));
        mTodayTemperatureRange.setText(data.getString(Constant.EXTRA_TEMPERATURE_RANGE, "18~23℃"));
        mForecastWhether.setText(data.getString(Constant.EXTRA_FORECAST_WHETHER, "晴"));
        mForecastTemperature.setText(data.getString(Constant.EXTRA_FORECAST_TEMPERATURE_RANGE, "20~25℃"));
        mAir.setText(data.getString(Constant.EXTRA_AIR_TEXT, "空气质量良好"));
        mPM2_5.setText(data.getString(Constant.EXTRA_AIR, "76"));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
