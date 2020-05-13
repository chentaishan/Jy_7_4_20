package com.example.geeknews.ui;

import android.app.ActionBar;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.WindowManager;

import com.example.geeknews.CalendarEvent;
import com.example.geeknews.R;
import com.example.geeknews.ui.base.BaseActivity;
import com.example.geeknews.utils.DateUtils;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import org.greenrobot.eventbus.EventBus;

import java.util.Calendar;

public class CalendarActivity extends BaseActivity {


    private Toolbar mToolbar;
    private MaterialCalendarView mCalender;

    @Override
    protected void initView() {
        super.initView();

//        getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary));


        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mCalender = (MaterialCalendarView) findViewById(R.id.calender);
        mToolbar.setTitle("日历");
        setSupportActionBar(mToolbar);
//        mToolbar.setBackgroundColor(getResources().getColor(R.color.colorAccent));
    }
    private CalendarDay calendarDay;
    @Override
    protected void initData() {

        mCalender.state().edit()
                .setFirstDayOfWeek(Calendar.MONDAY)
                .setMinimumDate(CalendarDay.from(2018,8,8))
                .setMaximumDate(CalendarDay.from(DateUtils.getCurrentYear(),DateUtils.getCurrentMonth(),DateUtils.getCurrentDay()))
                .setCalendarDisplayMode(CalendarMode.MONTHS)
                .commit();
        mCalender.setOnDateChangedListener(new OnDateSelectedListener() {


            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                calendarDay= date;
                //20200201

                //发送日期数据
                CalendarEvent calendarEvent = new CalendarEvent();
                // 填补月的数据
                String month = calendarDay.getMonth()+"";
                  month = month.length()==1? "0"+month:month;

                  //填补日期的数据
                String day = calendarDay.getDay()+"";
                day = day.length()==1? "0"+day:day;
                //20200511
                calendarEvent.date =calendarDay.getYear()+""+month+day;
                EventBus.getDefault().post(calendarEvent);
                //关闭页面
                finish();
            }
        });

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_calendar;
    }
}
