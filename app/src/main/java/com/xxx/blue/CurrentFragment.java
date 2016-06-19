package com.xxx.blue;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import Utility.Constant;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Burgess on 2016/6/11.
 */
public class CurrentFragment extends Fragment {
    @Bind(R.id.img_whether)
    ImageView mWhetherImg;
    @Bind(R.id.tv_whether)
    TextView mWhether;
    @Bind(R.id.tv_temperature)
    TextView mTemperature;
    @Bind(R.id.tv_wind_direction)
    TextView mWindDirection;
    @Bind(R.id.tv_wind_speed)
    TextView mWindSpeed;
    @Bind(R.id.tv_wet_percent)
    TextView mWetPercent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View viewGroup = inflater.inflate(R.layout.viewstub_current, container, false);
        ButterKnife.bind(this, viewGroup);
        setData(getArguments());
        return viewGroup;
    }


    public void setData(Bundle mData){
        mWhetherImg.setImageResource(mData.getInt(Constant.EXTRA_WHETHER_IMG, R.mipmap.ic_rain_24_24));
        mWhether.setText(String.valueOf(mData.getString(Constant.EXTRA_WHETHER, "小雨")));
        mTemperature.setText(String.valueOf(mData.getInt(Constant.EXTRA_TEMPERATURE, 19)) + "℃");
        mWindDirection.setText(mData.getString(Constant.EXTRA_WIND_DIRECTION, "东南风"));
        mWindSpeed.setText(mData.getString(Constant.EXTRA_WIND_SPEED, "3~5m/s"));
        mWetPercent.setText(mData.getInt(Constant.EXTRA_WET_PERCENT, 90) + "%");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
