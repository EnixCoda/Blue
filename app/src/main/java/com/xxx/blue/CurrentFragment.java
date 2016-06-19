package com.xxx.blue;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.OnClick;
import utility.Constant;
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
    @Bind(R.id.tv_pm2_5)
    TextView mPM2_5;
    @Bind(R.id.tv_air)
    TextView mAir;

    @OnClick(R.id.vg_air)
    void onAirClick(){
//        //TODO：跳转至天气质量详情
    }

    @OnClick(R.id.vg_temperature)
    void onTemperatureClick(){
//        //TODO：跳转至温度详情
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View viewGroup = inflater.inflate(R.layout.viewstub_current, container, false);
        ButterKnife.bind(this, viewGroup);
        Bundle bundle = getArguments();
        if (bundle != null)
            setData(bundle);
        return viewGroup;
    }



    public void setData(Bundle mData){
        if (mWhetherImg == null) return;
        mWhetherImg.setImageResource(mData.getInt(Constant.EXTRA_WHETHER_IMG, R.mipmap.ic_rain_24_24));
        mWhether.setText(String.valueOf(mData.getString(Constant.EXTRA_WHETHER, "--")));
        mTemperature.setText(mData.getString(Constant.EXTRA_TEMPERATURE, "--℃"));
        mWindSpeed.setText(mData.getString(Constant.EXTRA_WIND_SPEED, "--m/s"));
        mWetPercent.setText(mData.getString(Constant.EXTRA_WET_PERCENT, "--%"));
        mAir.setText(mData.getString(Constant.EXTRA_AIR_TEXT, "--"));
        mPM2_5.setText(mData.getString(Constant.EXTRA_AIR, "--"));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
