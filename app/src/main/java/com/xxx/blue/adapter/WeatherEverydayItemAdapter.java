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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xxx.blue.model.HintModel;
import com.xxx.blue.model.WeatherEverydayItem;
import com.xxx.blue.R;

import java.util.ArrayList;
import java.util.List;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 张丽娟 on 2016/6/17.
 */
public class WeatherEverydayItemAdapter extends BaseAdapter {
    List<WeatherEverydayItem> mModels;
    Context mContext;
    GridView view;
    List<String> Cweek = new ArrayList<String>();
    List<Bitmap> Cweathericon = new ArrayList<Bitmap>();
    Bitmap bmp;
    List<String> Chightemp = new ArrayList<String>();
    List<String> Clowtemp = new ArrayList<String>();

    public WeatherEverydayItemAdapter(Context mContext, List<WeatherEverydayItem> mModels) {
        this.mContext = mContext;
        this.mModels = mModels;
        Resources res = mContext.getResources();
        Cweek.add("周一");
        Cweek.add("周二");
        Cweek.add("周三");
        Cweek.add("周四");
        Cweek.add("周五");
        Cweek.add("周六");

        bmp = BitmapFactory.decodeResource(res, R.drawable.clouds);
        Cweathericon.add(bmp);
        bmp = BitmapFactory.decodeResource(res, R.drawable.rain);
        Cweathericon.add(bmp);
        bmp = BitmapFactory.decodeResource(res, R.drawable.rain);
        Cweathericon.add(bmp);
        bmp = BitmapFactory.decodeResource(res, R.drawable.clouds);
        Cweathericon.add(bmp);
        bmp = BitmapFactory.decodeResource(res, R.drawable.rain);
        Cweathericon.add(bmp);
        bmp = BitmapFactory.decodeResource(res, R.drawable.rain);
        Cweathericon.add(bmp);

        Chightemp.add("25℃");
        Chightemp.add("27℃");
        Chightemp.add("21℃");
        Chightemp.add("24℃");
        Chightemp.add("22℃");
        Chightemp.add("22℃");

        Clowtemp.add("19℃");
        Clowtemp.add("17℃");
        Clowtemp.add("17℃");
        Clowtemp.add("18℃");
        Clowtemp.add("18℃");
        Clowtemp.add("19℃");
    }
    public void setGridView(GridView view){
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
    public class ItemViewHolder extends FrameLayout{
        @Bind(R.id.wei_container)
        ViewGroup mContainer;
        @Bind(R.id.week)
        TextView week;
        @Bind(R.id.weathericon)
        ImageView weathericon;
        @Bind(R.id.hightemp)
        TextView hightemp;
        @Bind(R.id.lowtemp)
        TextView lowtemp;

        public ItemViewHolder(Context context, int position) {
            super(context);
            View view = LayoutInflater.from(context).inflate(R.layout.weather_everyday_item, null);
            addView(view);
            ButterKnife.bind(this, view);
            week.setText(Cweek.get(position));
            weathericon.setImageBitmap(Cweathericon.get(position));
            hightemp.setText(Chightemp.get(position));
            lowtemp.setText(Clowtemp.get(position));
        }

    }
}
