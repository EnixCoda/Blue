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

import com.xxx.blue.R;
import com.xxx.blue.model.WeatherEveryhourItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 张丽娟 on 2016/6/18.
 */
public class WeatherEveryhourItemAdapter extends BaseAdapter {
    List<WeatherEveryhourItem> mModels;
    Context mContext;
    GridView view;
    List<String> Chour = new ArrayList<>();
    List<String> Cdate = new ArrayList<>();
    List<Bitmap> ChourWeatherIcon = new ArrayList<>();
    Bitmap bmp;
    List<String> Ctemp = new ArrayList<>();
    List<Bitmap> Cwind = new ArrayList<>();
    List<Bitmap> CrainAmount = new ArrayList<>();
    List<String> CrainPossibility = new ArrayList<>();

    public WeatherEveryhourItemAdapter(Context mContext, List<WeatherEveryhourItem> mModels) {
        this.mContext = mContext;
        this.mModels = mModels;
        Resources res = mContext.getResources();
        for (WeatherEveryhourItem weatherEveryhourItem : mModels) {
            String description = weatherEveryhourItem.description;
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
            ChourWeatherIcon.add(bmp);
            Cdate.add(weatherEveryhourItem.dateS);
            Chour.add(weatherEveryhourItem.hour);
            Ctemp.add(weatherEveryhourItem.tempS);
            CrainPossibility.add(weatherEveryhourItem.rainPossibilityS);
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
            int width = view.getWidth() / 50;
            ViewGroup.LayoutParams params = holder.mContainer.getLayoutParams();
            params.height = height;
            params.width = width;
            holder.setLayoutParams(params);
        }
        return holder;
    }

    public class ItemViewHolder extends FrameLayout {
        @Bind(R.id.wehi_container)
        ViewGroup mContainer;
        @Bind(R.id.date)
        TextView date;
        @Bind(R.id.hour)
        TextView hour;
        @Bind(R.id.hourweathericon)
        ImageView hourWeatherIcon;
        @Bind(R.id.temp)
        TextView temp;
        @Bind(R.id.rainpercent)
        TextView rainPossibility;

        public ItemViewHolder(Context context, int position) {
            super(context);
            View view = LayoutInflater.from(context).inflate(R.layout.weather_everyhour_item, null);
            addView(view);
            ButterKnife.bind(this, view);
            date.setText(Cdate.get(position));
            hour.setText(Chour.get(position));
            hourWeatherIcon.setImageBitmap(ChourWeatherIcon.get(position));
            temp.setText(Ctemp.get(position));
            rainPossibility.setText(CrainPossibility.get(position));
        }

    }
}
