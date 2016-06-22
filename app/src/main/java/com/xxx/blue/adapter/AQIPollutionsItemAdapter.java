package com.xxx.blue.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.TextView;

import com.xxx.blue.R;
import com.xxx.blue.model.AQIPollutionItem;
import com.xxx.blue.model.WeatherEverydayItem;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Exin on 2016/6/22.
 */
public class AQIPollutionsItemAdapter extends BaseAdapter {
    List<AQIPollutionItem> mModels;
    Context mContext;
    GridView view;

    public AQIPollutionsItemAdapter(Context mContext, List<AQIPollutionItem> mModels) {
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
            int height = view.getHeight() / 3;
            int width = view.getWidth() / 2;
            ViewGroup.LayoutParams params = holder.mContainer.getLayoutParams();
            params.height = height;
            params.width = width;
            holder.setLayoutParams(params);
        }
        return holder;
    }

    public class ItemViewHolder extends FrameLayout {
        @Bind(R.id.aqipia_container)
        ViewGroup mContainer;
        @Bind(R.id.pollutionName)
        TextView pollutionName;
        @Bind(R.id.pollutionValue)
        TextView pollutionValue;
        @Bind(R.id.textView2)
        TextView pollutionUnit;

        public ItemViewHolder(Context context, int position) {
            super(context);
            View view = LayoutInflater.from(context).inflate(R.layout.pollution_grid_item, null);
            addView(view);
            ButterKnife.bind(this, view);
            pollutionName.setText(mModels.get(position).name);
            pollutionValue.setText(Integer.toString(mModels.get(position).value));
            pollutionUnit.setText(Html.fromHtml("μg/m<sup>3</sup>"));
        }
    }
}
