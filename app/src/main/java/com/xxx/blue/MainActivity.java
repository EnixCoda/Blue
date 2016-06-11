package com.xxx.blue;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xxx.blue.UI.widget.LocationDialog;

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
    CurrentFragment mCurrentFragment;
    PredictionFragment mPredictionFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_ui);
        ButterKnife.bind(this);

        mCurrentFragment = new CurrentFragment();
        mPredictionFragment = new PredictionFragment();

        getFragmentManager().beginTransaction()
                .replace(R.id.active_fragment, mCurrentFragment)
                .commit();
    }

    @OnClick({R.id.vg_search, R.id.menu_more, R.id.tab_current, R.id.tab_prediction})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.vg_search:
                LocationDialog locationDialog = new LocationDialog();
                locationDialog.show(getSupportFragmentManager(), "locationDialog");
                break;
            case R.id.menu_more:
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
