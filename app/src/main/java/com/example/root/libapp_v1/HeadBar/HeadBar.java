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

import com.beardedhen.androidbootstrap.FontAwesomeText;
import com.example.root.libapp_v1.R;

/**
 * Created by root on 15-4-1.
 */
public class HeadBar extends FrameLayout {

    private FontAwesomeText leftButton;
   // private Button rightButton;
    private FontAwesomeText rightButton;

    private TextView titleText;

    int sdk;
    public HeadBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        sdk = Build.VERSION.SDK_INT;
        LayoutInflater.from(context).inflate(R.layout.head_title, this);
        titleText = (TextView) findViewById(R.id.title_text);
        leftButton = (FontAwesomeText) findViewById(R.id.button_left);
        rightButton = (FontAwesomeText) findViewById(R.id.button_right);
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

    /**
     * @param icon : the awesome text icon which right button want to show
     */
    public void setRightButtonIcon(String icon) {
        rightButton.setIcon(icon);
    }
    /*
    * stop show right button
    * */
    public void setRightButtonNoused() {
        rightButton.setClickable(false);
        rightButton.setVisibility(View.GONE);
    }
    /*
    * set right button listener
    * */
    public void setRightButtonListener(OnClickListener l) {
        rightButton.setOnClickListener(l);
    }
}
