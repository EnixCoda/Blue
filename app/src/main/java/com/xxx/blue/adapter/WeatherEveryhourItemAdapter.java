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
import com.xxx.blue.model.WeatherEverydayItem;
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
    List<String> Chour = new ArrayList<String>();
    List<Bitmap> Chourweathericon = new ArrayList<Bitmap>();
    Bitmap bmp;
    List<String> Ctemp = new ArrayList<String>();
    List<Bitmap> Cwind = new ArrayList<Bitmap>();
    List<Bitmap> Crainnum = new ArrayList<Bitmap>();
    List<String> Crainpercent = new ArrayList<String>();

    public WeatherEveryhourItemAdapter(Context mContext, List<WeatherEveryhourItem> mModels) {
        this.mContext = mContext;
        this.mModels = mModels;
        Resources res = mContext.getResources();
        Chour.add("01:00");
        Chour.add("02:00");
        Chour.add("03:00");
        Chour.add("04:00");
        Chour.add("05:00");
        Chour.add("06:00");
        Chour.add("07:00");
        Chour.add("08:00");
        Chour.add("09:00");
        Chour.add("10:00");
        Chour.add("11:00");
        Chour.add("12:00");

        bmp = BitmapFactory.decodeResource(res, R.drawable.nightcloud);
        Chourweathericon.add(bmp);
        bmp = BitmapFactory.decodeResource(res, R.drawable.nightclouds);
        Chourweathericon.add(bmp);
        bmp = BitmapFactory.decodeResource(res, R.drawable.nightclouds);
        Chourweathericon.add(bmp);
        bmp = BitmapFactory.decodeResource(res, R.drawable.clouds);
        Chourweathericon.add(bmp);
        bmp = BitmapFactory.decodeResource(res, R.drawable.clouds);
        Chourweathericon.add(bmp);
        bmp = BitmapFactory.decodeResource(res, R.drawable.clouds);
        Chourweathericon.add(bmp);
        bmp = BitmapFactory.decodeResource(res, R.drawable.nightcloud);
        Chourweathericon.add(bmp);
        bmp = BitmapFactory.decodeResource(res, R.drawable.nightclouds);
        Chourweathericon.add(bmp);
        bmp = BitmapFactory.decodeResource(res, R.drawable.nightclouds);
        Chourweathericon.add(bmp);
        bmp = BitmapFactory.decodeResource(res, R.drawable.clouds);
        Chourweathericon.add(bmp);
        bmp = BitmapFactory.decodeResource(res, R.drawable.clouds);
        Chourweathericon.add(bmp);
        bmp = BitmapFactory.decodeResource(res, R.drawable.clouds);
        Chourweathericon.add(bmp);

        Ctemp.add("19℃");
        Ctemp.add("19℃");
        Ctemp.add("20℃");
        Ctemp.add("20℃");
        Ctemp.add("19℃");
        Ctemp.add("20℃");
        Ctemp.add("19℃");
        Ctemp.add("19℃");
        Ctemp.add("20℃");
        Ctemp.add("20℃");
        Ctemp.add("19℃");
        Ctemp.add("20℃");

        bmp = BitmapFactory.decodeResource(res, R.drawable.wind);
        Cwind.add(bmp);
        Cwind.add(bmp);
        Cwind.add(bmp);
        Cwind.add(bmp);
        Cwind.add(bmp);
        Cwind.add(bmp);
        Cwind.add(bmp);
        Cwind.add(bmp);
        Cwind.add(bmp);
        Cwind.add(bmp);
        Cwind.add(bmp);
        Cwind.add(bmp);

        bmp = BitmapFactory.decodeResource(res, R.drawable.rainnum60);
        Crainnum.add(bmp);
        bmp = BitmapFactory.decodeResource(res, R.drawable.rainnum80);
        Crainnum.add(bmp);
        bmp = BitmapFactory.decodeResource(res, R.drawable.rainnum30);
        Crainnum.add(bmp);
        bmp = BitmapFactory.decodeResource(res, R.drawable.rainnum20);
        Crainnum.add(bmp);
        bmp = BitmapFactory.decodeResource(res, R.drawable.rainnum0);
        Crainnum.add(bmp);
        bmp = BitmapFactory.decodeResource(res, R.drawable.rainnum0);
        Crainnum.add(bmp);
        bmp = BitmapFactory.decodeResource(res, R.drawable.rainnum60);
        Crainnum.add(bmp);
        bmp = BitmapFactory.decodeResource(res, R.drawable.rainnum80);
        Crainnum.add(bmp);
        bmp = BitmapFactory.decodeResource(res, R.drawable.rainnum30);
        Crainnum.add(bmp);
        bmp = BitmapFactory.decodeResource(res, R.drawable.rainnum20);
        Crainnum.add(bmp);
        bmp = BitmapFactory.decodeResource(res, R.drawable.rainnum0);
        Crainnum.add(bmp);
        bmp = BitmapFactory.decodeResource(res, R.drawable.rainnum0);
        Crainnum.add(bmp);

        Crainpercent.add("60%");
        Crainpercent.add("80%");
        Crainpercent.add("30%");
        Crainpercent.add("20%");
        Crainpercent.add("0%");
        Crainpercent.add("0%");
        Crainpercent.add("60%");
        Crainpercent.add("80%");
        Crainpercent.add("30%");
        Crainpercent.add("20%");
        Crainpercent.add("0%");
        Crainpercent.add("0%");

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
            int width = view.getWidth() / 12;
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
        @Bind(R.id.hour)
        TextView hour;
        @Bind(R.id.hourweathericon)
        ImageView hourweathericon;
        @Bind(R.id.temp)
        TextView temp;
        @Bind(R.id.wind)
        ImageView wind;
        @Bind(R.id.rainnum)
        ImageView rainnum;
        @Bind(R.id.rainpercent)
        TextView rainpercent;

        public ItemViewHolder(Context context, int position) {
            super(context);
            View view = LayoutInflater.from(context).inflate(R.layout.weather_everyhour_item, null);
            addView(view);
            ButterKnife.bind(this, view);
            hour.setText(Chour.get(position));
            hourweathericon.setImageBitmap(Chourweathericon.get(position));
            temp.setText(Ctemp.get(position));
            wind.setImageBitmap(Cwind.get(position));
            rainnum.setImageBitmap(Crainnum.get(position));
            rainpercent.setText(Crainpercent.get(position));
        }

    }
}
