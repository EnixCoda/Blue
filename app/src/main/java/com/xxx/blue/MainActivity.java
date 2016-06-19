package com.xxx.blue;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shehabic.droppy.DroppyClickCallbackInterface;
import com.shehabic.droppy.DroppyMenuPopup;
import com.shehabic.droppy.animations.DroppyFadeInAnimation;
import com.xxx.blue.adapter.LifeHintItemAdapter;
import com.xxx.blue.UI.widget.LocationDialog;
import com.xxx.blue.adapter.PagerViewerAdapter;
import com.xxx.blue.model.HintModel;
import com.xxx.blue.presenter.MainPresenter;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import getData.Day;

public class MainActivity extends AppCompatActivity implements MainPresenter.MainView{
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
    @Bind(R.id.tv_pm2_5)
    TextView mPM2_5;
    @Bind(R.id.tv_air)
    TextView mAir;
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

    @OnClick(R.id.active_fragment)
    void onFragmentClick(){
        //TODO：跳转至天气详情
    }

    @OnClick(R.id.tv_air)
    void onAirClick(){
        //TODO: 跳转至空气质量详情
    }

    @OnClick(R.id.menu_more)
    void onMenuClick(){
        DroppyMenuPopup.Builder droppyBuilder = new DroppyMenuPopup.Builder(this, mMenuMore);
        DroppyMenuPopup droppyMenu = droppyBuilder.fromMenu(R.menu.main_menu)
                .triggerOnAnchorClick(false)
                .setOnClick(new DroppyClickCallbackInterface() {
                    @Override
                    public void call(View v, int id) {
                        switch (id){
                            case R.id.menu_share: //分享
                                Intent intent = new Intent();
                                intent.setType("text/plain"); // 纯文本
                                startActivity(Intent.createChooser(intent, "分享"));
                                break;
                            case R.id.menu_about://TODO:跳转至关于界面
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

        mTabCurrent.setAlpha(0.4f);
        mTabPrediction.setAlpha(1.0f);
        //viewPager
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(mCurrentFragment);
        fragments.add(mPredictionFragment);
        mPagerAdapter = new PagerViewerAdapter(getSupportFragmentManager(), fragments);
        mViewPager.setAdapter(mPagerAdapter);

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

        mPresenter.loadCurrentData(mPrefenrence.getString(EXTRA_LOCATION, "上海"));
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
                        mLocation.setText(text);
                        //数据持久化
                        mPrefenrence.edit()
                                .putString(EXTRA_LOCATION, text)
                                .apply();
                        locationDialog.dismiss();
                    }
                });
                break;
            case R.id.tab_current:
                mTabCurrent.setAlpha(0.4f);
                mTabPrediction.setAlpha(1.0f);
                getFragmentManager().beginTransaction()
                        .replace(R.id.active_fragment, mCurrentFragment)
                        .commitAllowingStateLoss();
                isCurrent = true;
                break;
            case R.id.tab_prediction:
                isCurrent = false;
                mTabPrediction.setAlpha(0.4f);
                mTabCurrent.setAlpha(1.0f);
                getFragmentManager().beginTransaction()
                        .replace(R.id.active_fragment, mPredictionFragment)
                        .commitAllowingStateLoss();
                break;
        }
    }

    @Override
    public void showCurrentData(Day data) {
        mPM2_5.setText("53");
        mAir.setText("空气质量良好");
        if (isCurrent){
            getFragmentManager().beginTransaction()
                    .replace(R.id.active_fragment, mCurrentFragment)
                    .commitAllowingStateLoss();
            mCurrentFragment.setData(new Bundle());

        }else {
            getFragmentManager().beginTransaction()
                    .replace(R.id.active_fragment, mPredictionFragment)
                    .commitAllowingStateLoss();
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
