package com.xxx.blue.UI.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xxx.blue.HintModel;
import com.xxx.blue.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Burgess on 2016/6/14.
 */
public class LifeHintItemAdapter extends BaseAdapter{
    List<HintModel> mModels;
    Context mContext;
    GridView view;

    public LifeHintItemAdapter(Context mContext, List<HintModel> mModels) {
        this.mContext = mContext;
        this.mModels = mModels;
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
        ItemViewHolder holder = new ItemViewHolder(mContext);
        if (view != null) {
            int height = view.getHeight() / 3;
            int width = view.getWidth() / 3;
            ViewGroup.LayoutParams params = holder.mContainer.getLayoutParams();
            params.height = height;
            params.width = width;
            holder.setLayoutParams(params);
        }
        return holder;
    }

    public class ItemViewHolder extends FrameLayout{
        @Bind(R.id.vg_container)
        ViewGroup mContainer;
        @Bind(R.id.img_hint)
        ImageView imgHint;
        @Bind(R.id.tv_hint)
        TextView tvHint;
        @Bind(R.id.vg_hint_container)
        LinearLayout vgHintContainer;
        @Bind(R.id.tv_hint_detail)
        TextView tvHintDetail;

        public ItemViewHolder(Context context) {
            super(context);
            View view = LayoutInflater.from(context).inflate(R.layout.widget_grid_item, null);
            addView(view);
            ButterKnife.bind(this, view);
        }

        @OnClick(R.id.vg_container)
        public void onClick() {
            if (vgHintContainer.getVisibility() == VISIBLE){
                vgHintContainer.setVisibility(GONE);
                tvHintDetail.setVisibility(VISIBLE);
            }else {
                vgHintContainer.setVisibility(VISIBLE);
                tvHintDetail.setVisibility(GONE);
            }
        }
    }
}
