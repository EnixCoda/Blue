package com.xxx.blue;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.shehabic.droppy.DroppyClickCallbackInterface;
import com.shehabic.droppy.DroppyMenuPopup;
import com.shehabic.droppy.animations.DroppyFadeInAnimation;
import com.xxx.blue.UI.widget.LocationDialog;
import com.xxx.blue.adapter.LifeHintItemAdapter;
import com.xxx.blue.adapter.PagerViewerAdapter;
import com.xxx.blue.model.HintModel;
import com.xxx.blue.presenter.MainPresenter;

import java.util.ArrayList;
import java.util.Hashtable;

import analyse.Suggestion;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import getData.Day;
import getData.Forecast;
import getData.IAQI;
import utility.Constant;

public class MainActivity extends AppCompatActivity implements MainPresenter.MainView {
    private static final String EXTRA_LOCATION = "extra.location";
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
    @Bind(R.id.grid_hint)
    GridView mGridHint;
    @Bind(R.id.view_pager)
    ViewPager mViewPager;
    CurrentFragment mCurrentFragment;
    PredictionFragment mPredictionFragment;
    LifeHintItemAdapter mAdapter;
    PagerViewerAdapter mPagerAdapter;

    MainPresenter mPresenter;

    SharedPreferences mPrefenrence;//做持久化数据
    boolean isCurrent = true;//判断当前所在页面
    private Day mData;
    private String mLocationText;

    @OnClick(R.id.menu_more)
    void onMenuClick() {
        DroppyMenuPopup.Builder droppyBuilder = new DroppyMenuPopup.Builder(this, mMenuMore);
        DroppyMenuPopup droppyMenu = droppyBuilder.fromMenu(R.menu.main_menu)
                .triggerOnAnchorClick(false)
                .setOnClick(new DroppyClickCallbackInterface() {
                    @Override
                    public void call(View v, int id) {
                        switch (id) {
                            case R.id.menu_share: //分享
                                Intent intent = new Intent();
                                intent.setType("text/plain"); // 纯文本
                                startActivity(Intent.createChooser(intent, "分享"));
                                break;
                            case R.id.menu_about://跳转至关于界面
                                Intent intent2 = new Intent(MainActivity.this, About.class);
                                startActivity(intent2);
                                break;
                            default:
                                break;
                        }
                    }
                })
                .setPopupAnimation(new DroppyFadeInAnimation())
                .setXOffset(5)
                .setYOffset(5)
                .build();
        droppyMenu.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_ui);
        ButterKnife.bind(this);

        init();
    }

    private void init() {
        mPrefenrence = getPreferences(MODE_PRIVATE);
        //presenter
        mPresenter = new MainPresenter();
        mPresenter.attachView(this);
        //fragment
        mCurrentFragment = new CurrentFragment();
        mPredictionFragment = new PredictionFragment();

        //viewPager
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(mCurrentFragment);
        fragments.add(mPredictionFragment);
        mPagerAdapter = new PagerViewerAdapter(getSupportFragmentManager(), fragments);
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    mTabCurrent.setAlpha(1.0f);
                    mTabPrediction.setAlpha(0.4f);
                    mCurrentFragment.setData(setCurrentDataBundle());
                } else {
                    mTabCurrent.setAlpha(0.4f);
                    mTabPrediction.setAlpha(1.0f);
                    mPredictionFragment.setData(setPredictionDataBundle());
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }

        //gridView
        ArrayList<HintModel> models = new ArrayList<>();
        TypedArray imageId = getResources().obtainTypedArray(R.array.suggest_images);
        for (int i = 0; i < 9; ++i) {
            models.add(new HintModel("等待数据", imageId.getResourceId(i, 0), 0));
        }
        mAdapter = new LifeHintItemAdapter(this, models);
        mAdapter.setGridView(mGridHint);
        mGridHint.setAdapter(mAdapter);

        mLocationText = mPrefenrence.getString(EXTRA_LOCATION, "上海");
        mPresenter.loadCurrentData(mLocationText);
    }

    @OnClick({R.id.vg_search, R.id.tab_current, R.id.tab_prediction})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.vg_search:
                final LocationDialog locationDialog = new LocationDialog();
                locationDialog.show(getSupportFragmentManager(), "locationDialog");
                locationDialog.setListener(new LocationDialog.OnLocationClickListener() {
                    @Override
                    public void onClick(String text) {
                        mLocationText = text;
                        //数据持久化
                        mPrefenrence.edit()
                                .putString(EXTRA_LOCATION, text)
                                .apply();
                        locationDialog.dismiss();
                        mPresenter.loadCurrentData(mLocationText);
                    }
                });
                break;
            case R.id.tab_current:
                mViewPager.setCurrentItem(0, true);
                mCurrentFragment.setData(setCurrentDataBundle());
                isCurrent = true;
                break;
            case R.id.tab_prediction:
                isCurrent = false;
                mViewPager.setCurrentItem(1, true);
                mPredictionFragment.setData(setPredictionDataBundle());
                break;
        }
    }

    private Bundle setCurrentDataBundle() {
        if (null == mData) return new Bundle();
        Bundle bundle = new Bundle();
        bundle.putString(Constant.EXTRA_AIR, String.valueOf(mData.aqi));
        bundle.putString(Constant.EXTRA_AIR_TEXT, mData.AQIConclusion);

        bundle.putInt(Constant.EXTRA_WHETHER_IMG, R.mipmap.weather_null);
        bundle.putString(Constant.EXTRA_WHETHER, mData.weatherConclusion);
        IAQI temperature = mData.stringIAQIHashtable.get("温度");
        bundle.putString(Constant.EXTRA_TEMPERATURE, temperature.cur + "℃");
        IAQI wind = mData.stringIAQIHashtable.get("风");
        if (wind != null) {
            bundle.putString(Constant.EXTRA_WIND_SPEED, wind.cur + "m/s");
        }
        IAQI wet = mData.stringIAQIHashtable.get("湿度");
        bundle.putString(Constant.EXTRA_WET_PERCENT, wet.cur + "%");

        return bundle;
    }

    private Bundle setPredictionDataBundle() {
        if (null == mData) return new Bundle();
        Bundle bundle = new Bundle();

        Forecast todayForecast = mData.dailyForecasts.get(0);
        Forecast tomorrowForecast = mData.dailyForecasts.get(1);

        bundle.putString(Constant.EXTRA_AIR, String.valueOf(mData.tomorrow.aqi));
        bundle.putString(Constant.EXTRA_AIR_TEXT, mData.tomorrow.AQIConclusion);

        bundle.putString(Constant.EXTRA_WHETHER, todayForecast.description);
        bundle.putString(Constant.EXTRA_TEMPERATURE_RANGE, todayForecast.tempMin + "~" + todayForecast.tempMax + "℃");
        bundle.putString(Constant.EXTRA_FORECAST_WHETHER, tomorrowForecast.description);
        bundle.putString(Constant.EXTRA_FORECAST_TEMPERATURE_RANGE, tomorrowForecast.tempMin + "~" + todayForecast.tempMax + "℃");

        return bundle;
    }

    @Override
    public void showCurrentData(Day data) {
        if (null == data) {
            Toast.makeText(this, "获取数据失败", Toast.LENGTH_SHORT).show();
            return;
        }
//        Toast.makeText(this, "获取数据成功", Toast.LENGTH_SHORT).show();

        mData = data;
        //gridView更新
        ArrayList<HintModel> lists = new ArrayList<>();
        TypedArray imageId = getResources().obtainTypedArray(R.array.suggest_images);
        Hashtable<String, Suggestion> suggestionTable = data.getInstructions();
        int i = 0;
        for (Object key : suggestionTable.keySet()) {
            Suggestion value = suggestionTable.get(key);
            HintModel hint = new HintModel(value.getDesc(), imageId.getResourceId(i++, 0), value.getCode());
            lists.add(hint);
        }
        mAdapter.resetAndNotify(lists);
        mLocation.setText(mLocationText);
        if (isCurrent) {
            mViewPager.setCurrentItem(0, true);
            mCurrentFragment.setData(setCurrentDataBundle());

        } else {
            mViewPager.setCurrentItem(1, true);
            mPredictionFragment.setData(setPredictionDataBundle());
        }
    }

    @Override
    public Context getViewContext() {
        return this;
    }

    @Override
    public void detachView() {
        finish();
    }
}
