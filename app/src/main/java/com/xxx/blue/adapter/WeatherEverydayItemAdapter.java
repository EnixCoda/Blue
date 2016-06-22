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

import com.xxx.blue.model.WeatherEverydayItem;
import com.xxx.blue.R;

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

    public WeatherEverydayItemAdapter(Context mContext, List<WeatherEverydayItem> mModels) {
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
        @Bind(R.id.lowTemp)
        TextView lowTemp;

        public ItemViewHolder(Context context, int position) {
            super(context);
            View view = LayoutInflater.from(context).inflate(R.layout.weather_everyday_item, null);
            addView(view);
            ButterKnife.bind(this, view);
            week.setText(mModels.get(position).week);
            weatherIcon.setImageResource(mModels.get(position).iconResourceId);
            highTemp.setText(mModels.get(position).highTempS);
            lowTemp.setText(mModels.get(position).lowTempS);
        }
    }
}
