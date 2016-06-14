package com.xxx.blue;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xxx.blue.UI.widget.LifeHintItemAdapter;
import com.xxx.blue.UI.widget.LocationDialog;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    @Bind(R.id.tv_location)
    TextView mLocation;
    @Bind(R.id.vg_search)
    LinearLayout mSearch;
    @Bind(R.id.menu_more)
    ImageView mMenuMore;
    @Bind(R.id.tab_current)
    TextView mTabCurrent;
    @Bind(R.id.tab_prediction)
    TextView mTabPrediction;
    @Bind(R.id.tv_temperature)
    TextView mTemperature;
    @Bind(R.id.tv_air)
    TextView mAir;
    @Bind(R.id.grid_hint)
    GridView mGridHint;
    CurrentFragment mCurrentFragment;
    PredictionFragment mPredictionFragment;
    LifeHintItemAdapter mAdapter;

    @OnClick(R.id.tv_temperature)
    void onTemperatureClick(){
        //TODO：跳转至天气详情
    }

    @OnClick(R.id.tv_air)
    void onAirClick(){
        //TODO: 跳转至空气质量详情
    }

    @OnClick(R.id.menu_more)
    void onMenuClick(){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_ui);
        ButterKnife.bind(this);

        init();
    }

    private void init() {
        //fragment
        mCurrentFragment = new CurrentFragment();
        mPredictionFragment = new PredictionFragment();

        getFragmentManager().beginTransaction()
                .replace(R.id.active_fragment, mCurrentFragment)
                .commit();
        //gridView
        ArrayList<HintModel> models = new ArrayList<>();
        models.add(new HintModel());
        models.add(new HintModel());
        models.add(new HintModel());
        models.add(new HintModel());
        models.add(new HintModel());
        models.add(new HintModel());
        models.add(new HintModel());
        models.add(new HintModel());
        models.add(new HintModel());
        mAdapter = new LifeHintItemAdapter(this, models);
        mAdapter.setGridView(mGridHint);
        mGridHint.setAdapter(mAdapter);
    }

    @OnClick({R.id.vg_search, R.id.tab_current, R.id.tab_prediction})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.vg_search:
                LocationDialog locationDialog = new LocationDialog();
                locationDialog.show(getSupportFragmentManager(), "locationDialog");
                break;
            case R.id.tab_current:
                getFragmentManager().beginTransaction()
                        .replace(R.id.active_fragment, mCurrentFragment)
                        .commit();
                break;
            case R.id.tab_prediction:
                getFragmentManager().beginTransaction()
                        .replace(R.id.active_fragment, mPredictionFragment)
                        .commit();
                break;
        }
    }
}
