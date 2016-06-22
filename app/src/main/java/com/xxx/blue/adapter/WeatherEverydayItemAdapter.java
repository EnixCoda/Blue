package com.xxx.blue.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.xxx.blue.model.WeatherEverydayItem;
import com.xxx.blue.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 张丽娟 on 2016/6/17.
 */
public class WeatherEverydayItemAdapter extends BaseAdapter {
    List<WeatherEverydayItem> mModels;
    Context mContext;
    GridView view;
    List<String> Cweek = new ArrayList<>();
    List<Bitmap> CweatherIcon = new ArrayList<>();
    Bitmap bmp;
    List<String> ChighTemp = new ArrayList<>();
    List<String> ClowTemp = new ArrayList<>();

    public WeatherEverydayItemAdapter(Context mContext, List<WeatherEverydayItem> mModels) {
        this.mContext = mContext;
        this.mModels = mModels;
        Resources res = mContext.getResources();
        for (WeatherEverydayItem weatherEverydayItem : mModels) {
            String description = weatherEverydayItem.description;
            description = description.split("转")[0];
            switch (description) {
                case "晴朗":
                case "晴":
                    bmp = BitmapFactory.decodeResource(res, R.drawable.clear);
                    break;
                case "多云":
                    bmp = BitmapFactory.decodeResource(res, R.drawable.clouds);
                    break;
                case "小雨":
                case "中雨":
                case "大雨":
                case "雷阵雨":
                case "阵雨":
                case "有雨":
                    bmp = BitmapFactory.decodeResource(res, R.drawable.rain);
                    break;
                default:
                    bmp = BitmapFactory.decodeResource(res, R.drawable.clouds);
                    break;
            }
            CweatherIcon.add(bmp);
            Cweek.add(weatherEverydayItem.week);
            ChighTemp.add(weatherEverydayItem.highTempS);
            ClowTemp.add(weatherEverydayItem.lowTempS);
        }
    }

    public void setGridView(GridView view) {
        this.view = view;
    }

    @Override
    public int getCount() {
        return mModels.size();
    }

    @Override
    public Object getItem(int position) {
        return mModels.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemViewHolder holder = new ItemViewHolder(mContext, position);
        if (view != null) {
            int height = view.getHeight();
            int width = view.getWidth() / 6;
            ViewGroup.LayoutParams params = holder.mContainer.getLayoutParams();
            params.height = height;
            params.width = width;
            holder.setLayoutParams(params);
        }
        return holder;
    }

    public class ItemViewHolder extends FrameLayout {
        @Bind(R.id.wei_container)
        ViewGroup mContainer;
        @Bind(R.id.week)
        TextView week;
        @Bind(R.id.weathericon)
        ImageView weatherIcon;
        @Bind(R.id.hightemp)
        TextView highTemp;
        @Bind(R.id.lowtemp)
        TextView lowTemp;

        public ItemViewHolder(Context context, int position) {
            super(context);
            View view = LayoutInflater.from(context).inflate(R.layout.weather_everyday_item, null);
            addView(view);
            ButterKnife.bind(this, view);
            week.setText(Cweek.get(position));
            weatherIcon.setImageBitmap(CweatherIcon.get(position));
            highTemp.setText(ChighTemp.get(position));
            lowTemp.setText(ClowTemp.get(position));
        }
    }
}
