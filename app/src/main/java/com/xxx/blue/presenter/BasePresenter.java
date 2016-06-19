package com.xxx.blue.presenter;

import android.content.Context;

import com.xxx.blue.BaseView;

/**
 * Created by Burgess on 2016/6/18.
 */
public abstract class BasePresenter<V extends BaseView> {
    private V mView;

    public BasePresenter(V view) {
        this.mView = view;
    }

    public BasePresenter() {
    }

    public Context getContext(){
        return getView().getViewContext();
    }

    public void attachView(V view){
        mView = view;
    }

    public void detachView(){
        getView().detachView();
    }

    protected V getView() {
        if (mView == null) {
            throw new NullPointerException();
        }
        return mView;
    }
}
