package com.xxx.blue;

import android.content.Intent;
import android.os.Bundle;
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
import com.xxx.blue.model.HintModel;

import java.util.ArrayList;

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
                final LocationDialog locationDialog = new LocationDialog();
                locationDialog.show(getSupportFragmentManager(), "locationDialog");
                locationDialog.setListener(new LocationDialog.OnLocationClickListener() {
                    @Override
                    public void onClick(String text) {
                        mLocation.setText(text);
                        locationDialog.dismiss();
                    }
                });
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
