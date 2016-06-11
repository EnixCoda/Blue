package com.xxx.blue.UI.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import com.xxx.blue.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Burgess on 2016/6/11.
 */
public class LocationDialog extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Builder builder = new Builder(getContext());
        return builder.show();
    }

    public class Builder extends AlertDialog.Builder {
        @Bind(R.id.search_view)
        SearchView mSearchView;
        @Bind(R.id.location_recyclerView)
        RecyclerView mLocationRecyclerView;
        LocationAdapter mAdapter;

        public Builder(Context context) {
            super(context);
            View viewGroup = LayoutInflater.from(context).inflate(R.layout.dialog_location, null, false);
            ButterKnife.bind(this, viewGroup);
            
            setView(viewGroup);
            init();
        }

        private void init() {
            String[] provinces = getContext().getResources().getStringArray(R.array.string_province);
            //init RecyclerView
            mAdapter = new LocationAdapter(new ArrayList<>(Arrays.asList(provinces)));
            mLocationRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            mLocationRecyclerView.setAdapter(mAdapter);
            //mSearchView
            mSearchView.setIconifiedByDefault(false);
            mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    mAdapter.setFilter(newText);
                    return false;
                }
            });
        }

        public void setListener(OnLocationClickListener mListener) {
            mAdapter.setListener(mListener);
        }

        @Override
        public AlertDialog create() {
            return super.create();
        }
    }

    class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.LocationViewHolder>{
        private List<String> mList;
        private List<String> mFilterList;
        private OnLocationClickListener mListener;

        public LocationAdapter(List<String> mList) {
            this.mList = mList;
            mFilterList = new ArrayList<>(mList);
        }

        @Override
        public LocationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.list_item_location, null);
            return new LocationViewHolder(view);
        }

        @Override
        public void onBindViewHolder(LocationViewHolder holder, int position) {
            holder.mLocation.setText(mFilterList.get(position));
        }

        public void setFilter(String text){
            mFilterList.clear();
            for (String str : mList){
                if (str.contains(text)){
                    mFilterList.add(str);
                }
            }
            reset(mFilterList);
        }

        @Override
        public int getItemCount() {
            return mFilterList.size();
        }

        public void reset(List<String> mList) {
            mFilterList = mList;
            notifyDataSetChanged();
        }

        public void setListener(OnLocationClickListener mListener) {
            this.mListener = mListener;
        }

        class LocationViewHolder extends RecyclerView.ViewHolder{
            @Bind(R.id.tv_location)
            TextView mLocation;

            @OnClick(R.id.tv_location)
            void onLocationClick(){
                if (null != mListener){
                    mListener.onClick();
                }
            }

            public LocationViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }
    }

    public interface OnLocationClickListener{
        void onClick();
    }
}
