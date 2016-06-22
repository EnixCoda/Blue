package com.xxx.blue.model;

/**
 * Created by Burgess on 2016/6/14.
 */
public class HintModel {
    private int mImageId;
    private int suggestCode;
    private String mHint;
    private String mHintDetail;

    public HintModel() {
    }

    public HintModel(String mHint, int imageId, int suggestCode) {
        mImageId = imageId;
        this.mHint = mHint;
        this.suggestCode = suggestCode;
    }

    public HintModel(String mHint) {
        this.mHint = mHint;
    }

    public String getmHint() {
        return mHint;
    }

    public void setmHint(String mHint) {
        this.mHint = mHint;
    }

    public String getmHintDetail() {
        return mHintDetail;
    }

    public void setmHintDetail(String mHintDetail) {
        this.mHintDetail = mHintDetail;
    }

    public int getmImageId() {
        return mImageId;
    }

    public int getCode() {
        return suggestCode;
    }
}
