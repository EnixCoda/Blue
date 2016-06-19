package com.xxx.blue.UI.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.SearchView;
import android.widget.TextView;

import com.xxx.blue.R;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import utility.QueryLocation;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Burgess on 2016/6/11.
 */
public class LocationDialog extends DialogFragment {
    OnLocationClickListener mListener;
    QueryLocation mQueryLocation;
    Builder builder;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mQueryLocation = new QueryLocation(getActivity());
        builder = new Builder(getContext());
        builder.setListener(mListener);
        return builder.show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        this.getDialog().setOnKeyListener(new DialogInterface.OnKeyListener()
        {
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event){
                if (keyCode == KeyEvent.KEYCODE_BACK){
                    builder.adjustContent();
                }
                return false;
            }
        });
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public void setListener(OnLocationClickListener mListener) {
        this.mListener = mListener;
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
            String[] recommendLocation = mQueryLocation.query("");
            //String[] provinces = getContext().getResources().getStringArray(R.array.string_province);
            //init RecyclerView
            mAdapter = new LocationAdapter(new ArrayList<>(Arrays.asList(recommendLocation)));
            mLocationRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            mLocationRecyclerView.setAdapter(mAdapter);
            mLocationRecyclerView.requestFocus();
            //mSearchView
            mSearchView.setIconifiedByDefault(false);
            try {
                //获得文本编辑框mSearchSrcTextView
                Field f = mSearchView.getClass().getDeclaredField("mSearchSrcTextView");
                f.setAccessible(true);
                AutoCompleteTextView at = (AutoCompleteTextView) f.get(mSearchView);
                at.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (!hasFocus){
                            ViewGroup.LayoutParams params = mLocationRecyclerView.getLayoutParams();
                            params.height = 1600;
                            mLocationRecyclerView.setLayoutParams(params);
                        }
                    }
                });
                at.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ViewGroup.LayoutParams params = mLocationRecyclerView.getLayoutParams();
                        params.height = 600;
                        mLocationRecyclerView.setLayoutParams(params);
                    }
                });
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
            mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    //mAdapter.setFilter(newText);
                    String[] searchResult = mQueryLocation.query(newText);
                    mAdapter.resetAndNotify(Arrays.asList(searchResult));
                    return false;
                }
            });
        }

        private boolean isKeyboardShown(View rootView) {
            final int softKeyboardHeight = 100;
            Rect r = new Rect();
            rootView.getWindowVisibleDisplayFrame(r);
            DisplayMetrics dm = rootView.getResources().getDisplayMetrics();
            int heightDiff = rootView.getBottom() - r.bottom;
            return heightDiff > softKeyboardHeight * dm.density;
        }

        public void setListener(OnLocationClickListener mListener) {
            mAdapter.setListener(mListener);
        }

        @Override
        public AlertDialog create() {
            return super.create();
        }

        public void adjustContent() {
            ViewGroup.LayoutParams params = mLocationRecyclerView.getLayoutParams();
            params.height = 1600;
            mLocationRecyclerView.setLayoutParams(params);
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
            String [] list = mQueryLocation.query(text);
            for (String str : list){
                mFilterList.add(str);
            }
            resetAndNotify(mFilterList);
        }

        @Override
        public int getItemCount() {
            return mFilterList.size();
        }

        public void resetAndNotify(List<String> mList) {
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
                    mListener.onClick(mLocation.getText().toString());
                }
            }

            public LocationViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }
    }

    public interface OnLocationClickListener{
        void onClick(String text);
    }
}
