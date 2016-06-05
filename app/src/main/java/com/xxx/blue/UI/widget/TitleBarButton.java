package com.xxx.blue.UI.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.xxx.blue.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TitleBarButton extends FrameLayout {
    @Bind(R.id.vg_container) ViewGroup mContainer;
    @Bind(R.id.img_image) ImageView mImageView;
    @Bind(R.id.img_placeholder) ImageView mImagePlaceholder;
    @Bind(R.id.txt_text) TextView mTextView;
    @Bind(R.id.txt_placeholder) TextView mTextPlaceholder;

    public TitleBarButton(Context context) {
        super(context);
        init(null);
    }

    public TitleBarButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public TitleBarButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        LayoutInflater.from(getContext()).inflate(R.layout.widget_title_bar_button, this);
        ButterKnife.bind(this);

        if (attrs != null) {
            TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.TitleBarButton);

            for (int i = 0; i < array.getIndexCount(); i++) {
                switch (array.getIndex(i)) {
                    case R.styleable.TitleBarButton_tbb_text:
                        setText(array.getString(R.styleable.TitleBarButton_tbb_text));
                        break;
                    case R.styleable.TitleBarButton_tbb_drawable:
                        setImageResource(array.getResourceId(R.styleable.TitleBarButton_tbb_drawable, 0));
                        break;
                }
            }
        }
    }

    @Override
    public void setAlpha(float alpha) {
        mImageView.setAlpha(alpha);
        mTextView.setAlpha(alpha);
        mImagePlaceholder.setAlpha(1 - alpha);
        mTextPlaceholder.setAlpha(1 - alpha);
    }

    public void setText(int textId) {
        mImageView.setVisibility(GONE);
        mTextView.setVisibility(VISIBLE);
        mTextView.setText(textId);
    }

    public void setText(CharSequence text) {
        mImageView.setVisibility(GONE);
        mTextView.setVisibility(VISIBLE);
        mTextView.setText(text);
    }

    public void setTextPlaceholder(int resId) {
        mImagePlaceholder.setVisibility(GONE);
        mTextPlaceholder.setVisibility(VISIBLE);
        mTextPlaceholder.setText(resId);
    }

    public void setTextPlaceholder(CharSequence text) {
        mImagePlaceholder.setVisibility(GONE);
        mTextPlaceholder.setVisibility(VISIBLE);
        mTextPlaceholder.setText(text);
    }

    public void setTextColor(int color) {
        mTextView.setTextColor(color);
    }

    public void setImageResource(int resId) {
        mTextView.setVisibility(GONE);
        mImageView.setVisibility(VISIBLE);
        mImageView.setImageResource(resId);
    }

    public void setImagePlaceholder(int resId) {
        mTextPlaceholder.setVisibility(GONE);
        mImagePlaceholder.setVisibility(VISIBLE);
        mImagePlaceholder.setImageResource(resId);
    }

    @Override
    public boolean isEnabled() {
        return mContainer.isEnabled();
    }

    @Override
    public void setEnabled(boolean enabled) {
        mContainer.setEnabled(enabled);
    }

    @Override
    public void setOnClickListener(OnClickListener l) {
        mContainer.setOnClickListener(l);
    }
}
