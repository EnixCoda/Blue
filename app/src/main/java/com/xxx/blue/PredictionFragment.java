package com.xxx.blue;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xxx.blue.R;

import butterknife.ButterKnife;

/**
 * Created by Burgess on 2016/6/11.
 */
public class PredictionFragment extends Fragment{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View viewGroup = inflater.inflate(R.layout.viewstub_predictions, container, false);
        ButterKnife.bind(viewGroup);
        return viewGroup;
    }
}
