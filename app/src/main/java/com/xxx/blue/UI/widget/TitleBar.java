package com.xxx.blue.UI.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shehabic.droppy.DroppyClickCallbackInterface;
import com.shehabic.droppy.DroppyMenuPopup;
import com.xxx.blue.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TitleBar extends FrameLayout {
    public static final boolean ON_LEFT = true;
    public static final boolean ON_RIGHT = false;

    @Bind(R.id.vg_root) ViewGroup mRoot;
    @Bind(R.id.vg_left) LinearLayout mLeftGroup;
    @Bind(R.id.vg_right) LinearLayout mRightGroup;
    @Bind(R.id.txt_title) TextView mTitle;
    @Bind(R.id.view_shadow) View mShadow;

    private List<TitleBarButton> mLeftList = new ArrayList<>();
    private List<TitleBarButton> mRightList = new ArrayList<>();

    public TitleBar(Context context) {
        this(context, null);
    }

    public TitleBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View view = LayoutInflater.from(context).inflate(R.layout.widget_title_bar, this);
        ButterKnife.bind(this, view);
    }

    @Override
    public void setAlpha(float alpha) {
        mRoot.getBackground().setAlpha((int) (alpha * 0xff));
        mTitle.setAlpha(alpha);
        mShadow.setAlpha(alpha);
        for (TitleBarButton button : mLeftList) {
            button.setAlpha(alpha);
        }
        for (TitleBarButton button : mRightList) {
            button.setAlpha(alpha);
        }
    }

    public void setTitle(int resId) {
        mTitle.setText(resId);
    }

    public void setTitle(CharSequence title) {
        mTitle.setText(title);
    }

    public void setTitleTextColor(int color) {
        mTitle.setTextColor(color);
    }

    @Override
    public void setBackgroundColor(int color) {
        mRoot.setBackgroundColor(color);
    }

    @Override
    public void setBackground(Drawable background) {
        mRoot.setBackgroundDrawable(background);
    }

    private void showBack(final Activity activity, boolean withPlaceholder, OnClickListener listener) {
        if (listener == null) {
            listener = new OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.finish();
                }
            };
        }
        if (withPlaceholder) {
            addButton(R.mipmap.ic_back_blue_24dp, R.mipmap.ic_back_white_24dp, listener, ON_LEFT);
        } else {
            addButton(R.mipmap.ic_back_blue_24dp, listener, ON_LEFT);
        }
    }

    public void bindActivity(Activity activity) {
        bindActivity(activity, true);
    }

    public void bindActivity(final Activity activity, boolean showBack) {
        bindActivity(activity, showBack, false, null);
    }

    public void bindActivity(final Activity activity, boolean showBack, OnClickListener listener) {
        bindActivity(activity, showBack, false, listener);
    }

    public void bindActivity(final Activity activity, boolean showBack, boolean withPlaceholder) {
        bindActivity(activity, showBack, withPlaceholder, null);
    }

    public void bindActivity(final Activity activity, boolean showBack, boolean withPlaceholder, OnClickListener listener) {
        setTitle(activity.getTitle());
        if (showBack) {
            showBack(activity, withPlaceholder, listener);
        }
    }

    public TitleBarButton addButton(int resId, OnClickListener onClickListener) {
        return addButton(resId, onClickListener, ON_RIGHT);
    }

    public TitleBarButton addButton(int resId, OnClickListener onClickListener, boolean onLeft) {
        TitleBarButton button = new TitleBarButton(getContext());
        button.setImageResource(resId);
        button.setOnClickListener(onClickListener);

        if (onLeft) {
            addLeft(button);
        } else {
            addRight(button);
        }

        return button;
    }

    public TitleBarButton addButton(int resId, int placeholderId, OnClickListener onClickListener) {
        return addButton(resId, placeholderId, onClickListener, ON_RIGHT);
    }

    public TitleBarButton addButton(int resId, int placeholderId, OnClickListener onClickListener, boolean onLeft) {
        TitleBarButton button = new TitleBarButton(getContext());
        button.setImageResource(resId);
        button.setImagePlaceholder(placeholderId);
        button.setOnClickListener(onClickListener);

        if (onLeft) {
            addLeft(button);
        } else {
            addRight(button);
        }
        button.setAlpha(0);

        return button;
    }

    public TitleBarButton addText(int resId, OnClickListener onClickListener) {
        return addText(getResources().getText(resId), onClickListener);
    }

    public TitleBarButton addText(int resId, OnClickListener onClickListener, boolean onLeft) {
        return addText(getResources().getText(resId), onClickListener, onLeft);
    }

    public TitleBarButton addText(CharSequence text, OnClickListener onClickListener) {
        return addText(text, onClickListener, ON_RIGHT);
    }

    public TitleBarButton addText(CharSequence text, OnClickListener onClickListener, boolean onLeft) {
        TitleBarButton button = new TitleBarButton(getContext());
        button.setText(text);
        button.setTextPlaceholder(text);
        button.setOnClickListener(onClickListener);
        if (onLeft) {
            addLeft(button);
        } else {
            addRight(button);
        }

        return button;
    }

    public TitleBarButton addMenu(int imageResId, int menuResId, DroppyClickCallbackInterface onClickCallback) {
        final DroppyMenuPopup menu = new DroppyMenuPopup.Builder(getContext(), mRightGroup)
                .triggerOnAnchorClick(false)
                .fromMenu(menuResId)
                .setOnClick(onClickCallback)
                .build();
        return addButton(imageResId, new OnClickListener() {
            @Override
            public void onClick(View v) {
                menu.show();
            }
        }, ON_RIGHT);
    }

    public TitleBarButton addMenu(int imageResId, int placeholderId, int menuResId, DroppyClickCallbackInterface onClickCallback) {
        final DroppyMenuPopup menu = new DroppyMenuPopup.Builder(getContext(), mRightGroup)
                .triggerOnAnchorClick(false)
                .fromMenu(menuResId)
                .setOnClick(onClickCallback)
                .build();
        return addButton(imageResId, placeholderId, new OnClickListener() {
            @Override
            public void onClick(View v) {
                menu.show();
            }
        }, ON_RIGHT);
    }


    private void addLeft(TitleBarButton button) {
        mLeftGroup.addView(button);
        mLeftList.add(button);
    }

    private void addRight(TitleBarButton button) {
        mRightGroup.addView(button);
        mRightList.add(button);
    }

    public void removeRightItems() {
        mRightGroup.removeAllViews();
        mRightList.clear();
    }

    public void removeLeftItems() {
        mLeftGroup.removeAllViews();
        mLeftList.clear();
    }
}
