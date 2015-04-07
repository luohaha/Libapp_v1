package com.example.root.libapp_v1.LyxListView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View.*;
import android.widget.LinearLayout;


/**
 * Author : Yixin Luo
 * Date : 2015-3-7
 *  finish the function of LyxListView, it contains push down refresh function.
 *  ps, where?
 */
public class LyxListView extends LinearLayout implements OnTouchListener {
    /**
    * the status when you pull down
    * */
    private static final int STATUS_PULL_TO_REFRESH = 0;
    /**
    *the status when you release and it will refresh
    * */
    private static final int STATUS_RELEASE_TO_REFRESH = 1;
    /**
    *refreshing now!!
    * */
    private static final int STATUS_REFRESHING = 2;
    /**
    * finish refresh
    */
    private static final int STATUS_REFRESH_FINISHED = 3;
    /**
     * the speed of scroll
     */
    public static final int SCROLL_SPEED = -20;

    /**
     *
     */
    public static final long ONE_MINUTE = 60 * 1000;

    /**
     * 一小时的毫秒值，用于判断上次的更新时间
     */
    public static final long ONE_HOUR = 60 * ONE_MINUTE;

    /**
     * 一天的毫秒值，用于判断上次的更新时间
     */
    public static final long ONE_DAY = 24 * ONE_HOUR;

    /**
     * 一月的毫秒值，用于判断上次的更新时间
     */
    public static final long ONE_MONTH = 30 * ONE_DAY;

    /**
     * 一年的毫秒值，用于判断上次的更新时间
     */
    public static final long ONE_YEAR = 12 * ONE_MONTH;
    /**
     * interface of pull to refresh listener
     * */
     
     public LyxListView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }
}
