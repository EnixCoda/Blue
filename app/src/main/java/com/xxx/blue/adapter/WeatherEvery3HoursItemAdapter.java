package com.xxx.blue.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.xxx.blue.R;
import com.xxx.blue.model.WeatherEvery3HoursItem;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 张丽娟 on 2016/6/18.
 */
public class WeatherEvery3HoursItemAdapter extends BaseAdapter {
    List<WeatherEvery3HoursItem> mModels;
    Context mContext;
    GridView view;

    public WeatherEvery3HoursItemAdapter(Context mContext, List<WeatherEvery3HoursItem> mModels) {
        this.mContext = mContext;
        this.mModels = mModels;
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
        @Bind(R.id.hourWeatherIcon)
        ImageView hourWeatherIcon;
        @Bind(R.id.temp)
        TextView temp;
        @Bind(R.id.rainPossibility)
        TextView rainPossibility;

        public ItemViewHolder(Context context, int position) {
            super(context);
            View view = LayoutInflater.from(context).inflate(R.layout.weather_everyhour_item, null);
            addView(view);
            ButterKnife.bind(this, view);
            date.setText(mModels.get(position).dateS);
            hour.setText(mModels.get(position).hourS);
            hourWeatherIcon.setImageResource(mModels.get(position).iconResourceId);
            temp.setText(mModels.get(position).tempS);
            rainPossibility.setText(mModels.get(position).rainPossibilityS);
        }

    }
}
