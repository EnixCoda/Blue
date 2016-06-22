package com.xxx.blue.presenter;

import com.xxx.blue.BaseView;

import utility.ChineseToPinyin;
import getData.Day;
import getData.FetchData;

/**
 * Created by Burgess on 2016/6/18.
 */
public class MainPresenter extends BasePresenter<MainPresenter.MainView> {
    public void loadCurrentData(String location){
        String localLocation = ChineseToPinyin.getPinYin(location);
        FetchData fetchData = new FetchData(new FetchData.AsyncResponse() {
            @Override
            public void processFinish(Day day) {
                getView().showCurrentData(day);
            }
        });
        fetchData.execute(localLocation);
    }

    public interface MainView extends BaseView{
        void showCurrentData(Day data);
    }
}
