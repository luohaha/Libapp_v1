package com.example.root.libapp_v1.HeadBar;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.root.libapp_v1.R;

/**
 * Created by root on 15-4-1.
 */
public class HeadBar extends FrameLayout {

    private Button leftButton;
    private Button rightButton;

    private TextView titleText;

    int sdk;
    public HeadBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        sdk = Build.VERSION.SDK_INT;
        LayoutInflater.from(context).inflate(R.layout.head_title, this);
        titleText = (TextView) findViewById(R.id.title_text);
        leftButton = (Button) findViewById(R.id.button_left);
        rightButton = (Button) findViewById(R.id.button_right);
        leftButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity) getContext()).finish();
            }
        });
    }

    public void setTitleText(String text) {
        titleText.setText(text);
    }

    //class for setting left button for topbar
    public void setLeftButtonText(String text) {
        leftButton.setText(text);
    }
    
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void setLeftButtonBackground(Drawable drawable) {
        if (sdk < Build.VERSION_CODES.JELLY_BEAN) {
            leftButton.setBackgroundDrawable(drawable);
        }
        else {
            leftButton.setBackground(drawable);
        }
    }
    public void setLeftButtonNoused() {
        leftButton.setClickable(false);
        leftButton.setVisibility(View.GONE);
    }
    public void setLeftButtonListener(OnClickListener l) {
        leftButton.setOnClickListener(l);
    }

    //class for setting right button in topbar
    public void setRightButtonText(String text) {
        rightButton.setText(text);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void setRightButtonBackground(Drawable drawable) {
        if (sdk < Build.VERSION_CODES.JELLY_BEAN) {
            rightButton.setBackgroundDrawable(drawable);
        }
        else {
            rightButton.setBackground(drawable);
        }
    }

    public void setRightButtonNoused() {
        rightButton.setClickable(false);
        rightButton.setVisibility(View.GONE);
    }
    public void setRightButtonListener(OnClickListener l) {
        rightButton.setOnClickListener(l);
    }
}
